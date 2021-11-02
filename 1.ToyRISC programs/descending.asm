    .data
a:
    70
    80
    40
    20
    10
    30
    50
    60
n:
    8
    .text
main:
    load %x0, $n, %x3
    add %x0, %x0, %x4
    add %x0, %x0, %x5
    subi %x3, 1, %x10
    jmp loop
loop1:
    addi %x4, 1, %x4
    add %x0, %x0, %x5
    jmp loop
loop2:
    addi %x5, 1, %x5
    beq %x5, %x10, loop1
    jmp loop
loop:
    beq %x4, %x3, final
    beq %x5, %x10, loop1
    addi %x5, 1, %x6
    load %x5, $a, %x8
    load %x6, $a, %x7
    bgt %x7, %x8, swap
    jmp loop2
swap:
    add %x8, %x0, %x9
    add %x7, %x0, %x8
    add %x9, %x0, %x7
    store %x8, $a, %x5
    store %x7, $a, %x6
    jmp loop2
final:
    end
