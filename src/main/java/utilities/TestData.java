package utilities;

/**
 * @author TX
 *
 */
public class TestData {

	private String projectID;
	private String suiteName;
	private String testId;
	private String testDesc;
	private String complexity;
	private String testPlatformInfo;
	private double expectedTime = 0;

	/**
	 * 
	 */
	public TestData() {
		super();
		this.projectID = "";

		this.suiteName = "";
		this.testId = "";
		this.testDesc = "";
		this.complexity = "";
		this.testPlatformInfo = "";
		this.expectedTime = 0;
	}

	/**
	 * @param projectID
	 * @param suiteName
	 * @param testId
	 * @param testDesc
	 * @param complexity
	 * @param testPlatformInfo
	 * @param expectedTime
	 */
	public TestData(String projectID, String suiteName, String testId, String testDesc, String complexity,
			String testPlatformInfo, double expectedTime) {
		super();
		this.projectID = projectID;

		this.suiteName = suiteName;
		this.testId = testId;
		this.testDesc = testDesc;
		this.complexity = complexity;
		this.testPlatformInfo = testPlatformInfo;
		this.expectedTime = expectedTime;
	}

	/**
	 * @return
	 */
	public String getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID
	 */
	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	/**
	 * @return
	 */
	public String getSuiteName() {
		return suiteName;
	}

	/**
	 * @param suiteName
	 */
	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	/**
	 * @return
	 */
	public String getTestId() {
		return testId;
	}

	/**
	 * @param testId
	 */
	public void setTestId(String testId) {
		this.testId = testId;
	}

	/**
	 * @return
	 */
	public String getTestDesc() {
		return testDesc;
	}

	/**
	 * @param testDesc
	 */
	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	/**
	 * @return
	 */
	public String getComplexity() {
		return complexity;
	}

	/**
	 * @param complexity
	 */
	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	/**
	 * @return
	 */
	public String getTestPlatformInfo() {
		return testPlatformInfo;
	}

	/**
	 * @param testPlatformInfo
	 */
	public void setTestPlatformInfo(String testPlatformInfo) {
		this.testPlatformInfo = testPlatformInfo;
	}

	/**
	 * @return
	 */
	public double getExpectedTime() {
		return expectedTime;
	}

	/**
	 * @param expectedTime
	 */
	public void setExpectedTime(double expectedTime) {
		this.expectedTime = expectedTime;
	}

}
