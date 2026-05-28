package com.fusion.jvmviz.simulation;

import com.fusion.jvmviz.dto.ByteCodeInstruction;
import com.fusion.jvmviz.dto.ExecutionStep;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class OperandStackSimulator {
    public List<ExecutionStep> simulator(
            List<ByteCodeInstruction> instructions
    ) {
        Stack<String> operandStack = new Stack<>();

        List<ExecutionStep> executionSteps = new ArrayList<>();

        for(ByteCodeInstruction instruction: instructions) {
            List<String> before = new ArrayList<>(operandStack);

            executionInstruction(instruction, operandStack);

            List<String> after = new ArrayList<>(operandStack);
            executionSteps.add(
                    new ExecutionStep(
                            instruction.getIndex(),
                            instruction.getOpcode(),
                            instruction.getOperand(),
                            before,
                            after
                    )
            );
        }

        return executionSteps;
    }

    private void executionInstruction(
            ByteCodeInstruction instruction,
            Stack<String> operandStack
    ) {
        String opcode = instruction.getOpcode();

        switch (opcode) {
            case "ILOAD" -> {
                operandStack.push(
                        "var" + instruction.getOperand()
                );
            }

            case "ALOAD" -> {
                operandStack.push(
                        "ref" + instruction.getOperand()
                );
            }

            case "ICONST_0", "ICONST_1", "ICONST_2", "ICONST_3", "ICONST_4", "ICONST_5" -> {
                operandStack.push(
                        opcode.substring(7)
                );
            }

            case "IADD" -> {
                String value2 = operandStack.pop();
                String value1 = operandStack.pop();
                operandStack.push(
                        "(" + value1 + " + " + value2 + ")"
                );
            }

            case "ISUB" -> {
                String value2 = operandStack.pop();
                String value1 = operandStack.pop();
                operandStack.push(
                        "(" + value1 + " - " + value2 + ")"
                );
            }

            case "IMUL" -> {
                String value2 = operandStack.pop();
                String value1 = operandStack.pop();
                operandStack.push(
                        "(" + value1 + " * " + value2 + ")"
                );
            }

            case "IDIV" -> {
                String value2 = operandStack.pop();
                String value1 = operandStack.pop();
                operandStack.push(
                        "(" + value1 + " / " + value2 + ")"
                );
            }

            case "IRETURN", "RETURN" -> {
                if (!operandStack.isEmpty()) {
                    operandStack.pop();
                }
            }
        }
    }
}
