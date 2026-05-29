export default function Panel({
  title,
  children
}) {

  return (

    <div
      className="
      bg-zinc-900
      border
      border-zinc-800
      rounded-2xl
      overflow-hidden
      "
    >

      <div
        className="
        px-5
        py-4
        border-b
        border-zinc-800
        "
      >

        <h2
          className="
          text-lg
          font-semibold
          "
        >
          {title}
        </h2>

      </div>

      <div className="p-4">

        {children}

      </div>

    </div>
  );
}