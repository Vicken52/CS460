package edu.csula.cs460.game;

import edu.csula.cs460.graph.Graph;
import edu.csula.cs460.graph.Node;

public class Minimax {

    private static Graph graph;
    private static Node mainRoot;

    private static Node minimax(Node root, Integer depth, Boolean min) {
        if(depth == 0 || graph.neighbors(root).size() == 0)
        {
            return root;
        }


        if(min) {
            Node tmp = null;
            int bestValue = Integer.MIN_VALUE;

            for(Node node : graph.neighbors(root)) {
                Node value = minimax(node, depth-1, false);

                if((Integer) value.getData() > bestValue)
                {
                    tmp = value;
                    bestValue = (Integer) tmp.getData();
                }
            }

            if(root.getId() == mainRoot.getId())
            {
                assert tmp != null;
                mainRoot.setData(tmp.getData());
            }

            assert tmp != null;
            graph.getNode(root.getId()).get().setData(tmp.getData());
            return tmp;
        }
        else {
            Node tmp = null;
            int bestValue = Integer.MAX_VALUE;

            for(Node node : graph.neighbors(root)) {
                Node value = minimax(node, depth-1, true);

                if((Integer) value.getData() < bestValue)
                {
                    tmp = value;
                    bestValue = (Integer) tmp.getData();
                }
            }

            if(root.getId() == mainRoot.getId())
            {
                assert tmp != null;
                mainRoot.setData(tmp.getData());
            }

            assert tmp != null;
            graph.getNode(root.getId()).get().setData(tmp.getData());
            return tmp;
        }
    }

    public static Node getBestMove(Graph graphTmp, Node root, Integer depth, Boolean min) {

        graph = graphTmp;
        mainRoot = root;

        minimax(root, depth, min);

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
