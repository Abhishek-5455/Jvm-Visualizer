package com.fusion.jvmviz.parser;

import com.fusion.jvmviz.dto.ByteCodeInstruction;
import org.objectweb.asm.*;
import org.objectweb.asm.util.Printer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ByteCodeParserService {
    public List<ByteCodeInstruction> parse(byte[] classBytes) throws Exception {
        List<ByteCodeInstruction> instructions = new ArrayList<>();
        ClassReader reader = new ClassReader(classBytes);

        AtomicInteger instructionIndex = new AtomicInteger(0);

        Map<Label, Integer> labelMap = new HashMap<>();
        reader.accept(new ClassVisitor(Opcodes.ASM9) {



            @Override
            public org.objectweb.asm.MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions
            ) {
                return new MethodVisitor(Opcodes.ASM9) {

                    @Override
                    public void visitLabel(Label label) {
                        labelMap.put(label, instructionIndex.get());
                    }

                    @Override
                    public void visitInsn(int opcode) {
                        instructions.add(
                          new ByteCodeInstruction(
                                    instructionIndex.getAndIncrement(),
                                    Printer.OPCODES[opcode],
                                    null
                          )
                        );
                    }

                    @Override
                    public void visitVarInsn(int opcode, int varIndex) {
                        instructions.add(
                               new ByteCodeInstruction(
                                        instructionIndex.getAndIncrement(),
                                        Printer.OPCODES[opcode],
                                        String.valueOf(varIndex)
                               )
                        );
                    }

                    @Override
                    public void visitMethodInsn(
                            int opcode,
                            String owner,
                            String name,
                            String descriptor,
                            boolean isInterface
                    ) {
                        instructions.add(
                                new ByteCodeInstruction(
                                        instructionIndex.getAndIncrement(),
                                        Printer.OPCODES[opcode],
                                        owner + "." + name + " : " + descriptor
                                )
                        );
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
                        instructions.add(
                            new ByteCodeInstruction(
                                    instructionIndex.getAndIncrement(),
                                    Printer.OPCODES[opcode],
                                    owner + "." + name + " : " + descriptor
                            )
                        );
                    }

                    @Override
                    public void visitJumpInsn(int opcode, Label label) {
                        instructions.add(
                                new ByteCodeInstruction(
                                        instructionIndex.getAndIncrement(),
                                        Printer.OPCODES[opcode],
                                        String.valueOf(labelMap.getOrDefault(label, -1)) + label.hashCode()
                                )
                        );
                    }

                };
            }
        }, 0);

        return instructions;
    }
}