package agent;

import java.sql.Time;
import java.util.Date;

public class HistoryMessage {
	private String text;
	private Time Time;
	private String usrSrc, usrDest, conv;
	
	public HistoryMessage(String text, String src, String dest, String conversation, Time t) {
		this.text = text;
		this.usrSrc = src;
		this.usrDest = dest;
		this.conv = conversation;
		this.Time = t;
	}

	//new Time((new Date()).getTime())

	public String getUsrDest() {
		return usrDest;
	}

	public String getUsrSrc() {
		return usrSrc;
	}

	public String getConv() { return conv; }

	public Time getTime() {
		return Time;
	}

	public String getText() {
		return text;
	}
}