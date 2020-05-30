package eu.polimi.tiw.populator;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import eu.polimi.tiw.bean.ProjectUserBean;
import eu.polimi.tiw.repository.ProjectUserRepository;

public class ProjectUserPopulator {

	private static ProjectUserPopulator instance;

	private ProjectUserPopulator() {
	}

	// Lazy initialization singleton pattern
	public static ProjectUserPopulator getInstance() {
		if (instance == null) {
			instance = new ProjectUserPopulator();
		}
		return instance;
	}

	public ProjectUserBean populate(HttpServletRequest req) throws SQLException {

		ProjectUserBean toReturn = new ProjectUserBean();
		String[] splittedProjectData = req.getParameter("selectedValuePersonalPrj").split("-");
		String projectId = splittedProjectData[0].trim();
		toReturn.setIdProgetto(Integer.parseInt(projectId));
		toReturn.setIdUtente(Integer.parseInt(req.getParameter("userId")));
		return toReturn;
	}

	public ProjectUserRepository convert(ProjectUserBean projectUserBean) {

		ProjectUserRepository toReturn = new ProjectUserRepository();
		toReturn.setIdProgetto(projectUserBean.getIdProgetto());
		toReturn.setIdUtente(projectUserBean.getIdUtente());
		return toReturn;

	}

}
