package eu.polimi.tiw.repository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class represents the java repository for the table Progetto.
 */
public class ProjectRepository {

	private int idDipartimento;
	private int idProgetto;
	private String nome;

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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
