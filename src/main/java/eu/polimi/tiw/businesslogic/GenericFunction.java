package eu.polimi.tiw.businesslogic;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import org.apache.log4j.*;

import java.sql.*;
import java.text.*;
import java.util.*;

public abstract class GenericFunction {

    private static Logger log = Logger.getLogger(GenericFunction.class);

    /**
     * Search single employee
     * @param email
     * @throws AppCrash
     * @throws SQLException
     */
    public EmployeeBean searchEmployee(String email) throws AppCrash, SQLException {
        log.info("GenericFunction - searchEmployee - START");
        try(Connection conn = DbConnection.getInstance().getConnection();) {
            EmployeeDao employeeDao = new EmployeeDao(conn);
            ResultSet searchResult = employeeDao.searchEmployee(email);
            if (!searchResult.next()) {
                log.error("GenericFunction - searchEmployee - EmployeeNotFoundException");
                throw new EmployeeNotFoundException("An user with this email is not present in our system. Please contact support!");
            }
            log.info("GenericFunction - searchEmployee - END");
            return EmployeeBeanPopulator.populateBean(searchResult);
        }catch (SQLException e) {
            log.error("FunctionLogin - searchEmployee - SQLException - somthing went wrong during DB connection");
            throw new SQLException("Something went wrong. Please contact support!");
        }
    }

    /**
     * Search all active meetings were the user is invited
     * @param employeeBean
     * @return List<MeetingBean>
     * @throws AppCrash
     */
    public List<MeetingBean> searchEmployeeInvitedActiveMeetings(EmployeeBean employeeBean) throws AppCrash {
        log.info("GenericFunction - searchEmployeeInvitedActiveMeetings - START");
        try(Connection conn = DbConnection.getInstance().getConnection();) {
            MeetingsDao meetingsDao = new MeetingsDao(conn);
            List<MeetingBean> listToReturn = new ArrayList();
            ResultSet searchResult = meetingsDao.searchInvitedMeetingsByEmployee(employeeBean);

            while(searchResult.next()) {
                listToReturn.add(MeetingBeanPopulator.getInstance().populateMeeting(searchResult));
            }
            log.info("GenericFunction - searchEmployeeInvitedActiveMeetings - END");
            return listToReturn;
        }catch (SQLException | ParseException e) {
            log.error("GenericFunction - searchEmployeeInvitedActiveMeetings - SQLException or ParseException");
            throw new AppCrash("Something went wrong. Please contact support!");
        }
    }
}
