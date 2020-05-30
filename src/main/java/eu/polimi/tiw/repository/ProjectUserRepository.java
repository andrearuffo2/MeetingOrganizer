package eu.polimi.tiw.repository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class represents the java repository for the table
 *        utente_progetto.
 */
public class ProjectUserRepository {

	private int idUtente;
	private int idProgetto;

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

}
