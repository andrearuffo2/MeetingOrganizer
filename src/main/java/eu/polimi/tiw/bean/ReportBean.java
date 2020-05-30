package eu.polimi.tiw.bean;

import java.time.LocalDate;

public class ReportBean {

	private LocalDate reportDay;
	private int hour;
	private int projectId;
	private int userId;

	public LocalDate getReportDay() {
		return reportDay;
	}

	public void setReportDay(LocalDate reportDay) {
		this.reportDay = reportDay;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
