package utilities;

public class IGP_Url {
	
	private String urlDesc,url,executionFlag;
	private int requiredStatus;

	public String getUrlDesc() {
		return urlDesc;
	}

	public void setUrlDesc(String urlDesc) {
		this.urlDesc = urlDesc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRequiredStatus() {
		return requiredStatus;
	}

	public void setRequiredStatus(int requiredStatus) {
		this.requiredStatus = requiredStatus;
	}

	public String getExecutionFlag() {
		return executionFlag;
	}

	public void setExecutionFlag(String executionFlag) {
		this.executionFlag = executionFlag;
	}

	@Override
	public String toString() {
		return "IGP_Url [urlDesc=" + urlDesc + ", url=" + url + ", executionFlag=" + executionFlag + ", requiredStatus="
				+ requiredStatus + "]";
	}
	
	

}
