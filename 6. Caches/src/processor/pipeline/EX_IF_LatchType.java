package processor.pipeline;
import generic.*;
public class EX_IF_LatchType {
	
	/* EX-IF Latch is currently redundant */
	IF_EnableLatchType IF_EnableLatch;
	boolean EX_IFenable;
	public void setIF_enable(boolean iF_enable) 
	{

		IF_EnableLatch.setIF_enable(iF_enable);
	}
	public EX_IF_LatchType()
	{
		EX_IFenable = false;
	}
	public boolean isEX_IFenable() 
	{
		return EX_IFenable;
	}
	public void setEX_IFenable(boolean iF_enable) 
	{

		EX_IFenable = iF_enable;
	}
	int branch_pc = 0;
	boolean is_branch_taken = false;
	public void set_is_branch_taken(boolean is_branch_taken)
	{
		this.is_branch_taken = is_branch_taken;
	}
	public boolean get_is_branch_taken()
	{
		return is_branch_taken;
	}
	public void set_branch_pc(int branch_pc)
	{
		this.branch_pc = branch_pc;
	}
	public int get_branch_pc()
	{
		return branch_pc;
	}
}
