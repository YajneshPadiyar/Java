package hellow;

import java.util.HashMap;
import java.util.Map;

public class ExecuteAdapterParam {
	private String Name;
	private String Age;
	private Map<String,String> Parameters = new HashMap<String,String>();
	
	public ExecuteAdapterParam(){
		
	}
	
public ExecuteAdapterParam(String Name, String Age, Map<String,String> Parameters){
		this.setAge(Age);
		this.setName(Name);
		this.setParameters(Parameters);
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAge() {
		return Age;
	}

	public void setAge(String age) {
		Age = age;
	}
	
	public void setParameters(Map<String,String> Parameters){
		this.Parameters = Parameters;
	}
	
	public Map<String,String> getParameters(){
		return this.Parameters;
	}
}
