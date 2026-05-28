export default function StackPanel({
  executionSteps
}) {

  return (
    <div className="space-y-4 max-h-[450px] overflow-y-auto pr-2">

      {
        executionSteps.map((step) => (

          <div
            key={step.instructionIndex}
            className="bg-black/40 border border-zinc-800 rounded-2xl p-4 hover:border-purple-500 transition-all duration-200"
          >

            <div className="flex items-center justify-between mb-4">

              <div className="font-mono text-cyan-400 font-bold text-lg">
                {step.opcode}
              </div>

              <div className="text-zinc-500 text-sm">
                #{step.instructionIndex}
              </div>
            </div>

            <div className="grid grid-cols-2 gap-4">

              <StackState
                title="Before"
                values={step.stackBefore}
              />

              <StackState
                title="After"
                values={step.stackAfter}
              />
            </div>
          </div>
        ))
      }
    </div>
  );
}

function StackState({ title, values }) {

  return (
    <div className="bg-zinc-900 border border-zinc-800 rounded-xl p-3">

      <div className="text-sm text-zinc-400 mb-3">
        {title}
      </div>

      <div className="flex flex-wrap gap-2 min-h-[40px]">

        {
          values.length === 0
            ? (
              <span className="text-zinc-600 text-sm">
                Empty
              </span>
            )
            : values.map((value, index) => (

              <div
                key={index}
                className="bg-purple-500/20 border border-purple-500/40 px-3 py-1 rounded-lg text-sm font-mono"
              >
                {value}
              </div>
            ))
        }
      </div>
    </div>
  );
}