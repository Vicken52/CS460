package edu.csula.cs460.graph.search;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

import java.util.*;

public class Dijkstra implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {

        List<Node> nodes = graph.getNodes();
        List<Node> queue = new ArrayList<>();
        Map<Node, Integer> distance = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();

        for(Node node : nodes)
        {
            distance.put(source, 0);
            if(!node.equals(source))
            {
                distance.put(node, 0);
                previous.put(node, null);
            }

            queue.add(0, node);
        }

        while(!queue.isEmpty())
        {
            Node n = queue.remove(0);

            for(Node node : graph.neighbors(n))
            {
                int tmp = distance.get(n) + graph.distance(n, node);

                if(distance.get(node) == 0 || tmp < distance.get(node)
                        && n.getId() < previous.get(node).getId())
                {
                    distance.replace(node, tmp);
                    previous.replace(node, n);
                }
            }
        }

        List<Edge> result = new ArrayList<>();

        Node tmp = dist;

        while(!tmp.equals(source))
        {
            Node tmp2 = previous.get(tmp);
            result.add(0, new Edge(tmp2, tmp, graph.distance(tmp2, tmp)));
            tmp = tmp2;
        }

        return result;

//        List<Edge> edges = new ArrayList<>();
//        List<Node> visited = new ArrayList<>();
//        Queue<Node> queue = new LinkedList<>();
//        Map<Integer, List<Edge>> paths = new HashMap<>();
//        List<Edge> result = new ArrayList<>();
//
//        queue.add(source);
//
//        while (!queue.isEmpty())
//        {
//            Node n = queue.poll();
//
//            for (Node node : graph.neighbors(n))
//            {
//                edges.add(new Edge(n, node, graph.distance(n, node)));
//                if (node.getId() != dist.getId())
//                {
//                    queue.add(node);
//                }
//                else
//                {
//                    Node tempN = n;
//                    result.add(new Edge(n, node, graph.distance(n, node)));
//
//                    for (ListIterator iterator = edges.listIterator(edges.size()); iterator.hasPrevious();) {
//                        Edge edge = (Edge) iterator.previous();
//
//                        if (edge.getTo().equals(result.get(0).getTo())
//                                && visited.contains(edge.getFrom())
//                                && edge.getFrom().getId() > result.get(0).getFrom().getId()) {
//                            //This sets the front edge.
//                            result.set(0, edge);
//                            tempN = edge.getFrom();
//                        }
//                        else if (edge.getTo().equals(tempN)
//                                && visited.contains(edge.getFrom())) {
//                            //This adds to the front.
//                            result.add(0, edge);
//                            tempN = edge.getFrom();
//                        }
//                    }
//
//                    int newSum = 0;
//
//                    for (Edge edge : result) {
//                        newSum += graph.distance(edge.getFrom(), edge.getTo());
//                    }
//
//                    paths.put(newSum, result);
//
//                }
//            }
//
//            visited.add(n);
//        }
//
//        int shortest = 1000;
//
//        for (Map.Entry<Integer, List<Edge>> entry : paths.entrySet())
//        {
//            if(shortest >= entry.getKey())
//            {
//                shortest = entry.getKey();
//            }
//        }
//
//        return paths.get(shortest);
    }
}
