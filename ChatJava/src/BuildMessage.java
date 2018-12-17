import java.net.InetAddress;
import java.io.*;
import java.lang.String; 

public class BuildMessage {

	//structure d'un message :
	//send(@src, @dest, payload)
	//sendFile(@src, @dest, payload, file)
	
	private static String FLAG_NORM = "0";
	private static String FLAG_SPE = "1";
	
	private static String NEW_CO = "0";
	private static String CONNECT = "1";
	private static String IS_CONNECTED = "2";
	private static String DISCONNECT = "3";
	private static String TRY_NKNM = "4";
	private static String CHGD_NKMN = "5";

	private static String SEND_MSG = "0";
	private static String SEND_FILE = "1";
	
	
	public void newConnection(LocalUser user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(NEW_CO);
		payload.concat(user.getNickname());
		
		//la payload doit contenir le flag special mis sur true (1),
		//le nickname de l'utilisateur envoyant le message de nouvelle connection
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void connect(LocalUser user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(CONNECT);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}
	
	public void tryNickname(LocalUser user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(TRY_NKNM);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void isConnected(LocalUser user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(IS_CONNECTED);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void disconnection(LocalUser user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(DISCONNECT);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}
	
	public void changedNickname(LocalUser user, Agent agent, String oldNickname) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(CHGD_NKMN);
		payload.concat(oldNickname);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void sendMsg(LocalUser user, InetAddress destAddr, String message) {
		String payload = new String();
		payload.concat(FLAG_NORM);
		payload.concat(SEND_MSG);
		payload.concat(user.getNickname());
		//Il serait egalement bien d'envoyer le nickname 
		//de l'user distant (au cas ou erreur a l'envoi)
		payload.concat(message);
		
		SysCom.send(user.getAddr(), destAddr, payload);
	}

	public void sendFile(LocalUser user, InetAddress destAddr, File inFile) {
		String payload = new String();
		payload.concat(FLAG_NORM);
		payload.concat(SEND_FILE);
		payload.concat(user.getNickname());
		//Il serait egalement bien d'envoyer le nickname 
		//de l'user distant (au cas ou erreur a l'envoi)
		
		SysCom.sendFile(user.getAddr(), destAddr, payload, inFile);
	}
	
	public String receive (DistantUser user){
		String receivedMessage;
		receivedMessage = SysCom.receive();
		if (Character.toString(receivedMessage.charAt(0)).equals(FLAG_NORM)) {
			if (Character.toString(receivedMessage.charAt(1)).equals(SEND_MSG)) {
				char textchar[];
				receivedMessage.getChars(2,receivedMessage.length(),textchar,0);
				String text = String.valueOf(textchar);
				return text;
			}
		} else 
	}
	
}
