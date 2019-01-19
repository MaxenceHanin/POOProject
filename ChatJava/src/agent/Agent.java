/*

 * @startuml

 * car --|> wheel

 * @enduml

 */


package agent;

/*import a voir*/
import database.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;

public class Agent {
	private InetAddress addrLAN;
	public List<String> listChatOpened = new LinkedList<String>();

	public void DebugDB() {
		Access a = new Access("pitou", "pwd");
		/* débug de l'insertion de message dans la database */
		/*
		HistoryMessage Msg = new HistoryMessage("La BDD marche", "Pitou", "Maxou","Conv02");
		HistoryMessage Msg2 = new HistoryMessage("Oui", "Maxou", "Pitou","Conv02");
		a.StoreMsg(Msg);
		a.StoreMsg(Msg2);

		a.ShowPreviousMsg("Conv02");
		*/
		String convo_name = a.databaseAlreadyExists("Maxou","Pitou");
		System.out.println("nom de la convo : "+convo_name);
		HistoryMessage Msg = new HistoryMessage("MSG1", "Pitou", "Maxou",convo_name);
		HistoryMessage Msg2 = new HistoryMessage("MSG2", "Maxou", "Pitou",convo_name);
		a.StoreMsg(Msg);
		a.StoreMsg(Msg2);
		a.ShowPreviousMsg(convo_name);

		HistoryMessage Msg3 = new HistoryMessage("MSG3", "Pitou", "Dharsy","PitouDharsy");
		a.StoreMsg(Msg3);

		a.ShowPreviousMsg("PitouDharsy");
		try {
			ResultSet myRs = a.extractConv("Pitou");
			while (myRs.next()) {
				System.out.println("User 'Pitou' is in the conversation :"+myRs.getString("tbl_name"));
			}
		} catch(SQLException e) {
		e.printStackTrace();
		throw new IllegalStateException("Cannot retrieve conversations from the database!", e);
		}
	}
	
	public Agent () {
		try  {
			this.addrLAN = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Agent agent = new Agent();
		agent.DebugDB();
	}

	
	public InetAddress getAddrLAN() {
		return this.addrLAN;
	}

	public void chooseFile() {
		
	}


}
