package algorithms.connectivity;

import core.components.Edge;
import core.components.Vertex;
import core.visualizer.Visualizer;

import java.util.*;

import edu.uci.ics.jung.graph.Graph;
import generators.random.Barabasi;

/** 
Returns an array, PR,  with page rank for each vertex in a given graph. 
NOTE: it is assumed that each vertex of the graph is numbered 
      from 0 - n where n is the max index of verteces in the graph.
      PR[i] gives the page rank of the i'th vertex of the graph. 

*/
public class PageRank {

    Graph<Vertex, Edge> graph;

    public PageRank(Graph<Vertex, Edge> G) {
        graph = G;
    }

    /**
     * Compute the PageRanks for the given graph.
     */
    public double[] pageRank() {

        double[] PR = new double[50]; // page rank array initialized at 1/50

        //intiialize PR arrray w 1/50 in each element 
        for (int i = 0; i < PR.length; i++) {
            PR[i] = 1.0 / 50.0;
            System.out.println("PR[" + i + "]: " + PR[i]);
        }


        // System.out.println("VERTEX COUNT FOR GRAOH: " + graph.getVertexCount());
        String vertStr = "";
        int vertInd;
        String sourceStr = "";
        int sourceInd;
        int sourceDeg = 0;
        double sourcePR = 0.0;


        for (Vertex targetVertex : graph.getVertices()) {  //for all nodes in graph, ie current node to iter through
            vertStr = "" + targetVertex;                   //get current node id
            vertInd = Integer.valueOf(vertStr);            //make it a number (for the array)
            double PR_value = 0.0;                         // our page rank to be calculated.

            Collection<Edge> targetInEdges = graph.getInEdges(targetVertex); // all edges pointing to current node

            for (Edge e : targetInEdges) { // for all edges going into target node
                sourceStr = "" + graph.getSource(e);
                sourceInd = Integer.valueOf(sourceStr);

                //  graph.getSource(e); //the source node of the edge going into target node
                // System.out.println(targetVertex + " has " + graph.getSource(e) + " pointing to it, which has outDegree: " + graph.outDegree(graph.getSource(e)));
                sourceDeg = graph.outDegree(graph.getSource(e)); //denom
                sourcePR = PR[sourceInd]; //numerator

                PR_value += sourcePR / sourceDeg;
                PR[vertInd] = PR_value;

            }

            // System.out.println("VERTEX: " + targetVertex + " HAS OUT DEGREE: " + graph.outDegree(targetVertex) +
            //       " AND IN DEGREE: " + graph.inDegree(targetVertex) + " AND PAGE RANK: " + PR[vertInd]);


        }
        return PR;

    }
}

//public static void main(String[] args) {
//        Graph<Vertex, Edge> graph = new Barabasi<>().getGraph(3, 50, true);
//        //Visualizer.viewGraph(graph);
//
//        double[] PR = new double[50]; // page rank array initialized at 1/50
//
//        //intiialize
//        for (int i = 0; i < PR.length; i++) {
//            PR[i] = 1.0 / 50.0;
//            System.out.println("PR[" + i + "]: " + PR[i]);
//        }
//
//
//        System.out.println("VERTEX COUNT FOR GRAOH: " + graph.getVertexCount());
//        String vertStr = "";
//        int vertInd;
//        String sourceStr = "";
//        int sourceInd;
//        int sourceDeg = 0;
//        double sourcePR = 0.0;
//        for (Vertex targetVertex : graph.getVertices()) {  //for all nodes in graph, ie current node to iter through
//            vertStr = "" + targetVertex;                   //get current node id
//            vertInd = Integer.valueOf(vertStr);            //make it a number (for the array)
//            double PR_value = 0.0;                         // our page rank to be calculated.
//
//            Collection<Edge> targetInEdges = graph.getInEdges(targetVertex); // all edges pointing to current node
//
//            for (Edge e : targetInEdges) { // for all edges going into target node
//                sourceStr = "" + graph.getSource(e);
//                sourceInd = Integer.valueOf(sourceStr);
//
//                //  graph.getSource(e); //the source node of the edge going into target node
//                // System.out.println(targetVertex + " has " + graph.getSource(e) + " pointing to it, which has outDegree: " + graph.outDegree(graph.getSource(e)));
//                sourceDeg = graph.outDegree(graph.getSource(e)); //denom
//                sourcePR = PR[sourceInd]; //numerator
//
//                PR_value += sourcePR / sourceDeg;
//                PR[vertInd] = PR_value;
//
//            }
//
//            System.out.println("VERTEX: " + targetVertex + " HAS OUT DEGREE: " + graph.outDegree(targetVertex) +
//                    " AND IN DEGREE: " + graph.inDegree(targetVertex) + " AND PAGE RANK: " + PR[vertInd]);
//
//
//        }
//        //  System.out.println("MAX: " + n);  MAX is 49 => there are 50 nodes.
//          double sum = 0.0;
//          for(int j = 0 ; j < PR.length ; j++) {
//              double curr = PR[j];
//              if( curr != 0.02)
//                sum += curr;
//             // System.out.println(curr);
//
//          }
//        System.out.println("SUM: " + sum);
//    }//end of main
