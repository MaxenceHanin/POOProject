import java.net.InetAddress;

public class DistantUser {
	private String nickname;
	private InetAddress address;

	public DistantUser(String nickname, InetAddress address) {
		this.nickname = nickname;
		this.address = address;
	}

	public InetAddress getAddress() {
		return address;
	}

	public String getNickname() {
		return nickname;
	}
}
