package pathsearch.AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Node {

    private int id;
    private int x;
    private int y;
    private Node parent;
    private int depth;
    private boolean visited = false;
    private double heur;
    private int dist;

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setIsVisited(boolean visited) {
        this.visited = visited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public double getHeur() {
        return heur;
    }

    public void setHeur(double heur) {
        this.heur = heur;
    }

    public void setHeur(int heur) {
        this.heur = heur;
    }

    private List<Node> adjList;

    public Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.adjList = new ArrayList<Node>();
    }

    public List<Node> getAdjList() {
        return adjList;
    }

    public void calculateHeuristic(Node goalNode) {
        setHeur(Math.sqrt((Math.pow(this.getX() - goalNode.getX(), 2) + (Math.pow(this.getY() - goalNode.getY(), 2)))));
    }

    public void setAdjList(List<Node> adjList) {
        this.adjList = adjList;
    }

    public Node(int id, Node parent) {
        this.id = id;
        this.parent = parent;
        this.adjList = new ArrayList<Node>();
    }

    // find the successor of this node
    public List<Node> successors() {
        return this.getAdjList();
    }

    // track back the path
    public Stack<Node> traceback() {
        Node node = this;
        Stack<Node> path = new Stack<Node>();
        while (node != null) {
            path.push(node);
            node = node.getParent();
        }
        return path;
    }
}
