package edu.csula.cs460.graph.strategy;

import edu.csula.cs460.graph.Node;
import edu.csula.cs460.graph.Edge;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class AdjacencyList implements Representation {
    private Map<Node, List<Node>> adjacencyList;

    protected AdjacencyList(File file) {
        // TODO: read file and parse it into adjacencyList variable

        try
        {
          FileReader fr = new FileReader(file);
          BufferedReader br = new BufferedReader(fr);

          List<Node> nodes = new ArrayList<Node>();
          List<Edge> edges = new ArrayList<Edge>();

          int nodeNum = Integer.parseInt(br.readLine());

          for(int i = 0; i < nodeNum; i++)
          {
            nodes.add(new Node(i));
          }

          String[] inputArray;
          String line = "";

          while((line = br.readLine()) != null)
          {
            inputArray = line.split(":");
            edges.add(new Edge(nodes.get(Integer.parseInt(inputArray[0])),
                               nodes.get(Integer.parseInt(inputArray[1])),
                               Integer.parseInt(inputArray[2])));
          }

          for(int i = 0; i < nodes.size(); i++)
          {
              List<Node> nodeTmp = new ArrayList<Node>();

              for(int j = 0; j < edges.size(); j++)
              {
                if(edges.get(j).getFrom() == nodes.get(i))
                {
                  nodeTmp.add(edges.get(j).getTo());
                }
              }

              adjacencyList.put(nodes.get(i), nodeTmp);
          }
        }
        catch(IOException ex) {}
    }

    @Override
    public boolean adjacent(Node x, Node y) {
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        return null;
    }

    @Override
    public boolean addNode(Node x) {
        return false;
    }

    @Override
    public boolean removeNode(Node x) {
        return false;
    }

    @Override
    public boolean addEdge(Edge x) {
        return false;
    }

    @Override
    public boolean removeEdge(Edge x) {
        return false;
    }
}
