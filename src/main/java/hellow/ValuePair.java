package hellow;

import java.util.HashMap;

public class ValuePair {

	private HashMap<String, String> RecordsSet = new HashMap<String, String>();

	public ValuePair() {
	}

	public HashMap<String, String> getHValuePair() {
		return RecordsSet;
	}

	public void setHValuePair(HashMap<String, String> RecordsSet) {
		this.RecordsSet = RecordsSet;
	}

	public void addValuePair(String key, String value) {
		this.RecordsSet.put(key, value);
	}
}
