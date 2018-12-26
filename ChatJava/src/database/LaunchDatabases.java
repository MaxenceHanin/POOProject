package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class LaunchDatabases {
    public LaunchDatabases() {
        LaunchUserDB();
        LaunchMessageDB();
    }

    private void LaunchMessageDB() {
        String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
        String userDB = "pitou";
        String pass = "pwd";
        try {
            Connection myConn = DriverManager.getConnection(dataBaseURL,userDB,pass);

            Statement myStmt = myConn.createStatement();

            String update = "CREATE TABLE Message(" +
                    "idMsg SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "time TIME NOT NULL,"+
                    "user_src SMALLINT UNSIGNED NOT NULL REFERENCES User(id)," +
                    "user_dest SMALLINT UNSIGNED NOT NULL REFERENCES User(id)," +
                    "text TEXT,"+
                    "PRIMARY KEY (idMsg)" +
                    ")";

            myStmt.executeUpdate(update);
            System.out.println("Message Database created");

        } catch(SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private void LaunchUserDB() {
        String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
        String userDB = "pitou";
        String pass = "pwd";
        try {
            Connection myConn = DriverManager.getConnection(dataBaseURL,userDB,pass);

            Statement myStmt = myConn.createStatement();

            String update = "CREATE TABLE User(" +
                    "idUsr SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT," +
                    "nickname CHAR(60) NOT NULL," +
                    "PRIMARY KEY (idUsr)" +
                    ")";

            myStmt.executeUpdate(update);
            System.out.println("User Database created");

        } catch(SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

}
