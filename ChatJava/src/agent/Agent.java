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

	public void DebugDB(){
		Access a = new Access();
		a.StoreIncomingMsg("Maxence", "Pitou", "Coucou");
	}
	
	public Agent () {
		try  {
			this.addrLAN = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	 static public void main(String[] args) {
		Agent agent = new Agent();
		agent.DebugDB();
	 }

	
	public InetAddress getAddrLAN() {
		return this.addrLAN;
	}

	public void chooseFile() {
		
	}


}
