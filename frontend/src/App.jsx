import { useState } from "react";
import { Play, Cpu, GitBranch, Layers3 } from "lucide-react";

import CodeEditor from "./components/CodeEditor";
import BytecodePanel from "./components/BytecodePanel";
import StackPanel from "./components/StackPanel";

import { analyzeCode } from "./api/bytecodeApi";

export default function App() {

  const [code, setCode] = useState(`public class Demo {

  public int add(int a, int b) {
    return a + b;
  }
}`);

  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);

  const analyze = async () => {

    try {

      setLoading(true);

      const response = await analyzeCode(code);

      setResult(response);

    } catch (error) {

      console.error(error);

    } finally {

      setLoading(false);
    }
  };

  return (

    <div className="min-h-screen bg-gradient-to-br from-black via-zinc-950 to-zinc-900 text-white overflow-hidden">

      <div className="absolute inset-0 opacity-20">
        <div className="absolute top-0 left-0 w-96 h-96 bg-blue-500 rounded-full blur-3xl" />
        <div className="absolute bottom-0 right-0 w-96 h-96 bg-purple-500 rounded-full blur-3xl" />
      </div>

      <div className="relative z-10 p-8 max-w-7xl mx-auto">

        <div className="mb-8 flex items-center justify-between">

          <div>
            <h1 className="text-5xl font-black tracking-tight bg-gradient-to-r from-blue-400 to-cyan-300 text-transparent bg-clip-text">
              JVM Bytecode Visualizer
            </h1>

            <p className="text-zinc-400 mt-3 text-lg">
              Interactive JVM execution and bytecode analysis engine
            </p>
          </div>

          <div className="hidden md:flex gap-4">

            <StatCard
              icon={<Cpu size={20} />}
              title="Compiler"
              value="ASM 9"
            />

            <StatCard
              icon={<Layers3 size={20} />}
              title="Execution"
              value="Stack Sim"
            />

            <StatCard
              icon={<GitBranch size={20} />}
              title="Analysis"
              value="CFG"
            />
          </div>
        </div>

        <div className="grid grid-cols-1 xl:grid-cols-2 gap-6">

          <div className="bg-zinc-900/70 backdrop-blur-xl border border-zinc-800 rounded-3xl shadow-2xl overflow-hidden">

            <div className="flex items-center justify-between px-6 py-4 border-b border-zinc-800">

              <div>
                <h2 className="text-xl font-semibold">
                  Java Source Editor
                </h2>

                <p className="text-sm text-zinc-400 mt-1">
                  Write Java code and inspect JVM instructions
                </p>
              </div>

              <button
                onClick={analyze}
                disabled={loading}
                className="flex items-center gap-2 bg-gradient-to-r from-blue-600 to-cyan-500 hover:scale-105 transition-all duration-200 px-6 py-3 rounded-2xl font-semibold shadow-lg disabled:opacity-50"
              >
                <Play size={18} />
                {
                  loading
                    ? "Analyzing..."
                    : "Analyze"
                }
              </button>
            </div>

            <CodeEditor
              code={code}
              setCode={setCode}
            />
          </div>

          <div className="space-y-6">

            {
              result && (
                <>

                  <GlassPanel title="Bytecode Instructions">
                    <BytecodePanel
                      instructions={result.instructions}
                    />
                  </GlassPanel>

                  <GlassPanel title="Operand Stack Execution">
                    <StackPanel
                      executionSteps={result.executionSteps}
                    />
                  </GlassPanel>
                </>
              )
            }

            {
              !result && (

                <div className="bg-zinc-900/70 border border-dashed border-zinc-700 rounded-3xl h-full flex items-center justify-center p-10 text-center">

                  <div>
                    <Cpu
                      size={60}
                      className="mx-auto text-zinc-600 mb-4"
                    />

                    <h3 className="text-2xl font-bold mb-2">
                      Awaiting Analysis
                    </h3>

                    <p className="text-zinc-400 max-w-md">
                      Compile Java source code dynamically and visualize JVM bytecode execution, operand stack transitions, and control flow behavior.
                    </p>
                  </div>
                </div>
              )
            }
          </div>
        </div>
      </div>
    </div>
  );
}

function GlassPanel({ title, children }) {

  return (
    <div className="bg-zinc-900/70 backdrop-blur-xl border border-zinc-800 rounded-3xl shadow-2xl overflow-hidden">

      <div className="px-6 py-4 border-b border-zinc-800">
        <h2 className="text-xl font-semibold">
          {title}
        </h2>
      </div>

      <div className="p-6">
        {children}
      </div>
    </div>
  );
}

function StatCard({ icon, title, value }) {

  return (
    <div className="bg-zinc-900/80 border border-zinc-800 rounded-2xl px-5 py-4 min-w-[140px] shadow-xl">

      <div className="flex items-center gap-2 text-blue-400 mb-2">
        {icon}
        <span className="text-sm text-zinc-400">
          {title}
        </span>
      </div>

      <div className="text-xl font-bold">
        {value}
      </div>
    </div>
  );
}