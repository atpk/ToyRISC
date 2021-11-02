	.data
n:
	10
	.text
main:
	addi %x0, 65534, %x3
	load %x0, $n, %x4
	addi %x0, 2, %x5
	blt %x4, %x5, final
	addi %x0, 1, %x6
	store %x6, 0, %x3
	subi %x4, 2, %x4
fibonacci:
	beq %x0, %x4, final
	load %x3, 0, %x6
	load %x3, 1, %x7
	add %x6, %x7, %x8
	subi %x3, 1, %x3
	store %x8, 0, %x3
	subi %x4, 1, %x4
	jmp fibonacci
final:
	end
