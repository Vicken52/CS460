package edu.csula.cs460.graph.strategy;

import edu.csula.cs460.graph.Node;
import edu.csula.cs460.graph.Edge;

import java.io.File;
import java.util.*;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.stream.Collectors;

public class AdjacencyList implements Representation {
    private Map<Node, List<Node>> adjacencyList = new LinkedHashMap<>();
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    protected AdjacencyList(File file) {
        // TODO: read file and parse it into adjacencyList variable

        try
        {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            int nodeNum = Integer.parseInt(br.readLine());

            for(int i = 0; i < nodeNum; i++)
            {
                nodes.add(new Node(i));
            }

            String[] inputArray;
            String line;

            while((line = br.readLine()) != null)
            {
                inputArray = line.split(":");
                edges.add(new Edge(new Node(Integer.parseInt(inputArray[0])),
                        new Node(Integer.parseInt(inputArray[1])),
                        Integer.parseInt(inputArray[2])));
            }

            updateMap();
        }
        catch(IOException ignored) {}
    }

    protected AdjacencyList() {

    }

    private void updateMap() {
        for (Node node : nodes) {
            List<Node> nodeTmp = edges.stream().filter(edge -> edge.getFrom().equals(node)).map(Edge::getTo).collect(Collectors.toList());

            adjacencyList.put(node, nodeTmp);
        }
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
        return adjacencyList.get(x).contains(y);
    }

    @Override
    public List<Node> neighbors(Node x) {
        return adjacencyList.get(x);
    }

    @Override
    public boolean addNode(Node x) {
        for (Node node : nodes) {
            if (node.equals(x)) {
                return false;
            }
        }
        nodes.add(x);
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        for (Node node : nodes) {

            if (node.equals(x)) {
                for (int j = 0; j < edges.size(); j++) {

                    if (edges.get(j).getTo().equals(x)) {
                        edges.remove(j);
                    }
                }
                updateMap();
                return true;

            }
        }
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        for (Edge edge : edges) {
            if (edge.equals(x)) {
                return false;
            }
        }
        edges.add(x);
        updateMap();
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        for(int j = 0; j < edges.size(); j++) {
            if(edges.get(j).equals(x))
            {
                edges.remove(j);
                updateMap();
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