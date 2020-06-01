package eu.polimi.tiw.bean;

import eu.polimi.tiw.common.AppCrash;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This bean will represent the register bean.
 */
public class RegisterBean {

	private String name;
	private String surname;
	private String email;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws AppCrash {
		this.password = password;
	}

}