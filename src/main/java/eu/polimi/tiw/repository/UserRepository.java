package eu.polimi.tiw.repository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class represents the java repository for the table Utenti.
 */
public class UserRepository {

	private String nome;
	private String cognome;
	private int idUtente;
	private int idDipartimento;
	private String email;
	private String password;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdDipartimento() {
		return idDipartimento;
	}

	public void setIdDipartimento(int idDipartimento) {
		this.idDipartimento = idDipartimento;
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

	public void setPassword(String password) {
		this.password = password;
	}

}
