	.data
a:
	11
	.text
main:
	load %x0, $a, %x4
	addi %x0, 2, %x6
loop:
	div %x4, %x6, %x7
	addi %x0, 0, %x8
	beq %x31, %x8, notprime
	addi %x6, 1, %x6
	beq %x6, %x4, prime
	jmp loop
notprime:
	subi %x0, 1, %x10
	end
prime:
	addi %x0, 1, %x10
	end
