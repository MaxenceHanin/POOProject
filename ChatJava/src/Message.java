import java.net.InetAddress;
import java.lang.String; 

public class Message {
	//structure d'un message :
	//send(@src, @dest, payload)
	//payload 
	//
	
	private static String FLAG_SP = "1";
	private static String FLAG_NORM = "0";
	
	private static String NEW_CO = "0";
	private static String CONNECT = "1";
	
	public void buildPayload
	
	public void newConnection(User user) {
		String payload = new String();
		payload.concat(FLAG_SP);
		payload.concat()
		//la payload doit contenir le flag special mis sur true (1),
		//le nickname de l'utilisateur envoyant le message de nouvelle connection
		SysCom.send(user.getAddr(),Agent.getAddrLAN(),payload);
	}

	public void connect() {}
	
	public void tryNickname() {}

	public void isConnected() {}

	public void disconnection() {}
	
	public void changedNickname() {}

	public void discardOldNickname() {}

	public void sendMsg() {}

	public void sendFile() {}
}
