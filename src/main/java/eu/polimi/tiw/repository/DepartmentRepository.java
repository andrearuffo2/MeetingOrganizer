package eu.polimi.tiw.repository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class represents the java repository for the table dipartimento.
 */
public class DepartmentRepository {

	private int idDipartimento;
	private String nome;

	public int getIdDipartimento() {
		return idDipartimento;
	}

	public void setIdDipartimento(int idDipartimento) {
		this.idDipartimento = idDipartimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
