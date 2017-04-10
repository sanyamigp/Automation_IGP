package utilities;

import java.util.Date;

/**
 * @author TX
 *
 */
public class TestResults {
	private Date runDateTime;
	private int runId;
	private String status;
	private String remarks;
	private String screenshotref;
	private double actualTime = 0;
	/**
	 * 
	 */
	public TestResults() {
		super();

		this.runDateTime = new Date();
		this.runId = 0;
		this.status = "";
		this.remarks = "";
		this.actualTime = 0;
	}
	/**
	 * @param runDateTime
	 * @param runId
	 * @param status
	 * @param remarks
	 * @param actualTime
	 */
	public TestResults(Date runDateTime, int runId, String status, String remarks, double actualTime) {
		super();
		this.runDateTime = runDateTime;
		this.runId = runId;
		this.status = status;
		this.remarks = remarks;
		this.actualTime = actualTime;
	}
	/**
	 * @return
	 */
	public String getScreenshotref() {
		return screenshotref;
	}

	/**
	 * @param screenshot_ref
	 */
	public void setScreenshotref(String screenshotref) {
		this.screenshotref = screenshotref;
	}

	

	

	

	/**
	 * @return
	 */
	public Date getRunDateTime() {
		return runDateTime;
	}

	/**
	 * @param runDateTime
	 */
	public void setRunDateTime(Date runDateTime) {
		this.runDateTime = runDateTime;
	}

	/**
	 * @return
	 */
	public int getRunId() {
		return runId;
	}

	/**
	 * @param runId
	 */
	public void setRunId(int runId) {
		this.runId = runId;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return
	 */
	public double getActualTime() {
		return actualTime;
	}

	/**
	 * @param actualTime
	 */
	public void setActualTime(double actualTime) {
		this.actualTime = actualTime;
	}

}
