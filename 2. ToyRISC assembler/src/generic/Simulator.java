package generic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.nio.ByteBuffer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Simulator {

	static FileInputStream inputcodeStream = null;
	
	// Create a mapping for instructions
	public static Map<Instruction.OperationType, String> mapping = new HashMap<Instruction.OperationType, String>() {{
		put(Instruction.OperationType.add, "00000");//0
		put(Instruction.OperationType.addi, "00001");//1
		put(Instruction.OperationType.sub, "00010");//2
		put(Instruction.OperationType.subi, "00011");//3
		put(Instruction.OperationType.mul, "00100");//4
		put(Instruction.OperationType.muli, "00101");//5
		put(Instruction.OperationType.div, "00110");//6
		put(Instruction.OperationType.divi, "00111");//7
		put(Instruction.OperationType.and, "01000");//8
		put(Instruction.OperationType.andi, "01001");//9
		put(Instruction.OperationType.or, "01010");//10
		put(Instruction.OperationType.ori, "01011");//11
		put(Instruction.OperationType.xor, "01100");//12
		put(Instruction.OperationType.xori, "01101");//13
		put(Instruction.OperationType.slt, "01110");//14
		put(Instruction.OperationType.slti, "01111");//15
		put(Instruction.OperationType.sll, "10000");//16
		put(Instruction.OperationType.slli, "10001");//17
		put(Instruction.OperationType.srl, "10010");//18
		put(Instruction.OperationType.srli, "10011");//19
		put(Instruction.OperationType.sra, "10100");//20
		put(Instruction.OperationType.srai, "10101");//21
		put(Instruction.OperationType.load, "10110");//22
		put(Instruction.OperationType.store, "10111");//23
		put(Instruction.OperationType.jmp, "11000");//24
		put(Instruction.OperationType.beq, "11001");//25
		put(Instruction.OperationType.bne, "11010");//26
		put(Instruction.OperationType.blt, "11011");//27
		put(Instruction.OperationType.bgt, "11100");//28
		put(Instruction.OperationType.end, "11101");//29
	}};

	
	public static String parsing(int a, String s) {
		String str = String.valueOf(a);
		str = str + s;
		return str;
	}
	
	
	// given
	public static void setupSimulation(String assemblyProgramFile) {
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}
	
	// Convert given integer to string of specific length
	private static String convertToBinaryWithPrecision(int num, int precision) {
		
//		System.out.println(String.format("%" + precision + "s", Integer.toBinaryString(num)).replace(' ', '0'));
		
		return String.format("%" + precision + "s", Integer.toBinaryString(num)).replace(' ', '0');
	}
	
	// Convert given operand to Binary with required precision length
	private static String convert(Operand type, int precisionLength) {
		
		// 
		
		if (type == null)
			return convertToBinaryWithPrecision(0, precisionLength);

		if (type.getOperandType() == Operand.OperandType.Label)
			return convertToBinaryWithPrecision(ParsedProgram.symtab.get(type.getLabelValue()), precisionLength);

		// If opType is a label return the label value
		return convertToBinaryWithPrecision(type.getValue(), precisionLength);
	}

	// Function to be completed
	public static void assemble(String objectProgramFile) {
		// TODO your assembler code
		
		FileOutputStream outputFile;
		try {
			//1. open the objectProgramFile in binary mode
			outputFile = new FileOutputStream(objectProgramFile);
			BufferedOutputStream bufferFile = new BufferedOutputStream(outputFile);

			//2. write the firstCodeAddress to the file
			byte[] codeAdd = ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array();
			bufferFile.write(codeAdd);

			// System.out.println(codeAdd);
			
			//3. write the data to the file
			for (int i: ParsedProgram.data) {
				byte[] fileData = ByteBuffer.allocate(4).putInt(i).array();
				
//				System.out.println(fileData);
				
				bufferFile.write(fileData);
			}

			//4. assemble one instruction at a time, and write to the file
			for (Instruction inst: ParsedProgram.code) {

				// First we will convert the value from decimal to binary in string foramt
				// If value is label we will call ParsedProgram.symtab.get() to get its address
				
				String resultInBinary = "";

				// Convert instruction to opCode
				resultInBinary = resultInBinary + mapping.get(inst.getOperationType());
				
//				System.out.println(resultInBinary);				
				
				// convert binary opCode to decimal Number for further computations
				int opCode = Integer.parseInt(resultInBinary, 2);
				
				if (opCode >= 30) {
					
					System.out.println("opCode can't be greater than 29");
					
					break;
				}
				
//				System.out.println(opCode);
				
				// Store the program counter locally
				int PC = inst.getProgramCounter();
				
				if (opCode == 29) {
					
					// end instruction -> terminate execution
					
					resultInBinary += convertToBinaryWithPrecision(0, 27);
				}
				else if (opCode == 24) {
					
					// beq instruction
					
					String temp = convertToBinaryWithPrecision(0, 5);
					
					resultInBinary = resultInBinary + temp;
					String otherTemp = convertToBinaryWithPrecision(Integer.parseInt(convert(inst.getDestinationOperand(), 5), 2) - PC, 22);
					
					if (opCode < 24)
					System.out.println("end here");
					
					resultInBinary = resultInBinary + otherTemp.substring(otherTemp.length() - 22);
				}
				else if (opCode < 21 && opCode % 2 == 0) {
					
					// R3 Type
					
					String temp = convert(inst.getSourceOperand1(), 5);
					resultInBinary = resultInBinary + temp;
					temp = convert(inst.getSourceOperand2(), 5);
					resultInBinary = resultInBinary + temp;
					
					temp = convert(inst.getDestinationOperand(), 5);
					resultInBinary = resultInBinary + temp;
					
					temp = convertToBinaryWithPrecision(0, 12);
					resultInBinary = resultInBinary + temp;
				}
				else {
					// R2I Type
					if (opCode > 24 && opCode < 29) {
						
						// branch instructions except beq
						
						String temp = convert(inst.getSourceOperand1(), 5);
						resultInBinary = resultInBinary + temp;
						
						temp = convert(inst.getSourceOperand2(), 5);
						
						resultInBinary = resultInBinary + temp;
						
						temp = convertToBinaryWithPrecision(Integer.parseInt(convert(inst.getDestinationOperand(), 5), 2) - PC, 17);
						resultInBinary = resultInBinary + temp.substring(temp.length() - 17);
					}
					else {	
						
						// rest all instructions with immediate
						
						String temp = convert(inst.getSourceOperand1(), 5);
						resultInBinary += temp;
						
						temp = convert(inst.getDestinationOperand(), 5);
						resultInBinary += temp;
						
						temp = convert(inst.getSourceOperand2(), 17);
						resultInBinary += temp;
					}
				}
				
				// convert result in binary into decimal
				int tempInt = (int) Long.parseLong(resultInBinary, 2);
				
//				System.out.println(tempInt);
				
				byte[] instBinary = ByteBuffer.allocate(4).putInt(tempInt).array();
				bufferFile.write(instBinary);
			}

			//5. close the file
			bufferFile.close();
		} catch (IOException e) {			
			e.printStackTrace();
		} 
	}
	
}
