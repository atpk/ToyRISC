package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import generic.Operand.OperandType;

import java.util.*;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
		
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}

	// Convert binary string to int
	public static int binaryStringToInt(String bin) {
		int n = Integer.parseInt(bin, 2);
		return n;
	}
	
	// Method to find two's complement
	// Implementation of this function is done using sources over internet
	static String twosComplement(String str_s)
	{
		StringBuffer str = new StringBuffer(str_s);
		int n = str.length();
	 
		// Traverse the string to get first '1' from
		// the last of string
		int i;
		for (i = n-1 ; i >= 0 ; i--)
			if (str.charAt(i) == '1')
				break;
	 
		// If there exists no '1' concat 1 at the
		// starting of string
		if (i == -1)
			return "1" + str;
		  
		// Continue traversal after the position of
		// first '1'
		for (int k = i-1 ; k >= 0; k--)
		{
			//Just flip the values
			if (str.charAt(k) == '1')
				str.replace(k, k+1, "0");
			else
				str.replace(k, k+1, "1");
		}
			  
		// return the modified string
		return str.toString();
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			OperationType[] opType = OperationType.values();
			// Compute 32-bit binary instruction
			String inst_type = Integer.toBinaryString(IF_OF_Latch.getInstruction());
			while(inst_type.length()!=32)
			{
				inst_type = "0" + inst_type;
			}
			
			// other initializations
			String opcode = inst_type.substring(0, 5);
			int int_op = binaryStringToInt(opcode);
			// get operation
			OperationType o = opType[int_op];
			List<OperationType> list = Arrays.asList(OperationType.values());
			int inst_code = list.indexOf(o);

			Instruction inst = new Instruction();
			Operand rs1 = new Operand();
			Operand rs2 = new Operand();
			Operand rd = new Operand();
			int regNo;
			
			// Perform the operation according to instruction
			switch(inst_code)
			{
				// end operation
				case 29:
					inst.setOperationType(opType[int_op]);
					break;
					
				// jump operation
				case 24:
					Operand op = new Operand();
					String imm = inst_type.substring(10, 32);
					int imm_val = binaryStringToInt(imm);
					// check if immediate value is -ve
					if (imm.charAt(0) == '1')
					{
						imm = twosComplement(imm);
						imm_val = binaryStringToInt(imm) * -1;
					}
					// calculate and set if immdeiate else register
					if (imm_val != 0)
					{
						op.setOperandType(OperandType.Immediate);
						op.setValue(imm_val);
					}
					else
					{
						regNo = binaryStringToInt(inst_type.substring(5, 10));
						op.setOperandType(OperandType.Register);
						op.setValue(regNo);
					}

					inst.setOperationType(opType[int_op]);
					inst.setDestinationOperand(op);
					break;
				
				// conditional branch operations
				case 25:
				case 26:
				case 27:
				case 28:
					// calculate and set source register
					rs1.setOperandType(OperandType.Register);
					regNo = binaryStringToInt(inst_type.substring(5, 10));
					rs1.setValue(regNo);

					// calculate and set destination register
					rs2.setOperandType(OperandType.Register);
					regNo = binaryStringToInt(inst_type.substring(10, 15));
					rs2.setValue(regNo);

					// calculate and set immediate value
					rd.setOperandType(OperandType.Immediate);
					imm = inst_type.substring(15, 32);
					imm_val = binaryStringToInt(imm);
					if (imm.charAt(0) == '1')
					{
						imm = twosComplement(imm);
						imm_val = binaryStringToInt(imm) * -1;
					}
					rd.setValue(imm_val);

					inst.setOperationType(opType[int_op]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
					break;
				
				// operations without immediate
				case 0:
				case 2:
				case 4:
				case 6:
				case 8:
				case 10:
				case 12:
				case 14:
				case 16:
				case 18:
				case 20:
					// calculate and set source register 1
					rs1.setOperandType(OperandType.Register);
					regNo = binaryStringToInt(inst_type.substring(5, 10));
					rs1.setValue(regNo);

					// calculate and set source register 2
					rs2.setOperandType(OperandType.Register);
					regNo = binaryStringToInt(inst_type.substring(10, 15));
					rs2.setValue(regNo);

					// calculate and set destination register
					rd.setOperandType(OperandType.Register);
					regNo = binaryStringToInt(inst_type.substring(15, 20));
					rd.setValue(regNo);

					inst.setOperationType(opType[int_op]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
					break;
				
				// operations with immediate
				default:
					// calculate and set source register
					rs1.setOperandType(OperandType.Register);
					regNo = binaryStringToInt(inst_type.substring(5, 10));
					rs1.setValue(regNo);

					// calculate and set destination register
					rd.setOperandType(OperandType.Register);
					regNo = binaryStringToInt(inst_type.substring(10, 15));
					rd.setValue(regNo);

					// calculate and set immediate
					rs2.setOperandType(OperandType.Immediate);
					imm = inst_type.substring(15, 32);
					imm_val = binaryStringToInt(imm);
					if (imm.charAt(0) == '1')
					{
						imm = twosComplement(imm);
						imm_val = binaryStringToInt(imm) * -1;
					}
					rs2.setValue(imm_val);

					inst.setOperationType(opType[int_op]);
					inst.setSourceOperand1(rs1);
					inst.setSourceOperand2(rs2);
					inst.setDestinationOperand(rd);
					break;
			}

			OF_EX_Latch.setInstruction(inst);
			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
