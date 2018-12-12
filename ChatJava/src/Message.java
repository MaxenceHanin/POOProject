import java.net.InetAddress;
import java.lang.String; 

public class Message {
	//structure d'un message :
	//send(@src, @dest, payload)
	//payload 
	//
	
	private static String FLAG_NORM = "0";
	private static String FLAG_SPE = "1";
	
	private static String NEW_CO = "0";
	private static String CONNECT = "1";
	private static String DISCONNECT = "2";
	private static String TRY_NKNM = "3";
	private static String CHGD_NKMN = "4";

	private static String SEND_MSG = "0";
	private static String SEND_FILE = "1";
	
	//static public class Message {
	//	String message;
	//	ArrayList<Integer> array;
	//}
	
	public void newConnection(User user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(NEW_CO);
		payload.concat(user.getNickname());
		
		//la payload doit contenir le flag special mis sur true (1),
		//le nickname de l'utilisateur envoyant le message de nouvelle connection
		SysCom.send(user.getAddr(),Agent.getAddrLAN(),payload);
	}

	public void connect(User user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(NEW_CO);
		payload.concat(user.getNickname());
		
		//la payload doit contenir le flag special mis sur true (1),
		//le nickname de l'utilisateur envoyant le message de nouvelle connection
		SysCom.send(user.getAddr(), user.getAddrLAN(),payload);
	}
	
	public void tryNickname() {}

	public void isConnected() {}

	public void disconnection() {}
	
	public void changedNickname() {}

	public void discardOldNickname() {}

	public void sendMsg() {}

	public void sendFile() {}
}
