package hellow;

import java.util.ArrayList;
import java.util.List;

public class ListofRecordSet {

	private List<ValuePair> ListofRecords = new ArrayList<ValuePair>();
	
	public ListofRecordSet(){
		
	}
	
	public List<ValuePair> getListValuePair() {
		return ListofRecords;
	}

	public void setListValuePair(List<ValuePair> listValuePair) {
		ListofRecords = listValuePair;
	}
	
	public void addListValuePair(ValuePair RecordsSet){
		this.ListofRecords.add(RecordsSet);
	}
	
}
