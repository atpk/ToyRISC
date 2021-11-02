	.data
a:
	33
	.text
main:
	load %x0, $a, %x4
	divi %x4, 2, %x5
	addi %x0, 1, %x6
	beq %x31, %x6, odd
	subi %x0, 1, %x10
	end
odd:
	addi %x0, 1, %x10
	end
