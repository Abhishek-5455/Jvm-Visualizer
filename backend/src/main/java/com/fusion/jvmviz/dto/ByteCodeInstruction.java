package com.fusion.jvmviz.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ByteCodeInstruction {
    private int index;
    private String opcode;
    private String operand;
}
