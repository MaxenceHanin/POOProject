/*import a voir*/
import java.net.InetAddress;
import java.util.List;
import java.util.LinkedList;

public class Agent {
	private InetAddress addrLAN;
	public List listChatOpened = new LinkedList();
	
	public Agent () {
		this.addrLAN = InetAddress.getLocalHost();
	}
	
	public InetAddress getAddrLAN() {
		return this.addrLAN;
	}
	
	
	public void chooseFile() {
		
	}
	
}
