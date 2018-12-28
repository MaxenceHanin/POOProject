package agent;

import java.util.Date;

public class HistoryMessage {
	private String text;
	private long Time;
	private DistantUser usrSrc, usrDest;
	
	public HistoryMessage(String text, DistantUser src, DistantUser dest){
		this.text = text;
		this.usrSrc = src;
		this.usrDest = dest;
		this.Time = (new Date()).getTime();
	}

	public DistantUser getUsrDest() {
		return usrDest;
	}

	public DistantUser getUsrSrc() {
		return usrSrc;
	}

	public long getTime() {
		return Time;
	}

	public String getText() {
		return text;
	}
}