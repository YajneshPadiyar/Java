package hellow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ManageTestCase {
	public StoreProcTestCase getTestSettings(String Project, String Environment, Map<String, String> ConfigSetting) {
		//Map<String, String> TestCaseSetting = new HashMap<String, String>();
		StoreProcTestCase  TestCase = 
				new StoreProcTestCase();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			
			
			LogDetails.LogDetailGeneral("jdbc:mysql://"+ConfigSetting.get(SystemConstants.cTCHost)+":"
					+ConfigSetting.get(SystemConstants.cTCPort)+"/"
					+ConfigSetting.get(SystemConstants.cTCDBName), ConfigSetting.get(SystemConstants.cEnableLog_4));
			Connection con = DriverManager.getConnection("jdbc:mysql://"+ConfigSetting.get(SystemConstants.cTCHost)+":"
															+ConfigSetting.get(SystemConstants.cTCPort)+"/"
															+ConfigSetting.get(SystemConstants.cTCDBName), 
														ConfigSetting.get(SystemConstants.cTCUserName),
														ConfigSetting.get(SystemConstants.cTCPassword));
			// here sonoo is database name, root is username and password
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(SystemConstants.cQueryTestCaseDetails);
			// SELECT * FROM y_test_cases Test,y_sp_defination SP WHERE
			// Test.STORED_PROC_NAME = SP.NAME
			while (rs.next()) {
				
				LogDetails.LogDetailGeneral("Test Case : ", ConfigSetting.get(SystemConstants.cEnableLog_4));
				
				LogDetails.LogDetailGeneral(rs.getInt(SystemConstants.cROW_ID) + "  "
						+ rs.getString(SystemConstants.cSP_NAME) + "  " + rs.getInt(SystemConstants.cPARAM_COUNT) + "  "
						+ rs.getString(SystemConstants.cPARAM_LIST) + "  " + rs.getString(SystemConstants.cTYPE) + " "
						+ rs.getString(SystemConstants.cSTATUS) + " " + rs.getString(SystemConstants.cSCENARIO) + " "
						+ rs.getString(SystemConstants.cERROR_CODE) + " " + rs.getString(SystemConstants.cERR_MESSAGE)
						+ " " + rs.getString(SystemConstants.cSAMPLE_INPUT) + " ",
						ConfigSetting.get(SystemConstants.cEnableLog_4));

				String StoreProcName = rs.getString(SystemConstants.cSP_NAME);
				//String StoreProcSpec = rs.getString(SystemConstants.cPARAM_LIST);
				int StoreProcParamCnt = rs.getInt(SystemConstants.cPARAM_COUNT);
				String StoreProcType = rs.getString(SystemConstants.cTYPE);
				//int StoreProcTestCaseId = rs.getInt(SystemConstants.cROW_ID);
				
				//String TestCaseStatus = rs.getString(SystemConstants.cSTATUS);
				String TestScenario = rs.getString(SystemConstants.cSCENARIO);
				String TestErroCode = rs.getString(SystemConstants.cERROR_CODE);
				String TestErroMessage = rs.getString(SystemConstants.cERR_MESSAGE);
				String TestSampleInput = rs.getString(SystemConstants.cSAMPLE_INPUT);

				String SPExecuteSting = null;
				
				if (StoreProcParamCnt > 0) {
					SPExecuteSting = "{call " + StoreProcName + "("
							+ GetParamString(StoreProcParamCnt, ConfigSetting.get(SystemConstants.cEnableLog_4))
							+ ")}";
				} else {
					SPExecuteSting = "{call " + StoreProcName + "()}";
				}
				
				LogDetails.LogDetailGeneral(SPExecuteSting, ConfigSetting.get(SystemConstants.cEnableLog_4));
				
				String[] ParamList = TestSampleInput.split(SystemConstants.cSEPERATOR);
				Map<String, StoreProcParam> TCase = new HashMap<String, StoreProcParam>();
				for (String value : ParamList) {
					LogDetails.LogDetailGeneral(value, ConfigSetting.get(SystemConstants.cEnableLog_4));
					String[] ParamValue = value.split(SystemConstants.cHASH_VALUE,4);

					StoreProcParam Case = new StoreProcParam(ParamValue[0], ParamValue[2],
							ParamValue[1], ParamValue[3]);
					
					TCase.put(ParamValue[0], Case);
				}
				
				
				
				TestCase.setERR_MESSAGE(TestErroMessage);
				TestCase.setERROR_CODE(TestErroCode);
				TestCase.setSPName(StoreProcName);
				TestCase.setSPExecuteSting(SPExecuteSting);
				TestCase.setScenario(TestScenario);
				TestCase.setParams(TCase);
				TestCase.setSPType(StoreProcType);
			}

			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// con.close();
		}

		return TestCase;
	}

	public static final String GetParamString(int Count, String EnableLog) {
		int tCount = 0;
		String ParamString = "";
		while (tCount != Count) {
			if (tCount == 0) {
				ParamString = SystemConstants.cPARAM_IDENTIFIER;
			} else {
				ParamString += SystemConstants.cSEPERATOR + SystemConstants.cPARAM_IDENTIFIER;
			}
			tCount++;
		}

		LogDetails.LogDetailGeneral(ParamString, EnableLog);
		return ParamString;
	}
}
