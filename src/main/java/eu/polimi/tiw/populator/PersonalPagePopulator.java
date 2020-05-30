package eu.polimi.tiw.populator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import eu.polimi.tiw.bean.PersonalPageBean;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class populate the PersonalPageBean from the HttpRequest.
 */
public class PersonalPagePopulator {

	private static PersonalPagePopulator instance;

	private PersonalPagePopulator() {
	}

	// Lazy initialization singleton pattern
	public static PersonalPagePopulator getInstance() {
		if (instance == null) {
			instance = new PersonalPagePopulator();
		}
		return instance;
	}

	/**
	 * 
	 * @param rs
	 * @return Populated PersonalPageBean
	 * @throws SQLException
	 */
	public PersonalPageBean populate(ResultSet rs) throws SQLException {
		PersonalPageBean toReturn = new PersonalPageBean();
		toReturn.setIdProgetto(rs.getInt("id_progetto"));
		toReturn.setDaysOfTheMonth(getAllDaysOfTheCurrentMonth());
		toReturn.setNomeProgetto(rs.getString("nome"));
		return toReturn;
	}

	public List<String> getAllDaysOfTheCurrentMonth() {
		List<String> listOfDays = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		listOfDays.add(df.format(cal.getTime()));
		for (int i = 1; i < maxDay; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i + 1);
			listOfDays.add(df.format(cal.getTime()));
		}
		return listOfDays;
	}

}
