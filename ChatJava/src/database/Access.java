package database;

import agent.DistantUser;
import agent.HistoryMessage;
import agent.LocalUser;

import java.sql.*;

public class Access { //Driver to access the database
	private Connection myConn = null;

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
			CallableStatement statement = myConn.prepareCall("SELECT idUsr FROM User WHERE User.nickname = ?");
			statement.setString(1, msg.getUsrSrc());

			Integer srcUserID;

			statement.registerOutParameter("idUsr", Types.SMALLINT);
			statement.execute();
			srcUserID = statement.getInt("idUsr");

			if (srcUserID == 0)  {
				statement = myConn.prepareCall("INSERT INTO User (nickname) VALUE ?");
				statement.setString(1, msg.getUsrSrc());
				statement.registerOutParameter("idUsr", Types.SMALLINT);
				statement.execute();
				srcUserID = statement.getInt("idUsr");
			}

			statement = myConn.prepareCall("SELECT idUsr FROM User WHERE User.nickname = ?");
			statement.setString(1, msg.getUsrDest());

			Integer destUserID;

			statement.registerOutParameter("idUsr", Types.SMALLINT);
			statement.execute();
			destUserID = statement.getInt("idUsr");

			if (srcUserID == 0)  {
				statement = myConn.prepareCall("INSERT INTO User (nickname) VALUE ?");
				statement.setString(1, msg.getUsrDest());
				statement.registerOutParameter("idUsr", Types.SMALLINT);
				statement.execute();
				destUserID = statement.getInt("idUsr");
			}

			statement = myConn.prepareCall("INSERT INTO Message (time, user_src, user_dest, text) VALUE (?,?,?,?)");
			statement.setTime(1,msg.getTime());
			statement.setInt(2,srcUserID);
			statement.setInt(3,destUserID);
			statement.setString(4,msg.getText());
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