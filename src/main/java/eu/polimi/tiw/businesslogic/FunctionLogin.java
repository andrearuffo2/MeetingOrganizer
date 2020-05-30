package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eu.polimi.tiw.bean.PersonalPageBean;
import eu.polimi.tiw.bean.RegisterBean;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.DbConnection;
import eu.polimi.tiw.common.Encription;
import eu.polimi.tiw.dao.UserDao;
import eu.polimi.tiw.dao.UserProjectDao;
import eu.polimi.tiw.populator.PersonalPagePopulator;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class handle user login logic.
 */
public class FunctionLogin {

	/**
	 * @param registerBean
	 * @param conn
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public void searchUser(RegisterBean registerBean) throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		UserDao userDao = new UserDao(conn);

		// Added logic for simply refresh the personal user page.
		if (!registerBean.getRefreshPage()) {
			userDao.setEmail(registerBean.getEmail());
			ResultSet searchResult = userDao.searchSingleUser();
			if (!searchResult.next()) {
				throw new AppCrash("An user with this email is not present in our system. Please register!");
			}

			registerBean.setIdUtente(searchResult.getInt("id_utente"));

			if (!Encription.verifyHash(registerBean.getPassword(), searchResult.getString("password"))) {
				throw new AppCrash("The passwor is not correct. Please retry");
			}
		}
	}

	/**
	 * @param registerBean
	 * @return List of PersonalPageBean that represents the project of a single
	 *         users.
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public List<PersonalPageBean> searchUsersProjects(RegisterBean registerBean) throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		UserProjectDao userDao = new UserProjectDao(conn);
		List<PersonalPageBean> listToReturn = new ArrayList<>();

		userDao.setIdUtente(registerBean.getIdUtente());
		ResultSet searchResult = userDao.searchProgettiUtente();

		while (searchResult.next()) {
			listToReturn.add(PersonalPagePopulator.getInstance().populate(searchResult));
		}
		return listToReturn;
	}
}
