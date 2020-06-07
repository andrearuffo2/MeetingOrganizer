package eu.polimi.tiw.businesslogic;

import com.mysql.jdbc.exceptions.*;
import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;
import eu.polimi.tiw.request.*;
import org.apache.commons.lang3.exception.*;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle save meetings logic.
 */
public class FunctionSaveMeetings extends GenericFunction{

    /**
     * This method is called to save a new meeting
     * @param saveMeetingRequest
     * @throws AppCrash
     * @throws SQLException
     * @throws MySQLIntegrityConstraintViolationException
     */
    public int insertNewMeeting(SaveMeetingRequest saveMeetingRequest) throws SQLException, AppCrash {
        int savedMeetingId = 0;
        try(Connection conn = DbConnection.getInstance().getConnection();) {
            MeetingsDao meetingsDao = new MeetingsDao(conn);

            MeetingBean meetingBeanToSave = MeetingBeanPopulator.getInstance().populateMeetingInsert(saveMeetingRequest);
            savedMeetingId = meetingsDao.insertMeeting(meetingBeanToSave);

        } catch (SQLException e) {
            throw new SQLException("Something went wrong. Please contact support!");
        } catch(ParseException ex){
            throw new AppCrash(ex.getMessage());
        }
        return savedMeetingId;
    }



    /**
     * This method is called to save a new meetingsEmployee relations into db
     * @param employeeMeetingBean
     * @throws AppCrash
     * @throws SQLException
     * @throws MySQLIntegrityConstraintViolationException
     */
    public void insertNewMeetingEmployeesRelations(EmployeeMeetingBean employeeMeetingBean) throws SQLException {
        try(Connection conn = DbConnection.getInstance().getConnection();) {

            EmployeeMeetingsDao employeeMeetingsDao = new EmployeeMeetingsDao(conn);
            employeeMeetingsDao.insertMeetingEmployeeRelation(employeeMeetingBean);

        }catch (SQLException e) {
            throw new SQLException("Something went wrong. Please contact support!");
        }
    }

    /**
     * This method is called to get a meeting by id
     * @param meetingId
     * @throws SQLException
     */
    public MeetingBean searchMeetingById(int meetingId) throws SQLException, AppCrash {
        try(Connection conn = DbConnection.getInstance().getConnection();) {

            MeetingsDao meetingsDao = new MeetingsDao(conn);
            ResultSet resultSet = meetingsDao.searchMeetingById(meetingId);
            MeetingBean meetingBeanToReturn = null;
            if(resultSet.next()) {
                meetingBeanToReturn = MeetingBeanPopulator.getInstance().populateMeeting(resultSet);
            }
            return meetingBeanToReturn;

        }catch (SQLException e) {
            throw new SQLException("Something went wrong. Please contact support!");
        } catch (ParseException ex){
            throw new AppCrash("Something went wrong. Please contact support!");
        }
    }

    public List<EmployeeBean> searchInvitedEmployeesByEmail(List<String> invitedEmployeesEmail) throws AppCrash, SQLException {
        List<EmployeeBean> employeeListToReturn = new ArrayList<>();

        try(Connection conn = DbConnection.getInstance().getConnection();) {
            EmployeeDao employeeDao = new EmployeeDao(conn);

            //If the employee isnt found on the db throw new appcrash
            for (String email : invitedEmployeesEmail) {
                ResultSet resultSet = employeeDao.searchEmployee(email);
                if (!resultSet.next()) {
                    throw new AppCrash("Something went wrong. Please contact support!");
                }
                employeeListToReturn.add(EmployeeBeanPopulator.populateBean(resultSet));
            }
        }catch (SQLException e) {
            throw new SQLException("Something went wrong. Please contact support!");
        }
        return employeeListToReturn;
    }
}
