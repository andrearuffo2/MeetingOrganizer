package eu.polimi.tiw.businesslogic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import eu.polimi.tiw.bean.PersonalPageBean;
import eu.polimi.tiw.common.AppCrash;
import eu.polimi.tiw.common.DbConnection;
import eu.polimi.tiw.dao.DepartmentDao;
import eu.polimi.tiw.dao.ProjectDao;
import eu.polimi.tiw.repository.DepartmentRepository;
import eu.polimi.tiw.repository.ProjectRepository;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT This class returns registration data's for registration
 *        page.
 */
public class FunctionRetrieveRegisterData {

	private final String PROJECTID = "id_progetto";
	private final String NAME = "nome";
	private final String DEPARTMENTID = "id_dipartimento";

	/**
	 * @return list of project data.
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public List<ProjectRepository> retrieveProjectsData() throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		ProjectDao projectDao = new ProjectDao(conn);
		List<ProjectRepository> projectNameList = new ArrayList<>();

		ResultSet rs = projectDao.getAllProjects();

		try {
			while (rs.next()) {
				ProjectRepository singleProject = new ProjectRepository();
				singleProject.setIdProgetto(rs.getInt(PROJECTID));
				singleProject.setNome(rs.getString(NAME));
				projectNameList.add(singleProject);
			}
		} catch (SQLException e) {
			conn.close();
			throw new SQLException(e.getMessage());
		}

		conn.close();
		return projectNameList;

	}

	/**
	 * @return list of projects that can be subscribed by the user.
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public List<ProjectRepository> retrieveProjectsDataForLoginPage(List<PersonalPageBean> singleUserProjects)
			throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		ProjectDao projectDao = new ProjectDao(conn);
		List<ProjectRepository> projectNameList = new ArrayList<>();
		List<ProjectRepository> toReturn = new ArrayList<>();

		ResultSet rs = projectDao.getAllProjects();

		try {
			while (rs.next()) {
				ProjectRepository singleProject = new ProjectRepository();
				singleProject.setIdProgetto(rs.getInt(PROJECTID));
				singleProject.setNome(rs.getString(NAME));
				projectNameList.add(singleProject);
			}
		} catch (SQLException e) {
			conn.close();
			throw new SQLException(e.getMessage());
		}

		// To avoid concurrent modification
		toReturn.addAll(projectNameList);

		for (PersonalPageBean singleProjectSubscribed : singleUserProjects) {
			for (ProjectRepository singleProjectSubscribable : projectNameList) {
				if (singleProjectSubscribable.getIdProgetto() == singleProjectSubscribed.getIdProgetto()) {
					toReturn.remove(singleProjectSubscribable);
				}
			}
		}

		rs.close();
		conn.close();
		return toReturn;

	}

	/**
	 * @return list of department data.
	 * @throws AppCrash
	 * @throws SQLException
	 */
	public List<DepartmentRepository> retrieveDepartmentsData() throws AppCrash, SQLException {
		Connection conn = DbConnection.getInstance().getConnection();
		DepartmentDao projectDao = new DepartmentDao(conn);
		List<DepartmentRepository> departmentList = new ArrayList<>();

		ResultSet rs = projectDao.getAllDepartment();

		try {
			while (rs.next()) {
				DepartmentRepository singleDepartment = new DepartmentRepository();
				singleDepartment.setIdDipartimento(rs.getInt(DEPARTMENTID));
				singleDepartment.setNome(rs.getString(NAME));
				departmentList.add(singleDepartment);
			}
		} catch (SQLException e) {
			conn.close();
			throw new SQLException(e.getMessage());
		}
		conn.close();
		return departmentList;
	}

}
