import dagre from "dagre";

const dagreGraph = new dagre.graphlib.Graph();

dagreGraph.setDefaultEdgeLabel(
  () => ({})
);

const nodeWidth = 180;
const nodeHeight = 60;

export function layoutGraph(
  nodes,
  edges
) {

  dagreGraph.setGraph({
    rankdir: "TB"
  });

  nodes.forEach((node) => {

    dagreGraph.setNode(
      node.id,
      {
        width: nodeWidth,
        height: nodeHeight
      }
    );
  });

  edges.forEach((edge) => {

    dagreGraph.setEdge(
      edge.source,
      edge.target
    );
  });

  dagre.layout(dagreGraph);

  return nodes.map((node) => {

    const position =
      dagreGraph.node(node.id);

    return {
      ...node,
      position: {
        x: position.x,
        y: position.y
      }
    };
  });
}