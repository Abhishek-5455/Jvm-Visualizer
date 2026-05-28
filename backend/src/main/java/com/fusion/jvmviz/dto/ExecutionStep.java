package com.fusion.jvmviz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutionStep {
    private int instructionIndex;
    private String opcode;
    private String operand;
    private List<String> stackBefore;
    private List<String> stackAfter;
}
