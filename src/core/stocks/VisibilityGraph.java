package core.stocks;

import core.components.Edge;
import core.components.Vertex;
import core.tools.Tools;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

/**
 * Create the visibility graph of a stock vertex
 */
public class VisibilityGraph {

    /**
     * Fixed implementation to create a visibility graph as specified by the
     * financial history of a company.
     *
     * @param company The stock to create the visibility graph for
     * @return A graph representing the visibility graph of a companies
     *         stock history.
     */
    public Graph<Vertex, Edge> createGraph(Company company, int numberOfDays) {
        Graph<Vertex, Edge> graph = new SparseGraph<>();

        double[] dataPoints = company.getDataPoints();

        // +1 to account for heading row for data
        double startPoint = dataPoints.length - (numberOfDays+1);

        if (startPoint < 0) {
            startPoint = 0.0;
        }

        // Loop from the first vertex
        for (double tA = startPoint; tA < dataPoints.length-1; tA++) {

            int iAdjustedIndex = (int)(tA - startPoint);

            Vertex vertex = Tools.getVertex(graph, ""+iAdjustedIndex);

            if (vertex == null) {
                vertex = new Vertex(""+iAdjustedIndex);
                graph.addVertex(vertex);
            }

            // Check every vertex starting from the next one after tA
            for (double tB = tA+1; tB < dataPoints.length; tB++) {

                int jAdjustedIndex = (int)(tB - startPoint);
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
                    continue;
                }

                boolean visible = true;
                // Continue until an obstruction is reached, if no obstruction is recorded we will add an edge
                for (double tC = tA+1; tC < tB; tC++) {

                    double yA = dataPoints[(int)tA];
                    double yB = dataPoints[(int)tB];
                    double yC = dataPoints[(int)tC];

                    double meanValue = yB + (yA - yB)*((tB - tC)/(tB - tA));

                    // If a point is less than the mean value then the points {ta,tb} are visible to each other
                    if (yC >= meanValue) {
                        visible = false;
                        break;
                    }

                }

                // Passed every test for tC between tA and tB, add an edge between the vertices
                if (visible) {
                    graph.addEdge(new Edge(), vertex, nextVertex);
                }

            }

        }

        return graph;
    }

}
