package edu.csula.cs460.graph.search;

import edu.csula.cs460.graph.Edge;
import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;
import edu.csula.cs460.graph.strategy.Representation;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AStar implements SearchStrategy {
    private int rowLength = 0;
    private  Map<Node, Double> f_score;

    private class NodeComparator implements Comparator<Node>
    {
        @Override
        public int compare(Node x, Node y)
        {
            return (int) (f_score.getOrDefault(x, Double.POSITIVE_INFINITY) - f_score.getOrDefault(y, Double.POSITIVE_INFINITY));
        }
    }

    /*
    Distance:
        0 = North
        1 = East
        2 = South
        3 = West
     */

    public double value(Node x, Node y)
    {
        return Math.sqrt(Math.pow((double) ( x.getId() % rowLength) - (double) (y.getId() % rowLength), 2.0) +
                Math.pow((double) (x.getId() / rowLength) - (double) (y.getId() / rowLength), 2.0));
    }

    private List<Edge> reconstruct_path(Graph graph, Map<Node, Node> from, Node n)
    {
        List<Edge> result = new ArrayList<>();

        while(from.containsKey(n))
        {
            result.add(0, new Edge(from.get(n), n, graph.distance(from.get(n), n)));
            n = from.get(n);
        }

        return result;
    }

    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        Map<Node, Node> from = new HashMap<>();
        List<Node> visited = new ArrayList<>();

        System.out.println("Source: " + source);
        System.out.println("Dist: " + dist);

        Map<Node, Double> g_score = new HashMap<>();
        g_score.put(source, 0.0);

        f_score = new HashMap<>();
        f_score.put(source, g_score.get(source) + value(source, dist));


        Queue<Node> queue = new PriorityQueue<>(new NodeComparator());
        queue.add(source);

        while(!queue.isEmpty())
        {
            Node n = queue.poll();
            //System.out.print(n);
            visited.add(n);

            if(n.getId() == dist.getId())
            {
                return reconstruct_path(graph, from, n);
            }

            graph.neighbors(n).stream().filter(node -> !visited.contains(node)).forEach(node -> {

                if (!queue.contains(node))
                {
                    double tmp = g_score.getOrDefault(n, Double.POSITIVE_INFINITY) + (double) graph.distance(n, node);

                    if(!queue.contains(node))
                    {
                        queue.add(node);
                    }

                    if (tmp <= g_score.getOrDefault(node, Double.POSITIVE_INFINITY))
                    {
                        //System.out.println(n +  " " + node);
                        from.put(node, n);
                        g_score.put(node, tmp);
                        f_score.put(node, f_score.getOrDefault(n, Double.POSITIVE_INFINITY) + value(node, dist));
                    }
                }
            });
        }

        return null;
    }

    /**
     * A lower level implementation to get path from key point to key point
     */
    public String searchFromGridFile(File file) {
        Graph graph = new Graph(Representation.of(
                            Representation.STRATEGY.OBJECT_ORIENTED));
        Node source = null;
        Node dist = null;
        String result = "";

        try
        {
            Map<Integer, Node> map = new HashMap<>();
            Scanner in = new Scanner(file);

            String line;

            line = in.nextLine();
            rowLength = ((int) Math.floor((line.length()) / 2.0)) - 1;
            int rowNum = 0;

            while(in.hasNextLine())
            {
                line = in.nextLine().substring(1);
                //System.out.println(line);
                int colNum = 0;

                if(!line.contains("#") && !line.contains("@"))
                {
                    for(int i = 0; i < rowLength; i++)
                    {
                        Node tempN = new Node(rowLength*rowNum + i);
                        map.put(rowLength*rowNum + i, tempN);
                        graph.addNode(tempN);
                        //System.out.println(tempN);

                        if(map.containsKey(rowLength*rowNum + i - 1) && i != 0)
                        {
                            graph.addEdge(new Edge(map.get(rowLength*rowNum + i - 1), tempN, 1));
                            graph.addEdge(new Edge(tempN, map.get(rowLength*rowNum + i - 1), 3));
                        }

                        if(map.containsKey(rowLength*(rowNum-1) + i))
                        {
                            graph.addEdge(new Edge(map.get(rowLength*(rowNum-1) + i), tempN, 2));
                            graph.addEdge(new Edge(tempN, map.get(rowLength*(rowNum-1) + i), 0));
                        }

                    }
                }
                else
                {
                    while(line.length() > 1)
                    {
                        if (!line.startsWith("##"))
                        {
                            Node tempN = new Node(rowLength * rowNum + colNum);
                            map.put(rowLength * rowNum + colNum, tempN);
                            graph.addNode(tempN);
                            //System.out.println(tempN);

                            if (map.containsKey(rowLength * rowNum + colNum - 1) && colNum != 0) {
                                graph.addEdge(new Edge(map.get(rowLength * rowNum + colNum - 1), tempN, 1));
                                graph.addEdge(new Edge(tempN, map.get(rowLength * rowNum + colNum - 1), 3));
                            }

                            if (map.containsKey(rowLength * (rowNum - 1) + colNum)) {
                                graph.addEdge(new Edge(map.get(rowLength * (rowNum - 1) + colNum), tempN, 2));
                                graph.addEdge(new Edge(tempN, map.get(rowLength * (rowNum - 1) + colNum), 0));
                            }

                            if (line.startsWith("@1")) {
                                //System.out.println(rowNum + " " + colNum);
                                source = tempN;
                            } else if (line.startsWith("@")) {
                                //System.out.println(rowNum + " " + colNum);
                                dist = tempN;
                            }
                        }

                        colNum++;
                        line = line.substring(2);

                    }
                }

                rowNum++;
            }

            in.close();
        }
        catch(IOException ignored) {}

        List<Edge> route = search(graph, source, dist);

        if(route.size() > 0)
        {
            for (Edge edge : route)
            {
                //System.out.println(edge);
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
