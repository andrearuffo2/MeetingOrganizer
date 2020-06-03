package eu.polimi.tiw.dao;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;

import java.sql.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 */
public class MeetingsDao extends GenericDao {
    private final String SEARCH_ALL_MEETINGS_BY_EMPLOYEE = "query.searcownhmeeting";

    public MeetingsDao(Connection conn) {
        super(conn);
    }

    /**
     * Search all active meetings made by the employee
     * @param employeeBean
     * @return
     */
    public ResultSet searchOwnMeetingsByEmployee(EmployeeBean employeeBean) {
        ResultSet rs = null;

        try {
            Statement statement = this.getConn().createStatement();
            StringBuilder queryToExecute = new StringBuilder();
            queryToExecute.append(Config.getInstance().getProperty(SEARCH_ALL_MEETINGS_BY_EMPLOYEE));
            queryToExecute.append(this.whereConditionForOwnMeetingList(employeeBean.getEmployeeId()));
            rs = statement.executeQuery(queryToExecute.toString());
            return rs;
        } catch (SQLException var5) {
            var5.printStackTrace();
            return rs;
        }
    }

    /**
     * Search all active meetings were the employee is invited
     * @param employeeBean
     * @return
     */
    public ResultSet searchInvitedMeetingsByEmployee(EmployeeBean employeeBean) {
        ResultSet rs = null;

        try {
            Statement statement = this.getConn().createStatement();
            StringBuilder queryToExecute = new StringBuilder();
            queryToExecute.append(Config.getInstance().getProperty(SEARCH_ALL_MEETINGS_BY_EMPLOYEE));
            queryToExecute.append(this.whereConditionForInvitedMeetingList(employeeBean.getEmployeeId()));
            rs = statement.executeQuery(queryToExecute.toString());
            return rs;
        } catch (SQLException var5) {
            var5.printStackTrace();
            return rs;
        }
    }

    public StringBuilder whereCondition() {
        StringBuilder whereConditionToReturn = new StringBuilder();
        return whereConditionToReturn;
    }

    public StringBuilder whereConditionForOwnMeetingList(int employeedId) {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where rp." + MOConstants.EMPLOYEE_ID + "='");
        whereConditionToReturn.append(employeedId);
        whereConditionToReturn.append("' and r." + MOConstants.MEETING_USERNAME_ORGANIZER + "='");
        whereConditionToReturn.append(employeedId);
        whereConditionToReturn.append("';");
        return whereConditionToReturn;
    }

    public StringBuilder whereConditionForInvitedMeetingList(int employeedId) {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where rp." + MOConstants.EMPLOYEE_ID + "='");
        whereConditionToReturn.append(employeedId);
        whereConditionToReturn.append("' and r." + MOConstants.MEETING_USERNAME_ORGANIZER + "<>'");
        whereConditionToReturn.append(employeedId);
        whereConditionToReturn.append("';");
        return whereConditionToReturn;
    }
}
