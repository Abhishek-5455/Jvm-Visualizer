package com.fusion.jvmviz.dto;

import lombok.Data;

@Data
public class CodeRequest {
    private String className;
    private String sourceCode;
}
