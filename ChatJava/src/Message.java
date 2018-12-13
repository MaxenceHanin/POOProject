import java.net.InetAddress;
import java.io.*;
import java.lang.String; 

public class Message {
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
	
	
	public void newConnection(User user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(NEW_CO);
		payload.concat(user.getNickname());
		
		//la payload doit contenir le flag special mis sur true (1),
		//le nickname de l'utilisateur envoyant le message de nouvelle connection
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void connect(User user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(CONNECT);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}
	
	public void tryNickname(User user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(TRY_NKNM);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void isConnected(User user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(IS_CONNECTED);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void disconnection(User user, Agent agent) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(DISCONNECT);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}
	
	public void changedNickname(User user, Agent agent, String oldNickname) {
		String payload = new String();
		payload.concat(FLAG_SPE);
		payload.concat(CHGD_NKMN);
		payload.concat(oldNickname);
		payload.concat(user.getNickname());
		
		SysCom.send(user.getAddr(), agent.getAddrLAN(), payload);
	}

	public void sendMsg(User user, InetAddress destAddr, String message) {
		String payload = new String();
		payload.concat(FLAG_NORM);
		payload.concat(SEND_MSG);
		payload.concat(user.getNickname());
		//Il serait egalement bien d'envoyer le nickname 
		//de l'user distant (au cas ou erreur a l'envoi)
		payload.concat(message);
		
		SysCom.send(user.getAddr(), destAddr, payload);
	}

	public void sendFile(User user, InetAddress destAddr, File inFile) {
		String payload = new String();
		payload.concat(FLAG_NORM);
		payload.concat(SEND_FILE);
		payload.concat(user.getNickname());
		//Il serait egalement bien d'envoyer le nickname 
		//de l'user distant (au cas ou erreur a l'envoi)
		
		SysCom.sendFile(user.getAddr(), destAddr, payload, inFile);
	}
}
