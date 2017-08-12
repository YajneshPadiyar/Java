package hellow;

import java.util.ArrayList;
import java.util.List;

public class ListofRecordSet {

	private List<RowSet> ListofRecords = new ArrayList<RowSet>();
	
	public ListofRecordSet(){
		
	}
	
	public List<RowSet> getListValuePair() {
		return ListofRecords;
	}

	public void setListValuePair(List<RowSet> ListofRecords) {
		this.ListofRecords = ListofRecords;
	}
	
	public void addListValuePair(RowSet RecordsSet){
		this.ListofRecords.add(RecordsSet);
	}
	
}
