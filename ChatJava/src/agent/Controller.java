package agent;

//import java.util.LinkedList;
//import java.util.List;
import java.util.*;

public class Controller {
	
	public LinkedList<DistantUser> listConnectedUsers = new LinkedList<DistantUser>();
	public LinkedList<String> listUsedNicknames = new LinkedList<String>();
	
	public boolean checkUnicityNickname(String nickname) {
		return listUsedNicknames.contains(nickname);
	}
	
	public void updateListConnectedUsers(DistantUser user, int InOrOut) {
		//Si InOrOut = 1, alors on ajoute le nickname donné en argument sur la liste
		//des utilisateurs connectés, sinon on le supprime de cette liste
		if (InOrOut ==1) {
			listConnectedUsers.addLast(user);
		} else if (InOrOut ==0) {
			listConnectedUsers.remove(user);
		}
	}
	
	public void updateListUsedNicknames(String nickname, int InOrOut) {
		//Si InOrOut = 1, alors on ajoute le nickname donné en argument sur la liste
		//des utilisateurs connectés, sinon on le supprime de cette liste
		if (InOrOut ==1) {
			listUsedNicknames.addLast(nickname);
		} else if (InOrOut ==0) {
			listUsedNicknames.remove(nickname);
		}
	}
	
}
