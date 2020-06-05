package eu.polimi.tiw.businesslogic;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import eu.polimi.tiw.dao.*;
import eu.polimi.tiw.populator.*;

import java.sql.*;

public abstract class GenericFunction {

    /**
     * Search single employee
     * @param email
     * @throws AppCrash
     * @throws SQLException
     */
    public EmployeeBean searchEmployee(String email) throws AppCrash, SQLException {
        try(Connection conn = DbConnection.getInstance().getConnection();) {
            EmployeeDao employeeDao = new EmployeeDao(conn);
            ResultSet searchResult = employeeDao.searchEmployee(email);
            if (!searchResult.next()) {
                throw new AppCrash("An user with this email is not present in our system. Please register!");
            }
            return EmployeeBeanPopulator.populateBean(searchResult);
        }catch (SQLException e) {
            throw new SQLException("Something went wrong while querying the db");
        }
    }
}
