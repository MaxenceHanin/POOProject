package Database;
import java.sql
import java.sql.DriverManager;*;

public class Access {
String dataBaseURL = "jdbc:mysql://localhost:3306/demo";
String user = "...";
String pass = "...";

Connection myConn = DriverManager.getConnection(dataBaseURL,user,pass);


}
