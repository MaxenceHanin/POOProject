import java.util.*;

public class Message {
	private String text;
	private String nickSrc, nickDest;
	private char FlagType, FlagTypeNotif;
	
	
	public Message(String src, String dest, char InFlagType, char InFlagNotif, String payload){
		this.text = payload;
		this.nickSrc = src;
		this.nickDest = dest;
		this.FlagType = InFlagType;
		this.FlagTypeNotif = InFlagNotif;
	}
	
}