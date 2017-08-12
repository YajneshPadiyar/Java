package hellow;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PostExecuteAdapter {
	
	static Logger log = Logger.getLogger(ApplicationStart.class.getName());
	@RequestMapping(value = "/ExecuteAdapterPost", method = RequestMethod.POST)
	public @ResponseBody AdapterOutput addStudent(@RequestBody Map<String, Object> inputParam) {
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> ConfigSetting = new HashMap<String, String>();
		try{
		String JSONRequestString = mapper.writeValueAsString(inputParam);
		
		//LoadApplicationSettings appSetting = new LoadApplicationSettings();
		ConfigSetting = new LoadApplicationSettings().getConfigSetting();
		
		
		log.setLevel(SystemConstants.getLogLevel(ConfigSetting));
		log.info("Loaded Application Settings");
		//log.debug(mapper.writeValueAsString(ConfigSetting));
		log.debug("Request Message");
		log.debug(JSONRequestString);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		AdapterOutput lAdapterOutput = new AdapterOutput();
		ExecuteStoreProc ExeSP = new ExecuteStoreProc();
		
		try{
		

		String StoreProcName = (String) inputParam.get(SystemConstants.cSPName);
		String StoreProcType = (String) inputParam.get(SystemConstants.cSPType);
		int StoreProcParamCnt = (int) inputParam.get(SystemConstants.cSPParamCount);
		
		
		// Object SPParamList = (Object) inputParam.get("SPParamList");

		LogDetails.LogDetailApplication(StoreProcName, ConfigSetting.get(SystemConstants.cEnableLog_3));
		LogDetails.LogDetailApplication(StoreProcType, ConfigSetting.get(SystemConstants.cEnableLog_3));
		LogDetails.LogDetailApplication(Integer.toString(StoreProcParamCnt),
				ConfigSetting.get(SystemConstants.cEnableLog_3));
		Map<String, Object> SPParamListObj = (HashMap<String, Object>) inputParam.get(SystemConstants.cSPParamList);

		
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

		StoreProcTestCase SPDetails = new StoreProcTestCase();

		Map<String, StoreProcParam> ParamDetailsList = new HashMap<String, StoreProcParam>();
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

			ParamDetailsList.put(ParamName, Case);
		}

		SPDetails.setSPName(StoreProcName);
		SPDetails.setSPExecuteSting(SPExecuteSting);
		SPDetails.setSPType(StoreProcType);
		SPDetails.setParams(ParamDetailsList);
		
		lAdapterOutput = ExeSP.ExecuteStoreProcCURSOR(ConfigSetting, SPDetails);
		String JSONResponseString = mapper.writeValueAsString(lAdapterOutput);
		log.debug("Response Message");
		log.debug(JSONResponseString);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.toString());
		}
		
		return lAdapterOutput;
	}
}
