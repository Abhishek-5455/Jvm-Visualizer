export default function BytecodePanel({
  instructions
}) {

  return (
    <div className="space-y-2 max-h-[350px] overflow-y-auto pr-2">

      {
        instructions.map((inst) => (

          <div
            key={inst.index}
            className="flex items-center justify-between bg-black/40 border border-zinc-800 rounded-xl px-4 py-3 hover:border-blue-500 transition-all duration-200"
          >

            <div className="flex items-center gap-4">

              <div className="text-zinc-500 font-mono text-sm w-8">
                {inst.index}
              </div>

              <div className="font-mono text-cyan-400 font-semibold">
                {inst.opcode}
              </div>
            </div>

            <div className="font-mono text-zinc-300 text-sm">
              {inst.operand}
            </div>
          </div>
        ))
      }
    </div>
  );
}