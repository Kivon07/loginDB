package logReg;

public class UserInput {
	private static String username;
	private static String pass;

	
	public UserInput(){
		username= "";
		pass = "";
		
		
	}
	public UserInput(String userame, String pass){
		this.username = username;
		this.pass = pass;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getUsername(String username) {
		return username;
	}
	
	public String getPass() {
		return pass;
	}
	
	public String encryptPass() {
		convertHash hash = new convertHash();
		
		String encryptedPass = getPass();
		
		return encryptedPass;
	}
	
}
