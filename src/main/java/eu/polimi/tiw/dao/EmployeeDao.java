package eu.polimi.tiw.dao;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import org.apache.commons.lang3.*;

import java.sql.*;
/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 */
public class EmployeeDao extends GenericDao {
    private String email;
    private final String SEARCH_QUERY = "query.seachemployee";
    private final String INSERT_EMPLOYEE_QUERY = "query.insertemployee";

    public EmployeeDao(Connection conn) {
        super(conn);
    }

    /**
     * Searching employee existance
     * @param email
     * @return
     * @throws AppCrash
     */
    public ResultSet searchEmployee(String email) throws AppCrash, SQLException {
        ResultSet rs = null;
        this.email = email;

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
    }

    /**
     * @return
     * @throws AppCrash
     */
    public ResultSet searchAllEmployee() throws SQLException {

        Statement statement = this.getConn().createStatement();
        StringBuilder queryToExecute = new StringBuilder();
        queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
        ResultSet rs = statement.executeQuery(queryToExecute.toString());
        return rs;
    }

    public StringBuilder whereCondition() {
        StringBuilder whereConditionToReturn = new StringBuilder();
        whereConditionToReturn.append(" where email='");
        whereConditionToReturn.append(this.email);
        whereConditionToReturn.append("';");
        return whereConditionToReturn;
    }

    /**
     * Method to save the user
     * @param employeeToSave
     */
    public void insertUser(EmployeeBean employeeToSave) throws SQLException {

        Statement statement;
        StringBuilder updateQuery = new StringBuilder();
        statement = getConn().createStatement();
        updateQuery.append(Config.getInstance().getProperty(INSERT_EMPLOYEE_QUERY));
        updateQuery.append("'");
        updateQuery.append(employeeToSave.getName());
        updateQuery.append("','");
        updateQuery.append(employeeToSave.getSurname());
        updateQuery.append("','");
        updateQuery.append(employeeToSave.getEmail());
        updateQuery.append("','");
        updateQuery.append(Encription.hashPassword(employeeToSave.getPassKey()));
        updateQuery.append("');");

        statement.executeUpdate(updateQuery.toString());
        // To add eventually logs
        statement.close();
    }
}