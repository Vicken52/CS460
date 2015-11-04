package edu.csula.cs460.graph.search;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

import java.util.ArrayList;
import java.util.List;

public class DFS implements SearchStrategy {
    public List<Edge> search(Graph graph, Node source, Node dist) {

        List<Edge> result;
        for(Node node : graph.neighbors(source))
        {
            if(node.getId() == dist.getId())
            {
                result = new ArrayList<>();
                result.add(new Edge(source, dist, graph.distance(source, dist)));
                return result;
            }
            else {
                result = search(graph, node, dist);

                if(result != null)
                {
                    result.add(0, new Edge(source, result.get(0).getFrom(), graph.distance(source, result.get(0).getFrom())));
                    return result;
                }
            }
        }

        return null;
    }
}
