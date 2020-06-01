package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.repository.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle registration logic.
 */
public class FunctionRegister {

//	/**
//	 * @return true if an user whit same email is present. false otherwise.
//	 * @throws SQLException
//	 */
//	private boolean isUserPresent(UserDao userDao) throws AppCrash, SQLException {
//		// I want the exception is thrown to apply the logic for the error page
//
//		boolean toReturn = false;
//
//		ResultSet searchResult = userDao.searchSingleUser();
//		if (searchResult.next()) {
//			toReturn = true;
//		}
//
//		if (!searchResult.isClosed())
//			searchResult.close();
//		return toReturn;
//	}

	/**
	 * This method will check if user already exists on db.
	 * Is triggered by ajax call inside register.jsp page
	 * @param employeeBean
	 * @return
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public boolean isEmployeeAlreadyRegistered(EmployeeBean employeeBean) throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		EmployeeDao employeeDao = new EmployeeDao(conn);
		ResultSet searchResult = employeeDao.searchEmployee(employeeBean.getEmail());
		if (!searchResult.next())
				return false;
		return true;
	}

	public void register(EmployeeBean employeeBean) throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		registerUser(employeeBean, conn);
		conn.close();
	}

	/**
	 * @param employeeBean
	 * @param conn
	 * @throws AppCrash
	 * @throws SQLException
	 */
	private void registerUser(EmployeeBean employeeBean, Connection conn) throws AppCrash, SQLException {
		EmployeeDao employeeDao = new EmployeeDao(conn);
		EmployeeRepository employeeToSave = new EmployeeRepository();
		employeeToSave.setEmail(employeeBean.getEmail());

		String hashedPassword = Encription.hashPassword(employeeBean.getPassKey());
		employeeToSave.setPasskey(hashedPassword);


		employeeDao.insertUser(employeeToSave);

	}

//	/**
//	 * @param registerBean
//	 * @param conn
//	 * @throws AppCrash
//	 * @throws SQLException
//	 */
//	private void registerUserProject(RegisterBean registerBean, Connection conn)
//			throws AppCrash, SQLException {
//		UserDao userDao = new UserDao(conn);
//		UserProjectDao userProjectDao = new UserProjectDao(conn);
//
//		ProjectUserBean projectUserBean = new ProjectUserBean();
//		userDao.setEmail(registerBean.getEmail());
//
//		ResultSet userResult = userDao.searchSingleUser();
//		if (!userResult.next()) {
//			throw new AppCrash("Something went wrong. No users with this email.");
//		}
//		projectUserBean.setIdProgetto(registerBean.getIdProgetto());
//		projectUserBean.setIdUtente(userResult.getInt("id_utente"));
//
//		userResult.close();
//
//		ProjectUserRepository projectUserToSave = ProjectUserPopulator.getInstance().convert(projectUserBean);
//		userProjectDao.insertUtenteProgetto(projectUserToSave);
//
//	}

//	private boolean isDepartmentExists(DepartmentDao departmentDao) throws AppCrash, SQLException {
//		// I want the exception is thrown to apply the logic for the error page
//
//		boolean toReturn = false;
//		ResultSet searchResult = departmentDao.searchDepartment();
//		if (searchResult.next()) {
//			toReturn = true;
//		}
//
//		if (!searchResult.isClosed())
//			searchResult.close();
//
//		return toReturn;
//	}
//
//	private boolean isProjectExists(ProjectDao projectDao) throws AppCrash, SQLException {
//		// I want the exception is thrown to apply the logic for the error page
//
//		boolean toReturn = false;
//		ResultSet searchResult = projectDao.searchSingleProject();
//		if (searchResult.next()) {
//			toReturn = true;
//		}
//
//		if (!searchResult.isClosed())
//			searchResult.close();
//
//		return toReturn;
//	}

}
