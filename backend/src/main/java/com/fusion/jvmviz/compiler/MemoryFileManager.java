package com.fusion.jvmviz.compiler;

import org.springframework.javapoet.JavaFile;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

public class MemoryFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private MemoryByteCode memoryByteCode;
    public MemoryFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);

    }

    @Override
    public JavaFileObject getJavaFileForOutput(
            Location location,
            String className,
            JavaFileObject.Kind kind,
            FileObject sibling
    ) {
        memoryByteCode = new MemoryByteCode(className, kind);
        return memoryByteCode;
    }

    public byte[] getClassBytes() {
        if(memoryByteCode == null) {
            throw new RuntimeException("No compiled bytecode found.");
        }
        return memoryByteCode.getBytes();
    }
}
