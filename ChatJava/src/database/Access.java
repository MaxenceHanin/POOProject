package database;

import agent.DistantUser;
import agent.HistoryMessage;
import agent.LocalUser;
import com.sun.jndi.cosnaming.CNCtx;

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

	public void ShowPreviousMsg(String ConvNo) {
		try {
			System.out.println("Database connected!");

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