package eu.polimi.tiw.dao;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;

import java.sql.*;

public class MeetingsDao extends GenericDao {
    private final String SEARCH_ALL_MEETINGS_BY_EMPLOYEE = "query.searchmeeting";

    public MeetingsDao(Connection conn) {
        super(conn);
    }

    public ResultSet searchAllMeetingsByEmployee(EmployeeBean employeeBean) {
        ResultSet rs = null;

        try {
            Statement statement = this.getConn().createStatement();
            StringBuilder queryToExecute = new StringBuilder();
            queryToExecute.append(Config.getInstance().getProperty("query.searchmeeting"));
            queryToExecute.append(this.whereCondition());
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

    public StringBuilder whereConditionForMeetingList(int employeedId) {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where rp." + MOConstants.EMPLOYEE_ID + "='");
        whereConditionToReturn.append(employeedId);
        whereConditionToReturn.append("';");
        return whereConditionToReturn;
    }
}
