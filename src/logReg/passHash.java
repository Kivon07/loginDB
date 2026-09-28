package logReg;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passHash {
	
	public String encryptString(String input) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		byte[] messageDigest = md.digest(input.getBytes());
		
		
		StringBuilder hexString = new StringBuilder();
		
		for(byte b: messageDigest) {
			hexString.append(String.format("%02x",b));
		}
		
		return hexString.toString()
;	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		passHash hash = new passHash();
		
		String pass = "banana";
		
		System.out.println(hash.encryptString(pass));
	}
}
