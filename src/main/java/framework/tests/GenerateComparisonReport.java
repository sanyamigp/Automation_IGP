package framework.tests;

import java.nio.file.Paths;
import java.text.ParseException;
import utilities.ReportFactoryDB;
/**
 * Its genrate comparison report
 *
 */
public class GenerateComparisonReport {
	
	private GenerateComparisonReport(){
		
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		ReportFactoryDB.switchOn();

		ReportFactoryDB.initSqlite(Paths.get("").toAbsolutePath().toString() + "\\ExecutionReports\\DB");

		ReportFactoryDB.getComparisonReport("DAMCO");

		ReportFactoryDB.closeDBConnection();
	}

}
