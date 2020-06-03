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

	/**
	 * This method will check if user already exists on db.
	 * Is triggered by ajax call inside register.jsp page
	 * @param employeeBean
	 * @return
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public boolean isEmployeeAlreadyRegistered(EmployeeBean employeeBean) throws AppCrash, SQLException {
		try(Connection conn = DbConnection.getInstance().getConnection();) {
			EmployeeDao employeeDao = new EmployeeDao(conn);
			ResultSet searchResult = employeeDao.searchEmployee(employeeBean.getEmail());
			if (!searchResult.next()) {
				return false;
			}
			return true;
		}catch (SQLException e) {
			throw new SQLException("Something went wrong during db connection..");
		}
	}

	/**
	 * This method is called to save a new employee
	 * @param employeeBean
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public void register(EmployeeBean employeeBean) throws AppCrash, SQLException {
		try(Connection conn = DbConnection.getInstance().getConnection();) {
			EmployeeDao employeeDao = new EmployeeDao(conn);
			employeeDao.insertUser(employeeBean);
		}catch (SQLException e) {
			throw new SQLException("Something went wrong during db connection..");
		}
	}
}
