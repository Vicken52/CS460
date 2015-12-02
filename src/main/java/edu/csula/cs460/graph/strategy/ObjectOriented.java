package edu.csula.cs460.graph.strategy;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Node;

import java.io.File;
import java.util.*;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class ObjectOriented implements Representation {
    private List<Node> nodes = new ArrayList<>();
    private Set<Node> nodeCheck = new HashSet<>();
    private List<Edge> edges = new ArrayList<>();
    private Set<Edge> edgeCheck = new HashSet<>();
    private Map<Node, Map<Node, Integer>> neighbors = new HashMap<>();

    protected ObjectOriented(File file) {
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
    public Optional<Node> getNode(int index) {
        return Optional.of(nodes.get(index));
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
        List<Node> nodes = new ArrayList<>(neighbors.get(x).keySet());
        Collections.reverse(nodes);
        return nodes;
    }

    @Override
    public boolean addNode(Node x) {

        if(nodeCheck.add(x))
        {
            nodes.add(x);
            neighbors.put(x, new HashMap<>());
            return true;
        }

        return false;
    }

    @Override
    public boolean removeNode(Node x) {
        if(neighbors.containsKey(x))
        {
            neighbors.remove(x);
            nodeCheck.remove(x);
            nodes.remove(x);
            nodes.parallelStream().filter(node -> neighbors.containsKey(node)).forEach(node -> {
                if(neighbors.get(node).containsKey(x))
                {
                    Map<Node, Integer> tmp = neighbors.get(node);
                    tmp.remove(x);
                    neighbors.replace(node, tmp);
                }
            });
            return true;
        }

        return false;
    }

    @Override
    public boolean addEdge(Edge x) {

        if(edgeCheck.add(x))
        {
            edges.add(x);
            neighbors.get(x.getFrom()).put(x.getTo(), x.getValue());
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        if(edgeCheck.remove(x))
        {
            edges.remove(x);
            neighbors.get(x.getFrom()).remove(x.getTo());
            return true;
        }
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        return neighbors.get(from).get(to);
    }
}