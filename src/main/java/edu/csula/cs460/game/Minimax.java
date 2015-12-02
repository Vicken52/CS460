package edu.csula.cs460.game;

import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

public class Minimax {
    public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean max) {
        if(depth == 0 || graph.neighbors(root).size() == 0)
        {
            return root;
        }


        if(max) {
            Node tmp = null;
            int bestValue = Integer.MIN_VALUE;

            for(Node node : graph.neighbors(root)) {
                Node value = getBestMove(graph, node, depth-1, false);

                if((Integer) value.getData() > bestValue)
                {
                    tmp = value;
                    bestValue = (Integer) tmp.getData();
                }
            }

            return tmp;
        }
        else {
            Node tmp = null;
            int bestValue = Integer.MAX_VALUE;

            for(Node node : graph.neighbors(root)) {
                Node value = getBestMove(graph, node, depth-1, true);

                if((Integer) value.getData() < bestValue)
                {
                    tmp = value;
                    bestValue = (Integer) tmp.getData();
                }
            }

            return tmp;
        }
    }
}
