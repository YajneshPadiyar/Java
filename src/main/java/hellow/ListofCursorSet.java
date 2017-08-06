package hellow;

import java.util.HashMap;

public class ListofCursorSet {

	private HashMap<String,ListofRecordSet> CursorSet = new HashMap<String,ListofRecordSet>();
	
	public ListofCursorSet(){}
	
	public HashMap<String,ListofRecordSet> getCursorSet() {
		return CursorSet;
	}

	public void setCursorSet(HashMap<String,ListofRecordSet> cursorSet) {
		CursorSet = cursorSet;
	}
	
	public void addCursorSet(String CursorName,ListofRecordSet HRecordSet){
		this.CursorSet.put(CursorName, HRecordSet);
	}
	
}
