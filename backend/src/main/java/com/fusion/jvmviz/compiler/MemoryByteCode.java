package com.fusion.jvmviz.compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

public class MemoryByteCode extends SimpleJavaFileObject {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    public MemoryByteCode(String className, Kind kind) {
        super(
                URI.create(
                        "byte:///" +
                        className.replace('.', '/') +
                        kind.extension
                ),
                kind
        );
    }

    @Override
    public OutputStream openOutputStream() {
        return outputStream;
    }

    public byte[] getBytes() {
        return outputStream.toByteArray();
    }
}
