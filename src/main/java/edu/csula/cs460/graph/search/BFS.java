package edu.csula.cs460.graph.search;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

import java.util.*;

public class BFS implements SearchStrategy {
    public List<Edge> search(Graph graph, Node source, Node dist) {

        List<Edge> result = null;
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);

        while (!queue.isEmpty()) {
            Node n = queue.poll();

            for(Node node : graph.neighbors(n))
            {
                if(node.getId() != dist.getId())
                {
                    queue.add(node);
                }
                else
                {
                    if(result != null)
                    {
                        result.add(0, new Edge(n, node, graph.distance(n, node)));
                    }
                    else
                    {
                        result = new ArrayList<>();
                        result.add(new Edge(n, node, graph.distance(n, node)));
                    }

                    if(n == source)
                    {
                        return result;
                    }
                    else
                    {
                        dist = node;
                        queue.clear();
                        queue.add(source);
                    }
                }
            }
        }

        return null;
    }
}
