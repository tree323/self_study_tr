package zuochengyun.basic_class_05;

import zuochengyun.basic_class_05.Edge;
import zuochengyun.basic_class_05.Node;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
	public HashMap<Integer, Node> nodes;
	public HashSet<Edge> edges;

	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}
}
