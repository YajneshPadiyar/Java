package hellow;
/*Test Commit 2*/
public class AdapterOutput {

	private RowSet Output = new RowSet();
	private ListofCursorSet CursorOutput = new ListofCursorSet();

	public AdapterOutput() {
	}

	public AdapterOutput(RowSet Output, ListofCursorSet CursorOutput) {
		this.CursorOutput = CursorOutput;
		this.Output = Output;
	}

	public RowSet getOutput() {
		return Output;
	}

	public void setOutput(RowSet output) {
		this.Output = output;
	}

	public ListofCursorSet getCursorOutput() {
		return CursorOutput;
	}

	public void setCursorOutput(ListofCursorSet cursorOutput) {
		this.CursorOutput = cursorOutput;
	}

}
