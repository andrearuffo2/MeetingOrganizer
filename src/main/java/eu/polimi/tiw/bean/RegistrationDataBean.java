package eu.polimi.tiw.bean;

import java.util.List;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This bean will represent the registration data bean.
 */
public class RegistrationDataBean {

	private List<String> listaDipartimento;
	private List<String> listaProgetti;

	public List<String> getListaDipartimento() {
		return listaDipartimento;
	}

	public void setListaDipartimento(List<String> listaDipartimento) {
		this.listaDipartimento = listaDipartimento;
	}

	public List<String> getListaProgetti() {
		return listaProgetti;
	}

	public void setListaProgetti(List<String> listaProgetti) {
		this.listaProgetti = listaProgetti;
	}

}
