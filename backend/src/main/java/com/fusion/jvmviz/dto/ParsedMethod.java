package com.fusion.jvmviz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ParsedMethod {
    private String methodName;
    private String descriptor;
    private List<ByteCodeInstruction> instructions;
}
