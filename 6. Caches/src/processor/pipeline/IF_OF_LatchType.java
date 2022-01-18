package processor.pipeline;
import generic.*;
public class IF_OF_LatchType {
	
	boolean OF_enable;
	boolean is_nop; /* flag to indicate if an instruction is nop and OF has to ignore such instruction */
	int instruction;
	Instruction.OperationType optype = null;
	boolean is_OF_busy;
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	public void force_nop() /* method to force nop */
	{
		optype = Instruction.OperationType.valueOf("nop");

	}
	public void set_is_nop(boolean noP) /* method to set nop flag */
	{
		is_nop=noP;
	}
	public boolean get_is_nop()
	{
		return is_nop;
	}
	public boolean isOF_busy() {
		return is_OF_busy;
	}

	public void setOF_busy(boolean is_true) {
		is_OF_busy = is_true;
	}
}
