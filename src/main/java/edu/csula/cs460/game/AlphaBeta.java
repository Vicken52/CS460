package edu.csula.cs460.game;

import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

public class AlphaBeta {
    public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean min) {

        if(depth == 0 | graph.neighbors(source).size() == 0)
        {
            return source;
        }

        if(min) {
            Node nodeValue = null;
            int value = Integer.MIN_VALUE;

            for(Node node : graph.neighbors(source)) {
                Node nodeTmp = getBestMove(graph, node, depth - 1, alpha, beta, false);

                if(value < (Integer) nodeTmp.getData())
                {
                    nodeValue = nodeTmp;
                    value = (Integer) nodeTmp.getData();
                }
                alpha = Integer.max(alpha, value);

                if(beta <= alpha) { break; }
            }

            return nodeValue;
        }
        else {
            Node nodeValue = null;
            int value = Integer.MAX_VALUE;

            for(Node node : graph.neighbors(source)) {
                Node nodeTmp = getBestMove(graph, node, depth - 1, alpha, beta, true);

                if(value > (Integer) nodeTmp.getData())
                {
                    nodeValue = nodeTmp;
                    value = (Integer) nodeTmp.getData();
                }
                beta = Integer.min(alpha, value);

                if(beta <= alpha) { break; }
            }

            return nodeValue;
        }
    }
}
