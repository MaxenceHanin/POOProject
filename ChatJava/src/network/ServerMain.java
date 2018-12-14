package network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class ServerMain {
    public static void main(String[] args) throws UnknownHostException {
    	Thread s = new Thread (new TCPServer());
    	Thread c1 = new Thread (new TCPClient("coucou cest max",InetAddress.getLocalHost()));
    	Thread c2 = new Thread (new TCPClient("coucou cest patou",InetAddress.getByName("10.1.5.75")));
    	s.start();
    	c1.start();
    	c2.start();
    }
}
