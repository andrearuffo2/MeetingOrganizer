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
	private String dipartimento;
	private String progetto;
	private int idDipartimento;
	private int idProgetto;
	private int idUtente;
	private int numeroOre;
	private String email;
	private String password;
	private boolean refreshPage;

	public boolean getRefreshPage() {
		return refreshPage;
	}

	public void setRefreshPage(boolean refreshPage) {
		this.refreshPage = refreshPage;
	}

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

	public String getDipartimento() {
		return dipartimento;
	}

	public void setDipartimento(String dipartimento) {
		this.dipartimento = dipartimento;
	}

	public void setProgetto(String progetto) {
		this.progetto = progetto;
	}

	public String getProgetto() {
		return progetto;
	}

	public int getIdDipartimento() {
		return idDipartimento;
	}

	public void setIdDipartimento(int idDipartimento) {
		this.idDipartimento = idDipartimento;
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getNumeroOre() {
		return numeroOre;
	}

	public void setNumeroOre(int numeroOre) {
		this.numeroOre = numeroOre;
	}

}