package eu.polimi.tiw.dao;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;

import java.sql.*;

public class EmployeeMeetingsDao extends GenericDao {
    public EmployeeMeetingsDao(Connection conn) {
        super(conn);
    }

    private final String INSERT_EMPLOYEE_MEETING_QUERY = "query.insertemployeemeeting";

    /**
     * Method to save the the relation between employee and meeting
     * @param employeeMeetingBean
     */
    public void insertMeetingEmployeeRelation(EmployeeMeetingBean employeeMeetingBean) throws SQLException {

        Statement statement;
        StringBuilder updateQuery = new StringBuilder();
        statement = getConn().createStatement();
        updateQuery.append(Config.getInstance().getProperty(INSERT_EMPLOYEE_MEETING_QUERY));
        updateQuery.append(employeeMeetingBean.getMeetingId());
        updateQuery.append(",");
        updateQuery.append(employeeMeetingBean.getEmployeeId());
        updateQuery.append(");");

        statement.executeUpdate(updateQuery.toString());
        // To add eventually logs
        statement.close();

    }


    public StringBuilder whereCondition() {
        return null;
    }
}

