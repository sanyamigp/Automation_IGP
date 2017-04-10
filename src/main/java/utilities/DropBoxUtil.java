package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;
import utilities.LogUtil;
/**
 * With this class we can store all the results file in dropbox 
 *
 */
public class DropBoxUtil {

	private static boolean dropBoxswitch = false;
	private static String accesstoken = "";
	private DropBoxUtil(){
		
	}
	/**
	 * <H1>Dropbox Initialization. </H1> 
	 * <p>Initialization is required in order to use DropBox Utility file</p>
	 * @param accessToken
	 * @return
	 */
	
	public static void init(String accessToken) {
		
		if(accessToken.isEmpty())
			DropBoxUtil.dropBoxswitch=false;
		
		if (isSwitchOn()) {
			DropBoxUtil.accesstoken = accessToken;
		} else {
			LogUtil.infoLog(DropBoxUtil.class, "Dropbox is switched off.");
		}

	}

	/**
	 * <H1>Dropbox switch</H1> 
	 * <p>switch should be on state to upload the file</p>
	 */
	public static void switchOn() {
		DropBoxUtil.dropBoxswitch = true;
	}

	/**
	 * 
	 */
	public static void switchOff() {
		DropBoxUtil.dropBoxswitch = false;
	}

	/**
	 * @return
	 */
	private static boolean isSwitchOn() {
		return DropBoxUtil.dropBoxswitch;
	}

	/**
	 * <H1>Dropbox Upload file. </H1> <p>Upload the file to Dropbox. Required a
	 * file path </p>
	 * 
	 * @param filePath
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 * 
	 * @throws DbxException
	 * 
	 * @throws IOException
	 */
	public static void uploadFile(String filePath) throws DbxException, IOException   {
		if (isSwitchOn()) {
			@SuppressWarnings("deprecation")
			DbxRequestConfig config = new DbxRequestConfig("javarootsDropbox/1.0");
			DbxClientV2 client = new DbxClientV2(config, accesstoken);
			// Get current account info
			FullAccount account = client.users().getCurrentAccount();
			LogUtil.infoLog(DropBoxUtil.class, "Dropbox account name: " + account.getName().getDisplayName());

			// Upload file into Dropbox
			try (InputStream in = new FileInputStream(filePath)) {
				File f = new File(filePath);
				if (f.exists()) {
					String pattern = "MM-dd-yyyy hh-mm-ss";
					SimpleDateFormat format = new SimpleDateFormat(pattern);
					LogUtil.infoLog(DropBoxUtil.class, format.format(new Date()));
					LogUtil.infoLog(DropBoxUtil.class, "Please wait DropBox file upload in progress...");
					FileMetadata metadata = client.files()
							.uploadBuilder("/" + format.format(new Date()) + "-" + f.getName())
							.uploadAndFinish(in);
					LogUtil.infoLog(DropBoxUtil.class, "Data successfully Uploaded -->" + metadata);
					LogUtil.infoLog(DropBoxUtil.class, "File download link -> "
							+ client.files().getTemporaryLink(metadata.getPathLower()).getLink());
				}
			}
			
			 
			
		} // End of Switch check
	}// End of uploadFile()
}