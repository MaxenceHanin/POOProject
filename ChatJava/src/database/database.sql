DELIMITER ;

DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Conversations;
DROP TABLE IF EXISTS Conv02;

DROP PROCEDURE IF EXISTS insertUser;
DROP PROCEDURE IF EXISTS createNewConversation;
DROP PROCEDURE IF EXISTS insertMessage;

SELECT 'Creating tables' AS 'Message to print';
CREATE TABLE User(idUsr SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                  nickname CHAR(60) NOT NULL,
                  PRIMARY KEY (idUsr)
                  ) ENGINE=InnoDB;

CREATE TABLE Conversations(idConvo SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                            user_src SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                            user_dest SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                            tbl_name CHAR(60) NOT NULL,
                            PRIMARY KEY (idConvo)
                            ) ENGINE=InnoDB;

SELECT 'Creating procedures' AS 'Message to print';

DELIMITER //
CREATE PROCEDURE insertUser (IN UsrNickname TEXT)
  BEGIN
    IF NOT EXISTS(SELECT * FROM User where (nickname = UsrNickname)) THEN
      INSERT INTO User (nickname) VALUES (UsrNickname);
    END IF;
  END //




CREATE PROCEDURE createNewConversation (IN srcNickname TEXT,
                                        IN destNickname TEXT,
                                        IN tblname CHAR(60))
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



CREATE PROCEDURE insertMessage (IN ConvNum CHAR(60),
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