package pathsearch.AI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {

	private int vertexCount;
	private int edgeCount;
	private List<Node> nodes = new ArrayList<Node>();
	private Map<String, Node> nodeCordinatesToNodeObject = new HashMap<String, Node>();
	boolean readVertices = false;
	boolean readEdges = false;
	public static int debug = 0;

	public void generateGraphFromFile(String filePath) {

		BufferedReader br = null;
		try {

			br = new BufferedReader(new FileReader(filePath));
			String line = br.readLine();
			while (line != null) {

				if (line.contains("vertices: ")) {
					vertexCount = Integer.parseInt(line.split("\\s+")[1]);
					readVertices = true;
					line = br.readLine();
					continue;
				}
				if (line.contains("edges: ")) {
					edgeCount = Integer.parseInt(line.split("\\s+")[1]);
					readEdges = true;
					readVertices = false;
					line = br.readLine();
					continue;
				}
				if (readVertices) {
					String eachLine[] = line.split("\\s+");
					int id = Integer.parseInt(eachLine[0]);
					int x = Integer.parseInt(eachLine[1]);
					int y = Integer.parseInt(eachLine[2]);
					Node vertex = new Node(id, x, y);
					nodeCordinatesToNodeObject.put(x + "," + y, vertex);
					this.nodes.add(vertex);
				}
				if (readEdges) {
					String eachLine[] = line.split("\\s+");
					int v1_id = Integer.parseInt(eachLine[1]);
					int v2_id = Integer.parseInt(eachLine[2]);
					addEdge(v1_id, v2_id);
				}
				line = br.readLine();
			}

		} catch (Exception e) {
			System.out.println("Error while reading the file. Error: " + e.getMessage());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("Error while reading the file. Error: " + e.getMessage());
				}
			}
		}

	}

	public int getVertexCount() {
		return vertexCount;
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public int getEdgeCount() {
		return edgeCount;
	}

	public void setEdgeCount(int edgeCount) {
		this.edgeCount = edgeCount;
	}

	public void addEdge(int v1_id, int v2_id) {

		Node v1 = nodes.get(v1_id);
		Node v2 = nodes.get(v2_id);
		v1.getAdjList().add(v2);
		v2.getAdjList().add(v1);
	}

	// public void printGraph() {
	//
	// for (Node v : this.nodes) {
	// System.out.println(v.getId());
	// System.out.println("Neighbors :");
	// for (Node v0 : v.getAdjList()) {
	// System.out.print(" " + v0.getId() + "(" + v0.getX() + "," + v0.getY() +
	// ")");
	// }
	// System.out.println();
	// }
	// }

	private void printSummary(Node goalNode) {

		Stack<Node> path = goalNode.traceback();
		int pathLength = path.size() - 1;
		System.out.println("==============");
		System.out.println("Solution path:");
		while (!path.isEmpty()) {
			Node node = path.pop();
			System.out.println("  vertex " + node.getId() + " (" + node.getX() + "," + node.getY() + ")");
			//System.out.println(node.getX() +" "+ node.getY());
		}
		System.out.println("Search Algortihm  = " + GraphSearch.searchTypeUsed);
		System.out.println("Total Iterations  = " + GraphSearch.iterations);
		System.out.println("Max Frontier Size = " + GraphSearch.maxFrontierSize);
		System.out.println("Vertices visited   = " + GraphSearch.verticesVisited + "/" + vertexCount);
		System.out.println("Path length       = " + pathLength);
	}

	public static void main(String[] args) {

		if (args.length != 7) {
			System.out.println("Invalid arguments.");
			System.exit(1);
		}
		Graph graph = new Graph();
		// generate graph from the file entered in args[0]
		graph.generateGraphFromFile(args[0]);
		// enable debug
		if (Integer.parseInt(args[5]) == 1) {
			debug = 1;
		}

		Node startNode = graph.nodeCordinatesToNodeObject.get(args[1] + "," + args[2]);
		Node goalNode = graph.nodeCordinatesToNodeObject.get(args[3] + "," + args[4]);

		GraphSearch.search(graph, startNode, goalNode, args[6]);
		// print Summary
		graph.printSummary(goalNode);

	}

}
