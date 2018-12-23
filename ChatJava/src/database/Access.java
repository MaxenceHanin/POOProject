package database;

import java.sql.*;

public class Access { //Driver to access the database

	public void StoreIncomingMsg(String fromUser, String localuser,  String text) {
		String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
		String userDB = "pitou";
		String pass = "pwd";
		try {
			Connection myConn = DriverManager.getConnection(dataBaseURL,userDB,pass);
			
			Statement myStmt = myConn.createStatement();

			String update = "insert into message " +
					"(user_src, user_dest, text)" +
					"values ('"+fromUser+"', '"+localuser+"', '"+text+"')";

			myStmt.executeUpdate(update);

		} catch(java.sql.SQLException e) {
			System.out.println("mauvaise URL pour la DB ou mauvais identifiants pour y acceder");
		}
	}

	//public void StoreOutgoingMsg(LocalUser user, String toUser, String text) {
	//	String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
	//	String userDB = "pitou";
	//	String pass = "pwd";
	//	try {
	//		Connection myConn = DriverManager.getConnection(dataBaseURL,userDB,pass);
	//
	//		Statement myStmt = myConn.createStatement();
	//		String update = "insert into message " +
	//				"(user_src, user_dest, text)" +
	//				"values ('"+user.getNickname()+"', '"+toUser+"', '"+text+"')";
	//		myStmt.executeUpdate(update);
	//	} catch(java.sql.SQLException e) {
	//		System.out.println("mauvaise URL pour la DB ou mauvais identifiants pour y acceder");
	//	}
	//}

}