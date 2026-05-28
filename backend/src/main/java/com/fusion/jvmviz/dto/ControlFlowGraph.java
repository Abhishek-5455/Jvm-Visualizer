package com.fusion.jvmviz.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ControlFlowGraph {
    private List<CFGNode> nodes;
    private List<CFGEdge> edges;
}
