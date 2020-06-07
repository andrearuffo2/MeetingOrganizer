package eu.polimi.tiw.common;

import eu.polimi.tiw.exception.*;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class will handle the decryption and the encryption of sensible
 *        data.
 */
public class StrongAES {

	private static final String ENC_PROPERTY = "encrkey";

	public static byte[] encrypt(String passphrase) throws AppCrash {
		try {
			// 128 bit key
			String key = Config.getInstance().getProperty(ENC_PROPERTY);

			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			byte[] encrypted = cipher.doFinal(passphrase.getBytes());

			return encrypted;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppCrash("There was a problem while encrypting the key you've passed.");
		}
	}

	public static String decrypt(byte[] encryptedString)
			throws AppCrash, NoSuchAlgorithmException, NoSuchPaddingException {

		Key aesKey = new SecretKeySpec(encryptedString, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		String decrypted;
		// decrypt the text
		try {
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			decrypted = new String(cipher.doFinal(encryptedString));
			System.err.println(decrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppCrash("There was a problem while decrptying the key you've passed.");

		}
		return decrypted;
	}

}