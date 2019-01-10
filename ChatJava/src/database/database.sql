DELIMITER ;

SELECT 'Drop existing entries' AS 'Message to print';
DROP TABLE IF EXISTS Message CASCADE;
DROP TABLE IF EXISTS User CASCADE;

DROP PROCEDURE IF EXISTS test;

SELECT 'Creating tables' AS 'Message to print';
CREATE TABLE User(idUsr SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                  nickname CHAR(60) NOT NULL,
                  PRIMARY KEY (idUsr)
                  ) ENGINE=InnoDB;

CREATE TABLE Message(idMsg SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                    time TIME NOT NULL,
                    user_src SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                    user_dest SMALLINT UNSIGNED NOT NULL REFERENCES User(id),
                    text TEXT,
                    PRIMARY KEY (idMsg)
                    ) ENGINE=InnoDB;

SELECT 'Creating procedures' AS 'Message to print';

DELIMITER //
CREATE PROCEDURE insertMessage (IN srcNickname TEXT,
                                IN destNickname TEXT,
                                IN InTime TIME,
                                IN InText TEXT)
  BEGIN
    SELECT idUsr FROM User where (nickname = srcNickname) AS srcUserID;
    IF exists() then
    INSERT INTO Message (time,user_src,user_dest,text) VALUES (8);
  END //

DELIMITER ;