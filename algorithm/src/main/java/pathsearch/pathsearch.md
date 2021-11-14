# 路径规划算法

[路径规划学习路径](https://vslam.net/categories/theory/路径规划/)

+ BFS与DFS
+ GBFS
+ Dijkstra

[寻路算法整理](https://github.com/zhangga/PathFinding) #based on

+ A-STAR
+ JPS



+ PRM
+ RRT
+ RRT*

## BFS与DFS  - 拓扑排序

### 1. 概述

DFS和BFS是两种搜索树和图的基本策略，一种往深处搜，一种往边上搜。 DFS常用于暴力搜索所有状态，BFS常用于搜索到达某一状态的最短路径。广度优先搜索在进一步遍历图中顶点之前，先访问当前顶点的所有邻接结点。深度优先搜索在搜索过程中访问某个顶点后，需要递归地访问此顶点的所有未访问过的相邻顶点。

### 2. 算法详解

#### 2.1 深度优先搜索

深度优先搜索策略是递归的访问此顶点的所有未访问过的相邻节点。如下图所示：

![截屏2021-11-13 下午11.40.42](https://raw.githubusercontent.com/tree323/picBed/main/img/202111132341506.png)

- 从节点1开始遍历，相邻的节点有2，3，4，先遍历节点2，再遍历节点5，然后再节点9
- 上图中最右侧的一条路已经走到底了，此时从节点9开始回溯到上一个节点，如果上一个节点有子节点，则在该节点处继续遍历它的子节点；如果上一个节点，没有子节点，则继续回溯。如上图所示，遍历到节点9之后，回溯到节点5，节点5没有子节点，继续回退到节点2，再回溯到节点1；此时，节点1有其他的子节点3和4，那么继续遍历3的子节点
- 重复上一步，直到遍历到节点10，则进行回溯，直到节点3，发现节点3有子节点7还未被访问，那么遍历节点7及其子节点
- 从节点7往上回溯到3，1，发现1还有节点4未遍历，所以此时沿着4，8进行遍历，最后遍历结束

#### 2.2 广度优先搜索

广度优先遍历从图的一个未遍历的节点出发，先遍历这个节点的相邻节点，再依次遍历每个相邻节点的相邻节点。所以广度优先遍历也叫层序遍历。正如上图所示，广度优先遍历先遍历第一层（节点1），再遍历第二层（节点2，3，4），第三层（5，6，7，8），第四层（9，10）。

[拓扑排序](https://www.cnblogs.com/bigsai/p/11489260.html)

代码实现:

```java
import java.util.ArrayList;
// 节点定义
public class Node {
	public int value;
	public int in;
	public int out;
	public ArrayList<Node> nexts;
	public ArrayList<Edge> edges;

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}
}
```

边定义

```java
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
```

图定义

```java
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

```

dfs:

```java
public static void dfs(Node node) {
   if (node == null) {
      return;
   }
  // 栈
   Stack<Node> stack = new Stack<>();
  // 标记是否访问过,剪枝
   HashSet<Node> set = new HashSet<>();
   stack.add(node);
   set.add(node);
   System.out.println(node.value);
   while (!stack.isEmpty()) {
      Node cur = stack.pop();
      for (Node next : cur.nexts) {
         if (!set.contains(next)) {
            stack.push(cur);
            stack.push(next);
            set.add(next);
            System.out.println(next.value);
            break;
         }
      }
   }
}
```

bfs

```java
public static void bfs(Node node) {
   if (node == null) {
      return;
   }
  // 队列 , 这里用LinkedList
   Queue<Node> queue = new LinkedList<>();
   HashSet<Node> map = new HashSet<>();
   queue.add(node);
   map.add(node);
   while (!queue.isEmpty()) {
      Node cur = queue.poll();
      System.out.println(cur.value);
      for (Node next : cur.nexts) {
         if (!map.contains(next)) {
            map.add(next);
            queue.add(next);
         }
      }
   }
}
```

拓扑排序

```java
public static List<Node> sortedTopology(Graph graph) {
   // 入度
   HashMap<Node, Integer> inMap = new HashMap<>();
   // 入度为0
   Queue<Node> zeroInQueue = new LinkedList<>();
   for (Node node : graph.nodes.values()) {
      inMap.put(node, node.in);
      if (node.in == 0) {
         zeroInQueue.add(node);
      }
   }
   List<Node> result = new ArrayList<>();
   while (!zeroInQueue.isEmpty()) {
      // 从入度为0的节点中选择一个开始bfs
      Node cur = zeroInQueue.poll();
      result.add(cur);
      for (Node next : cur.nexts) {
         // 删除以这个点为起点的边
         inMap.put(next, inMap.get(next) - 1);
         // 更新入度为0的节点
         if (inMap.get(next) == 0) {
            zeroInQueue.add(next);
         }
      }
   }
   return result;
}
```



## 贪婪最佳优先算法

### 概述

BFS与DFS的区别主要在于节点的弹出策略，根据弹出策略的不同，分别使用了队列和栈这两种数据结构，而栈和队列这两种数据结构仅仅将节点进入容器的顺序作为弹出节点的依据，并未考虑目标位置等因素。这就使得搜索过程变得漫无目的，效率比较底下。

启发式Heuristic搜索算法就是用来解决效率问题的。一个启发的定义就是当前节点的位置到目标节点有多远的猜测。在实际使用中，从当前位置到终点的最短路径是无法得知的，除非已经完成搜索。因此，在搜索过程中，只能够猜测当前位置到终点的距离。当前，这种猜测主要基于启发式函数来实现，例如**欧氏距离、曼哈顿距离**等。

![截屏2021-11-13 上午11.07.26.png](https://raw.githubusercontent.com/tree323/picBed/main/img/202111132320131.png)

### 算法详解

最佳优先搜索算法在广度优先搜索的基础上，用启发估价函数对将要被遍历到的点进行估价，然后选择代价小的进行遍历，直到找到目标节点或者遍历完所有点，算法结束。

在图搜索算法中，F(N)表示**代价函数**，用来作为优先级判断的标准，F(n)越小，优先级越高，反之优先级越低。GBFS作为一种启发式搜索算法，使用启发评估函数f(n)来作为代价函数，即**当前节点到终点的代价**，它可以指引搜索算法往终点靠近，主要使用欧氏距离或者曼哈顿距离来表示。有了该代价函数，GBFS在搜索过程中极具方向性，应用在二维地图路径规划中，它的指向性或者说目的非常明显，从起点直扑终点。

*但是，在实际的地图中，常常会有很多障碍物，它就很容易陷入局部最优的陷阱*

> 算法主要思想：要实现最佳优先搜索我们必须使用一个优先队列来实现，通常采用一个open优先队列和一个closed集，open优先队列用来存储还没有遍历将要遍历的节点，而closed集用来储存已经被遍历过的节点。

---

算法步骤：

- 一、将开始结点压入优先队列中
- 二、从优先队列中取出优先级最高的节点为当前拓展点，设置为已访问
- 三、判断当前节点是否为目标节点，若是则输出路径搜索结束，否则进行下一步
- 四、访问当前节点的所有相邻子节点
    - X的子节点Y不在open队列或者closed中，由估价函数计算出估价值，放入open队列中
    - X的子节点Y在open队列中，且估价值优于open队列中的子节点Y，将open队列中的子节点Y的估价值替换成新的估价值并按优先值排序。
    - X的子节点Y在closed集中，且估价值优于closed集中的子节点Y，将closed集中的子节点Y溢出，并将子节点Y加入open优先队列
- 五、将节点X放入closed集中
- 重复过程第二、三、四、五步直到目标节点找到，或者open为空，程序结束

---

### 代码实现



---

### GBFS的优点：

搜索速度快

缺点：不一定最优

容易陷入死循环

有可能一直沿着一条道，但是这条道到不了终点。

## Dijkstra

用来寻找图形中节点之间的最短路径。

> 从图中的某个定点出发到达另外一个顶点的所经过的边的权重和最小的一条路径，称为最短路径。

该算法的主要特点是以起始点为中心向外层层扩展（广度优先搜索思想），直到扩展到终点为止。

![截屏2021-11-13 下午3.10.15.png](https://raw.githubusercontent.com/tree323/picBed/main/img/202111132320480.png)

### 算法思想

设G=(v，e)是一个带权有向图，把图中的顶点集合V分为两组，第一组为已求出最短路径的顶点集合（用S表示，初始时S中只有一个源点，以后每求得一条最短路径，就将顶点加入到集合S中。直到全部顶点都加入到S中，算法就结束了），第二组为其余未确定最短路径的顶点集合（用U表示），按最短路径长度的递增次序一次把第二组的顶点加入到S中。在加入的过程中，总保持从源点V到S中各顶点的最短路径长度不大于从源点v到U中任何顶点的最短路径长度。此外，每个顶点对应一个距离，S中的顶点的距离就是从v到此顶点的最短路径长度，U中的顶点的距离，是从v到此顶点只包括S中的顶点为中间顶点的当前最短路径长度。

![截屏2021-11-13 下午3.22.06.png](https://raw.githubusercontent.com/tree323/picBed/main/img/202111132320960.png)

```java
public static HashMap<Node, Integer> dijkstra1(Node head) {
    // 当前顶点可达的各顶点最短路径集合
    HashMap<Node, Integer> distanceMap = new HashMap<>();
    // 起始点加入集合，最短路径为0
    distanceMap.put(head, 0);
    // 已经求出最短路径的顶点集合
    HashSet<Node> selectedNodes = new HashSet<>();

    NodeminNode= getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
    // 还有顶点没有找到最短路径的话
    while (minNode!= null) {
        int distance = distanceMap.get(minNode);
        for (Edge edge :minNode.edges) {
            Node toNode = edge.to;
            if (!distanceMap.containsKey(toNode)) {
                distanceMap.put(toNode, distance + edge.weight);
            }
            // 更新最短路径
            distanceMap.put(edge.to, Math.min(distanceMap.get(toNode), distance + edge.weight));
        }
        selectedNodes.add(minNode);
minNode= getMinDistanceAndUnselectedNode(distanceMap, selectedNodes);
    }
    return distanceMap;
}

/**
 * 找出当前最短路径
 *
 * @param distanceMap  已经求出最短路的顶点集合
 * @param touchedNodes 已经访问过的顶点
 * @return
 */
public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes) {
    NodeminNode= null;
    intminDistance= Integer.MAX_VALUE;
    for (Entry<Node, Integer> entry : distanceMap.entrySet()) {
        Node node = entry.getKey();
        int distance = entry.getValue();
        if (!touchedNodes.contains(node) && distance <minDistance) {
minNode= node;
minDistance= distance;
        }
    }
    returnminNode;
}
```

## 双向Dijkstra

Dijkstra算法是一种单向的最短路径算法，有研究者提出一种优化算法，即shuangxiangDijkstra算法。其主要思想就是从起点和终点同时开始搜索，这样应该能够提升算法效率。

```java
package net.coderodde.gsp.model.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.coderodde.gsp.model.AbstractGraphNode;
import net.coderodde.gsp.model.AbstractGraphWeightFunction;
import net.coderodde.gsp.model.AbstractPathFinder;
import net.coderodde.gsp.model.queue.MinimumPriorityQueue;
import net.coderodde.gsp.model.queue.support.DaryHeap;

public class BidirectionalDijkstraPathFinder<N extends AbstractGraphNode<N>> 
extends AbstractPathFinder<N> {
    
    private MinimumPriorityQueue<N> OPENA;
    private MinimumPriorityQueue<N> OPENB;
    
    private Set<N> CLOSEDA;
    private Set<N> CLOSEDB;
    
    private Map<N, N> PARENTSA;
    private Map<N, N> PARENTSB;
    
    private Map<N, Double> DISTANCEA;
    private Map<N, Double> DISTANCEB;
    
    private N source;
    private N target;
    
    private final AbstractGraphWeightFunction<N> weightFunction;
    
    private double bestPathLength;
    private N touchNode;
    
    public BidirectionalDijkstraPathFinder(
            AbstractGraphWeightFunction<N> weightFunction) {
        Objects.requireNonNull(weightFunction, "The weight function is null.");
        this.weightFunction = weightFunction;
    }
        
    private BidirectionalDijkstraPathFinder(
            N source,
            N target,
            AbstractGraphWeightFunction<N> weightFunction) {
        OPENA = getQueue() == null ? new DaryHeap<>() : getQueue().spawn();
        OPENB = OPENA.spawn();
        
        CLOSEDA = new HashSet<>();
        CLOSEDB = new HashSet<>();
        
        PARENTSA = new HashMap<>();
        PARENTSB = new HashMap<>();
        
        DISTANCEA = new HashMap<>();
        DISTANCEB = new HashMap<>();
        
        this.source = source;
        this.target = target;
        this.weightFunction = weightFunction;
        
        this.bestPathLength = Double.POSITIVE_INFINITY;
        this.touchNode = null;
    }
    
    @Override
    public List<N> search(N source, N target) {
        Objects.requireNonNull(source, "The source node is null.");
        Objects.requireNonNull(target, "The target node is null.");
        
        return new BidirectionalDijkstraPathFinder(source, 
                                                   target,
                                                   weightFunction).search();
    }
    
    private void updateForwardFrontier(N node, double nodeScore) {
        if (CLOSEDB.contains(node)) {
            double pathLength = DISTANCEB.get(node) + nodeScore;
            
            if (bestPathLength > pathLength) {
                bestPathLength = pathLength;
                touchNode = node;
            }
        }
    }
    
    private void updateBackwardFrontier(N node, double nodeScore) {
        if (CLOSEDA.contains(node)) {
            double pathLength = DISTANCEA.get(node) + nodeScore;
            
            if (bestPathLength > pathLength) {
                bestPathLength = pathLength;
                touchNode = node;
            }
        }
    }
    
    private void expandForwardFrontier() {
        N current = OPENA.extractMinimum();
        CLOSEDA.add(current);
        
        for (N child : current.children()) {
            if (!CLOSEDA.contains(child)) {
                double tentativeScore = DISTANCEA.get(current) + 
                                        weightFunction.get(current, child);
                
                if (!DISTANCEA.containsKey(child)) {
                    DISTANCEA.put(child, tentativeScore);
                    PARENTSA.put(child, current);
                    OPENA.add(child, tentativeScore);
                    updateForwardFrontier(child, tentativeScore);
                } else if (DISTANCEA.get(child) > tentativeScore) {
                    DISTANCEA.put(child, tentativeScore);
                    PARENTSA.put(child, current);
                    OPENA.decreasePriority(child, tentativeScore);
                    updateForwardFrontier(child, tentativeScore);
                }
            }
        }
    }
    
    private void expandBackwardFrontier() {
        N current = OPENB.extractMinimum();
        CLOSEDB.add(current);
        
        for (N parent : current.parents()) {
            if (!CLOSEDB.contains(parent)) {
                double tentativeScore = DISTANCEB.get(current) + 
                                        weightFunction.get(parent, current);
                
                if (!DISTANCEB.containsKey(parent)) {
                    DISTANCEB.put(parent, tentativeScore);
                    PARENTSB.put(parent, current);
                    OPENB.add(parent, tentativeScore);
                    updateBackwardFrontier(parent, tentativeScore);
                } else if (DISTANCEB.get(parent) > tentativeScore) {
                    DISTANCEB.put(parent, tentativeScore);
                    PARENTSB.put(parent, current);
                    OPENB.decreasePriority(parent, tentativeScore);
                    updateBackwardFrontier(parent, tentativeScore);
                }
            }
        }
    }
    
    private List<N> search() {
        if (source.equals(target)) {
            // Bidirectional search algorithms cannont handle the case where
            // source and target nodes are same.
            List<N> path = new ArrayList<>(1);
            path.add(source);
            return path;
        }
        
        OPENA.add(source, 0.0);
        OPENB.add(target, 0.0);
        
        PARENTSA.put(source, null);
        PARENTSB.put(target, null);
        
        DISTANCEA.put(source, 0.0);
        DISTANCEB.put(target, 0.0);
        
        while (!OPENA.isEmpty() && !OPENB.isEmpty()) {
            double mtmp = DISTANCEA.get(OPENA.min()) +
                          DISTANCEB.get(OPENB.min());
            
            if (mtmp >= bestPathLength) {
                return tracebackPath(touchNode, PARENTSA, PARENTSB);
            }
            
            if (OPENA.size() + CLOSEDA.size() < 
                OPENB.size() + CLOSEDB.size()) {
                expandForwardFrontier();
            } else {
                expandBackwardFrontier();
            }
        }
        
        return Collections.<N>emptyList();
    }

    @Override
    public String humanReadableName() {
        return "Bidirectional Dijkstra's algorithm";
    }
}
```

## A-Star算法

回顾Dijkstra算法，基于广度优先搜索策略来遍历空间内所有节点，最终计算出来的全局最优的路径。那么它的计算量会非常大。一开始介绍的基于启发式的贪婪最佳优先算法，速度快，但是结果可能不是最优的。那么，如何将二者的优势结合呢？这就是A*算法

### 算法详解

A*算法的代价函数表示为：f(n)=g(n)+h(n),**g(n)表示从起始节点到当前节点所需要的代价，h(n)表示从当前节点到目标节点所需要的代价**。为了对这俩值进行想家，这两个值必须使用相同的衡量单位。

### 启发式函数

启发式函数可以控制A*的行为：

[Graphs in Java - A* Algorithm](https://stackabuse.com/graphs-in-java-a-star-algorithm/)

欧氏距离

## JPS算法

Jump point search,跳点搜索算法,该算法是对A-star算法的一个改进.JSP优化了A-star搜索后继节点的操作.A-star的处理是把周边能搜索到的格子,加进OpenList,然后在OpenList中弹出最小值.JPS先用一种更高效的方法来搜索需要加进OpenList的点,然后在OpenList中弹出最小值.JPS完整地保留了A*的框架.

启发式函数和A*算法定义的一样.

## 强迫邻居

JPS算法主要考虑当前节点需要考虑的下一个节点是否应该被考虑.

节点x的8个邻居中有障碍,且x的父节点p经过x到达n的距离代价比不经过x到达的n的任意路径的距离代价小,则称n是x的强迫邻居.

(直观来说,实际就是因为前进方向(父节点到x节点的方向为前进方向)的某一边的靠后位置有障碍物,因此想要到该边靠前的空位有最短的路径,就必须得经过x节点)

![截屏2021-11-13 下午9.38.48.png](https://raw.githubusercontent.com/tree323/picBed/main/img/202111132321566.png)

### 算法原理

- 第一步,openlist取一个权值最低的点,然后开始搜索.
- 先进行直线搜索(4/8个方向,跳跃搜索),然后再斜向搜索(四个方向,只搜索一步).如果期间某个方向搜索到跳点或者碰到障碍(或边界),则当前方向完成搜索,若有搜到跳点就添加进openlist
- 若斜方向没完成搜索,则斜方向前进一步
- 若所有方向完成搜索,则认为当前节点搜索完毕,将当前节点移除openlist,加入closedlist
- 重复取openlist权值最低节点搜索,直到openlist为空或者找到终点.