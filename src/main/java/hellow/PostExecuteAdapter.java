package hellow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PostExecuteAdapter {
	@RequestMapping(value = "/InitAdapter", method = RequestMethod.GET)
	public @ResponseBody ExecuteAdapterParam student() {
		return new ExecuteAdapterParam("student", "command", new HashMap<String, String>());
	}

	@RequestMapping(value = "/ExecuteAdapterPost", method = RequestMethod.POST)
	public @ResponseBody AdapterOutput addStudent(@RequestBody Map<String, Object> inputParam) {

		Map<String, String> ConfigSetting = new HashMap<String, String>();
		LoadApplicationSettings appSetting = new LoadApplicationSettings();
		ConfigSetting = appSetting.GetConfigSettings();

		String StoreProcName = (String) inputParam.get(SystemConstants.cSPName);
		String StoreProcType = (String) inputParam.get(SystemConstants.cSPType);
		int StoreProcParamCnt = (int) inputParam.get(SystemConstants.cSPParamCount);
		
		
		// Object SPParamList = (Object) inputParam.get("SPParamList");

		LogDetails.LogDetailApplication(StoreProcName, ConfigSetting.get(SystemConstants.cEnableLog_3));
		LogDetails.LogDetailApplication(StoreProcType, ConfigSetting.get(SystemConstants.cEnableLog_3));
		LogDetails.LogDetailApplication(Integer.toString(StoreProcParamCnt),
				ConfigSetting.get(SystemConstants.cEnableLog_3));
		Map<String, Object> SPParamListObj = (HashMap<String, Object>) inputParam.get(SystemConstants.cSPParamList);

		// LogDetails.LogDetailApplication("SPParamListObj");
		// Map<String, String> SPParamListMSISDN = (HashMap<String, String>)
		// SPParamListObj.get("MSISDN");
		// LogDetails.LogDetailApplication("SPParamListMSISDN");
		// String parameters = (String) SPParamListMSISDN.get("TYPE");
		// LogDetails.LogDetailApplication("parameters : " + parameters);
		Map<String, String> SPInput = (HashMap<String, String>) inputParam.get(SystemConstants.cSPInput);

		// LogDetails.LogDetailApplication("SPInput");

		String SPExecuteSting = null;

		if (StoreProcParamCnt > 0) {
			SPExecuteSting = "{call " + StoreProcName + "("
					+ ManageTestCase.GetParamString(StoreProcParamCnt, ConfigSetting.get(SystemConstants.cEnableLog_4))
					+ ")}";
		} else {
			SPExecuteSting = "{call " + StoreProcName + "()}";
		}
		LogDetails.LogDetailApplication("SPExecuteSting : ", ConfigSetting.get(SystemConstants.cEnableLog_3));

		StoreProcTestCase TestCase = new StoreProcTestCase();

		Map<String, StoreProcParam> TCase = new HashMap<String, StoreProcParam>();
		for (Map.Entry<String, Object> entry : SPParamListObj.entrySet()) {
			String ParamName = entry.getKey();
			Map<String, String> ParamDetails = (HashMap<String, String>) entry.getValue();
			String ParamType = ParamDetails.get(SystemConstants.cTYPE);
			String ParamDataType = ParamDetails.get(SystemConstants.cDATATYPE);
			String ParamValue = SystemConstants.cHASH_VALUE;
			if (ParamType.equalsIgnoreCase(SystemConstants.cParamTypeIN)) {
				ParamValue = SPInput.get(ParamName);
			}
			StoreProcParam Case = new StoreProcParam(ParamName, ParamDataType, ParamType, ParamValue);
			LogDetails
					.LogDetailApplication(
							"ParamName : " + ParamName + " : ParamDataType " + ParamDataType + " : ParamType "
									+ ParamType + " : ParamValue" + ParamValue,
							ConfigSetting.get(SystemConstants.cEnableLog_3));

			TCase.put(ParamName, Case);
		}

		TestCase.setSPName(StoreProcName);
		TestCase.setSPExecuteSting(SPExecuteSting);
		TestCase.setSPType(StoreProcType);
		TestCase.setParams(TCase);
		AdapterOutput lAdapterOutput = new AdapterOutput();
		ExecuteStoreProc ExeSP = new ExecuteStoreProc();
		lAdapterOutput = ExeSP.ExecuteStoreProcCURSOR(ConfigSetting, TestCase);

		return lAdapterOutput;
	}
}
