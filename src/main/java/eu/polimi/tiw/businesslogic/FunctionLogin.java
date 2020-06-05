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
import eu.polimi.tiw.populator.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle user login logic.
 */
public class FunctionLogin extends GenericFunction{

	public FunctionLogin() {
	}

	/**
	 * Search single employee
	 * @param loginEmployeeBean
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public EmployeeBean searchEmployee(EmployeeBean loginEmployeeBean) throws AppCrash, SQLException {
		try(Connection conn = DbConnection.getInstance().getConnection();) {
		EmployeeDao employeeDao = new EmployeeDao(conn);
			if (!loginEmployeeBean.isRefreshPage()) {
				ResultSet searchResult = employeeDao.searchEmployee(loginEmployeeBean.getEmail());
				if (!searchResult.next()) {
					throw new AppCrash("An user with this email is not present in our system. Please register!");
				}

				if (!Encription.verifyHash(loginEmployeeBean.getPassKey(), searchResult.getString(MOConstants.EMPLOYEE_PASSKEY_DB))) {
					throw new AppCrash("The password is not correct. Please retry");
				}
				return EmployeeBeanPopulator.populateBean(searchResult);
			}
		}catch (SQLException e) {
			throw new SQLException("Something went wrong while querying the db");
		}

		//TODO to complete with refreshPage logic
		return loginEmployeeBean;
	}

	public List<MeetingBean> searchEmployeeOwnActiveMeetings(EmployeeBean employeeBean) throws AppCrash, SQLException {
		try(Connection conn = DbConnection.getInstance().getConnection();) {
			MeetingsDao meetingsDao = new MeetingsDao(conn);
			List<MeetingBean> listToReturn = new ArrayList();
			ResultSet searchResult = meetingsDao.searchOwnMeetingsByEmployee(employeeBean);

			while(searchResult.next()) {
				listToReturn.add(MeetingBeanPopulator.getInstance().populateMeeting(searchResult));
			}

			return listToReturn;
		}catch (SQLException e) {
			throw new SQLException("Something went wrong while querying the db");
		} catch (ParseException ex){
			throw new AppCrash("Something went wrong. Please contact support!");
		}
	}

	/**
	 * Search all active meetings were the user is invited
	 * @param employeeBean
	 * @return
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public List<MeetingBean> searchEmployeeInvitedActiveMeetings(EmployeeBean employeeBean) throws AppCrash, SQLException {
		try(Connection conn = DbConnection.getInstance().getConnection();) {
			MeetingsDao meetingsDao = new MeetingsDao(conn);
			List<MeetingBean> listToReturn = new ArrayList();
			ResultSet searchResult = meetingsDao.searchInvitedMeetingsByEmployee(employeeBean);

			while(searchResult.next()) {
				listToReturn.add(MeetingBeanPopulator.getInstance().populateMeeting(searchResult));
			}

			return listToReturn;
		}catch (SQLException e) {
			throw new SQLException("Something went wrong while querying the db");
		} catch (ParseException ex){
			throw new AppCrash("Something went wrong. Please contact support!");
		}
	}
}
