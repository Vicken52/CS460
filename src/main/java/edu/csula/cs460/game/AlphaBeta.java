package edu.csula.cs460.game;

import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

public class AlphaBeta {
    private static Graph graph;
    private static Node mainRoot;

    private static Node alphabeta(Node source, Integer depth, Integer alpha, Integer beta, Boolean min) {

        if(depth == 0 | graph.neighbors(source).size() == 0)
        {
            return source;
        }

        if(min) {
            Node nodeValue = null;
            int value = Integer.MIN_VALUE;

            for(Node node : graph.neighbors(source)) {
                Node nodeTmp = alphabeta(node, depth - 1, alpha, beta, false);

                if(value < (Integer) nodeTmp.getData())
                {
                    nodeValue = nodeTmp;
                    value = (Integer) nodeTmp.getData();
                }
                alpha = Integer.max(alpha, value);

                if(beta <= alpha) {
                    beta = Integer.MIN_VALUE;
                    break;
                }
            }

            if(source.getId() == mainRoot.getId())
            {
                assert nodeValue != null;
                mainRoot.setData(nodeValue.getData());
            }

            assert nodeValue != null;
            graph.getNode(source.getId()).get().setData(nodeValue.getData());
            return nodeValue;
        }
        else {
            Node nodeValue = null;
            int value = Integer.MAX_VALUE;

            for(Node node : graph.neighbors(source)) {
                Node nodeTmp = alphabeta(node, depth - 1, alpha, beta, true);

                if(value > (Integer) nodeTmp.getData())
                {
                    nodeValue = nodeTmp;
                    value = (Integer) nodeTmp.getData();
                }
                beta = Integer.min(beta, value);

                if(beta <= alpha) {
                    alpha = Integer.MAX_VALUE;
                    break;
                }
            }

            if(source.getId() == mainRoot.getId())
            {
                assert nodeValue != null;
                mainRoot.setData(nodeValue.getData());
            }

            assert nodeValue != null;
            graph.getNode(source.getId()).get().setData(nodeValue.getData());
            return nodeValue;
        }
    }

    public static Node getBestMove(Graph graphTmp, Node source, Integer depth, Integer alpha, Integer beta, Boolean min) {

        graph = graphTmp;
        mainRoot = source;
        
        alphabeta(source, depth, alpha, beta, min);

        for(Node node : graph.neighborsSearch(mainRoot))
        {
            Node tmp = graph.getNode(node.getId()).get();
            if(tmp.getData().equals(mainRoot.getData()))
            {
                return tmp;
            }
        }

        return null;
    }
}
