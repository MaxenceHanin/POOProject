package network;

import java.io.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient implements Runnable {
	
	private Socket sock;
	private String message;
	private InetAddress ipToSend;

	public TCPClient(String message, InetAddress ipToSend) {
		this.sock = new Socket();
		this.message = message;
		this.ipToSend = ipToSend;
	}

	public void run() {
		try {
			this.sock.connect(new InetSocketAddress(this.ipToSend, 8045));
			int count;
			byte[] buffer = new byte[1024];
			InputStream StringStream = new ByteArrayInputStream(message.getBytes()); 
				
			OutputStream out = sock.getOutputStream();
			BufferedInputStream in = new BufferedInputStream(StringStream);
			while ((count = in.read(buffer)) >= 0) {
			     out.write(buffer, 0, count);
			     out.flush();
			}
			in.close();
			this.sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}