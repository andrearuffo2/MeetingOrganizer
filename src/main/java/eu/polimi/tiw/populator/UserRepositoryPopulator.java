package eu.polimi.tiw.populator;

import java.sql.ResultSet;
import java.sql.SQLException;

import eu.polimi.tiw.bean.RegisterBean;
import eu.polimi.tiw.repository.UserRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class is able to populate to UserRepository.
 */
public class UserRepositoryPopulator {

	private static UserRepositoryPopulator instance;

	private UserRepositoryPopulator() {
	}

	// Lazy initialization singleton pattern
	public static UserRepositoryPopulator getInstance() {
		if (instance == null) {
			instance = new UserRepositoryPopulator();
		}
		return instance;
	}

	public UserRepository populate(ResultSet rs) throws SQLException {

		UserRepository toReturn = new UserRepository();
		toReturn.setNome(rs.getString("nome"));
		toReturn.setCognome(rs.getString("cognome"));
		toReturn.setEmail(rs.getString("email"));
		toReturn.setPassword(rs.getString("password"));
		toReturn.setIdUtente(rs.getInt("id_utente"));
		toReturn.setIdDipartimento(rs.getInt("id_dipartimento"));
		return toReturn;
	}

	public UserRepository convert(RegisterBean registerBean) {
		UserRepository toReturn = new UserRepository();
		toReturn.setNome(registerBean.getName());
		toReturn.setCognome(registerBean.getSurname());
		toReturn.setEmail(registerBean.getEmail());
		toReturn.setPassword(registerBean.getPassword());
		toReturn.setIdDipartimento(registerBean.getIdDipartimento());
		return toReturn;

	}

}
