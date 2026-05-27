package com.fusion.jvmviz.compiler;


import org.springframework.javapoet.JavaFile;
import org.springframework.stereotype.Service;

import javax.tools.*;
import java.util.List;

@Service
public class DynamicCompilerService {
    public byte[] compile(String className, String sourceCode) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        JavaFileObject file = new JavaSourceFromString(className, sourceCode);
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(diagnostics, null, null);

        MemoryFileManager fileManager = new MemoryFileManager(standardFileManager);

        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostics,
                null,
                null,
                List.of(file)
        );

        boolean success = task.call();

        if (!success) {
            throw new RuntimeException(diagnostics.getDiagnostics().toString());
        }

        return fileManager.getClassBytes();
    }
}
