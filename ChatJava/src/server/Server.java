package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server extends Thread {
	
	private InetAddress serverAddress;
    private final int serverPort;
    private ArrayList<ServerThread> tserverList = new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public ArrayList<ServerThread> gettserverList() {
        return tserverList;
    }

    @Override
    public void run() {
        try {
        	/*attention : socket jamais fermé*/
        	try(final DatagramSocket socket = new DatagramSocket()){
        		socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        		this.serverAddress = socket.getLocalAddress();
        	}
            DiscoveryThread dthread = new DiscoveryThread();
            dthread.start();
            ServerSocket serverSocket = new ServerSocket(this.serverPort, 1, this.serverAddress);
            while(true) {
                System.out.println("En attente d'acceptation de connection des clients...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion acceptee depuis " + clientSocket);
                ServerThread tserver = new ServerThread(this, clientSocket);
                tserverList.add(tserver);
                tserver.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removetserver(ServerThread ServerThread) {
        tserverList.remove(ServerThread);
    }
}