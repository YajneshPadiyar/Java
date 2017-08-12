package hellow;

import java.util.HashMap;

public class RowSet {

	private HashMap<String, String> RecordsSet = new HashMap<String, String>();

	public RowSet() {
	}

	public HashMap<String, String> getValuePair() {
		return RecordsSet;
	}

	public void setHValuePair(HashMap<String, String> RecordsSet) {
		this.RecordsSet = RecordsSet;
	}

	public void addValuePair(String key, String value) {
		this.RecordsSet.put(key, value);
	}
}
