package com.fusion.jvmviz.controller;


import com.fusion.jvmviz.cfg.CFGGeneratorService;
import com.fusion.jvmviz.compiler.DynamicCompilerService;
import com.fusion.jvmviz.dto.*;
import com.fusion.jvmviz.parser.ByteCodeParserService;
import com.fusion.jvmviz.simulation.OperandStackSimulator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.ldap.Control;
import java.util.List;

@RestController
@RequestMapping("/api/bytecode")
@RequiredArgsConstructor
public class ByteCodeController {
    private final DynamicCompilerService compilerService;
    private final ByteCodeParserService parserService;
    private final OperandStackSimulator simulator;
    private final CFGGeneratorService cfgGeneratorService;
    @PostMapping
    public ByteCodeResponse analyze(@RequestBody CodeRequest request) throws Exception {
        try {
            byte[] bytes = compilerService.compile(
                    request.getClassName(),
                    request.getSourceCode()
            );

            List<ByteCodeInstruction> instructions = parserService.parse(bytes);
            List<ExecutionStep> executionSteps = simulator.simulator(instructions);

            ControlFlowGraph cfg = cfgGeneratorService.generate(instructions);

            System.out.println("==== Instructions ====");
            instructions.forEach(System.out::println);

            System.out.println("==== Edges ====");
            cfg.getEdges().forEach(System.out::println);



            return new ByteCodeResponse(instructions, executionSteps, cfg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
