package framework.tests;

import utilities.DropBoxUtil;
import utilities.Utility;

/**
 * this class will is related to upload results into dropbox
 *
 */
public class DropBoxUtilTest {
	
	/**
	 * results store in dropbox by using switch on
	 * @throws Exception 
	 *
	 */

	public static void main(String[] args) throws Exception {

		DropBoxUtil.switchOn();
		DropBoxUtil.init(Utility.getValue("dropBox_AccessToken"));
		DropBoxUtil.uploadFile("C:\\Users\\SUKHJINDER\\Desktop\\SmartShowExecutionReports1.zip");

	}
}