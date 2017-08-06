package hellow;
/*Test Commit*/
public class AdapterOutput {

	private ValuePair Output = new ValuePair();
	private ListofCursorSet CursorOutput = new ListofCursorSet();

	public AdapterOutput() {
	}

	public AdapterOutput(ValuePair Output, ListofCursorSet CursorOutput) {
		this.CursorOutput = CursorOutput;
		this.Output = Output;
	}

	public ValuePair getOutput() {
		return Output;
	}

	public void setOutput(ValuePair output) {
		this.Output = output;
	}

	public ListofCursorSet getCursorOutput() {
		return CursorOutput;
	}

	public void setCursorOutput(ListofCursorSet cursorOutput) {
		this.CursorOutput = cursorOutput;
	}

}
