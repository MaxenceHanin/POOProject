import java.net.InetAddress;

public class Message {
	//structure d'un message :
	//send(@src, @dest, payload)
	//payload 
	//
	
	public void newConnection(User user) {
		SysCom.send(user.getAddr(),Agent.getAddrLAN(),payload);
	}

	public void connect() {}
	
	public void tryNickname() {}

	public void isConnected() {}

	public void disconnection() {}
	
	public void changedNickname() {}

	public void discardOldNickname() {}

	public void sendMdg() {}

	public void sendFile() {}
}
