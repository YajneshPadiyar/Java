package hellow;

public class StoreProcParam {
	
	private String ParamName;
	private String ParamDataType;
	private String ParamType;
	private String ParamValue;
	private int ROW_ID;
	
	public StoreProcParam(String ParamName, String ParamDataType, String ParamType, String ParamValue){
		this.setParamName(ParamName);
		this.setParamDataType(ParamDataType);
		this.setParamType(ParamType);
		this.setParamValue(ParamValue);
		
	}

	public String getParamName() {
		return ParamName;
	}

	public void setParamName(String paramName) {
		ParamName = paramName;
	}

	public String getParamDataType() {
		return ParamDataType;
	}

	public void setParamDataType(String paramDataType) {
		ParamDataType = paramDataType;
	}

	public String getParamType() {
		return ParamType;
	}

	public void setParamType(String paramType) {
		ParamType = paramType;
	}

	public String getParamValue() {
		return ParamValue;
	}

	public void setParamValue(String paramValue) {
		ParamValue = paramValue;
	}

	public int getROW_ID() {
		return ROW_ID;
	}

	public void setROW_ID(int rOW_ID) {
		ROW_ID = rOW_ID;
	}

}
