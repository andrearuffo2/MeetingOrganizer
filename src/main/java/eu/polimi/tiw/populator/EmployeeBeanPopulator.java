package eu.polimi.tiw.populator;

import eu.polimi.tiw.bean.*;
import eu.polimi.tiw.common.*;
import org.apache.commons.lang3.*;

import javax.security.auth.*;
import javax.servlet.http.*;
import java.sql.*;

public class EmployeeBeanPopulator {
    private static EmployeeBeanPopulator instance;

    private EmployeeBeanPopulator() {
    }

    public static EmployeeBeanPopulator getInstance() {
        if (instance == null) {
            instance = new EmployeeBeanPopulator();
        }

        return instance;
    }

    public static EmployeeBean populateRegister(HttpServletRequest req) {

        String name = req.getParameter(MOConstants.NAME);
        String surname = req.getParameter(MOConstants.SURNAME);
        String email = req.getParameter(MOConstants.EMAIL);
        String password = req.getParameter(MOConstants.PASSWORD);

        EmployeeBean toPopulate = new EmployeeBean();
        toPopulate.setName(name);
        toPopulate.setSurname(surname);
        toPopulate.setEmail(email);
        toPopulate.setPassKey(password);

        return toPopulate;
    }

    public static EmployeeBean populateLogin(HttpServletRequest req) {
        EmployeeBean toPopulate = new EmployeeBean();
        toPopulate.setEmail(req.getParameter(MOConstants.EMAIL));
        toPopulate.setPassKey(req.getParameter(MOConstants.PASSWORD));
        if (StringUtils.isNotEmpty(req.getParameter(MOConstants.REFRESH))) {
            //TODO da verificare
            toPopulate.setEmployeeId(Integer.parseInt(req.getParameter(MOConstants.EMPLOYEE_ID)));
            toPopulate.setRefreshPage(true);
        }

        return toPopulate;
    }

    public static EmployeeBean populateBean(ResultSet resultSet) throws SQLException {
        EmployeeBean toPopulate = new EmployeeBean();
        toPopulate.setEmployeeId(resultSet.getInt(MOConstants.EMPLOYEE_ID));
        toPopulate.setName(resultSet.getString(MOConstants.NAME_DB));
        toPopulate.setSurname(resultSet.getString(MOConstants.SURNAME_DB));
        toPopulate.setEmail(resultSet.getString(MOConstants.EMAIL));
        toPopulate.setPassKey(resultSet.getString(MOConstants.PASSKEY_DB));
        return toPopulate;
    }
}
