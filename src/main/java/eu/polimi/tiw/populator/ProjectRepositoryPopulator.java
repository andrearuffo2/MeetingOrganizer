package eu.polimi.tiw.populator;

import java.sql.ResultSet;
import java.sql.SQLException;

import eu.polimi.tiw.bean.RegisterBean;
import eu.polimi.tiw.repository.ProjectRepository;

public class ProjectRepositoryPopulator {

	private static ProjectRepositoryPopulator instance;

	private ProjectRepositoryPopulator() {
	}

	// Lazy initialization singleton pattern
	public static ProjectRepositoryPopulator getInstance() {
		if (instance == null) {
			instance = new ProjectRepositoryPopulator();
		}
		return instance;
	}

	public ProjectRepository populate(ResultSet rs) throws SQLException {

		ProjectRepository toReturn = new ProjectRepository();
		toReturn.setNome(rs.getString("nome"));
		toReturn.setIdProgetto(Integer.parseInt(rs.getString("id_progetto")));
		toReturn.setIdDipartimento(Integer.parseInt(rs.getString("id_dipartimento")));
		return toReturn;
	}

	public ProjectRepository convert(RegisterBean registerBean) {
		ProjectRepository toReturn = new ProjectRepository();
		toReturn.setNome(registerBean.getName());
		return toReturn;

	}

}
