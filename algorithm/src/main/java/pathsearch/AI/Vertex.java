package pathsearch.AI;

import java.util.ArrayList;
import java.util.List;


public class Vertex {

	private int id;
	private int x;
	private int y;
	private List<Vertex> adjList;

	public List<Vertex> getAdjList() {
		return adjList;
	}

	public void setAdjList(List<Vertex> adjList) {
		this.adjList = adjList;
	}

	public Vertex(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.adjList = new ArrayList<Vertex>();
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

}
