package eu.polimi.tiw.dao;

import com.mysql.jdbc.exceptions.*;
import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.exception.*;

import java.sql.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 */
public class MeetingsDao extends GenericDao {
    private final String SEARCH_SINGLE_MEETING = "query.searchmeetingbyid";
    private final String SEARCH_ALL_MEETINGS_BY_EMPLOYEE = "query.searcownhmeeting";
    private final String INSERT_MEETING_QUERY = "query.insertmeeting";

    public MeetingsDao(Connection conn) {
        super(conn);
    }

    /**
     * Search all active meetings made by the employee
     * @param employeeBean
     * @return
     */
    public ResultSet searchOwnMeetingsByEmployee(EmployeeBean employeeBean) throws SQLException {
        ResultSet rs = null;

        Statement statement = this.getConn().createStatement();
        StringBuilder queryToExecute = new StringBuilder();
        queryToExecute.append(Config.getInstance().getProperty(SEARCH_ALL_MEETINGS_BY_EMPLOYEE));
        queryToExecute.append(this.whereConditionForOwnMeetingList(employeeBean.getEmployeeId(), employeeBean.getEmail()));
        rs = statement.executeQuery(queryToExecute.toString());
        return rs;
    }

    /**
     * Search all active meetings were the employee is invited
     * @param employeeBean
     * @return
     */
    public ResultSet searchInvitedMeetingsByEmployee(EmployeeBean employeeBean) throws SQLException {
        ResultSet rs = null;

        Statement statement = this.getConn().createStatement();
        StringBuilder queryToExecute = new StringBuilder();
        queryToExecute.append(Config.getInstance().getProperty(SEARCH_ALL_MEETINGS_BY_EMPLOYEE));
        queryToExecute.append(this.whereConditionForInvitedMeetingList(employeeBean.getEmployeeId(), employeeBean.getEmail()));
        rs = statement.executeQuery(queryToExecute.toString());
        return rs;
    }

    public ResultSet searchMeetingById(int meetingId) throws SQLException {
        ResultSet rs = null;

        Statement statement = this.getConn().createStatement();
        StringBuilder queryToExecute = new StringBuilder();
        queryToExecute.append(Config.getInstance().getProperty(SEARCH_SINGLE_MEETING));
        queryToExecute.append(this.whereConditionById(meetingId));
        rs = statement.executeQuery(queryToExecute.toString());
        return rs;
    }

    /**
     * Method to save the meeting
     * @param meetingToSave
     */
    public int insertMeeting(MeetingBean meetingToSave) throws ConstraintViolationException, SQLException, CreateMeetingException {

        try {
            Statement statement;
            ResultSet rs = null;
            StringBuilder updateQuery = new StringBuilder();
            statement = getConn().createStatement();
            updateQuery.append(Config.getInstance().getProperty(INSERT_MEETING_QUERY));
            updateQuery.append("'");
            updateQuery.append(meetingToSave.getMeetingTitle());
            updateQuery.append("','");
            updateQuery.append(meetingToSave.getMeetingData());
            updateQuery.append("','");
            updateQuery.append(meetingToSave.getMeetingHour());
            updateQuery.append("','");
            updateQuery.append(meetingToSave.getMeetingsDuration());
            updateQuery.append("','");
            updateQuery.append(meetingToSave.getInvolvedEmployeeNumber());
            updateQuery.append("','");
            updateQuery.append(meetingToSave.getMeetingUsernameOrganizator());
            updateQuery.append("');");

            int affectedRows = statement.executeUpdate(updateQuery.toString(), Statement.RETURN_GENERATED_KEYS);
            if (affectedRows == 0) {
                throw new CreateMeetingException("Creating meeting failed, no rows affected.");
            }

            int meetingId;
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    meetingId = generatedKeys.getInt(1);
                } else {
                    throw new CreateMeetingException("Creating meeting failed, no ID obtained.");
                }
            }
            // To add eventually logs
            statement.close();
            return meetingId;
        } catch (SQLException ex){
            if(ex.getSQLState().startsWith("23")) {
                throw new ConstraintViolationException(MOConstants.CONSTRAINTS_VIOLATION);
            } else{
                throw new SQLException(ex.getMessage());
            }
        }
    }

    public StringBuilder whereCondition() {
        StringBuilder whereConditionToReturn = new StringBuilder();
        return whereConditionToReturn;
    }

    public StringBuilder whereConditionById(int meetingId) {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where r." + MOConstants.MEETING_ID_DB + "=");
        whereConditionToReturn.append(meetingId);
        whereConditionToReturn.append(";");
        return whereConditionToReturn;
    }

    public StringBuilder whereConditionForOwnMeetingList(int employeedId, String employeeEmail) {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where rp." + MOConstants.EMPLOYEE_ID_DB + "='");
        whereConditionToReturn.append(employeedId);
        whereConditionToReturn.append("' and r." + MOConstants.MEETING_USERNAME_ORGANIZATOR_DB + "='");
        whereConditionToReturn.append(employeeEmail);
        whereConditionToReturn.append("' and r." + MOConstants.MEETING_DATE_DB + "> now()");
        whereConditionToReturn.append(";");
        return whereConditionToReturn;
    }

    public StringBuilder whereConditionForInvitedMeetingList(int employeedId, String employeeEmail) {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where rp." + MOConstants.EMPLOYEE_ID_DB + "='");
        whereConditionToReturn.append(employeedId);
        whereConditionToReturn.append("' and r." + MOConstants.MEETING_USERNAME_ORGANIZATOR_DB + "<>'");
        whereConditionToReturn.append(employeeEmail);
        whereConditionToReturn.append("' and r." + MOConstants.MEETING_DATE_DB + "> now()");
        whereConditionToReturn.append(";");
        return whereConditionToReturn;
    }
}
