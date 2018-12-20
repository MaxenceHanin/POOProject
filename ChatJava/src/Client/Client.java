package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Client.UserStatusListener;

public class Client {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;

    /*listes des user connectés*/
    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    /*liste des messages envoyés*/
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public static void main(String[] args) throws IOException {
    	
        Client client = new Client("localhost", 6666);
     
        client.addUserStatusListener(new UserStatusListener() {
        	
        	/*notification connexion deconnexion*/
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
        client.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(String fromLogin, String msgBody) {
                System.out.println("message de " + fromLogin + " -> " + msgBody);
            }
        });
        
        /*verification si le client est connecté*/

        if (!client.connect()) {
            System.err.println("Connection échouée.");
        } else {
            System.out.println("Connection réussie");
            
            if (client.register("LocalUser")) {
                System.out.println("Inscription réussie");

                client.msg("LocalUser", "Bienvenue");
            } else {
                System.err.println("echec de l'inscription");
                }

            if (client.login("LocalUser")) {
                System.out.println("Login réussi");

            } else {
                System.err.println("Login échoué");
            }

        }
    }
    /*envoi des msg*/

    private void msg(String destUser, String msgBody) throws IOException {
        String cmd = "msg " + destUser + " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }
    /*register*/
    private boolean register(String nickname) throws IOException {
        String cmd = "register " + nickname + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println("Réponse:" + response);

        if ("Inscription réalisée avec succès".equals(response)) {
        	startMessageReader();
            return true;
        } else {
            return false;
        }
    }
    /*connexion*/
    private boolean login(String login) throws IOException {
        String cmd = "login " + login + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println("Réponse:" + response);

        if ("login bon".equals(response)) {
            startMessageReader();
            return true;
        } else {
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

    private boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Port client : " + socket.getLocalPort());
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
