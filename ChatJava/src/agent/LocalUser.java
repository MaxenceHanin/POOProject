package agent;

/*import a voir*/
import java.net.InetAddress;

public class LocalUser {
	private String localNickname;
	private InetAddress addrUser;
	private String pswd;
	private Controller controller;
	public boolean registered;

	public int checkPassword(String pwd, String nickname){
		if ((pwd==this.pswd)&&(nickname==this.localNickname)){
			controller.updateListConnectedUsers(BuildDistantUser(),1);
			return 0;
		}
		return -1;
	}

	public void logout(String username){
		controller.updateListConnectedUsers(BuildDistantUser(),0);
		//il manque la partie ou on previent les autres utilisateurs de son depart --> utiliser BuildMessage.tryNickname
	}

	public void changeNickname(String newname, LocalUser user){
		//il manque le trynickname pour demander aux autres users si le nickname est deja pris --> utiliser BuildMessage.changedNickname

		if(controller.checkUnicityNickname(newname)){
			controller.updateListUsedNicknames(user.getNickname(), 0);
			this.localNickname = newname;
			controller.updateListUsedNicknames(newname, 1);
		}
	}
	
	public void register(String nickname){
		/*while (!(registered)) loop{
			Message.trynickname(nickname);
			message.isConnected(); // user connectés
			Controller.updateListConnectedUsers();
			Controller.updateListUsedNicknames();
				if (checkUnicityNickname(Controller.listUsedNicknames)) {
					this.registered=true;
					chatWindow.display();
				}
		}*/
	}
	
	public String getNickname(){
		return this.localNickname;
	}
	
	public InetAddress getAddr(){
		return this.addrUser;
	}

	private DistantUser BuildDistantUser() {
		DistantUser dist = new DistantUser(this.localNickname, this.addrUser);
		return dist;
	}

	public LocalUser(InetAddress addrUser) {
		this.addrUser = addrUser;
		this.registered = false;
	}

}