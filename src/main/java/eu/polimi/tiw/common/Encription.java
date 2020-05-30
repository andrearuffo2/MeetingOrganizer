package eu.polimi.tiw.common;

import org.mindrot.jbcrypt.BCrypt;

public class Encription {

	public static String hashPassword(String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	public static boolean verifyHash(String password, String hash) {
		return BCrypt.checkpw(password, hash);
	}

}
