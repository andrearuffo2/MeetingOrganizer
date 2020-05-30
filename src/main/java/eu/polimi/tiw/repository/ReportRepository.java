package eu.polimi.tiw.repository;

import java.time.LocalDate;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class represents the java repository for the table
 *        rendicontazione.
 */
public class ReportRepository {

	private int idUtente;
	private int idProgetto;
	private LocalDate data;
	private int numeroOre;

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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getNumeroOre() {
		return numeroOre;
	}

	public void setNumeroOre(int numeroOre) {
		this.numeroOre = numeroOre;
	}

}
