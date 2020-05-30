package eu.polimi.tiw.bean;

import java.util.List;

public class PersonalPageBean {

	private String nomeProgetto;
	private int idProgetto;
	private List<String> daysOfTheMonth;

	public String getNomeProgetto() {
		return nomeProgetto;
	}

	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}

	public int getIdProgetto() {
		return idProgetto;
	}

	public void setIdProgetto(int idProgetto) {
		this.idProgetto = idProgetto;
	}

	public List<String> getDaysOfTheMonth() {
		return daysOfTheMonth;
	}

	public void setDaysOfTheMonth(List<String> daysOfTheMonth) {
		this.daysOfTheMonth = daysOfTheMonth;
	}

}
