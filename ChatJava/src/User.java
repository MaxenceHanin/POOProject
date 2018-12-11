/*import a voir*/
import java.net.InetAddress;
public class User {
	private String nickname;
	public InetAddress addrUser;
	private String pswd;
	public boolean registered;

public int login(String pwd, String username){
	
	if ((pwd==this.pswd)&&(username==this.nickname)){
		controller.updateListConnectedUser(nickname,1);
		return 0;
	}
	return -1;
}

public void logout(String pwd, String username){
	controller.updateListConnectedUser(nickname,0);
}

public void register(String username){
	while (not registered) loop{
		
	}
}

public void changeNickname(String newname){
	if(Agent.checkUnicityNickname()){
		this.nickname = newname;
		controller.updateListUsedNicknames();
	}
}
public String getNickname(){
	return this.nickname;
}

	
	public int checkPassword(String pwd, String username){
		if ((pwd==this.pswd)&&(username==this.nickname)){
			return 0;
		}
		return -1;
	}
	
	public void logout(String pwd, String username){
	}
	
	public void register(){
	}
	
	public void changeNickname(String newname){
		if(Agent.checkUnicityNickname()){
			this.nickname = newname;
			controller.UpdateListUsedNicknames();
		}
	}
	
	public String getNickname(){
		return this.nickname;
	}
}