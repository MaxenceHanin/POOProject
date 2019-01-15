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