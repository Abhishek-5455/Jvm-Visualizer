package com.fusion.jvmviz.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFGNode {
    private int id;
    private String opcode;
    private String operand;
}
