package eu.polimi.tiw.businesslogic;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.exception.*;
import eu.polimi.tiw.populator.*;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle user homepage logic.
 */
public class FunctionPopulateHomePage extends GenericFunction {

    /**
     * Search all active meetings made by the user
     * @param employeeBean
     * @return List<MeetingBean>
     * @throws AppCrash
     */
    public List<MeetingBean> searchEmployeeOwnActiveMeetings(EmployeeBean employeeBean) throws AppCrash {
        try(Connection conn = DbConnection.getInstance().getConnection();) {
            MeetingsDao meetingsDao = new MeetingsDao(conn);
            List<MeetingBean> listToReturn = new ArrayList();
            ResultSet searchResult = meetingsDao.searchOwnMeetingsByEmployee(employeeBean);

            while(searchResult.next()) {
                listToReturn.add(MeetingBeanPopulator.getInstance().populateMeeting(searchResult));
            }

            return listToReturn;
        }catch (SQLException | ParseException e) {
            throw new AppCrash("Something went wrong. Please contact support!");
        }
    }

    /**
     * @return List<EmployeeBean>
     * @throws SQLException
     */
    public List<EmployeeBean> searchAllEmployee() throws SQLException {
        try(Connection conn = DbConnection.getInstance().getConnection();) {
            EmployeeDao employeeDao = new EmployeeDao(conn);
            List<EmployeeBean> listToReturn = new ArrayList();
            ResultSet searchResult = employeeDao.searchAllEmployee();
            while(searchResult.next()) {
                listToReturn.add(EmployeeBeanPopulator.getInstance().populateBean(searchResult));
            }
            return listToReturn;
        }catch (SQLException e) {
            throw new SQLException("Something went wrong. Please contact support!");
        }
    }

    /**
     * returns the completeEmployeeList minus the logged one.
     *
     * @param completeEmployeeList
     * @param loggedEmployeeEmail
     * @return List<EmployeeBean>
     */
    public List<EmployeeBean> removeLoggedEmployeeFromEmployeeList(List<EmployeeBean> completeEmployeeList, String loggedEmployeeEmail){

        List<EmployeeBean> toReturn = new ArrayList<>();
        toReturn.addAll(completeEmployeeList);

        for(EmployeeBean employeeBean : completeEmployeeList){
            if(loggedEmployeeEmail.equalsIgnoreCase(employeeBean.getEmail())){
                toReturn.remove(employeeBean);
            }
        }

        return toReturn;
    }
}
