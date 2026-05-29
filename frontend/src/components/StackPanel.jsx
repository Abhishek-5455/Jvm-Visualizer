export default function StackPanel({
  executionSteps
}) {

  return (

    <div className="max-h-[300px] overflow-y-auto">

      {
        executionSteps.map((step) => (

          <div
            key={step.instructionIndex}
            className="
            border-b
            border-zinc-800
            pb-3
            mb-3
            "
          >

            <div
              className="
              text-purple-400
              font-mono
              mb-2
              "
            >
              {step.opcode}
            </div>

            <div
              className="
              flex
              flex-col-reverse
              gap-2
              "
            >

              {
                step.stackAfter.map(
                  (value, index) => (

                    <div
                      key={index}
                      className="
                      bg-purple-500/20
                      border
                      border-purple-500/40
                      rounded-lg
                      p-2
                      font-mono
                      "
                    >
                      {value}
                    </div>

                  )
                )
              }

            </div>

          </div>

        ))
      }

    </div>

  );
}