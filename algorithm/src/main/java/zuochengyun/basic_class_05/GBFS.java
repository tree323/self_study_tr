package zuochengyun.basic_class_05;

import java.util.PriorityQueue;

/**
 * @ Author     ：tangran
 * @ Date       ：Created in 2:17 下午 2021/11/13
 */
public class GBFS {
    public static void gbfs(Node node) {
        if (null == node) {
            return;
        }
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.offer(node);
        while (!q.isEmpty()) {
            Node cur = q.poll();

        }
    }

    public static int h(Node node) {
        return 0;
    }
}
