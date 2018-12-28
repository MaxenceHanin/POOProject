package database;

import agent.DistantUser;
import agent.HistoryMessage;
import agent.LocalUser;

import java.sql.*;

public class Access { //Driver to access the database

	public void StoreMsg(HistoryMessage msg) {
		String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
		String userDB = "pitou";
		String pass = "pwd";
		try {
			Connection myConn = DriverManager.getConnection(dataBaseURL,userDB,pass);
			System.out.println("Database connected!");

			Statement myStmt = myConn.createStatement();

			String getSrcUserId = "SELECT @src := idUsr FROM User " +
					"WHERE nickname = '"+msg.getUsrSrc().getNickname()+"' ";
			ResultSet srcRs = myStmt.executeQuery(getSrcUserId);

			if (srcRs.getString("idUsr").equals("")) {
				String updateUsr = "INSERT INTO User " +
						"(nickname) VALUE '"+msg.getUsrSrc().getNickname()+"'";
				myStmt.executeUpdate(updateUsr);

				String idSrcUsr = " SELECT @src := LAST_INSERT_ID()";
				ResultSet srcIdRs = myStmt.executeQuery(idSrcUsr);
			}

			String getDestUserId = "SELECT @dest := idUsr FROM User " +
					"WHERE nickname = '"+msg.getUsrDest().getNickname()+"' ";
			ResultSet destRs = myStmt.executeQuery(getDestUserId);

			if (destRs.getString("idUsr").equals("")) {
				String updateUsr = "INSERT INTO User " +
						"(nickname) VALUE '"+msg.getUsrDest().getNickname()+"'";
				myStmt.executeUpdate(updateUsr);

				String idDistUsr = " SELECT @dest := LAST_INSERT_ID()";
				ResultSet destIdRs = myStmt.executeQuery(idDistUsr);
			}

			String updateMsg = "insert into Message " +
					"(time, user_src, user_dest, text) VALUE " +
					"('"+msg.getTime()+"',@src,@dest,'"+msg.getText()+"')";

			myStmt.executeUpdate(updateMsg);
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	public void ShowPreviousMsg() {
		String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
		String userDB = "pitou";
		String pass = "pwd";
		try {
			Connection myConn = DriverManager.getConnection(dataBaseURL,userDB,pass);
			System.out.println("Database connected!");


			Statement myStmt = myConn.createStatement();

			ResultSet myRs = myStmt.executeQuery("SELECT * FROM Message");

			while (myRs.next()) {
				System.out.println("message n°"+myRs.getString("idMsg")+" from "+myRs.getString("user_src")+" to "+myRs.getString("user_dest")+" : "+myRs.getString("text"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot connect the database!", e);
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