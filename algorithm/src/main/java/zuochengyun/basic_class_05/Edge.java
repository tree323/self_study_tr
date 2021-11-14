package zuochengyun.basic_class_05;

public class Edge {
    // 权重
    public int weight;
    // 有向图的话 src
    public Node from;
    // 有向图的话 des
    public Node to;

    public Edge(int weight, Node from, Node to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
