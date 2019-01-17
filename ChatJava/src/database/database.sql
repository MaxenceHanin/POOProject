DELIMITER ;

DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Conversations;
DROP TABLE IF EXISTS Conv02;
DROP TABLE IF EXISTS MaxouPitou;

DROP PROCEDURE IF EXISTS insertUser;
DROP PROCEDURE IF EXISTS createNewConversation;
DROP PROCEDURE IF EXISTS insertMessage;
DROP PROCEDURE IF EXISTS setUserConnected;
DROP PROCEDURE IF EXISTS setUserDisconnected;
DROP PROCEDURE IF EXISTS userAlreadyExists;
DROP PROCEDURE IF EXISTS userIsConnected;
DROP PROCEDURE IF EXISTS databaseAlreadyExists;

SELECT 'Creating tables' AS 'Message to print';
CREATE TABLE User(idUsr SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                  nickname CHAR(60) NOT NULL,
                  connected BIT, /*1 is connected, 0 is disconnected*/
                  PRIMARY KEY (idUsr)
                  ) ENGINE=InnoDB;

CREATE TABLE Conversations(idConvo SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                            user_src SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                            user_dest SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                            tbl_name CHAR(120) NOT NULL,
                            PRIMARY KEY (idConvo)
                            ) ENGINE=InnoDB;

SELECT 'Creating procedures' AS 'Message to print';

DELIMITER //
CREATE PROCEDURE insertUser (IN UsrNickname TEXT)
  BEGIN
    IF NOT EXISTS(SELECT * FROM User where (nickname = UsrNickname)) THEN
      INSERT INTO User (nickname,connected) VALUES (UsrNickname,1);
    END IF;
  END //

CREATE PROCEDURE setUserConnected (IN UsrNickname TEXT)
  BEGIN
  DECLARE targetid SMALLINT UNSIGNED;
    IF NOT EXISTS(SELECT * FROM User where (nickname = UsrNickname)) THEN
      CALL insertUser(UsrNickname);
    ELSE
      SET targetid = (SELECT idUsr FROM User WHERE (nickname = UsrNickname));
      UPDATE User SET connected=1 WHERE idUsr=targetid;
    END IF;
  END //

  CREATE PROCEDURE setUserDisconnected (IN UsrNickname TEXT)
  BEGIN
  DECLARE targetid SMALLINT UNSIGNED;
    IF NOT EXISTS(SELECT * FROM User where (nickname = UsrNickname)) THEN
      CALL insertUser(UsrNickname);
    END IF;
    SET targetid = (SELECT idUsr FROM User WHERE (nickname = UsrNickname));
    UPDATE User SET connected=0 WHERE idUsr=targetid;

  END //


CREATE PROCEDURE createNewConversation (IN srcNickname TEXT,
                                        IN destNickname TEXT,
                                        IN tblname CHAR(120))
  BEGIN
  DECLARE srcUser SMALLINT UNSIGNED;
  DECLARE destUser SMALLINT UNSIGNED;

  IF NOT EXISTS(SELECT idUsr FROM User WHERE (nickname = srcNickname)) THEN
    CALL insertUser(srcNickname);
  END IF;
  SET srcUser = (SELECT idUsr FROM User WHERE (nickname = srcNickname));

  IF NOT EXISTS(SELECT * FROM User WHERE (nickname = destNickname)) THEN
    CALL insertUser(destNickname);
  END IF;
  SET destUser = (SELECT idUsr FROM User WHERE (nickname = destNickname));



  SET @tableName = tblName;
  SET @q = CONCAT('CREATE TABLE `', @tablename, '` (idMsg SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                    conv SMALLINT UNSIGNED NOT NULL REFERENCES Conversations(id),
                    user_src SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                    snick CHAR(60) NOT NULL,
                    user_dest SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                    dnick CHAR(60) NOT NULL,
                    time TIME NOT NULL,
                    text TEXT,
                    PRIMARY KEY (idMsg)
                    ) ENGINE=InnoDB;');
  PREPARE stmt FROM @q;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;

  INSERT INTO Conversations (user_src, user_dest, tbl_name) VALUES (srcUser, destUser, tblname);

  END //



CREATE PROCEDURE insertMessage (IN ConvNum CHAR(120),
                                IN srcNick CHAR(60),
                                IN destNick CHAR(60),
                                IN InTime TIME,
                                IN InText TEXT)
BEGIN
  DECLARE srcUser SMALLINT UNSIGNED;
  DECLARE destUser SMALLINT UNSIGNED;
  DECLARE Convid SMALLINT UNSIGNED;

  IF NOT EXISTS(SELECT idConvo FROM Conversations WHERE (tbl_name = ConvNum)) THEN
    SELECT 'On rentre dans le if pour creer une nouvelle convo' AS 'Message to print';
    CALL createNewConversation(srcNick, destNick, ConvNum);
  END IF;

  SET Convid = (SELECT idConvo FROM Conversations WHERE (tbl_name = ConvNum));
  SET srcUser = (SELECT idUsr FROM User WHERE (nickname = srcNick));
  SET destUser = (SELECT idUsr FROM User WHERE (nickname = destNick));


  SET @tableName = ConvNum;
  SET @q = CONCAT('INSERT INTO `', @tablename, '` (conv,user_src,snick,user_dest,dnick,time,text) VALUES (',Convid,',',srcUser,',"',srcNick,'",',destUser,',"',destNick,'","',InTime,'","',InText,'");');
  PREPARE stmt FROM @q;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;

  END //

SELECT 'Creating functions' AS 'Message to print';

CREATE PROCEDURE userAlreadyExists (IN nick CHAR(60),
                                  OUT userExists BIT)
BEGIN
  IF EXISTS (SELECT * FROM User WHERE (nickname = nick)) THEN
    SET userExists = 1;
  ELSE
    SET userExists = 0;
  END IF;
END //

CREATE PROCEDURE userIsConnected (IN nick CHAR(60),
                                  OUT isConnected SMALLINT)
BEGIN
  IF EXISTS (SELECT idUsr FROM User WHERE ((nickname = nick) AND (connected = 1))) THEN
    SET isConnected = 1;
  ELSE
    SET isConnected = 0;
  END IF;
END //

CREATE PROCEDURE databaseAlreadyExists (IN nick1 CHAR(60),
                                        IN nick2 CHAR(60),
                                        OUT conv_name CHAR(120))

BEGIN
  DECLARE temp CHAR(120);
  DECLARE idUser1 SMALLINT UNSIGNED;
  DECLARE idUser2 SMALLINT UNSIGNED;

  SET idUser1 = (SELECT idUsr FROM User WHERE (nickname = nick1));
  SET idUser2 = (SELECT idUsr FROM User WHERE (nickname = nick2));

  IF EXISTS (SELECT tbl_name FROM Conversations WHERE (((user_src = idUser1) AND (user_dest = idUser2)) OR ((user_src = idUser2) AND (user_dest = idUser1))) ) THEN
    SET conv_name = (SELECT tbl_name FROM Conversations WHERE (((user_src = idUser1) AND (user_dest = idUser2)) OR ((user_src = idUser2) AND (user_dest = idUser1))) );
  ELSE
    SET temp = CONCAT(nick1,nick2);
    CALL createNewConversation(nick1,nick2,temp);
    SET conv_name = temp;
  END IF;
END //

/*
CREATE FUNCTION getUser (IN idUser SMALLINT)
  RETURNS CHAR(60) DETERMINISTIC
  BEGIN
    DECLARE nick CHAR(60);
    SET @nick = (SELECT nickname FROM User WHERE (idUsr = idUser));
    RETURN @nick;
  END //
*/


DELIMITER ;