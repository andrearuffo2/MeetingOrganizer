package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.ArrayList;
import java.util.List;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import org.apache.log4j.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle user login logic.
 */
public class FunctionLogin extends GenericFunction{

	private static Logger log = Logger.getLogger(FunctionLogin.class);

	//TODO probably to delete all class
	public FunctionLogin() {
	}

	/**
	 * Search single employee
	 * @param loginEmployeeBean
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public EmployeeBean searchEmployee(EmployeeBean loginEmployeeBean) throws AppCrash, SQLException {
		log.info("FunctionLogin - searchEmployee - START");
		try(Connection conn = DbConnection.getInstance().getConnection();) {
			EmployeeDao employeeDao = new EmployeeDao(conn);
			ResultSet searchResult = employeeDao.searchEmployee(loginEmployeeBean.getEmail());
			if (!searchResult.next()) {
				log.error("FunctionLogin - searchEmployee - EmployeeNotFoundException");
				throw new EmployeeNotFoundException("An user with this email is not present in our system. Please register!");
			}

			if (!Encription.verifyHash(loginEmployeeBean.getPassKey(), searchResult.getString(MOConstants.EMPLOYEE_PASSKEY_DB))) {
				log.error("FunctionLogin - searchEmployee - BadPasswordException");
				throw new BadPasswordException("The password is not correct. Please retry");
			}
			log.info("FunctionLogin - searchEmployee - END");
			return EmployeeBeanPopulator.populateBean(searchResult);
		}catch (SQLException e) {
			log.error("FunctionLogin - searchEmployee - SQLException - somthing went wrong during DB connection");
			throw new SQLException("Something went wrong. Please");
		}
	}

}
