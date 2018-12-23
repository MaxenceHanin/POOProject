package agent;

import java.util.Date;

public class HistoryMessage {
	private String text;
	private long Time;
	private String nickSrc, nickDest;
	
	public HistoryMessage(String text, String src, String dest){
		this.text = text;
		this.nickSrc = src;
		this.nickDest = dest;
		this.Time = (new Date()).getTime();
	}
	
}