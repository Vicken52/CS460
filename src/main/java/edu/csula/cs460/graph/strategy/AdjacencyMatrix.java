package edu.csula.cs460.graph.strategy;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Node;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Optional;

public class AdjacencyMatrix implements Representation {
  private Node[] nodes;
  private int[][] adjacencyMatrix;
  private int nodeNum = 0;

  protected AdjacencyMatrix(File file) {
    // TODO: read file and parse the file content into adjacencyMatrix

    try
    {
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);

      nodeNum = Integer.parseInt(br.readLine());

      nodes = new Node[nodeNum];
      adjacencyMatrix = new int[nodeNum][nodeNum];

      for(int i = 0; i < nodeNum; i++)
      {
        nodes[i] = new Node(i);
      }

      String[] inputArray;
      String line;

      while((line = br.readLine()) != null)
      {
        inputArray = line.split(":");
        adjacencyMatrix[Integer.parseInt(inputArray[0])][Integer.parseInt(inputArray[1])] = Integer.parseInt(inputArray[2]);
      }
    }
    catch(IOException ignored) {}
  }

  protected AdjacencyMatrix() {
    nodes = new Node[nodeNum];
    adjacencyMatrix = new int[nodeNum][nodeNum];
  }

  private void updateMatrix(int test) {
    Node[] nodesTmp = new Node[nodeNum];
    boolean testT = false;

    for(int i = 0; i < nodes.length; i++)
    {
      if(test != i)
      {
        if(testT)
        {
          nodesTmp[i - 1] = nodes[i];
        }
        else
        {
          nodesTmp[i] = nodes[i];
        }
      }
      else
      {
        testT = true;
      }
    }
    nodes = nodesTmp;

    int[][] adjacencyTmp = new int[nodeNum][nodeNum];
    testT = false;
    boolean testJ;

    for(int i = 0; i < adjacencyMatrix.length; i++)
    {
      testJ = false;
      if(test != i)
      {
        if(testT)
        {
          for(int j = 0; j < adjacencyMatrix.length; j++)
          {
            if(test != j)
            {
              if(testJ)
              {
                adjacencyTmp[i - 1][j - 1] = adjacencyMatrix[i][j];
              }
              else
              {
                adjacencyTmp[i - 1][j] = adjacencyMatrix[i][j];
              }
            }
            else
            {
              testJ = true;
            }
          }
        }
        else
        {
          for(int j = 0; j < adjacencyMatrix.length; j++)
          {
            if(test != j)
            {
              if(testJ)
              {
                adjacencyTmp[i][j - 1] = adjacencyMatrix[i][j];
              }
              else
              {
                adjacencyTmp[i][j] = adjacencyMatrix[i][j];
              }
            }
            else
            {
              testJ = true;
            }
          }
        }
      }
      else
      {
        testT = true;
      }
    }

    adjacencyMatrix = adjacencyTmp;
  }

  @Override
  public List<Node> getNodes() {
    List<Node> nodeTmp = new ArrayList<>();

    Collections.addAll(nodeTmp, nodes);

    return nodeTmp;
  }

  @Override
  public Optional<Node> getNode(int index) {
    return Optional.of(nodes[index]);
  }

  @Override
  public boolean adjacent(Node x, Node y) {
    return adjacencyMatrix[x.getId()][y.getId()] > 0;
  }

  @Override
  public List<Node> neighbors(Node x) {
    List<Node> neighbors = new ArrayList<>();
    int indexX = 0;
    for(int i = 0; i < nodes.length; i++)
    {
      if(nodes[i].equals(x))
      {
        indexX = i;
        break;
      }
    }

    for(int i = 0; i < nodes.length; i++)
    {
      if(adjacencyMatrix[indexX][i] > 0)
      {
        neighbors.add(nodes[i]);
      }
    }
    return neighbors;
  }

  @Override
  public boolean addNode(Node x) {

    if(nodes.length > 0)
    {
      for (Node node : nodes) {
        if (node.equals(x)) {
          return false;
        }
      }
    }

    nodeNum++;
    updateMatrix(-1);
    nodes[nodeNum - 1] = x;
    return true;
  }

  @Override
  public boolean removeNode(Node x) {
    for(int i = 0; i < nodes.length; i++) {

      if(nodes[i].equals(x))
      {
        nodeNum--;
        updateMatrix(i);
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean addEdge(Edge x) {
    int from = 0;
    int to = 0;

    for(int i = 0; i < nodes.length; i++)
    {
      if(x.getFrom().equals(nodes[i]))
      {
        from = i;
      }
      if(x.getTo().equals(nodes[i]))
      {
        to = i;
      }
    }

    if(adjacencyMatrix[from][to] == 0)
    {
      adjacencyMatrix[from][to] = x.getValue();
      return true;
    }
    return false;
  }

  @Override
  public boolean removeEdge(Edge x) {
    int from = 0;
    int to = 0;

    for(int i = 0; i < nodes.length; i++)
    {
      if(x.getFrom().equals(nodes[i]))
      {
        from = i;
      }
      if(x.getTo().equals(nodes[i]))
      {
        to = i;
      }
    }

    if(adjacencyMatrix[from][to] != 0)
    {
      adjacencyMatrix[from][to] = 0;
      return true;
    }
    return false;
  }

  @Override
  public int distance(Node from, Node to) {
    return adjacencyMatrix[from.getId()][to.getId()];
  }
}