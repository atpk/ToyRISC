package processor.pipeline;
import generic.*;
public class EX_MA_LatchType {
	
	/* Booleans to indicate the instruction type */
	boolean MA_enable;
	boolean is_load = false;
	boolean is_store = false;
	boolean is_alu = false;
	boolean is_MA_busy;
	Instruction.OperationType optype;
	/* Variables required to store operands and immediate values */
	int word_at;
	int destination_register;
	int source_register;
	int alu_result;
	int x31_result;
	int ma_destination;
	int ma_dest_x31;


	/* Methods to set and get various values */
	public EX_MA_LatchType()
	{
		word_at = 0;
		destination_register = 0;
		source_register = 0;
		alu_result = 0;
		x31_result = 0;
		ma_destination = 0;
		ma_dest_x31 = 0;
		MA_enable = false;
	}

	public boolean isMA_enable() 
	{
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable) 
	{
		MA_enable = mA_enable;
	}

	public void setdestination_register(int rd) 
	{
		destination_register = rd;
	}

	public int getdestination_register() 
	{
		return destination_register;
	}

	public void setword_at(int word) 
	{
		word_at = word;
	}

	public int getword_at() 
	{
		return word_at;
	}

	public void setx31_result(int x31) 
	{
		x31_result = x31;
	}

	public int getx31_result() 
	{
		return x31_result;
	}

	public void setsource_register(int rs) 
	{
		source_register = rs;
	}

	public int getsource_register() 
	{
		return source_register;
	}

	public void setis_load() 
	{
		is_load = true;
	}

	public void setis_store() 
	{
		is_store = true;
	}

	public void setis_alu() 
	{
		is_alu = true;
	}

	public void setalu_result(int alu) 
	{
		alu_result = alu;
	}
	
	public int getalu_result() 
	{
		return alu_result;
	}

	public int get_ma_destination() 
	{
		return ma_destination;
	}

	public void set_ma_destination(int mA_destination) 
	{
		ma_destination = mA_destination;
	}

	public void setoptype(Instruction.OperationType given_optype) 
	{
		optype = given_optype;
	}
	public Instruction.OperationType getoptype() 
	{
		return optype;
	}
	public int get_ma_dest_x31() 
	{
		return ma_dest_x31;
	}

	public void set_ma_dest_x31(int mA_dest_x31) 
	{
		ma_dest_x31 = mA_dest_x31;
	}
	public boolean isMA_busy() {
		return is_MA_busy;
	}

	public void setMA_busy(boolean is_true) {
		is_MA_busy = is_true;
	}
}
