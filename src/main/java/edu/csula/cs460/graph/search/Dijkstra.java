package edu.csula.cs460.graph.search;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

import java.util.*;

public class Dijkstra implements SearchStrategy {
    private Map<Node, Integer> distance;

    public class NodeComparator implements Comparator<Node>
    {
        @Override
        public int compare(Node x, Node y)
        {
            if(distance.get(x) > distance.get(y))
            {
               return 1;
            }
            else if(distance.get(x) < distance.get(y))
            {
                return -1;
            }
            
            return 0;
        }
    }

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        List<Node> visited = new ArrayList<>();
        Queue<Node> queue = new PriorityQueue<>(new NodeComparator());
        distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();

        distance.put(source, 0);
        previous.put(source, null);
        queue.add(source);

        while(!queue.isEmpty())
        {
            Node n = queue.poll();
            if(!visited.contains(n))
            {
                visited.add(n);

                int tmp;
                for(Node node : graph.neighbors(n))
                {
                    tmp = graph.distance(n, node) + distance.get(n);
                    if(distance.get(node) == null || tmp < distance.get(node))
                    {
                        distance.put(node, tmp);
                        previous.put(node, n);
                    }

                    queue.add(node);
                }
            }
        }

        List<Edge> result = new ArrayList<>();

        Node tmp = dist;

        while(previous.get(tmp) != null)
        {
            Node tmp2 = previous.get(tmp);
            result.add(0, new Edge(tmp2, tmp, graph.distance(tmp2, tmp)));
            tmp = tmp2;
        }

        return result;
    }
}
