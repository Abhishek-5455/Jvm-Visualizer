export default function StackView({
  title,
  values
}) {

  return (

    <div>

      <h3
        className="
        text-sm
        text-zinc-400
        mb-3
        "
      >
        {title}
      </h3>

      <div
        className="
        min-h-[120px]
        bg-black/40
        border
        border-zinc-800
        rounded-xl
        p-3
        flex
        flex-col-reverse
        gap-2
        "
      >

        {
          values.length === 0 ? (

            <div
              className="
              text-zinc-600
              text-sm
              "
            >
              Empty Stack
            </div>

          ) : (

            values.map(
              (value, index) => (

                <div
                  key={index}
                  className="
                  bg-purple-500/20
                  border
                  border-purple-500/40
                  rounded-lg
                  px-3
                  py-2
                  font-mono
                  "
                >
                  {value}
                </div>

              )
            )
          )
        }

      </div>

    </div>

  );
}