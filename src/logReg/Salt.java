package logReg;


import org.mindrot.jbcrypt.BCrypt;


public class Salt {
	public boolean passChecker(String password, String hash) {
		
		
		
		boolean check = BCrypt.checkpw(password, hash);
		
		return check;
		
	}
	
	public String convPass(String password) {
		
		String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
		
		
		return hashed;
	}
	
	
	public static void main (String[] args) {
		
		Salt salt = new Salt();
		
		
		String pass = "potato";
		String wrongpass = "banana";
		
		
		
		String hashed = salt.convPass(pass);
		
		System.out.println(hashed);
		
		System.out.println(salt.passChecker(pass, hashed));
		
		System.out.println(salt.passChecker(wrongpass, hashed));
		
	}
	
}
