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
import java.util.ArrayList;
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
    public AnalysisResponse analyze(@RequestBody CodeRequest request) throws Exception {
        try {
            byte[] bytes = compilerService.compile(
                    request.getClassName(),
                    request.getSourceCode()
            );

            List<ParsedMethod> parsedMethods = parserService.parse(bytes);
            List<MethodAnalysis> analysis = new ArrayList<>();

            for(ParsedMethod parsedMethod: parsedMethods) {
                List<ExecutionStep> executionSteps = simulator.simulate(
                        parsedMethod.getInstructions()
                );

                ControlFlowGraph cfg = cfgGeneratorService.generate(parsedMethod.getInstructions());

                analysis.add(
                        new MethodAnalysis(
                                parsedMethod.getMethodName(),
                                parsedMethod.getDescriptor(),
                                parsedMethod.getInstructions(),
                                executionSteps,
                                cfg
                        )
                );

            }

            return new AnalysisResponse(analysis);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
