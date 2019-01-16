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
		/*	HistoryMessage Msg = new HistoryMessage("La BDD marche", "Pitou", "Maxou","Conv02");
			HistoryMessage Msg2 = new HistoryMessage("Oui", "Maxou", "Pitou","Conv02");
			a.StoreMsg(Msg);
			a.StoreMsg(Msg2);

			if (!(a.userExists("Pitou"))) {
			System.out.println("you can't use the nickname Pitou because it is already taken");
			}

		a.ShowPreviousMsg("Conv02"); */


		a.setUserDisconnected("Maxou");

		if (a.isConnected("Maxou")) {
			System.out.println("isConnected(Maxou) = true");
		} else {
			System.out.println("isConnected(Maxou) = false");
		}

		if (a.userExists("Maxou")) {
			System.out.println("userExists(Maxou) = true");
		} else {
			System.out.println("userExists(Maxou) = false");
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
