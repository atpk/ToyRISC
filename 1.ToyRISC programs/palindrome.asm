	.data
a:
	1001
	.text
main:
	load %x0, $a, %x4
	add %x0, %x4, %x5
	add %x0, %x0, %x6
	add %x0, %x0, %x7
loop:
	divi %x5, 10, %x5
	add %x6, %x31, %x6
	beq %x5, %x7, checkpal
	muli %x6, 10, %x6
	jmp loop
checkpal:
	beq %x6, %x4, pal
	subi %x0, 1, %x10
	end
pal:
	addi %x0, 1, %x10
	end
