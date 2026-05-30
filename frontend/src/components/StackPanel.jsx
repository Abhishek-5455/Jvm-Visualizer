import StackView from "./StackView";

export default function StackPanel({
  executionSteps
}) {

  return (

    <div className="space-y-6">

      {
        executionSteps.map((step) => (

          <div
            key={step.instructionIndex}
            className="
            bg-zinc-900
            border
            border-zinc-800
            rounded-xl
            p-4
            "
          >

            <div
              className="
              flex
              justify-between
              items-center
              mb-4
              "
            >

              <div
                className="
                text-cyan-400
                font-mono
                text-lg
                "
              >
                {step.opcode}
              </div>

              <div
                className="
                text-zinc-500
                text-sm
                "
              >
                #{step.instructionIndex}
              </div>

            </div>

            <div
              className="
              grid
              grid-cols-2
              gap-6
              "
            >

              <StackView
                title="Before"
                values={step.stackBefore}
              />

              <StackView
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