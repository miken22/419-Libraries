package core.stocks;

import core.components.Edge;
import core.components.Vertex;
import core.tools.Tools;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.util.ArrayList;

/**
 * Create the visibility graph of a stock vertex
 */
public class VisibilityGraph {

    /**
     * Fixed implementation to create a visibility graph as specified by the
     * financial history of a company.
     *
     * @param stockVertex The stock to create the visibility graph for
     * @return A graph representing the visibility graph of a companies
     *         stock history.
     */
    public Graph<Vertex, Edge> createGraph(StockVertex stockVertex) {
        Graph<Vertex, Edge> graph = new SparseGraph<>();

        double[] dataPoints = stockVertex.getDataPoints();

        // Make graph for last 60 days
        int startPoint = dataPoints.length-61;

        for (int tA = startPoint; tA < dataPoints.length-1; tA++) {

            boolean endOfNeighbourhood = false;
            int iAdjustedIndex = tA - startPoint;

            Vertex vertex = Tools.getVertex(graph, ""+iAdjustedIndex);

            if (vertex == null) {
                vertex = new Vertex(""+iAdjustedIndex);
                graph.addVertex(vertex);
            }

            for (int tB = tA+1; tB < dataPoints.length; tB++) {

                int jAdjustedIndex = tB - startPoint;
                // See if the vertex is in the graph already, use it if it is,
                // create a new one otherwise.
                Vertex nextVertex = Tools.getVertex(graph, ""+jAdjustedIndex);

                if (nextVertex == null) {
                    nextVertex = new Vertex(""+jAdjustedIndex);
                    graph.addVertex(nextVertex);
                }

                // Edge already exists between the two
                if (graph.isNeighbor(vertex, nextVertex)) {
                    continue;
                }

                // Neighbours are visible by default
                if (tB == tA+1) {
                    graph.addEdge(new Edge(), vertex, nextVertex);
                }

                // Continue until an obstruction is reached, move to next index when end of neighbourhood reached.
                for (int tC = tA+1; tC < tB; tC++) {

                    double yA = dataPoints[tA];
                    double yB = dataPoints[tB];
                    double yC = dataPoints[tC];

                    double meanValue = yB + (yA - yB)*((tB - tC)/(tB - tA));

                    // If a point is greater than or equal to the mean value then the points {ta,tb} are not visible to each other
                    if (yC >= meanValue) {
                        endOfNeighbourhood = true;
                        break;
                    }
                }
                // At the end of the neighbourhood increase the index for ta
                if (endOfNeighbourhood) {
                    break;
                } else {
                    // Otherwise no obstructions, add edge between them
                    graph.addEdge(new Edge(), vertex, nextVertex);
                }
            }
        }

        return graph;
    }

}
