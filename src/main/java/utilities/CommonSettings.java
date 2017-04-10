package utilities;
/**
 * This CommonSetting class help in generate results
 *
 */
public class CommonSettings {

	private String appType;
	private String appEnviornment;
	private String projectName;
	private String emailOutput;
	private String emailId;
	private String htmlReport;
	private String xlsReport;
	private String testLogs;
	

	/**
	 * @param projectName
	 * @param appType
	 * @param appEnviornment
	 * @param emailOutput
	 * @param emailId
	 * @param htmlReport
	 * @param xlsReport
	 * @param testLogs
	 * @param defectMgmt
	 * @param testMgmt
	 */
	public CommonSettings(String projectName, String appType, String appEnviornment, String emailOutput, String emailId,
			String htmlReport, String xlsReport, String testLogs, String defectMgmt, String testMgmt) {
		super();
		this.projectName = projectName;
		this.appType = appType;
		this.appEnviornment = appEnviornment;
		this.emailOutput = emailOutput;
		this.emailId = emailId;
		this.htmlReport = htmlReport;
		this.xlsReport = xlsReport;
		this.testLogs = testLogs;
	}

	/**
	 * 
	 */
	public CommonSettings() {
		super();
	}

	/**
	 * @param projectName
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @return
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * @param appType
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * @return
	 */
	public String getAppEnviornment() {
		return appEnviornment;
	}

	/**
	 * @param appEnviornment
	 */
	public void setAppEnviornment(String appEnviornment) {
		this.appEnviornment = appEnviornment;
	}

	/**
	 * @return
	 */
	public String getEmailOutput() {
		return emailOutput;
	}

	/**
	 * @param emailOutput
	 */
	public void setEmailOutput(String emailOutput) {
		this.emailOutput = emailOutput;
	}

	/**
	 * @return
	 */
	public String getEmailIds() {
		return emailId;
	}

	/**
	 * @param emailId
	 */
	public void setEmailIds(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return
	 */
	public String getHtmlReport() {
		return htmlReport;
	}

	/**
	 * @param htmlReport
	 */
	public void setHtmlReport(String htmlReport) {
		this.htmlReport = htmlReport;
	}

	/**
	 * @return
	 */
	public String getXlsReport() {
		return xlsReport;
	}

	/**
	 * @param xlsReport
	 */
	public void setXlsReport(String xlsReport) {
		this.xlsReport = xlsReport;
	}

	/**
	 * @return
	 */
	public String getTestLogs() {
		return testLogs;
	}

	/**
	 * @param testLogs
	 */
	public void setTestLogs(String testLogs) {
		this.testLogs = testLogs;
	}

	
}
