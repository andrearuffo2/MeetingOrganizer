package eu.polimi.tiw.bean;

import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        Represent a single day with all information for project calendar.
 */
public class ProjectCalendarBean implements Comparable<ProjectCalendarBean> {

	private int hourNumber;
	private int dayOfMonth;
	private int userId;
	private int projectId;
	private String currentMonth = getMonthForInt();
	private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

	public String getCurrentMonth() {
		return currentMonth;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getHourNumber() {
		return hourNumber;
	}

	public void setHourNumber(int hourNumber) {
		this.hourNumber = hourNumber;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	// No check is needed because the paramter value is get from
	// Calendar java object
	private String getMonthForInt() {
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		return months[Calendar.getInstance().get(Calendar.MONTH)];
	}

	@Override
	public int compareTo(ProjectCalendarBean otherProjectDayBean) {

		int dayToCompare = otherProjectDayBean.getDayOfMonth();
		return this.getDayOfMonth() - dayToCompare;
	}

}
