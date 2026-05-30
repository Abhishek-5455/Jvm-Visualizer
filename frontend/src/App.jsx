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

  const [selectedMethod, setSelectedMethod] = useState(null);

  const analyze = async () => {

    try {

      const response =
        await analyzeCode(code);

      console.log(response);

      setResult(response);

      if(response.methods.length > 0) {

        setSelectedMethod(response.methods[0]);
      }

    } catch (error) {

      console.error(error);
    }
  };

  return (

    <div className="min-h-screen bg-zinc-950 text-white">

      <Header analyze={analyze} />
      {
        result &&
        result.methods.length > 0 && (

          <div
            className="
            bg-zinc-900
            border
            border-zinc-800
            rounded-xl
            p-4
            "
          >

            <label
              className="
              block
              text-sm
              text-zinc-400
              mb-2
              "
            >
              Select Method
            </label>

            <select

              value={
                selectedMethod?.methodName
              }

              onChange={(e) => {

                const method =
                  result.methods.find(
                    m =>
                      m.methodName ===
                      e.target.value
                  );

                setSelectedMethod(
                  method
                );
              }}

              className="
              bg-zinc-950
              border
              border-zinc-700
              rounded-lg
              px-4
              py-2
              w-full
              "
            >

              {
                result.methods.map(
                  (method) => (

                    <option
                      key={
                        method.methodName
                      }
                      value={
                        method.methodName
                      }
                    >

                      {
                        method.methodName
                      }

                      {" "}

                      {
                        method.descriptor
                      }

                    </option>

                  )
                )
              }

            </select>

          </div>
        )
      }

      {
        selectedMethod && (

          <div
            className="
            bg-zinc-900
            border
            border-zinc-800
            rounded-xl
            p-4
            "
          >

            <h2
              className="
              text-xl
              font-semibold
              "
            >
              {
                selectedMethod.methodName
              }
            </h2>

            <p
              className="
              text-zinc-400
              mt-1
              "
            >
              {
                selectedMethod.descriptor
              }
            </p>

            <div
              className="
              mt-4
              flex
              gap-6
              "
            >

              <div>

                <span
                  className="
                  text-zinc-500
                  "
                >
                  Instructions:
                </span>

                {" "}

                {
                  selectedMethod
                    .instructions
                    .length
                }

              </div>

              <div>

                <span
                  className="
                  text-zinc-500
                  "
                >
                  Stack Steps:
                </span>

                {" "}

                {
                  selectedMethod
                    .executionSteps
                    .length
                }

              </div>

              <div>

                <span
                  className="
                  text-zinc-500
                  "
                >
                  CFG Nodes:
                </span>

                {" "}

                {
                  selectedMethod
                    .controlFlowGraph
                    .nodes
                    .length
                }

              </div>

            </div>

          </div>

        )
      }

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
                    selectedMethod.instructions
                  }
                />
              }

            </Panel>

            <Panel title="Operand Stack">

              {
                result &&
                <StackPanel
                  executionSteps={
                    selectedMethod.executionSteps
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
                  selectedMethod.controlFlowGraph
                }
              />

            </Panel>

          )
        }

      </div>

    </div>
  );
}