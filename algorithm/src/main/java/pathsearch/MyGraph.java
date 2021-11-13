package pathsearch;

/**
 * @ Author     ：tangran
 * @ Date       ：Created in 1:53 下午 2021/11/13
 */
public class MyGraph {
    final static int MAXSIZE = 1000;//最多1000个顶点
    String[] vertexes = new String[MAXSIZE];  //顶点数组
    int[][] edges = new int[MAXSIZE][MAXSIZE];//邻接矩阵
    int verNum, edgeNum;//顶点个数、边条数

    public MyGraph() {
        verNum = 0;
        edgeNum = 0;
    }

    public MyGraph(String[] Vertexes, int[][] Edges, int VerNum, int EdgeNum) {
        vertexes = Vertexes;
        edges = Edges;
        verNum = VerNum;
        edgeNum = EdgeNum;
    }
}
