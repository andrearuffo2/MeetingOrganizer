package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class FunctionLogin {

	public FunctionLogin() {
	}

	public void searchEmployee(EmployeeBean employeeBean) throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		EmployeeDao employeeDao = new EmployeeDao(conn);
		if (!employeeBean.isRefreshPage()) {
			ResultSet searchResult = employeeDao.searchEmployee(employeeBean.getEmail());
			if (!searchResult.next()) {
				throw new AppCrash("An user with this email is not present in our system. Please register!");
			}

			employeeBean.setEmployeeId(searchResult.getInt(MOConstants.EMPLOYEE_ID));
			if (!Encription.verifyHash(employeeBean.getPassKey(), searchResult.getString(MOConstants.EMPLOYEE_PASSKEY))) {
				throw new AppCrash("The password is not correct. Please retry");
			}
		}
	}

	public List<MeetingBean> searchEmployeeActiveMeetings(EmployeeBean employeeBean) throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		MeetingsDao meetingsDao = new MeetingsDao(conn);
		List<MeetingBean> listToReturn = new ArrayList();
		ResultSet searchResult = meetingsDao.searchAllMeetingsByEmployee(employeeBean);

		while(searchResult.next()) {
			listToReturn.add(MeetingBeanPopulator.getInstance().populate(searchResult));
		}

		return listToReturn;
	}
}
