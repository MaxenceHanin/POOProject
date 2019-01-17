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
import java.util.List;
import java.util.LinkedList;

public class Agent {
	private InetAddress addrLAN;
	public List<String> listChatOpened = new LinkedList<String>();

	public void DebugDB() {
		Access a = new Access();
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
		HistoryMessage Msg = new HistoryMessage("NTM pute", "Pitou", "Maxou",convo_name);
		HistoryMessage Msg2 = new HistoryMessage("JPP LOL", "Maxou", "Pitou",convo_name);
		a.StoreMsg(Msg);
		a.StoreMsg(Msg2);
		a.ShowPreviousMsg(convo_name);
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
