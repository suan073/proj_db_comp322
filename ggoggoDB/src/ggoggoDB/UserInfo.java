package ggoggoDB;

public class UserInfo {
	private final String ID;
	private final String password;
	private final Boolean infoValid;
	
	public UserInfo(String id, String pw, Boolean v) {
		this.ID = id;
		this.password = pw;
		this.infoValid = v;
	}
	
	public String getID() {
		return this.ID;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public Boolean isVaild() {
		return this.infoValid;
	}
	
}
