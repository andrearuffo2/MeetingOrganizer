package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.SQLException;


import eu.polimi.tiw.bean.ProjectUserBean;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.DbConnection;
import eu.polimi.tiw.dao.UserProjectDao;
import eu.polimi.tiw.populator.ProjectUserPopulator;
import eu.polimi.tiw.repository.ProjectUserRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle subscription to a project by an user.
 */
public class FunctionAddProject {

	/**
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public void registerUserProject(ProjectUserBean projectUserBean) throws AppCrash, SQLException {

		Connection conn = DbConnection.getInstance().getConnection();
		UserProjectDao userProjectDao = new UserProjectDao(conn);

		projectUserBean.setIdProgetto(projectUserBean.getIdProgetto());
		projectUserBean.setIdUtente(projectUserBean.getIdUtente());

		ProjectUserRepository projectUserToSave = ProjectUserPopulator.getInstance().convert(projectUserBean);
		userProjectDao.insertUtenteProgetto(projectUserToSave);

	}

}
