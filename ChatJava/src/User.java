/*import a voir*/
import java.net.InetAddress;
public class User {
	private String nickname;
	public InetAddress addrUser;
	private String pswd;
	public boolean registered;
	
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