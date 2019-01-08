package agent;

import java.sql.Time;
import java.util.Date;

public class HistoryMessage {
	private String text;
	private Time Time;
	private String usrSrc, usrDest;
	
	public HistoryMessage(String text, String src, String dest){
		this.text = text;
		this.usrSrc = src;
		this.usrDest = dest;
		this.Time = new Time((new Date()).getTime());
	}

	public String getUsrDest() {
		return usrDest;
	}

	public String getUsrSrc() {
		return usrSrc;
	}

	public Time getTime() {
		return Time;
	}

	public String getText() {
		return text;
	}
}