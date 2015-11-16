package edu.csula.cs460.graph.search;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;
import edu.csula.cs460.graph.strategy.Representation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AStar implements SearchStrategy {
    private int rowLength = 0;
    private Node source;

    /*
    Distance:
        0 = North
        1 = East
        2 = South
        3 = West
     */

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        PriorityQueue<Node> queue = new PriorityQueue<>(new NodeComparator());
        List<Node> closed = new ArrayList<>();
        List<Edge> result = new ArrayList<>();
        queue.add(source);

        while(!queue.isEmpty())
        {
            Node n = queue.poll();

            if(n.getId() == dist.getId())
            {
                Node previous = source;
                closed.remove(source);

                for(Node node : closed)
                {
                    result.add(new Edge(previous, node, graph.distance(previous, node)));
                    previous = node;
                }

                return result;
            }

            closed.add(n);
            double previous = 0.0;

            for(Node node : graph.neighbors(n))
            {
                double nodeValue = Math.sqrt(Math.pow((node.getId() % rowLength) - (source.getId() % rowLength), 2.0) +
                        Math.pow((node.getId() / rowLength) - (source.getId() / rowLength), 2.0));

                if(previous == 0.0)
                {
                    previous = nodeValue;
                }

                if (!closed.contains(node))
                {
                    queue.add(node);
                }
                else if(nodeValue < previous && nodeValue == previous)
                {
                    closed.remove(n);
                    queue.add(node);
                }
            }
        }

        return result;
    }

    private class NodeComparator implements Comparator<Node>
    {
        public int compare(Node x, Node y)
        {
            double xValue = Math.sqrt(Math.pow((x.getId() % rowLength) - (source.getId() % rowLength), 2.0) +
                    Math.pow((x.getId() / rowLength) - (source.getId() / rowLength), 2.0));
            double yValue = Math.sqrt(Math.pow((y.getId() % rowLength) - (source.getId() % rowLength), 2.0) +
                    Math.pow((y.getId() / rowLength) - (source.getId() / rowLength), 2.0));

            if(xValue > yValue) {
                return 1;
            }
            else if(xValue < yValue) {
                return -1;
            }
            else {
                return 0;
            }
        }
    }

    /**
     * A lower level implementation to get path from key point to key point
     */
    public String searchFromGridFile(File file) {
        Graph graph = new Graph(Representation.of(
                            Representation.STRATEGY.OBJECT_ORIENTED));
        Node dist = null;
        String result = "";
        int colNum = 0;

//        try
//        {
//            Map<Integer, Node> map = new HashMap<>();
//            FileReader fr = new FileReader(file);
//            BufferedReader br = new BufferedReader(fr);
//
//            String line;
//
//            while((line = br.readLine()) != null)
//            {
//
//                if(line.charAt(0) != '+')
//                {
//                    int rowNum = 0;
//                    line = line.substring(1);
//                    rowLength = (int) Math.floor(line.length()/2);
//
//                    while(line.length() > 1)
//                    {
//                        Node tempN = new Node(rowLength*colNum + rowNum);
//
//                        if(line.startsWith("@1"))
//                        {
//                            source = tempN;
//                        }
//                        else if(line.startsWith("@"))
//                        {
//                            dist = tempN;
//                        }
//
//                        if(!line.startsWith("##"))
//                        {
//                            map.put(rowLength*colNum + rowNum, tempN);
//                            graph.addNode(tempN);
//
//                            if(map.containsKey(rowLength*colNum + rowNum - 1))
//                            {
//                                graph.addEdge(new Edge(map.get(rowLength*colNum + rowNum - 1), tempN, 3));
//                                graph.addEdge(new Edge(tempN, map.get(rowLength*colNum + rowNum - 1), 1));
//                            }
//                            else if(map.containsKey(rowLength*(colNum-1) + rowNum))
//                            {
//                                graph.addEdge(new Edge(map.get(rowLength*(colNum-1) + rowNum), tempN, 0));
//                                graph.addEdge(new Edge(tempN, map.get(rowLength*(colNum-1) + rowNum), 2));
//                            }
//                        }
//
//                        rowNum++;
//                        line = line.substring(2);
//                    }
//                }
//
//                colNum++;
//            }
//
//            br.close();
//            fr.close();
//        }
//        catch(IOException ignored) {}

//        List<Edge> route = search(graph, source, dist);
//
//        if(route.size() > 0)
//        {
//            for (Edge edge : route)
//            {
//                if (edge.getValue() == 0) {
//                    result += "N";
//                } else if (edge.getValue() == 1) {
//                    result += "E";
//                } else if (edge.getValue() == 2) {
//                    result += "S";
//                } else {
//                    result += "W";
//                }
//            }
//        }

        return result;
    }
}
