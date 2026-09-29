package logReg;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class convertHash {
	
	public String encryptString(String input) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		byte[] messageDigest = md.digest(input.getBytes());
		
		
		StringBuilder hexString = new StringBuilder();
		
		for(byte b: messageDigest) {
			hexString.append(String.format("%02x",b));
		}
		
		return hexString.toString();	
		
	}
	
	public boolean hashChecker(String inputPass, String storedHash) throws NoSuchAlgorithmException{
		
		String inputHash = encryptString(inputPass);
		return inputHash.equals(storedHash);
	
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException{
		convertHash hash = new convertHash();
		
		String pass = "banana";
		
		System.out.println(hash.encryptString(pass));
	}
}
