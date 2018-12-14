import java.net.InetAddress;
import java.io.*;
import java.lang.String;
import network.*;

public class SysCom {
//implementer send, sendFile, receive et receive File
	TCPClient client = new TCPClient();
	public static void send(InetAddress addrSrc,InetAddress addrDest, String payload) {
		TCPClient client = new TCPClient(payload, addrDest);
	}
	
	public static void sendFile(InetAddress addrSrc,InetAddress addrDest, String payload, File infile) {
		
	}
	
}
