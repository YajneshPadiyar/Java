package hellow;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ExecuteAdapter")
public class StoreProcAdapter {

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody AdapterOutput storeProcAdapter(
			@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name,
			@RequestParam(value = "age", required = false, defaultValue = "not defined") String age) {
		AdapterOutput lAdapterOutput = new AdapterOutput();
		Map<String, String> ConfigSetting = new HashMap<String, String>();
		LoadApplicationSettings appSetting = new LoadApplicationSettings();
		{

			ConfigSetting = appSetting.GetConfigSettings();
			if (ConfigSetting.get(SystemConstants.cConfigLoaded) == "Y") {

				// LogDetails.LogDetailGeneral(args[0]);
				ManageTestCase MySQLConnect = new ManageTestCase();
				StoreProcTestCase TestCase = MySQLConnect.getTestSettings("TEST", "Environment", ConfigSetting);
				String StoreProcType = TestCase.getSPType();
				
				ExecuteStoreProc ExeSP = new ExecuteStoreProc();
				if (StoreProcType.equalsIgnoreCase(SystemConstants.cSPTYPEXML)) {					
					ExeSP.ExecuteStoreProcXML(ConfigSetting,TestCase);
				} else if(StoreProcType.equalsIgnoreCase(SystemConstants.cSPTYPECURSOR)) {
					lAdapterOutput = ExeSP.ExecuteStoreProcCURSOR(ConfigSetting,TestCase);
				}

			} else {
				System.out.println("Failed to load config details");
			}

		}

		return lAdapterOutput;
	}

}
