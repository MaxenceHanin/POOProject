package server;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerThread extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;
    /*on a decide de faire une collection pour pouvoir creer des conversations de groupe*/
    private HashSet<String> chatGroupSet = new HashSet<>();
    public ArrayList<String> listlog = new ArrayList<>(); 
 

    public ServerThread(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ( (line = reader.readLine()) != null) {
            String[] frags = line.split("\\s+");
            if (frags != null && frags.length > 0) {
                String cmd = frags[0];
                if ("logoff".equals(cmd) || "quit".equals(cmd)) {
                    handleLogoff();
                    break;
                } else if ("register".equals(cmd)) {
                    handleReg(outputStream, frags);
                }else if ("login".equals(cmd)) {
                    handleLogin(outputStream, frags);
                } else if ("msg".equals(cmd)) {
                    String[] fragsMsg = line.split("\\s+", 3);
                    handleMessage(fragsMsg);
                } else if ("joingrp".equals(cmd)) {
                    handleJoin(frags);
                } else if ("leavegrp".equals(cmd)) {
                    handleLeave(frags);
                } else {
                    String msg = "cmd non reconnue " + cmd + "\n";
                    outputStream.write(msg.getBytes());
                }
            }
        }

        clientSocket.close();
    }
    /*on rejoint ou quitte la conv de groupe <=> on ajoute/retire le user de la collection*/
    private void handleJoin(String[] frags) {
        if (frags.length > 1) {
            String chatGroup = frags[1];
            chatGroupSet.add(chatGroup);
        }
    }
    private void handleLeave(String[] frags) {
        if (frags.length > 1) {
            String chatGroup = frags[1];
            chatGroupSet.remove(chatGroup);
        }
    }

    public boolean isMemberOfchatGroup(String chatGroup) {
        return chatGroupSet.contains(chatGroup);
    }

    // format: "msg" "login" body...
    // format: "msg" "#chatGroup" body...
    private void handleMessage(String[] frags) throws IOException {
        String destUser = frags[1];
        String body = frags[2];
        /* char "#" pour distinguer les conv entre 2 user des conv de groupe*/
        boolean ischatGroup = destUser.charAt(0) == '#';
        /*nouveau tserver pour chaque conv*/
        ArrayList<ServerThread> tserverList = server.gettserverList();
        for(ServerThread tserver : tserverList) {
            if (ischatGroup) {
                if (tserver.isMemberOfchatGroup(destUser)) {
                    String outMsg = "msg " + destUser + ":" + login + " " + body + "\n";
                    tserver.send(outMsg);
                }
            } else {
                if (destUser.equalsIgnoreCase(tserver.getLogin())) {
                    String outMsg = "msg " + login + " " + body + "\n";
                    tserver.send(outMsg);
                }
            }
        }
    }

    private void handleLogoff() throws IOException {
        server.removetserver(this);
        ArrayList<ServerThread> tserverList = server.gettserverList();

        // envoie aux autres user en ligne le status du localuser
        String onlineMsg = "horsLigne " + login + "\n";
        for(ServerThread tserver : tserverList) {
            if (!login.equals(tserver.getLogin())) {
                tserver.send(onlineMsg);
            }
        }
        clientSocket.close();
    }

    public String getLogin() {
        return login;
    }

    private void handleLogin(OutputStream outputStream, String[] frags) throws IOException {
        if (frags.length == 2) {
            String login = frags[1];

            if (listlog.contains(login)) {
            	String msg = "login bon\n";
                outputStream.write(msg.getBytes());
                this.login = login;
                System.out.println("User s'est connecté avec succes: " + login);

                ArrayList<ServerThread> tserverList = server.gettserverList();

                // envoie au localuser de tous les autres users en ligne
                for(ServerThread tserver : tserverList) {
                    if (tserver.getLogin() != null) {
                        if (!login.equals(tserver.getLogin())) {
                            String msg2 = "enLigne " + tserver.getLogin() + "\n";
                            send(msg2);
                        }
                    }
                }

                // envoie aux autres user en ligne du status du localuser
                String onlineMsg = "enLigne " + login + "\n";
                for(ServerThread tserver : tserverList) {
                    if (!login.equals(tserver.getLogin())) {
                        tserver.send(onlineMsg);
                    }
                }
            } else {
                String msg = "erreur login\n";
                outputStream.write(msg.getBytes());
                System.err.println("Le login a échoué pour " + login);
            }
        }
    }
    
    private void handleReg(OutputStream outputStream, String[] frags) throws IOException {
        if (frags.length == 2) {
            String nickname = frags[1];

            if (!listlog.contains(nickname)) {
            	String msg = "Inscription réalisée avec succès\n";
                outputStream.write(msg.getBytes());
                listlog.add(nickname);
                this.login = nickname;
                System.out.println(msg);

            } else {
            	String msg = "Erreur, Pseudo déjà utilisé\n";
                outputStream.write(msg.getBytes());
                System.err.println(msg);
            }
        }
    }

    private void send(String msg) throws IOException {
        if (login != null) {
            outputStream.write(msg.getBytes());
        }
    }
}