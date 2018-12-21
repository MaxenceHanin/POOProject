package Database;
import java.sql.*;

public class Access {
	public void AccessDB() {
		String dataBaseURL = "jdbc:mysql://localhost:3306/demo"; //default address
		String user = "...";
		String pass = "...";
		try {
			Connection myConn = DriverManager.getConnection(dataBaseURL,user,pass);
			
			Statement myStmt = myConn.createStatement();
			
			ResultSet myRs = myStmt.executeQuery();
		} catch(java.sql.SQLException e) {
			System.out.println("mauvaise URL pour la DB ou mauvais identifiants pour y acceder");
		}
		
		
	}

}