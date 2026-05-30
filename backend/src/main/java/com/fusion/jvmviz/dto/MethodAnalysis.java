package com.fusion.jvmviz.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MethodAnalysis {
    private String methodName;
    private String descriptor;
    private List<ByteCodeInstruction> instructions;
    private List<ExecutionStep> executionSteps;
    private ControlFlowGraph controlFlowGraph;
}
