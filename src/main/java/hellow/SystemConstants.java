package hellow;

public final class SystemConstants {
	public static final String cEnableLog_1 = "EnableLog_1";
	public static final String cEnableLog_2 = "EnableLog_2";
	public static final String cEnableLog_3 = "EnableLog_3";
	public static final String cEnableLog_4 = "EnableLog_4";
	public static final String cConfigLoaded = "ConfigLoaded";

	public static final String cHost = "SPHost";
	public static final String cServiceName = "SPServiceName";
	public static final String cPort = "SPPort";
	public static final String cUserName = "SPUserName";
	public static final String cPassword = "SPPassword";
	
	public static final String cTCHost = "TCHost";
	public static final String cTCDBName = "TCDBName";
	public static final String cTCPort = "TCPort";
	public static final String cTCUserName = "TCUserName";
	public static final String cTCPassword = "TCPassword";

	public static final String cSiebErrCode = "SiebErrCode";
	public static final String cSiebelMessage = "SiebelMessage";
	public static final String cErrMessage = "ErrMessage";
	public static final String cSuccessErrCode = "0";
	public static final String cExceotionCode = "99999";

	public static final String cROW_ID = "ROW_ID";
	public static final String cSP_NAME = "NAME";
	public static final String cPARAM_COUNT = "PARAM_COUNT";
	public static final String cPARAM_LIST = "PARAM_LIST";
	public static final String cTYPE = "TYPE";
	public static final String cSTATUS = "STATUS";
	public static final String cSCENARIO = "SCENARIO";
	public static final String cERROR_CODE = "ERROR_CODE";
	public static final String cERR_MESSAGE = "ERROR_MESSAGE";
	public static final String cSAMPLE_INPUT = "SAMPLE_INPUT";

	public static final String cHASH_VALUE = "#";
	public static final String cSEPERATOR = ",";
	public static final String cSPTYPEXML = "XML";
	public static final String cSPTYPECURSOR = "CURSOR";
	public static final String cPARAM_IDENTIFIER = "?";

	public static final String cParamName = "PARAM_NAME";
	public static final String cParamType = "PARAM_TYPE";
	public static final String cParamDataType = "PARAM_DATA_TYPE";
	public static final String cParamValue = "PRAM_VALUE";
	public static final String cParamTypeIN = "IN";
	public static final String cParamTypeOUT = "OUT";
	public static final String cParamTypeVarchar = "VARCHAR2";
	public static final String cParamTypeXMLTYPE = "XMLTYPE";
	public static final String cParamTypeCursor = "SYS_REFCURSOR";
	public static final String cTestCaseTypePos = "POSITIVE";
	public static final String cTestCaseTypeNeg = "NEGATIVE";
	
	public static final String cQueryTestCaseDetails = "SELECT SP.NAME,SP.PARAM_COUNT,SP.PARAM_LIST,SP.TYPE,"
			+ "Test.ROW_ID,Test.STATUS,Test.SCENARIO,Test.ERROR_CODE,"
			+"Test.SAMPLE_INPUT,errCode.ERROR_MESSAGE"
			+ " FROM y_test_cases Test,y_sp_defination SP, y_error_codes errCode "+
			"WHERE Test.STORED_PROC_NAME = SP.NAME AND errCode.ERROR_CODE = Test.ERROR_CODE AND SP.TYPE='CURSOR'";

}
