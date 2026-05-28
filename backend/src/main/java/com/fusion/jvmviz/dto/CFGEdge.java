package com.fusion.jvmviz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CFGEdge {
    private int from;
    private int to;
    private String type;
}
