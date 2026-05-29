package com.fusion.jvmviz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.objectweb.asm.Label;

@Data
@AllArgsConstructor
public class PendingJump {
    private Label label;
    private ByteCodeInstruction instruction;
}
