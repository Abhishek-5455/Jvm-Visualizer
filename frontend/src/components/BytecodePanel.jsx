export default function BytecodePanel({
  instructions
}) {

  return (

    <div className="max-h-[300px] overflow-y-auto">

      {
        instructions.map((inst) => (

          <div
            key={inst.index}
            className="
            flex
            justify-between
            font-mono
            py-2
            px-3
            rounded-lg
            hover:bg-zinc-800
            "
          >

            <span className="text-zinc-500">
              {inst.index}
            </span>

            <span className="text-cyan-400">
              {inst.opcode}
            </span>

            <span className="text-zinc-300">
              {inst.operand}
            </span>

          </div>

        ))
      }

    </div>

  );
}