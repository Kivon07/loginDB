package logReg;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class Encryption {
	
	private SecretKey key;
	private int KEY_SIZE = 128;
	private int T_LEN = 128;
	private Cipher encryptionCi;
	
	
	public void init() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance("AES");
		generator.init(KEY_SIZE);
		key = generator.generateKey();
	}
	
	public String encrypt(String password) throws Exception{
		byte[] messageInBytes = password.getBytes();
		encryptionCi = Cipher.getInstance("AES/GCM/NoPadding");
		encryptionCi.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedBytes = encryptionCi.doFinal(messageInBytes);
		
		return encode(encryptedBytes);
	}
	
	
	public String decrypt(String enMessage) throws Exception{
		byte[] messageInBytes = decode(enMessage);
		Cipher decCi = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(T_LEN,encryptionCi.getIV());
		decCi.init(Cipher.DECRYPT_MODE,key,spec);
		
		byte[]  decBytes = decCi.doFinal(messageInBytes);
		
		return new String(decBytes);
		
	}
	
	private String encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}
	
	private byte[] decode(String data) {
		return Base64.getDecoder().decode(data);
	}
	
	public static void main(String[] args) {
		Encryption en = new Encryption();
		
		try {
			//initializes the encryption
			en.init();
			
			//converts it to encrypted text
			String pass = en.encrypt("Banana");
			
			
			// converts it to readable text
			String decM = en.decrypt(pass);
			
			System.err.println("En message " + pass);
			
			System.err.println("De message " + decM);
			
		}catch(Exception ignored) {
			
		}
	}
}
