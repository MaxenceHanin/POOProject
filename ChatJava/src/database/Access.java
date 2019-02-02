package database;

import agent.DistantUser;
import agent.HistoryMessage;
import agent.LocalUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Access { //Driver to access the database
	private Connection myConn = null;

	public Access(String userDB, String password) throws SQLException{
		String dataBaseURL = "jdbc:mysql://localhost:3306/chat_app"; //default address
			this.myConn = DriverManager.getConnection(dataBaseURL,userDB,password);
			System.out.println("Database connected!");


	}

	public void setUserConnected (String ConnectedUser) {
		try {
			CallableStatement statement = myConn.prepareCall("CALL setUserConnected(?)");
			statement.setString(1,ConnectedUser);
			statement.execute();
			System.out.println("User set to connected!");
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot set user to connected!", e);
		}
	}

	public void setUserDisconnected (String DisconnectedUser) {
		try {
			CallableStatement statement = myConn.prepareCall("CALL setUserDisconnected(?)");
			statement.setString(1,DisconnectedUser);
			statement.execute();
			System.out.println("User set to disconnected!");
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot set user to disconnected!", e);
		}
	}

	public boolean userExists (String triedNick) {
		try {
			CallableStatement statement = myConn.prepareCall("CALL userAlreadyExists(?,?)");
			statement.setString(1,triedNick);
			statement.registerOutParameter("userExists", Types.SMALLINT);
			statement.execute();
			Integer result = statement.getInt("userExists");
			return (result == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not retrieve information about users", e);
		}
	}

	public boolean isConnected (String triedNick) {
		try {
			CallableStatement statement = myConn.prepareCall("CALL userIsConnected(?,?)");
			statement.setString(1,triedNick);
			statement.registerOutParameter("isConnected", Types.SMALLINT);
			statement.execute();
			Integer result = statement.getInt("isConnected");
			return (result == 1);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not retrieve information about users", e);
		}
	}


	public void StoreMsg(HistoryMessage msg) {
		try {
			CallableStatement statement = myConn.prepareCall("CALL insertMessage(?,?,?,?,?)");
			statement.setString(1,msg.getConv());
			statement.setString(2,msg.getUsrSrc());
			statement.setString(3,msg.getUsrDest());
			statement.setTime(4,msg.getTime());
			statement.setString(5,msg.getText());
			statement.execute();
			System.out.println("Message inserted!");
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot insert message into the database!", e);
		}
	}

	public String databaseAlreadyExists (String user1, String user2) {
		try {
			CallableStatement statement = myConn.prepareCall("CALL databaseAlreadyExists(?,?,?)");
			statement.setString(1,user1);
			statement.setString(2,user2);
			statement.registerOutParameter("conv_name", Types.VARCHAR);
			statement.execute();
			return statement.getString("conv_name");
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot retrieve conversation name!", e);
		}
	}

	public List<HistoryMessage> extractMsg(String ConvNo)  {
		try {
			List<HistoryMessage> result = new ArrayList<>();
			String query="SELECT * FROM ".concat(ConvNo).concat(";");
			CallableStatement statement = myConn.prepareCall(query);
			ResultSet myRs = statement.executeQuery();
			while (myRs.next()){

				//result.add(myRs.getString("nickname"));
			}
			return result;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

	public List<String> extractConv(String nick) {
		try {
			List<String> result = new ArrayList<>();
			CallableStatement stmt1 = myConn.prepareCall("CALL getUserId(?,?)");
			stmt1.setString(1,nick);
			stmt1.registerOutParameter("ID", Types.SMALLINT);
			stmt1.execute();
			String UserID = stmt1.getString("ID");
			String query="SELECT tbl_name FROM Conversations WHERE ((user_src = ".concat(UserID).concat(") OR (user_dest = ").concat(UserID).concat("));");

			CallableStatement statement = myConn.prepareCall(query);
			ResultSet myRs = statement.executeQuery();
			while (myRs.next()){
				result.add(myRs.getString("tbl_name"));
			}
			return result;


		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot retrieve conversations from the database!", e);
		}
	}

	public List<String> UsersConnected() {
		try {
			List<String> result = new ArrayList<>();
			String query="SELECT nickname FROM User WHERE (connected=1)";
			CallableStatement statement = myConn.prepareCall(query);
			ResultSet myRs = statement.executeQuery();
			while (myRs.next()){
				result.add(myRs.getString("nickname"));
			}
			return result;

		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot retrieve Users from the database!", e);
		}
	}

	public String ReturnsOtherUser(String conversation, String nicknameLocalUser) {
		try {
			CallableStatement stmt1 = myConn.prepareCall("CALL returnsotherUser(?,?,?)");
			stmt1.setString(1,conversation);
			stmt1.setString(2,nicknameLocalUser);
			stmt1.registerOutParameter("nick_dest", Types.VARCHAR);
			stmt1.execute();
			return stmt1.getString("nick_dest");

		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot retrieve other conversation user from the database!", e);
		}
	}

	public void ShowPreviousMsg(String ConvNo) {
		try {
			String query="SELECT * FROM ".concat(ConvNo).concat(";");
			CallableStatement statement = myConn.prepareCall(query);
			ResultSet myRs = statement.executeQuery();

			while (myRs.next()) {
                System.out.println(myRs.getString("snick")+" -> "+myRs.getString("dnick")+" @"+myRs.getTime("time")+" : "+myRs.getString("text"));
			}

		} catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException("Cannot connect the database!", e);
		}
	}

}