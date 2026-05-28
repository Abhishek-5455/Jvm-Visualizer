package com.fusion.jvmviz.cfg;

import com.fusion.jvmviz.dto.ByteCodeInstruction;
import com.fusion.jvmviz.dto.CFGEdge;
import com.fusion.jvmviz.dto.CFGNode;
import com.fusion.jvmviz.dto.ControlFlowGraph;
import org.springframework.stereotype.Service;

import javax.naming.ldap.Control;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CFGGeneratorService {
    private static final Set<String> CONDITIONAL_JUMPS =
            Set.of(
                    "IFEQ", "IFNE", "IFLT", "IFGE", "IFGT", "IFLE",
                    "IF_ICMPEQ", "IF_ICMPNE", "IF_ICMPLT", "IF_ICMPGE", "IF_ICMPGT", "IF_ICMPLE"
            );

    public ControlFlowGraph generate(
            List<ByteCodeInstruction> instructions
    ) {
        List<CFGNode> nodes = new ArrayList<>();
        List<CFGEdge> edges = new ArrayList<>();

        for (ByteCodeInstruction instruction: instructions) {
            nodes.add(
                    new CFGNode(
                            instruction.getIndex(),
                            instruction.getOpcode(),
                            instruction.getOperand()
                    )
            );
        }

        for(int i = 0 ; i < instructions.size() ; i++) {
            ByteCodeInstruction current = instructions.get(i);
            String opcode = current.getOpcode();
            int currentId = current.getIndex();
            boolean isReturn = opcode.endsWith("RETURN");

            if(isReturn) {
                continue;
            }

            if (opcode.equals("GOTO")) {
                int target = extractJumpTarget(current);
                edges.add(
                        new CFGEdge(
                                currentId,
                                target,
                                "UNCONDITIONAL"
                        )
                );
                continue;
            }

            if (CONDITIONAL_JUMPS.contains(opcode)) {
                int target = extractJumpTarget(current);
                edges.add(
                        new CFGEdge(
                                currentId,
                                target,
                                "TRUE"
                        )
                );
                if(i + 1 < instructions.size()) {
                    edges.add(
                            new CFGEdge(
                                    currentId,
                                    instructions.get(i + 1).getIndex(),
                                    "FALSE"
                            )
                    );
                }
                continue;
            }

            if(i + 1 < instructions.size()) {
                edges.add(
                        new CFGEdge(
                                currentId,
                                instructions.get(i + 1).getIndex(),
                                "NEXT"
                        )
                );
            }

        }

        return new ControlFlowGraph(
                nodes, edges
        );
    }

    private int extractJumpTarget(ByteCodeInstruction instruction) {
        try {
            return Integer.parseInt(
                    instruction.getOperand()
            );
        } catch (Exception e) {
            return -1;
        }
    }
}
