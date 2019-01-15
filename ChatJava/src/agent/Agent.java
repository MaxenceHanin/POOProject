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
		//LaunchDatabases a = new LaunchDatabases();
		try {
			Access a = new Access();
			InetAddress lol = InetAddress.getByName("localhost");
			HistoryMessage Msg = new HistoryMessage("", "Pitou", "Maxou","Conv02");
			HistoryMessage Msg2 = new HistoryMessage("OK", "Maxou", "Pitou","Conv02");

			a.StoreMsg(Msg);
			a.StoreMsg(Msg2);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}


		//a.ShowPreviousMsg();
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
