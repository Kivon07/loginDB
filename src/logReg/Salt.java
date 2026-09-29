package logReg;
import java.security.NoSuchAlgorithmException;

import org.mindrot.jbcrypt.BCrypt;


public class Salt {
	public static void main(String[]args) {
		
		convertHash hash = new convertHash();
		String hashness;
		
		try {
			hashness = hash.encryptString("banana");
			System.out.println(hashness);
		}catch(NoSuchAlgorithmException e) {
			
		}
		
		
		String hashed = BCrypt.hashpw("banana", BCrypt.gensalt());
		
		boolean check = BCrypt.checkpw("banana", hashed);
		
		
		System.out.println(hashed + " " + check);
	}
	
}
