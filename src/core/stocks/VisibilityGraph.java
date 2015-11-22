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
     * financial history of a company. Each vertex is represented by a pair,
     * (tA, yA), where tA is the day the vertex represents, and yA is the
     * value of the companies stock on day tA.
     *
     * @param company The stock to create the visibility graph for
     * @return A graph representing the visibility graph of a companies
     *         stock history.
     */
    public Graph<Vertex, Edge> createGraph(Company company, int numberOfDays) {

        Graph<Vertex, Edge> graph = new SparseGraph<>();

        double[] dataPoints = company.getDataPoints();

        if (numberOfDays > dataPoints.length) {
            numberOfDays = dataPoints.length;
        }

        // Loop from the first vertex
        for (double tA = 0; tA < numberOfDays; tA++) {

            Vertex vertex = Tools.getVertex(graph, ""+tA);

            if (vertex == null) {
                vertex = new Vertex(""+tA);
                graph.addVertex(vertex);
            }

            // Check every vertex starting from the next one after tA
            for (double tB = tA+1; tB < numberOfDays; tB++) {

                // See if the vertex is in the graph already, use it if it is,
                // create a new one otherwise.
                Vertex nextVertex = Tools.getVertex(graph, ""+tB);

                if (nextVertex == null) {
                    nextVertex = new Vertex(""+tB);
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

                    // If any point, tC, between tA and tB is larger than the mean value
                    // between tA and tB at tC, then tC obstructs the view from tA to tB.
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
