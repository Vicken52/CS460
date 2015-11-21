package edu.csula.cs460.graph.strategy;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Node;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Map;

public class ObjectOriented implements Representation {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();
    private Map<Node, List<Node>> neighbors = new HashMap<>();

    protected ObjectOriented(File file) {
        //TODO: parse file content and add it to nodes
        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            int nodeNum = Integer.parseInt(br.readLine());

            for(int i = 0; i < nodeNum; i++)
            {
                Node tmp = new Node(i);
                addNode(tmp);
            }

            String[] inputArray;
            String line;

            while((line = br.readLine()) != null)
            {
                inputArray = line.split(":");
                Edge tmp = new Edge(new Node(Integer.parseInt(inputArray[0])),
                        new Node(Integer.parseInt(inputArray[1])),
                        Integer.parseInt(inputArray[2]));
                addEdge(tmp);
            }

        }
        catch(IOException ignored) {}
    }

    protected ObjectOriented() {

    }

    @Override
    public List<Node> getNodes() {

        return nodes;
    }

    @Override
    public boolean adjacent(Node x, Node y) {
        for (Edge edge : edges) {
            if (edge.getFrom().equals(x) && edge.getTo().equals(y)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        return neighbors.get(x);
        //return edges.parallelStream().filter(edge -> edge.getFrom().equals(x)).map(Edge::getTo).collect(Collectors.toList());
    }

    @Override
    public boolean addNode(Node x) {
//        for (Node node : nodes) {
//            if (node.equals(x)) {
//                return false;
//            }
//        }

        if(nodes.add(x))
        {
            neighbors.put(x, new ArrayList<>());
            return true;
        }

        return false;
    }

    @Override
    public boolean removeNode(Node x) {
//        for (Node node : nodes)
//            if (node.equals(x)) {
//                for (int j = 0; j < edges.size(); j++) {
//
//                    if (edges.get(j).getTo().equals(x)) {
//                        edges.remove(j);
//                        neighbors.remove(x);
//                    }
//
//                }
//                return true;
//            }
//        return false;

        if(neighbors.remove(x) != null)
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
//        for (Edge edge : edges) {
//            if (edge.equals(x)) {
//                return false;
//            }
//        }

        if(edges.add(x))
        {
            List<Node> tmp = neighbors.get(x.getFrom());
            tmp.add(x.getTo());
            neighbors.replace(x.getFrom(), tmp);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        for(int j = 0; j < edges.size(); j++) {
            if(edges.get(j).equals(x))
            {
                edges.remove(j);
                return true;
            }
        }
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        for (Edge edge : edges) {
            if (edge.getFrom().equals(from) && edge.getTo().equals(to)) return edge.getValue();
        }
        return 0;
    }
}