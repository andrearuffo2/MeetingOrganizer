package eu.polimi.tiw.dao;

import java.sql.Connection;

public class EmployeeMeetingsDao extends GenericDao {
    public EmployeeMeetingsDao(Connection conn) {
        super(conn);
    }

    public StringBuilder whereCondition() {
        return null;
    }
}

