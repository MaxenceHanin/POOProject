package agent;

import java.util.Date;

public class HistoryMessage {
	private String text;
	private long Time;
	private String usrSrc, usrDest;
	
	public HistoryMessage(String text, String src, String dest){
		this.text = text;
		this.usrSrc = src;
		this.usrDest = dest;
		this.Time = (new Date()).getTime();
	}

	public String getUsrDest() {
		return usrDest;
	}

	public String getUsrSrc() {
		return usrSrc;
	}

	public long getTime() {
		return Time;
	}

	public String getText() {
		return text;
	}
}