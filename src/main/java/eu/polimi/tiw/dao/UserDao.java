package eu.polimi.tiw.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang3.StringUtils;

import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.Config;
import eu.polimi.tiw.common.Encription;
import eu.polimi.tiw.repository.UserRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class has method to query the utenti table on the db.
 */
public class UserDao extends GenericDao {

	private String email;
	private final String SEARCH_QUERY = "query.ricercautente";
	private final String INSERT_USER_QUERY = "query.inserisciutente";

	/**
	 * Default constructor
	 */
	public UserDao(Connection conn) {
		super(conn);

	}

	public UserDao(String email, Connection conn) {
		super(conn);
		this.email = email;
	}

	public ResultSet searchSingleUser() throws AppCrash {
		Statement statement;
		ResultSet rs = null;
		try {
			statement = getConn().createStatement();
			if (StringUtils.isNotEmpty(this.email)) {
				StringBuilder queryToExecute = new StringBuilder();
				queryToExecute.append(Config.getInstance().getProperty(SEARCH_QUERY));
				queryToExecute.append(whereCondition());
				rs = statement.executeQuery(queryToExecute.toString());
				return rs;
			} else {
				throw new AppCrash("No email was set.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;

	}

	@Override
	public StringBuilder whereCondition() {

		StringBuilder whereConditionToReturn = new StringBuilder();
		whereConditionToReturn.append(" where email='");
		whereConditionToReturn.append(this.email);
		whereConditionToReturn.append("';");
		return whereConditionToReturn;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void insertUser(UserRepository user) {

		Statement statement;
		StringBuilder updateQuery = new StringBuilder();
		try {
			statement = getConn().createStatement();
			updateQuery.append(Config.getInstance().getProperty(INSERT_USER_QUERY));
			updateQuery.append("'");
			updateQuery.append(user.getNome());
			updateQuery.append("','");
			updateQuery.append(user.getCognome());
			updateQuery.append("','");
			updateQuery.append(user.getIdDipartimento());
			updateQuery.append("','");
			updateQuery.append(Encription.hashPassword(user.getPassword()));
			updateQuery.append("','");
			updateQuery.append(user.getEmail());
			updateQuery.append("');");

			statement.executeUpdate(updateQuery.toString());
			// To add eventually logs
			statement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
