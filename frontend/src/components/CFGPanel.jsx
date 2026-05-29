import ReactFlow, {
  Background,
  Controls,
  MiniMap
} from "reactflow";

import { layoutGraph } from "../utils/layoutGraph";

export default function CFGPanel({
  cfg
}) {

  let nodes = cfg.nodes.map(
    (node) => ({
        id: String(node.id),

        data: {
        label:
            `${node.id} ${node.opcode}`
        },

        position: {
        x: 0,
        y: 0
        }
    })
  );

  const edges = cfg.edges.map(
    (edge, index) => ({
        id: `e-${index}`,

        source:
        String(edge.from),

        target:
        String(edge.to),

        label:
        edge.type,

        animated:
        edge.type !== "NEXT"
    })
  );

  nodes = layoutGraph(
        nodes,
        edges
    );

  return (

    <div
      className="
      w-full
      h-[900px]
      "
    >

      <ReactFlow
        nodes={nodes}
        edges={edges}
        fitView
      >

        <Background />

        <Controls />

        <MiniMap />

      </ReactFlow>

    </div>

  );
}