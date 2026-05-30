package com.fusion.jvmviz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnalysisResponse {
    private List<MethodAnalysis> methods;
}
