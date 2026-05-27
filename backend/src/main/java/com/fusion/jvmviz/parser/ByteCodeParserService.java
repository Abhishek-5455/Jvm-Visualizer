package com.fusion.jvmviz.parser;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.Printer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ByteCodeParserService {
    public List<String> parse(byte[] classBytes) throws Exception {
        List<String> instructions = new ArrayList<>();
        ClassReader reaeder = new ClassReader(classBytes);

        reaeder.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override
            public org.objectweb.asm.MethodVisitor visitMethod(
                    int access,
                    String name,
                    String descriptor,
                    String signature,
                    String[] exceptions
            ) {
                return new org.objectweb.asm.MethodVisitor(Opcodes.ASM9) {
                    @Override
                    public void visitInsn(int opcode) {
                        instructions.add(Printer.OPCODES[opcode]);
                    }
                };
            }
        }, 0);

        return instructions;
    }
}