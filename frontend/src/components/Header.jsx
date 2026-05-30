export default function Header({
  analyze
}) {

  return (

    <div className="border-b border-zinc-800 px-8 py-5">

      <div className="flex justify-between items-center">

        <div>

          <h1 className="text-3xl font-bold">
            JVM Bytecode Visualizer
          </h1>

          <p className="text-zinc-400 mt-1">
            Interactive JVM Analysis Platform
          </p>

        </div>

        <button
          onClick={analyze}
          className="
          px-6
          py-3
          rounded-xl
          bg-blue-600
          hover:bg-blue-500
          transition
          "
        >
          Analyze
        </button>

      </div>

    </div>
  );
  
}