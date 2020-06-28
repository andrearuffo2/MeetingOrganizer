package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.controller.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.exception.*;
import org.apache.log4j.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle registration logic.
 */
public class FunctionRegister extends GenericFunction{

	private static Logger log = Logger.getLogger(FunctionRegister.class);
	/**
	 * This method will check if user already exists on db.
	 * Is triggered by ajax call inside register.jsp page
	 * @param employeeBean
	 * @return
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public boolean isEmployeeAlreadyRegistered(EmployeeBean employeeBean) throws AppCrash, SQLException {
		log.info("FunctionRegister - isEmployeeAlreadyRegistered - START");
		try(Connection conn = DbConnection.getInstance().getConnection();) {
			EmployeeDao employeeDao = new EmployeeDao(conn);
			ResultSet searchResult = employeeDao.searchEmployee(employeeBean.getEmail());
			if (!searchResult.next()) {
				return false;
			}
			log.info("FunctionRegister - isEmployeeAlreadyRegistered - END");
			return true;
		}catch (SQLException e) {
			throw new SQLException("Something went wrong while querying the db");
		}
	}

	/**
	 * This method is called to save a new employee
	 * @param employeeBean
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public void register(EmployeeBean employeeBean) throws AppCrash, SQLException {
		log.info("FunctionRegister - register - START");
		try(Connection conn = DbConnection.getInstance().getConnection();) {
			EmployeeDao employeeDao = new EmployeeDao(conn);
			employeeDao.insertUser(employeeBean);
			log.info("FunctionRegister - register - END");
		}catch (SQLException e) {
			throw new SQLException("Something went wrong while querying the db");
		}
	}
}
