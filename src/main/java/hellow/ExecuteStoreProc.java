package hellow;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import oracle.jdbc.OracleTypes;
import oracle.xdb.XMLType;

public class ExecuteStoreProc {
	static Logger log = Logger.getLogger(ExecuteStoreProc.class.getName());
	public void ExecuteStoreProcXML(Map<String, String> ConfigSetting, StoreProcTestCase TestCase) {
		Connection con = null;
		String ConnectString = ConfigSetting.get(SystemConstants.cUserName) + "/"
				+ ConfigSetting.get(SystemConstants.cPassword) + "@" + ConfigSetting.get(SystemConstants.cHost) + ":"
				+ ConfigSetting.get(SystemConstants.cPort) + "/" + ConfigSetting.get(SystemConstants.cServiceName);
		
		LogDetails.LogDetailGeneral(ConnectString, ConfigSetting.get(SystemConstants.cEnableLog_4));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					// "jdbc:oracle:thin:EXT_SYS/EXT_SYS@sbl-sit-scan3.vodafone.com.au:2521/siebel7s_oltp.vodafone.com.au"
					"jdbc:oracle:thin:" + ConnectString);

			CallableStatement SPStmt = con.prepareCall(TestCase.getSPExecuteSting());

			Map<String, StoreProcParam> SPParam = TestCase.getParams();

			for (Map.Entry<String, StoreProcParam> entry : SPParam.entrySet()) {
				LogDetails.LogDetailGeneral(entry.getKey(), ConfigSetting.get(SystemConstants.cEnableLog_4));

				StoreProcParam Param = entry.getValue();

				if (Param.getParamType().equalsIgnoreCase(SystemConstants.cParamTypeIN)) {
					if (Param.getParamDataType().equalsIgnoreCase(SystemConstants.cParamTypeVarchar)) {
						SPStmt.setString(Param.getParamName(), Param.getParamValue());
					}
				} else {
					if (Param.getParamDataType().equalsIgnoreCase(SystemConstants.cParamTypeXMLTYPE)) {
						SPStmt.registerOutParameter(Param.getParamName(), OracleTypes.OPAQUE, "SYS.XMLTYPE");
					}
				}

			}

			/*
			 * SPStmt.setString("IDval", "61450475767");
			 * SPStmt.setString("IDType", "MSISDN"); SPStmt.setString("Channel",
			 * "Web"); SPStmt.setString("CountryCode", "AU");
			 * SPStmt.setString("WebUser", "61450475767");
			 * SPStmt.registerOutParameter("outParam", OracleTypes.OPAQUE,
			 * "SYS.XMLTYPE");//
			 */

			LogDetails.LogDetailApplication("Execute Stored Procedure : " + TestCase.getSPName(),
					ConfigSetting.get(SystemConstants.cEnableLog_2));
			SPStmt.execute();

			XMLType ResponseXML = (XMLType) SPStmt.getObject("outParam");
			// SQLXML outParam = SPStmt.getSQLXML(1);

			Document XMLDocument = ResponseXML.getDocument();
			NodeList SiebelMessage = XMLDocument.getElementsByTagName(SystemConstants.cSiebelMessage);
			Node SiebelMessageParent = SiebelMessage.item(0);
			NamedNodeMap SiebelMessageAttributes = SiebelMessageParent.getAttributes();
			String SiebelErrorCode = null;
			String SiebelErrorMessage = null;

			LogDetails.LogDetailApplication(ResponseXML.getStringVal(),
					ConfigSetting.get(SystemConstants.cEnableLog_3));

			if (SiebelMessageAttributes != null) {
				SiebelErrorCode = SiebelMessageAttributes.getNamedItem(SystemConstants.cSiebErrCode).getNodeValue();

				LogDetails.LogDetailGeneral("Siebel Error Code : " + SiebelErrorCode,
						ConfigSetting.get(SystemConstants.cEnableLog_4));

			}
			boolean bError_Code = false;
			boolean bError_Message = false;
			boolean bScenario = false;
			if (SiebelErrorCode != null) {
				if (TestCase.getERROR_CODE().equalsIgnoreCase(SiebelErrorCode)) {
					LogDetails
							.LogDetailApplication(
									"Passed : Test Case Error Code Passed. Expected : " + TestCase.getERROR_CODE()
											+ ", Actual : " + SiebelErrorCode,
									ConfigSetting.get(SystemConstants.cEnableLog_3));
					bError_Code = true;
				} else {
					LogDetails
							.LogDetailApplication(
									"Failed : Test Case Error Code Failed. Expected : " + TestCase.getERROR_CODE()
											+ ", Actual : " + SiebelErrorCode,
									ConfigSetting.get(SystemConstants.cEnableLog_3));
				}

				if (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cSuccessErrCode)) {
					LogDetails.LogDetailGeneral("Success Response", ConfigSetting.get(SystemConstants.cEnableLog_4));
					// if
					// (TestCase.getScenario().contentEquals(SystemConstants.cTestCaseTypePos))
					// {

					if (TestCase.getScenario().contentEquals(SystemConstants.cTestCaseTypePos)) {
						LogDetails.LogDetailApplication(
								"Passed : Test Case Scenario Passed. Expected : " + TestCase.getScenario()
										+ " actual : " + SystemConstants.cTestCaseTypePos,
								ConfigSetting.get(SystemConstants.cEnableLog_3));
						bScenario = true;
					} else {
						LogDetails.LogDetailApplication(
								"Failed : Test Case Scenario Failed. Expected : " + TestCase.getScenario()
										+ ", Actual : " + SystemConstants.cTestCaseTypePos,
								ConfigSetting.get(SystemConstants.cEnableLog_3));
					}
					// }
				} else if (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cExceotionCode)) {
					LogDetails.LogDetailGeneral("Exception Response", ConfigSetting.get(SystemConstants.cEnableLog_4));
				} else {
					LogDetails.LogDetailGeneral("Failure Response", ConfigSetting.get(SystemConstants.cEnableLog_4));
					if (TestCase.getScenario().contentEquals(SystemConstants.cTestCaseTypeNeg)) {
						LogDetails.LogDetailApplication(
								"Passed : Test Case Scenario Passed. Expected : " + TestCase.getScenario()
										+ ", Actual : " + SystemConstants.cTestCaseTypeNeg,
								ConfigSetting.get(SystemConstants.cEnableLog_3));
						bScenario = true;
					} else {
						LogDetails.LogDetailApplication(
								"Failed : Test Case Scenario Failed. Expected : " + TestCase.getScenario()
										+ ", Actual : " + SystemConstants.cTestCaseTypePos,
								ConfigSetting.get(SystemConstants.cEnableLog_3));
					}
					SiebelErrorMessage = SiebelMessageAttributes.getNamedItem(SystemConstants.cErrMessage)
							.getNodeValue();
					LogDetails.LogDetailGeneral("Siebel Error Message : " + SiebelErrorMessage,
							ConfigSetting.get(SystemConstants.cEnableLog_4));
					if (TestCase.getERR_MESSAGE().equalsIgnoreCase(SiebelErrorMessage)) {
						LogDetails.LogDetailApplication(
								"Passed : Test Case Error Message Passed. Expected : " + TestCase.getERR_MESSAGE()
										+ ", Actual : " + SiebelErrorMessage,
								ConfigSetting.get(SystemConstants.cEnableLog_3));
						bError_Message = true;
					} else {
						LogDetails.LogDetailApplication(
								"Failed : Test Case Error Message Failed. Expected : " + TestCase.getERR_MESSAGE()
										+ ", Actual : " + SiebelErrorMessage,
								ConfigSetting.get(SystemConstants.cEnableLog_3));
					}

				}
			} else {

			}

			if (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cSuccessErrCode)) {
				if (bError_Code) {
					LogDetails.LogDetailApplication(
							"Passed : " + TestCase.getSPName() + " : Test Case Execution Successful");
				} else {
					LogDetails
							.LogDetailApplication("Failed : " + TestCase.getSPName() + " : Test Case Execution Failed");
				}
			} else if (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cExceotionCode)) {
				LogDetails
						.LogDetailApplication("Exception : " + TestCase.getSPName() + " : Test Case Execution Failed");
			} else if (bError_Message && bError_Code && bScenario) {
				LogDetails
						.LogDetailApplication("Passed : " + TestCase.getSPName() + " : Test Case Execution Successful");
			} else {
				LogDetails.LogDetailApplication("Failed : " + TestCase.getSPName() + " : Test Case Execution Failed");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public AdapterOutput ExecuteStoreProcCURSOR(Map<String, String> ConfigSetting, StoreProcTestCase TestCase) {
		Connection con = null;
		String ConnectString = ConfigSetting.get(SystemConstants.cUserName) + "/"
				+ ConfigSetting.get(SystemConstants.cPassword) + "@" + ConfigSetting.get(SystemConstants.cHost) + ":"
				+ ConfigSetting.get(SystemConstants.cPort) + "/" + ConfigSetting.get(SystemConstants.cServiceName);
		LogDetails.LogDetailGeneral(ConnectString, ConfigSetting.get(SystemConstants.cEnableLog_4));

		// HashMap<String, ListofRecordSet> CurosrSet = new HashMap<String,
		// ListofRecordSet>();
		ListofCursorSet CursorSet = new ListofCursorSet();
		RowSet VarcharOutSet = new RowSet();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					// "jdbc:oracle:thin:EXT_SYS/EXT_SYS@sbl-sit-scan3.vodafone.com.au:2521/siebel7s_oltp.vodafone.com.au"
					"jdbc:oracle:thin:" + ConnectString);

			CallableStatement SPStmt = con.prepareCall(TestCase.getSPExecuteSting());

			Map<String, StoreProcParam> SPParam = TestCase.getParams();

			HashMap<String, String> OutArguments = new HashMap<String, String>();

			for (Map.Entry<String, StoreProcParam> entry : SPParam.entrySet()) {
				LogDetails.LogDetailGeneral(entry.getKey(), ConfigSetting.get(SystemConstants.cEnableLog_4));

				StoreProcParam Param = entry.getValue();

				if (Param.getParamType().equalsIgnoreCase(SystemConstants.cParamTypeIN)) {
					if (Param.getParamDataType().equalsIgnoreCase(SystemConstants.cParamTypeVarchar)) {
						SPStmt.setString(Param.getParamName(), Param.getParamValue());
					}
				} else {
					OutArguments.put(Param.getParamName(), Param.getParamDataType());
					if (Param.getParamDataType().equalsIgnoreCase(SystemConstants.cParamTypeCursor)) {
						SPStmt.registerOutParameter(Param.getParamName(), OracleTypes.CURSOR);
					} else if (Param.getParamDataType().equalsIgnoreCase(SystemConstants.cParamTypeVarchar)) {
						SPStmt.registerOutParameter(Param.getParamName(), OracleTypes.VARCHAR);
					}
				}

			}

			LogDetails.LogDetailApplication("Execute Stored Procedure : " + TestCase.getSPName(),
					ConfigSetting.get(SystemConstants.cEnableLog_2));
			SPStmt.execute();

			for (Map.Entry<String, String> entry : OutArguments.entrySet()) {
				String OutParamName = entry.getKey();
				String OutParamType = entry.getValue();
				LogDetails.LogDetailApplication("OutParamName : " + OutParamName,
						ConfigSetting.get(SystemConstants.cEnableLog_2));
				LogDetails.LogDetailApplication("OutParamType : " + OutParamType,
						ConfigSetting.get(SystemConstants.cEnableLog_2));

				if (OutParamType.equalsIgnoreCase(SystemConstants.cParamTypeVarchar)) {
					// OutType is Varchar
					String ParamOut = SPStmt.getString(OutParamName);
					VarcharOutSet.addValuePair(OutParamName, ParamOut);
				} else if (OutParamType.equalsIgnoreCase(SystemConstants.cParamTypeCursor)) {

					// OutType is Cursor

					ResultSet ResponseResultSet = (ResultSet) SPStmt.getObject(OutParamName);
					ResultSetMetaData CursorRecordsDef = null;
					int CursorRecordCnt = 0;
					boolean isRecord = ResponseResultSet.next();
					if (isRecord) {
						CursorRecordsDef = ResponseResultSet.getMetaData();
						CursorRecordCnt = CursorRecordsDef.getColumnCount();
						LogDetails.LogDetailApplication("CursorRecordCnt : " + Integer.toString(CursorRecordCnt),
								ConfigSetting.get(SystemConstants.cEnableLog_2));
					}
					ListofRecordSet LRecordSet = new ListofRecordSet();
					
					while (isRecord) {
						RowSet Row = new RowSet();
						for (int i = 1; i <= CursorRecordCnt; i++) {
							LogDetails.LogDetailApplication(CursorRecordsDef.getColumnName(i),
									ConfigSetting.get(SystemConstants.cEnableLog_2));
							LogDetails.LogDetailApplication(
									ResponseResultSet.getString(CursorRecordsDef.getColumnName(i)),
									ConfigSetting.get(SystemConstants.cEnableLog_2));
							Row.addValuePair(CursorRecordsDef.getColumnName(i),
									ResponseResultSet.getString(CursorRecordsDef.getColumnName(i)));
							
						}
						LRecordSet.addListValuePair(Row);
						// KeyValuePair(ListKeyPair));
						isRecord = ResponseResultSet.next();
					} // */
					CursorSet.addCursorSet(OutParamName, LRecordSet);
				}

			}

			// SQLXML outParam = SPStmt.getSQLXML(1);

			/*
			 * Document XMLDocument = ResponseXML.getDocument(); NodeList
			 * SiebelMessage =
			 * XMLDocument.getElementsByTagName(SystemConstants.cSiebelMessage);
			 * Node SiebelMessageParent = SiebelMessage.item(0); NamedNodeMap
			 * SiebelMessageAttributes = SiebelMessageParent.getAttributes();
			 * String SiebelErrorCode = null; String SiebelErrorMessage = null;
			 * 
			 * LogDetails.LogDetailApplication(ResponseXML.getStringVal(),
			 * ConfigSetting.get(SystemConstants.cEnableLog_3));
			 * 
			 * if (SiebelMessageAttributes != null) { SiebelErrorCode =
			 * SiebelMessageAttributes.getNamedItem(SystemConstants.cSiebErrCode
			 * ).getNodeValue();
			 * 
			 * LogDetails.LogDetailGeneral("Siebel Error Code : " +
			 * SiebelErrorCode,
			 * ConfigSetting.get(SystemConstants.cEnableLog_4));
			 * 
			 * } boolean bError_Code = false; boolean bError_Message = false;
			 * boolean bScenario = false; if (SiebelErrorCode != null) { if
			 * (TestCase.getERROR_CODE().equalsIgnoreCase(SiebelErrorCode)) {
			 * LogDetails .LogDetailApplication(
			 * "Passed : Test Case Error Code Passed. Expected : " +
			 * TestCase.getERROR_CODE() + ", Actual : " + SiebelErrorCode,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); bError_Code =
			 * true; } else { LogDetails .LogDetailApplication(
			 * "Failed : Test Case Error Code Failed. Expected : " +
			 * TestCase.getERROR_CODE() + ", Actual : " + SiebelErrorCode,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); }
			 * 
			 * if
			 * (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cSuccessErrCode
			 * )) { LogDetails.LogDetailGeneral("Success Response",
			 * ConfigSetting.get(SystemConstants.cEnableLog_4)); // if //
			 * (TestCase.getScenario().contentEquals(SystemConstants.
			 * cTestCaseTypePos)) // {
			 * 
			 * if (TestCase.getScenario().contentEquals(SystemConstants.
			 * cTestCaseTypePos)) { LogDetails.LogDetailApplication(
			 * "Passed : Test Case Scenario Passed. Expected : " +
			 * TestCase.getScenario() + " actual : " +
			 * SystemConstants.cTestCaseTypePos,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); bScenario =
			 * true; } else { LogDetails.LogDetailApplication(
			 * "Failed : Test Case Scenario Failed. Expected : " +
			 * TestCase.getScenario() + ", Actual : " +
			 * SystemConstants.cTestCaseTypePos,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); } // } } else
			 * if
			 * (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cExceotionCode)
			 * ) { LogDetails.LogDetailGeneral("Exception Response",
			 * ConfigSetting.get(SystemConstants.cEnableLog_4)); } else {
			 * LogDetails.LogDetailGeneral("Failure Response",
			 * ConfigSetting.get(SystemConstants.cEnableLog_4)); if
			 * (TestCase.getScenario().contentEquals(SystemConstants.
			 * cTestCaseTypeNeg)) { LogDetails.LogDetailApplication(
			 * "Passed : Test Case Scenario Passed. Expected : " +
			 * TestCase.getScenario() + ", Actual : " +
			 * SystemConstants.cTestCaseTypeNeg,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); bScenario =
			 * true; } else { LogDetails.LogDetailApplication(
			 * "Failed : Test Case Scenario Failed. Expected : " +
			 * TestCase.getScenario() + ", Actual : " +
			 * SystemConstants.cTestCaseTypePos,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); }
			 * SiebelErrorMessage =
			 * SiebelMessageAttributes.getNamedItem(SystemConstants.cErrMessage)
			 * .getNodeValue();
			 * LogDetails.LogDetailGeneral("Siebel Error Message : " +
			 * SiebelErrorMessage,
			 * ConfigSetting.get(SystemConstants.cEnableLog_4)); if
			 * (TestCase.getERR_MESSAGE().equalsIgnoreCase(SiebelErrorMessage))
			 * { LogDetails.LogDetailApplication(
			 * "Passed : Test Case Error Message Passed. Expected : " +
			 * TestCase.getERR_MESSAGE() + ", Actual : " + SiebelErrorMessage,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); bError_Message
			 * = true; } else { LogDetails.LogDetailApplication(
			 * "Failed : Test Case Error Message Failed. Expected : " +
			 * TestCase.getERR_MESSAGE() + ", Actual : " + SiebelErrorMessage,
			 * ConfigSetting.get(SystemConstants.cEnableLog_3)); }
			 * 
			 * } } else {
			 * 
			 * }
			 * 
			 * if
			 * (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cSuccessErrCode
			 * )) { if (bError_Code) { LogDetails.LogDetailApplication(
			 * "Passed : " + TestCase.getSPName() +
			 * " : Test Case Execution Successful"); } else { LogDetails
			 * .LogDetailApplication("Failed : " + TestCase.getSPName() +
			 * " : Test Case Execution Failed"); } } else if
			 * (SiebelErrorCode.equalsIgnoreCase(SystemConstants.cExceotionCode)
			 * ) { LogDetails .LogDetailApplication("Exception : " +
			 * TestCase.getSPName() + " : Test Case Execution Failed"); } else
			 * if (bError_Message && bError_Code && bScenario) { LogDetails
			 * .LogDetailApplication("Passed : " + TestCase.getSPName() +
			 * " : Test Case Execution Successful"); } else {
			 * LogDetails.LogDetailApplication("Failed : " +
			 * TestCase.getSPName() + " : Test Case Execution Failed"); } //
			 */
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return new AdapterOutput(VarcharOutSet,CursorSet);
	}
}
