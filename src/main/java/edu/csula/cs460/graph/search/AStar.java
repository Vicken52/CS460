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
    private Node dist;

    private class NodeComparator implements Comparator<Node>
    {
        @Override
        public int compare(Node x, Node y)
        {
            double xValue = Math.sqrt(Math.pow((x.getId() % rowLength) - (dist.getId() % rowLength), 2.0) +
                    Math.pow((x.getId() / rowLength) - (dist.getId() / rowLength), 2.0));
            double yValue = Math.sqrt(Math.pow((y.getId() % rowLength) - (dist.getId() % rowLength), 2.0) +
                    Math.pow((y.getId() / rowLength) - (dist.getId() / rowLength), 2.0));

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

    /*
    Distance:
        0 = North
        1 = East
        2 = South
        3 = West
     */

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        List<Node> visited = new ArrayList<>();
        Queue<Node> queue = new PriorityQueue<>(new NodeComparator());
        List<Edge> result = new ArrayList<>();
        queue.add(source);

        while(!queue.isEmpty())
        {
            Node n = queue.poll();
            System.out.print(n);

            if(n.getId() != dist.getId() && !visited.contains(n))
            {
                visited.add(n);

                queue.addAll(graph.neighbors(n));
                Node tmp = queue.poll();
                result.add(new Edge(n, tmp, graph.distance(n, tmp)));

                queue.clear();
                queue.add(tmp);
            }
            else
            {
                break;
            }
        }

        return result;
//        Queue<Node> queue = new PriorityQueue<>(new NodeComparator());
//        List<Node> closed = new ArrayList<>();
//        List<Edge> result = new ArrayList<>();
//        queue.add(source);
//
//        while(!queue.isEmpty())
//        {
//            Node n = queue.poll();
//
//            if(n.getId() == dist.getId())
//            {
//                Node previous = source;
//                closed.remove(source);
//
//                for(Node node : closed)
//                {
//                    result.add(new Edge(previous, node, graph.distance(previous, node)));
//                    previous = node;
//                }
//
//                return result;
//            }
//
//            closed.add(n);
//            double previous = 0.0;
//
//            for(Node node : graph.neighbors(n))
//            {
//                double nodeValue = Math.sqrt(Math.pow((node.getId() % rowLength) - (source.getId() % rowLength), 2.0) +
//                        Math.pow((node.getId() / rowLength) - (source.getId() / rowLength), 2.0));
//
//                if(previous == 0.0)
//                {
//                    previous = nodeValue;
//                }
//
//                if (!closed.contains(node))
//                {
//                    queue.add(node);
//                }
//                else if(nodeValue < previous && nodeValue == previous)
//                {
//                    closed.remove(n);
//                    queue.add(node);
//                }
//            }
//        }
//
//        return result;
    }

    /**
     * A lower level implementation to get path from key point to key point
     */
    public String searchFromGridFile(File file) {
        Graph graph = new Graph(Representation.of(
                            Representation.STRATEGY.OBJECT_ORIENTED));
        String result = "";

        try
        {
            Map<Integer, Node> map = new HashMap<>();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;

            line = br.readLine();
            rowLength = (int) Math.floor((line.length()-2)/2);
            int rowNum = 0;

            while((line = br.readLine()) != null)
            {
                line = line.substring(1);
                System.out.println(line);
                int colNum = 0;

                while(line.length() > 1)
                {
                    if(!line.startsWith("##"))
                    {
                        Node tempN = new Node(rowLength*rowNum + colNum);
                        map.put(rowLength*rowNum + colNum, tempN);
                        graph.addNode(tempN);
                        //System.out.println(tempN);

                        if(map.containsKey(rowLength*rowNum + colNum - 1))
                        {
                            graph.addEdge(new Edge(map.get(rowLength*rowNum + colNum - 1), tempN, 1));
                            graph.addEdge(new Edge(tempN, map.get(rowLength*rowNum + colNum - 1), 3));
                        }

                        if(map.containsKey(rowLength*(rowNum-1) + colNum))
                        {
                            graph.addEdge(new Edge(map.get(rowLength*(rowNum-1) + colNum), tempN, 2));
                            graph.addEdge(new Edge(tempN, map.get(rowLength*(rowNum-1) + colNum), 0));
                        }

                        if(line.startsWith("@1"))
                        {
                            //System.out.println(rowNum + " " + colNum);
                            source = tempN;
                        }
                        else if(line.startsWith("@"))
                        {
                            //System.out.println(rowNum + " " + colNum);
                            dist = tempN;
                        }
                    }

                    colNum++;
                    line = line.substring(2);
                }

                rowNum++;
            }

            br.close();
            fr.close();
        }
        catch(IOException ignored) {}

        List<Edge> route = search(graph, source, dist);

        if(route.size() > 0)
        {
            for (Edge edge : route)
            {
                if (edge.getValue() == 0) {
                    result += "N";
                } else if (edge.getValue() == 1) {
                    result += "E";
                } else if (edge.getValue() == 2) {
                    result += "S";
                } else {
                    result += "W";
                }
            }
        }

        return result;
    }
}
