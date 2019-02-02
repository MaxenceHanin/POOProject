/*

 * @startuml

 * car --|> wheel

 * @enduml

 */


package agent;

/*import a voir*/
import database.*;
import jdk.nashorn.internal.ir.WhileNode;

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

		try {
			Access a = new Access("pitou", "pwd");

			List<String> b = a.extractConv("Pitou");
			System.out.println("These are all the conversations where Pitou is in :");
			for(int i =0; i<b.size();i++) {
				System.out.println(b.get(i));
			}

			//a.ShowPreviousMsg("Conv02");

			String convo_name = a.databaseAlreadyExists("Maxou","Pitou");
			System.out.println("nom de la convo : "+convo_name);
			List<HistoryMessage> c = a.extractMsg(convo_name);
			for(int i =0; i<c.size();i++) {
				System.out.println("from "+c.get(i).getUsrSrc()+" to "+c.get(i).getUsrDest()+" : "+c.get(i).getText());
			}

			a.ShowPreviousMsg(convo_name);

			a.ShowPreviousMsg("PitouDharsy");


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
