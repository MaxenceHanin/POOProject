package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DiscoveryThread extends Thread {
	@Override
	public void run() {
		try{
			DatagramSocket broadSocket = new DatagramSocket(6664);
			while(true) {
				broadSocket.setBroadcast(true);
				byte[] buffer = new byte[256];
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				broadSocket.receive(packet);
				String demande = new String(packet.getData(), 0, packet.getLength());
				if (demande.equals("Looking for server")) {
					String message = "I am the server";
					buffer = message.getBytes();
					int portClient = packet.getPort();
					InetAddress adresseClient = packet.getAddress();
					packet = new DatagramPacket(buffer, buffer.length, adresseClient, portClient);
					broadSocket.send(packet);
				}
        	} 
		} catch (IOException e) {
        	e.printStackTrace();
        }
	}
}
