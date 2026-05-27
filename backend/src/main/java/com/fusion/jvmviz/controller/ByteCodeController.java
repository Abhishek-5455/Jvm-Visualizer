package com.fusion.jvmviz.controller;


import com.fusion.jvmviz.compiler.DynamicCompilerService;
import com.fusion.jvmviz.dto.ByteCodeResponse;
import com.fusion.jvmviz.dto.CodeRequest;
import com.fusion.jvmviz.parser.ByteCodeParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bytecode")
@RequiredArgsConstructor
public class ByteCodeController {
    private final DynamicCompilerService compilerService;
    private final ByteCodeParserService parserService;

    @PostMapping
    public ByteCodeResponse analyze(@RequestBody CodeRequest request) throws Exception {
        byte[] bytes = compilerService.compile(
                request.getClassName(),
                request.getSourceCode()
        );

        List<String> instructions = parserService.parse(bytes);

        return new ByteCodeResponse(instructions);
    }
}
