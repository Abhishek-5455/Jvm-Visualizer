package com.fusion.jvmviz.compiler;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class JavaSourceFromString extends SimpleJavaFileObject {

    private final String sourceCode;
    public JavaSourceFromString(String className, String sourceCode) {
        super(
                URI.create(
                        "string:///" +
                        className.replace('.', '/')  +
                        Kind.SOURCE.extension
                ),
                Kind.SOURCE
        );

        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}
