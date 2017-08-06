package hellow;

import java.util.Map;

public class StoreProcTestCase {
	private Map<String, StoreProcParam> Params;
	private String Scenario;
	private String ERROR_CODE;
	private String ERR_MESSAGE;
	private String SPExecuteSting;
	private String SPName;
	private String SPType;
	
	public StoreProcTestCase(){
		
	}
	
	public StoreProcTestCase(Map<String, StoreProcParam> Params, String Scenario, 
			String ErrorCode, String Err_Message, String SPExecuteSting,
			String SPName, String SPType){
		this.setParams(Params);
		this.setScenario(Scenario);
		this.setERROR_CODE(ErrorCode);
		this.setERR_MESSAGE(Err_Message);
		this.setSPExecuteSting(SPExecuteSting);
		this.setSPName(SPName);
		this.setSPType(SPType);
	}

	public Map<String, StoreProcParam> getParams() {
		return Params;
	}

	public void setParams(Map<String, StoreProcParam> params) {
		Params = params;
	}

	public String getScenario() {
		return Scenario;
	}

	public void setScenario(String scenario) {
		Scenario = scenario;
	}

	public String getERROR_CODE() {
		return ERROR_CODE;
	}

	public void setERROR_CODE(String eRROR_CODE) {
		ERROR_CODE = eRROR_CODE;
	}

	public String getERR_MESSAGE() {
		return ERR_MESSAGE;
	}

	public void setERR_MESSAGE(String eRR_MESSAGE) {
		ERR_MESSAGE = eRR_MESSAGE;
	}

	public String getSPExecuteSting() {
		return SPExecuteSting;
	}

	public void setSPExecuteSting(String sPExecuteSting) {
		SPExecuteSting = sPExecuteSting;
	}

	public String getSPName() {
		return SPName;
	}

	public void setSPName(String sPName) {
		SPName = sPName;
	}

	public String getSPType() {
		return SPType;
	}

	public void setSPType(String sPType) {
		SPType = sPType;
	}
}
