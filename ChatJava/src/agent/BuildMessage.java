package agent;

import java.lang.String; 

public class BuildMessage {

	//structure d'un message :
	//send(@src, @dest, payload)
	//sendFile(@src, @dest, payload, file)
	
	private static char FLAG_NORM = '0';
	private static char FLAG_SPE = '1';
	
	private static char NEW_CO = '0';
	private static char CONNECT = '1';
	private static char IS_CONNECTED = '2';
	private static char DISCONNECT = '3';
	private static char TRY_NKNM = '4';
	private static char CHGD_NKMN = '5';

	private static char SEND_MSG = '0';
	private static char SEND_FILE = '1';
	
	
	public void newConnection(LocalUser user, Agent agent) {
		Message newCoMsg = new Message(user.getNickname(),"all",FLAG_SPE,NEW_CO,"");
		SysCom.send(user.getAddr(), agent.getAddrLAN(), newCoMsg);
	}

	public void connect(LocalUser user, Agent agent) {
		Message Msg = new Message(user.getNickname(),"all",FLAG_SPE,CONNECT,"");
		SysCom.send(user.getAddr(), agent.getAddrLAN(), Msg);
	}
	
	public void tryNickname(LocalUser user, Agent agent) {
		Message Msg = new Message(user.getNickname(),"all",FLAG_SPE,TRY_NKNM,"");
		SysCom.send(user.getAddr(), agent.getAddrLAN(), Msg);
	}

	public void isConnected(LocalUser user, Agent agent) {
		Message Msg = new Message(user.getNickname(),"all",FLAG_SPE,IS_CONNECTED,"");
		SysCom.send(user.getAddr(), agent.getAddrLAN(), Msg);
	}

	public void disconnection(LocalUser user, Agent agent) {
		Message Msg = new Message(user.getNickname(),"all",FLAG_SPE,DISCONNECT,"");
		SysCom.send(user.getAddr(), agent.getAddrLAN(), Msg);
	}
	
	public void changedNickname(LocalUser user, Agent agent, String oldNickname) {
		Message Msg = new Message(user.getNickname(),"all",FLAG_SPE,CHGD_NKMN,oldNickname);
		SysCom.send(user.getAddr(), agent.getAddrLAN(), Msg);
	}

	public void sendMsg(LocalUser user, DistantUser dest, String message) {
		Message Msg = new Message(user.getNickname(),dest.getNickname(),FLAG_NORM,SEND_MSG,message);
		SysCom.send(user.getAddr(), dest.getAddress(), Msg);
	}

	public void sendFile(LocalUser user, DistantUser dest, String inFile) {
		Message Msg = new Message(user.getNickname(),dest.getNickname(),FLAG_NORM,SEND_FILE,"");
		SysCom.sendFile(user.getAddr(), dest.getAddress(), Msg, inFile);
	}
	
	//public void receive (DistantUser user){
	//	Message receivedMessage;
	//	receivedMessage = SysCom.receive();
	//
	//	if (FLAG_NORM.equals(receivedMessage.getFlagType())) {
	//		if (SEND_MSG.equals(receivedMessage.getFlagTypeNotif())) {
	//			System.out.println(receivedMessage.);
	//		}
	//	}
	//}
	
}
