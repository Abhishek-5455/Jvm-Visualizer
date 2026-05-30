package com.fusion.jvmviz.parser;

import com.fusion.jvmviz.dto.ByteCodeInstruction;

import com.fusion.jvmviz.dto.ParsedMethod;
import com.fusion.jvmviz.dto.PendingJump;
import org.objectweb.asm.*;
import org.objectweb.asm.util.Printer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ByteCodeParserService {

    public List<ParsedMethod> parse(
            byte[] classBytes
    ) {

        List<ByteCodeInstruction> instructions =
                new ArrayList<>();

        Map<Label, Integer> labelMap =
                new HashMap<>();

        List<PendingJump> pendingJumps =
                new ArrayList<>();

        AtomicInteger instructionIndex =
                new AtomicInteger(0);

        ClassReader reader =
                new ClassReader(classBytes);

        List<ParsedMethod> methods = new ArrayList<>();

        reader.accept(
                new ClassVisitor(Opcodes.ASM9) {

                    @Override
                    public MethodVisitor visitMethod(
                            int access,
                            String name,
                            String descriptor,
                            String signature,
                            String[] exceptions
                    ) {

                        return new MethodVisitor(
                                Opcodes.ASM9
                        ) {

                            @Override
                            public void visitLabel(
                                    Label label
                            ) {

                                labelMap.put(
                                        label,
                                        instructionIndex.get()
                                );
                            }

                            @Override
                            public void visitInsn(
                                    int opcode
                            ) {

                                instructions.add(
                                        new ByteCodeInstruction(
                                                instructionIndex.getAndIncrement(),
                                                Printer.OPCODES[opcode],
                                                null
                                        )
                                );
                            }

                            @Override
                            public void visitVarInsn(
                                    int opcode,
                                    int varIndex
                            ) {

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
                                    String methodName,
                                    String methodDescriptor,
                                    boolean isInterface
                            ) {

                                instructions.add(
                                        new ByteCodeInstruction(
                                                instructionIndex.getAndIncrement(),
                                                Printer.OPCODES[opcode],
                                                owner +
                                                        "." +
                                                        methodName +
                                                        " : " +
                                                        methodDescriptor
                                        )
                                );
                            }

                            @Override
                            public void visitFieldInsn(
                                    int opcode,
                                    String owner,
                                    String fieldName,
                                    String fieldDescriptor
                            ) {

                                instructions.add(
                                        new ByteCodeInstruction(
                                                instructionIndex.getAndIncrement(),
                                                Printer.OPCODES[opcode],
                                                owner +
                                                        "." +
                                                        fieldName
                                        )
                                );
                            }

                            @Override
                            public void visitJumpInsn(
                                    int opcode,
                                    Label label
                            ) {

                                ByteCodeInstruction jumpInstruction =
                                        new ByteCodeInstruction(
                                                instructionIndex.getAndIncrement(),
                                                Printer.OPCODES[opcode],
                                                null
                                        );

                                instructions.add(
                                        jumpInstruction
                                );

                                pendingJumps.add(
                                        new PendingJump(
                                                label,
                                                jumpInstruction
                                        )
                                );
                            }


                            @Override
                            public void visitEnd() {
                                for(
                                        PendingJump jump: pendingJumps
                                ) {
                                    Integer target =
                                            labelMap.get(
                                                    jump.getLabel()
                                            );

                                    jump.getInstruction()
                                            .setOperand(
                                                    target != null ? String.valueOf(target) : "-1"
                                            );
                                }

                                methods.add(
                                        new ParsedMethod(
                                                name,
                                                descriptor,
                                                new ArrayList<>(instructions)
                                        )
                                );
                                super.visitEnd();
                            }
                        };
                    }

                },
                0
        );

        // PASS 2: Resolve labels

        for (PendingJump jump : pendingJumps) {

            Integer target =
                    labelMap.get(
                            jump.getLabel()
                    );

            if (target != null) {

                jump.getInstruction()
                        .setOperand(
                                String.valueOf(target)
                        );

            } else {

                jump.getInstruction()
                        .setOperand("-1");
            }
        }
        for (PendingJump jump : pendingJumps) {

            System.out.println(
                    jump.getInstruction()
            );
        }

        return methods;
    }
}