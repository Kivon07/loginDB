package logReg;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passHash {
	
	public String encryptString(String input) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		byte[] messageDigest = md.digest(input.getBytes());
		
		
		BigInteger bigInt = new BigInteger(1,messageDigest);
		
		return bigInt.toString(64);
	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		passHash hash = new passHash();
		
		String pass = "banana";
		
		System.out.println(hash.encryptString(pass));
	}
}
