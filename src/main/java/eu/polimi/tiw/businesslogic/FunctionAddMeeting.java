package eu.polimi.tiw.businesslogic;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.populator.*;

import java.sql.*;
import java.util.*;

public class FunctionAddMeeting {

    /**
     * @return
     * @throws AppCrash
     * @throws SQLException
     */
    public List<EmployeeBean> searchAllEmployee() throws AppCrash, SQLException {
        try(Connection conn = DbConnection.getInstance().getConnection();) {
            EmployeeDao employeeDao = new EmployeeDao(conn);
            List<EmployeeBean> listToReturn = new ArrayList();
            ResultSet searchResult = employeeDao.searchAllEmployee();
            while(searchResult.next()) {
                listToReturn.add(EmployeeBeanPopulator.getInstance().populateBean(searchResult));
            }
            return listToReturn;
        }catch (SQLException e) {
            throw new SQLException("Something went wrong during db connection..");
        }
    }
}
