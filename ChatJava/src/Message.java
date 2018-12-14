import java.util.*;

public class Message {
	private String text;
	private long Time;
	private String nickSrc, nickDest;
	
	public Message(String text, String src, String dest){
		this.text = text;
		this.nickSrc = src;
		this.nickDest = dest;
		this.Time = (new Date()).getTime();
	}
	
}