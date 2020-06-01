package eu.polimi.tiw.dao;

import eu.polimi.tiw.common.*;
import eu.polimi.tiw.repository.*;
import org.apache.commons.lang3.*;

import java.sql.*;

public class EmployeeDao extends GenericDao {
    private String email;
    private final String SEARCH_QUERY = "query.seachemployee";
    private final String INSERT_EMPLOYEE_QUERY = "query.inseriscidipendente";

    public EmployeeDao(Connection conn) {
        super(conn);
    }

    public ResultSet searchEmployee(String email) throws AppCrash {
        ResultSet rs = null;
        this.email = email;

        try {
            Statement statement = this.getConn().createStatement();
            if (StringUtils.isNotEmpty(email)) {
                StringBuilder queryToExecute = new StringBuilder();
                queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
                queryToExecute.append(this.whereCondition());
                rs = statement.executeQuery(queryToExecute.toString());
                return rs;
            } else {
                throw new AppCrash("No email was set.");
            }
        } catch (SQLException var5) {
            var5.printStackTrace();
            return rs;
        }
    }

    public StringBuilder whereCondition() {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where email='");
        whereConditionToReturn.append(this.email);
        whereConditionToReturn.append("';");
        return whereConditionToReturn;
    }

    public void insertUser(EmployeeRepository employeeToSave) {

        Statement statement;
        StringBuilder updateQuery = new StringBuilder();
        try {
            statement = getConn().createStatement();
            updateQuery.append(Config.getInstance().getProperty(INSERT_EMPLOYEE_QUERY));
            updateQuery.append("'");
            updateQuery.append(employeeToSave.getName());
            updateQuery.append("','");
            updateQuery.append(employeeToSave.getSurname());
            updateQuery.append("','");
            updateQuery.append(employeeToSave.getEmail());
            updateQuery.append("','");
            updateQuery.append(employeeToSave.getPasskey());
            updateQuery.append("');");

            statement.executeUpdate(updateQuery.toString());
            // To add eventually logs
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}