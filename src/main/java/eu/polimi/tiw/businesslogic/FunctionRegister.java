package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

import eu.polimi.tiw.bean.ProjectUserBean;
import eu.polimi.tiw.bean.RegisterBean;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.DbConnection;
import eu.polimi.tiw.dao.DepartmentDao;
import eu.polimi.tiw.dao.ProjectDao;
import eu.polimi.tiw.dao.UserDao;
import eu.polimi.tiw.dao.UserProjectDao;
import eu.polimi.tiw.populator.ProjectUserPopulator;
import eu.polimi.tiw.populator.UserRepositoryPopulator;
import eu.polimi.tiw.repository.ProjectUserRepository;
import eu.polimi.tiw.repository.UserRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle registration logic.
 */
public class FunctionRegister {

	/**
	 * @param registerBean
	 * @return true if an user whit same email is present. false otherwise.
	 * @throws SQLException
	 */
	private boolean isUserPresent(UserDao userDao) throws AppCrash, SQLException {
		// I want the exception is thrown to apply the logic for the error page

		boolean toReturn = false;

		ResultSet searchResult = userDao.searchSingleUser();
		if (searchResult.next()) {
			toReturn = true;
		}

		if (!searchResult.isClosed())
			searchResult.close();
		return toReturn;
	}

	public void register(RegisterBean registerBean) throws AppCrash, SQLException, PSQLException {
		Connection conn = DbConnection.getInstance().getConnection();

		registerUser(registerBean, conn);
		registerUserProject(registerBean, conn);

		conn.close();
	}

	/**
	 * @param registerBean
	 * @param conn
	 * @throws AppCrash
	 * @throws SQLException
	 */
	private void registerUser(RegisterBean registerBean, Connection conn) throws AppCrash, SQLException, PSQLException {
		UserDao userDao = new UserDao(conn);
		DepartmentDao departmentDao = new DepartmentDao(conn);
		ProjectDao projectDao = new ProjectDao(conn);

		projectDao.setIdProgetto(registerBean.getIdProgetto());
		userDao.setEmail(registerBean.getEmail());
		departmentDao.setDipartimento(registerBean.getDipartimento());

		if (this.isUserPresent(userDao)) {
			throw new AppCrash("An user with this email is already present");
		}

		if (!this.isDepartmentExists(departmentDao)) {
			throw new AppCrash("No department with this name exists");
		}

		if (!this.isProjectExists(projectDao)) {
			throw new AppCrash("No project with this name exists");
		}

		UserRepository userToSave = UserRepositoryPopulator.getInstance().convert(registerBean);

		userDao.insertUser(userToSave);

	}

	/**
	 * @param registerBean
	 * @param conn
	 * @throws AppCrash
	 * @throws SQLException
	 */
	private void registerUserProject(RegisterBean registerBean, Connection conn)
			throws AppCrash, SQLException, PSQLException {
		UserDao userDao = new UserDao(conn);
		UserProjectDao userProjectDao = new UserProjectDao(conn);

		ProjectUserBean projectUserBean = new ProjectUserBean();
		userDao.setEmail(registerBean.getEmail());

		ResultSet userResult = userDao.searchSingleUser();
		if (!userResult.next()) {
			throw new AppCrash("Something went wrong. No users with this email.");
		}
		projectUserBean.setIdProgetto(registerBean.getIdProgetto());
		projectUserBean.setIdUtente(userResult.getInt("id_utente"));

		userResult.close();

		ProjectUserRepository projectUserToSave = ProjectUserPopulator.getInstance().convert(projectUserBean);
		userProjectDao.insertUtenteProgetto(projectUserToSave);

	}

	private boolean isDepartmentExists(DepartmentDao departmentDao) throws AppCrash, SQLException {
		// I want the exception is thrown to apply the logic for the error page

		boolean toReturn = false;
		ResultSet searchResult = departmentDao.searchDepartment();
		if (searchResult.next()) {
			toReturn = true;
		}

		if (!searchResult.isClosed())
			searchResult.close();

		return toReturn;
	}

	private boolean isProjectExists(ProjectDao projectDao) throws AppCrash, SQLException {
		// I want the exception is thrown to apply the logic for the error page

		boolean toReturn = false;
		ResultSet searchResult = projectDao.searchSingleProject();
		if (searchResult.next()) {
			toReturn = true;
		}

		if (!searchResult.isClosed())
			searchResult.close();

		return toReturn;
	}

}
