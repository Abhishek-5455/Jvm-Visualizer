import { useState } from "react";

import Header from "./components/Header";
import Panel from "./components/Panel";
import CodeEditor from "./components/CodeEditor";
import BytecodePanel from "./components/BytecodePanel";
import StackPanel from "./components/StackPanel";
import CFGPanel from "./components/CFGPanel";

import { analyzeCode } from "./api/bytecodeApi";

export default function App() {

  const [code, setCode] = useState(`public class Demo {

  public int add(int a, int b) {
      return a + b;
  }
}`);

  const [result, setResult] = useState(null);

  const analyze = async () => {

    try {

      const response =
        await analyzeCode(code);

      console.log(response);

      setResult(response);

    } catch (error) {

      console.error(error);
    }
  };

  return (

    <div className="min-h-screen bg-zinc-950 text-white">

      <Header analyze={analyze} />

      <div className="p-6 space-y-6">

        <div className="grid grid-cols-12 gap-6">

          <div className="col-span-7">

            <Panel title="Java Source">

              <CodeEditor
                code={code}
                setCode={setCode}
              />

            </Panel>

          </div>

          <div className="col-span-5 flex flex-col gap-6">

            <Panel title="Bytecode">

              {
                result &&
                <BytecodePanel
                  instructions={
                    result.instructions
                  }
                />
              }

            </Panel>

            <Panel title="Operand Stack">

              {
                result &&
                <StackPanel
                  executionSteps={
                    result.executionSteps
                  }
                />
              }

            </Panel>

          </div>

        </div>

        {
          result && (

            <Panel title="Control Flow Graph">

              <CFGPanel
                cfg={
                  result.controlFlowGraph
                }
              />

            </Panel>

          )
        }

      </div>

    </div>
  );
}