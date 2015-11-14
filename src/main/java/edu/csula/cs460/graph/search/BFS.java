package edu.csula.cs460.graph.search;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

import java.util.*;

public class BFS implements SearchStrategy
{
    public List<Edge> search(Graph graph, Node source, Node dist)
    {

        List<Edge> edges = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(source);

        while (!queue.isEmpty())
        {
            Node n = queue.poll();

            for (Node node : graph.neighbors(n))
            {
                edges.add(new Edge(n, node, graph.distance(n, node)));
                if (node.getId() != dist.getId())
                {
                    queue.add(node);
                }
                else
                {
                    if (node.getId() == dist.getId())
                    {
                        Node tempN = n;
                        List<Edge> result = new ArrayList<>();
                        result.add(new Edge(n, node, graph.distance(n, node)));

                        for (ListIterator iterator = edges.listIterator(edges.size()); iterator.hasPrevious();)
                        {
                            Edge edge = (Edge) iterator.previous();
                            if (result.get(0).getTo().getId() == edge.getTo().getId())
                            {
                                //This sets the front edge.
                                result.set(0, edge);
                                tempN = edge.getFrom();
                            }
                            else if (edge.getTo().getId() == tempN.getId())
                            {
                                //This adds to the front.
                                result.add(0, edge);
                                tempN = edge.getFrom();
                            }
                        }
                        return result;
                    }
                }
            }
        }
        return null;
    }
}