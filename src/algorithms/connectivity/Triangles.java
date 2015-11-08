package algorithms.connectivity;

import edu.uci.ics.jung.graph.Graph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Count the number of triangles in a graph using
 * node iteration. Based on the paper available at
 * http://i11www.iti.uni-karlsruhe.de/extra/publications/sw-fclt-05_wea.pdf
 * by Thomas Schank and Dorothea Wagner.
 *
 * @author Mike Nowicki on 08/11/15.
 */
public class Triangles<V,E> {

    /**
     * Computes the number of triangles in a graph using the node
     * iteration technique.
     *
     * @param graph The graph to do the search on.
     * @param <V> The vertex type.
     * @param <E> The edge type.
     * @return The number of triangles in the graph.
     */
    public static<V,E> Integer count(Graph<V,E> graph) {

        Integer triangleCount = 0;

        HashMap<V, HashSet<V>> triangleMap = new HashMap<>();

        // Get all vertices and sort them by degree (largest first)
        ArrayList<V> nodeOrdering = new ArrayList<>();
        nodeOrdering.addAll(graph.getVertices());
        nodeOrdering.sort(new VertexComparator<>(graph));

        // Initialize all sets to be empty
        for (V vertex : graph.getVertices()) {
            triangleMap.put(vertex, new HashSet<>());
        }

        // Iterate over all vertices
        for (V vertex : nodeOrdering) {
            // Find all the neighbours of the vertex
            for (V neighbour : graph.getNeighbors(vertex)) {
                // If the neighbour has a lower degree and was placed lower in the
                // ordering test if they have common neighbours
                if (nodeOrdering.indexOf(vertex) < nodeOrdering.indexOf(neighbour)) {
                    // Get the collections of neighbours in the graph
                    HashSet<V> sourceSet = triangleMap.get(vertex);
                    HashSet<V> targetSet = triangleMap.get(neighbour);

                    // Iterate over one of the sets, find all common neighbours
                    // to increase triangle counts.
                    for (V comNeighbour : sourceSet) {
                        if (targetSet.contains(comNeighbour)) {
                            triangleCount++;
                        }
                    }
                    // Add the source node to the map of neighbours
                    triangleMap.get(neighbour).add(vertex);
                }
            }
        }
        return triangleCount;
    }

    /**
     * Private comparator that orders vertices based on their degree.
     * @param <T> The vertex type.
     * @param <E> The edge type.
     */
    private static class VertexComparator<T,E> implements Comparator<T> {
        private Graph<T,E> graph;

        public VertexComparator(Graph<T,E> graph) {
            this.graph = graph;
        }

        @Override
        public int compare(T o1, T o2) {

            int degOne = graph.degree(o1);
            int degTwo = graph.degree(o2);

            if (degOne > degTwo) {
                return -1;
            } else if (degOne < degTwo) {
                return 1;
            }
            return 0;
        }
    }

//    public static void main(String[] args) {
//
////        Graph<Vertex, Edge> graph = Barabasi.getGraph(5, 50);
//
//        Graph<Vertex, Edge> graph = new SparseGraph<>();//Barabasi.getGraph(5, 50);
//        Vertex v1 = new Vertex("1");
//        Vertex v2 = new Vertex("2");
//        Vertex v3 = new Vertex("3");
//
//        graph.addVertex(v1);
//        graph.addVertex(v2);
//        graph.addVertex(v3);
//
//        graph.addEdge(new Edge(), v1, v2);
//        graph.addEdge(new Edge(), v2, v3);
//        graph.addEdge(new Edge(), v3, v1);
//
//        System.out.println(Triangles.count(graph));
//
//    }
}
