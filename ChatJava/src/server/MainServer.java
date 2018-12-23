package server;

/******************************************************
 liste des commandes, à modifier pour adater au GUI

	login <user> <password>
    logoff

    msg <user> <contenu du message, peut contenir des espaces>
    msg <#ChatGroup> <contenu du message, peut contenir des espaces>

    joingrp <Nom_De_Conversation_De_Groupe_Qui_Commence_Par_#>
    leavegrp <Nom_De_Conversation_De_Groupe_Qui_Commence_Par_#>
*****************************************************/
public class MainServer {
    public static void main(String[] args) {
        int port = 6666;
        Server server = new Server(port);
        server.start();
    }
}

