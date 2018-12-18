package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
	
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
        	/*attention : socket jamais fermée*/
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while(true) {
                System.out.println("Waiting for accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
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