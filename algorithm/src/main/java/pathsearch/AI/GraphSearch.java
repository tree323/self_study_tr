package pathsearch.AI;

import java.util.*;

public class GraphSearch {

	public enum SearchType {
		BFS, DFS, GBFS
	};

	public static String searchTypeUsed;
	public static int verticesVisited = 1;
	public static int maxFrontierSize = 0;
	public static int iterations = 0;

	public static class Compare implements Comparator<Node> {
		public int compare(Node n1, Node n2) {
			if (n1.getHeur() < n2.getHeur()) {
				return -1;
			} else if (n1.getHeur() > n2.getHeur()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	public static void search(Graph graph, Node startNode, Node goalNode, String searchType) {

		searchTypeUsed = searchType;
		if (searchType.equals(SearchType.BFS.toString())) {
			breadthFirstSearch(graph, startNode, goalNode);
		} else if (searchType.equals(SearchType.DFS.toString())) {
			depthFirstSearch(graph, startNode, goalNode);
		} else if (searchType.equals(SearchType.GBFS.toString())) {
			greedyBestFirstSearch(graph, startNode, goalNode);
		} else {
			System.out.println("Invalid Search type. Please choose from BFS, DFS and GBFS.");
			System.exit(1);
		}
	}

	public static void breadthFirstSearch(Graph g, Node startNode, Node goalNode) {

		Queue<Node> queue = new LinkedList<Node>();
		for (Node n : g.getNodes()) {
			n.setIsVisited(false);
		}
		if (Graph.debug == 1) {
			printVertexInfo(g.getVertexCount(), g.getEdgeCount(), startNode.getX(), startNode.getY(), goalNode.getX(),
					goalNode.getY(), startNode.getId(), goalNode.getId());
		}
		startNode.setIsVisited(true);
		queue.add(startNode);
		while (!queue.isEmpty()) {
			iterations++;
			maxFrontierSize = maxFrontierSize < queue.size() ? queue.size() : maxFrontierSize;
			Node node = queue.remove();
			if (Graph.debug == 1) {
				node.calculateHeuristic(goalNode);
				printIterationInfo(queue.size(), node.getId(), node.getX(), node.getY(), node.getHeur());
			}
			if (node.getId() == goalNode.getId()) {
				return;
			}
			List<Node> list = node.successors();
			for (Node n : list) {
				if (!n.isVisited()) {
					if (Graph.debug == 1) {
						System.out.println("pushed " + n.getId() + " (" + n.getX() + "," + n.getY() + ")");
					}
					verticesVisited++;
					n.setIsVisited(true);
					n.setParent(node);
					queue.add(n);
				}
			}
		}
	}

	public static void greedyBestFirstSearch(Graph g, Node startNode, Node goalNode) {

		PriorityQueue<Node> queue = new PriorityQueue<Node>(11, new Compare());
		for (Node n : g.getNodes()) {
			n.setIsVisited(false);
		}
		if (Graph.debug == 1) {
			printVertexInfo(g.getVertexCount(), g.getEdgeCount(), startNode.getX(), startNode.getY(), goalNode.getX(),
					goalNode.getY(), startNode.getId(), goalNode.getId());
		}
		startNode.calculateHeuristic(goalNode);
		startNode.setDist(0);
		startNode.setParent(null);
		startNode.setIsVisited(true);
		queue.add(startNode);
		while (!queue.isEmpty()) {
			iterations++;
			maxFrontierSize = maxFrontierSize < queue.size() ? queue.size() : maxFrontierSize;
			Node node = queue.remove();
			if (Graph.debug == 1) {
				node.calculateHeuristic(goalNode);
				printIterationInfo(queue.size(), node.getId(), node.getX(), node.getY(), node.getHeur());
			}
			// 到达目标节点
			if (node.getId() == goalNode.getId()) {
				return;
			}
			List<Node> list = node.successors();
			for (Node n : list) {
				if (!n.isVisited()) {
					if (Graph.debug == 1) {
						System.out.println("pushed " + n.getId() + " (" + n.getX() + "," + n.getY() + ")");
					}
					verticesVisited++;
					n.calculateHeuristic(goalNode);
					n.setIsVisited(true);
					n.setParent(node);
					n.setDist(n.getParent().getDist() + 1);
					queue.add(n);
				}
			}
		}
	}

	public static void depthFirstSearch(Graph g, Node startNode, Node goalNode) {

		Stack<Node> stack = new Stack<Node>();
		for (Node n : g.getNodes()) {
			n.setIsVisited(false);
		}
		if (Graph.debug == 1) {
			printVertexInfo(g.getVertexCount(), g.getEdgeCount(), startNode.getX(), startNode.getY(), goalNode.getX(),
					goalNode.getY(), startNode.getId(), goalNode.getId());
		}
		startNode.setIsVisited(true);
		stack.push(startNode);
		while (!stack.isEmpty()) {
			iterations++;
			maxFrontierSize = maxFrontierSize < stack.size() ? stack.size() : maxFrontierSize;
			Node node = stack.pop();
			if (Graph.debug == 1) {
				node.calculateHeuristic(goalNode);
				printIterationInfo(stack.size(), node.getId(), node.getX(), node.getY(), node.getHeur());
			}
			if (node.getId() == goalNode.getId()) {
				return;
			}
			List<Node> list = node.successors();
			for (Node n : list) {
				if (!n.isVisited()) {
					if (Graph.debug == 1) {
						System.out.println("pushed " + n.getId() + " (" + n.getX() + "," + n.getY() + ")");
					}
					verticesVisited++;
					n.setIsVisited(true);
					n.setParent(node);
					stack.push(n);
				}
			}
		}
	}

	private static void printVertexInfo(int vertexCount, int edgeCount, int startX, int startY, int goalX, int goalY,
			int startId, int goalId) {
		System.out.println("verices=" + vertexCount + ", edges=" + edgeCount);
		System.out.println("start=(" + startX + "," + startY + "), goal=(" + goalX + "," + goalY + "), vertices: "
				+ startId + " and " + goalId);
	}

	private static void printIterationInfo(int qSize, int poppedId, int nodeX, int nodeY, double heur) {
		System.out.println("iter=" + iterations + ", frontier=" + qSize + ", popped=" + poppedId + " (" + nodeX + ","
				+ nodeY + ")" + ", dist2goal= " + String.format("%.1f", heur));
	}
}
