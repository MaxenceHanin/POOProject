package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import agent.DistantUser;
import database.Access;

public class Client {
	private InetAddress serverAddress;
    private int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;

    /*listes des user connectés*/
    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    /*liste des messages envoyés*/
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();


    public Client() {
     
        addUserStatusListener(new UserStatusListener() {
        	
        	/*notification connexion deconnexion*/
        	/*ne fonctionne pas*/
            @Override
            public void online(String login) {
                System.out.println("enLigne: " + login);
            }
            @Override
            public void offline(String login) {
                System.out.println("horsLigne: " + login);
            }
        });
        
        /*notification nouveau message*/
        addMessageListener(new MessageListener() {
            @Override
            public void onMessage(String fromLogin, String msgBody) {
                System.out.println("message de " + fromLogin + " -> " + msgBody);
            }
        });
    }
    /*envoi des msg*/

    private void msg(DistantUser destUser, String msgBody)  {
        
    }
    /*register*/
    public boolean register(String login, Access BDD)  {
    	if (!BDD.userExists(login)) {
    		BDD.setUserConnected(login);
    		return true;
    	} else {
    		return false;
    	}
    }
    /*connexion*/
    public boolean login(String login, Access BDD) {
        if (!(BDD.isConnected(login))&& BDD.userExists(login)) {
        	BDD.setUserConnected(login);
        	return true;
        }
        else {
        	return false;
        }
    }
    /*deconnexion*/
    private void logoff() throws IOException {
        String cmd = "logoff\n";
        serverOut.write(cmd.getBytes());
    }
    /*lancement du thread avec attente de messages en boucle */
    private void startMessageReader() {
        Thread t = new Thread() {
            @Override
            public void run() {
                readMessageLoop();
            }
        };
        t.start();
    }

    private void readMessageLoop() {
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] frags = line.split("\\s+");
                if (frags != null && frags.length > 0) {
                    String cmd = frags[0];
                    if ("online".equals(cmd)) {
                        handleOnline(frags);
                    } else if ("offline".equals(cmd)) {
                        handleOffline(frags);
                    } else if ("msg".equals(cmd)) {
                        String[] fragsMsg = line.split("\\s+", 3);
                        handleMessage(fragsMsg);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleMessage(String[] fragsMsg) {
        String login = fragsMsg[1];
        String msgBody = fragsMsg[2];

        for(MessageListener listener : messageListeners) {
            listener.onMessage(login, msgBody);
        }
    }

    private void handleOffline(String[] frags) {
        String login = frags[1];
        for(UserStatusListener listener : userStatusListeners) {
            listener.offline(login);
        }
    }

    private void handleOnline(String[] frags) {
        String login = frags[1];
        for(UserStatusListener listener : userStatusListeners) {
            listener.online(login);
        }
    }
    
    private void findServer() throws IOException {
    	        DatagramSocket broadSocket = new DatagramSocket(6665);
    	        broadSocket.setBroadcast(true);
    	        String message = "Looking for server";
    	        byte[] buffer = message.getBytes();
    	        InetAddress broadAddress = InetAddress.getByName("255.255.255.255");
    	        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadAddress, 6664);
    	        broadSocket.send(packet);
    	        packet = new DatagramPacket(buffer, buffer.length);
    	        broadSocket.receive(packet);
    	        String response = new String(packet.getData(), 0, packet.getLength());
    	        if (response.equals("I am the server")) {
    	        	System.out.println("Found server");
    	        	this.serverAddress = packet.getAddress();
    	        	this.serverPort = packet.getPort();
    	        }
    	        broadSocket.close();
    	    }
    
    private boolean connect() throws UnknownHostException {
        try {
			findServer();
            this.socket = new Socket(serverAddress, serverPort);
            System.out.println("Port client : " + this.socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addUserStatusListener(UserStatusListener listener) {
        userStatusListeners.add(listener);
    }

    public void removeUserStatusListener(UserStatusListener listener) {
        userStatusListeners.remove(listener);
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

}
