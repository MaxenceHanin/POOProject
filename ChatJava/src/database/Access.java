package database;

import agent.DistantUser;
import agent.HistoryMessage;
import agent.LocalUser;

import java.sql.*;

public class Access { //Driver to access the database
	Connection myConn;

	public Access() {
		String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
		String userDB = "pitou";
		String pass = "pwd";
		try {
			this.myConn = DriverManager.getConnection(dataBaseURL,userDB,pass);
			System.out.println("Database connected!");
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot connect the database!", e);
		}

	}

	public void StoreMsg(HistoryMessage msg) {
		String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
		String userDB = "pitou";
		String pass = "pwd";
		try {
			PreparedStatement statement = myConn.prepareStatement("SELECT idUsr FROM User WHERE nickname = ?");
			statement.setString(1, "Pitou");
			statement.executeUpdate();

			String getSrcUserId = "SELECT idUsr FROM User WHERE nickname = ?";// +
					//"WHERE User.nickname = '"+msg.getUsrSrc()+"' ";
			ResultSet srcRs = myStmt.executeQuery(getSrcUserId);
			String srcUserID;
			if (srcRs.next()) {
			srcUserID = srcRs.getString("idUsr");
			} else  {
				String updateUsr = "INSERT INTO User "+
						"(nickname) VALUE '"+msg.getUsrSrc()+"'";
				myStmt.executeUpdate(updateUsr);

				String idSrcUsr = " SELECT LAST_INSERT_ID()";
				ResultSet srcInsertRs = myStmt.executeQuery(idSrcUsr);
				srcUserID = srcInsertRs.getString("idUsr");
			}

			String getDestUserId = "SELECT idUsr FROM User " +
					"WHERE nickname = '"+msg.getUsrDest()+"' ";
			ResultSet destRs = myStmt.executeQuery(getDestUserId);
			String destUserID;
			if (destRs.next()) {
				 destUserID= srcRs.getString("idUsr");
			} else {
				String updateUsr = "INSERT INTO User " +
						"(nickname) VALUE '"+msg.getUsrDest()+"'";
				myStmt.executeUpdate(updateUsr);

				String idDistUsr = " SELECT LAST_INSERT_ID()";
				ResultSet destInsertRs = myStmt.executeQuery(idDistUsr);
				destUserID = destInsertRs.getString("idUsr");
			}

			String updateMsg = "insert into Message " +
					"(time, user_src, user_dest, text) VALUE " +
					"('"+msg.getTime()+"','"+srcUserID+"','"+destUserID+"','"+msg.getText()+"')";

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