package algorithms.distribution;

import algorithms.connectivity.Triangles;
import core.components.Edge;
import core.components.Vertex;
import edu.uci.ics.jung.graph.Graph;
import generators.random.Barabasi;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of Task 1 for Assignment 3
 *
 * Created by Mike Nowicki on 2015-11-11.
 */
public class DegreeDist {


    /**
     * Computes the degree distribution for a graph and given degree.
     * @param graph The graph to compute the degree distribution for.
     * @param degree The degree to use.
     * @param <V> The vertex type.
     * @param <E> The edge type.
     * @return The degree distribution of the given degree, 0 if none exist.
     */
    public static<V,E> double degreeDist(Graph<V,E> graph, Integer degree) {

        double numOfVertices = 0.0;
        for (V vertex : graph.getVertices()) {
            int testDegree = graph.degree(vertex);
            if (testDegree == degree) {
                numOfVertices++;
            }
        }

        return (numOfVertices/(double)graph.getVertexCount());

    }
    
    /**
     * Calculate the degree distribution for the graph.
     * @param graph The graph to work with.
     * @return The degree distribution as an array where the index is the degree and its value the distribution
     */
    public static<V,E> double[] degreeDist(Graph<V,E> graph){
    	// Calculate total degree
    	int totalDegree = 2*graph.getEdgeCount();
    	int numVertices = graph.getVertexCount();
    	double[] degreeDist = new double[totalDegree];
    	
    	for( V vertex : graph.getVertices()) {
    		double currentDeg = degreeDist[graph.degree(vertex)];
    		
    		// If we've already added to the count and then calculated the average
    		// 	we have to undo that average and recalculate.
    		if(currentDeg != 0 ) {
    			degreeDist[graph.degree(vertex)] *= numVertices;
    		}
    			
    		degreeDist[graph.degree(vertex)]++;
    		degreeDist[graph.degree(vertex)] = degreeDist[graph.degree(vertex)]/numVertices;
    		
    		System.out.println(graph.degree(vertex) + " " + degreeDist[graph.degree(vertex)] );
    	}
    	    
    	return degreeDist;
    }


    /**
     * Estimates the power-law exponent of the degree distribution.
     * @param graph The graph to use.
     * @param <V> The vertex type.
     * @param <E> The edge type.
     * @return The power law exponent estimation.
     */
    public static<V,E> double aprroxExponent(Graph<V,E> graph) {

        double sum = 0.0;

        ArrayList<V> vertices = new ArrayList<>();
        vertices.addAll(graph.getVertices());
        vertices.sort(new Triangles.VertexComparator(graph));

        // They are sorted from highest to lowest degree, last element
        // will have the minimum degree in the graph.
        int xMin = graph.degree(vertices.get(vertices.size()-1));

        // Ensure the minimum degree isn't 0.
        if (xMin == 0) {
            System.out.println("Cannot be computed if there are isolated vertices");
            return 0.0;
        }

        // Compute the sum: SUM{1..n}[ log_e( degree(vertex) / xMin - 0.5) ]
        for (V vertex : graph.getVertices()) {
            sum += Math.log(graph.degree(vertex) / (xMin - 0.5));
        }

        // Invert and multiply by n
        sum = (1 / sum);
        sum = graph.getVertexCount() * sum;

        // Return the approximation by taking the resulting sum and adding 1.
        return 1 + sum;
    }
    
    /**
     * Computes the average degree of a graph.
     * @param graph The graph to use.
     * @param <V> The vertex type.
     * @param <E> The edge type.
     * @return The average degree of all vertices in the graph.
     */
    public static<V,E> double averageDegree(Graph<V,E> graph) {
    	double total = 0;
    	
    	for( V vertex : graph.getVertices() )
		{
    		total += graph.degree(vertex);
		}
    	return total/graph.getVertices().size();
    }


//    public static void main(String[] args) {
//        Graph<Vertex, Edge> graph = Barabasi.getGraph(5, 50);
//
//        double approx = DegreeDist.aprroxExponent(graph);
//
//        Vertex v = graph.getVertices().iterator().next();
//        double dist = DegreeDist.degreeDist(graph, graph.degree(v));
//        
//        double[] degDist = DegreeDist.degreeDist(graph);
//        String distbn = "";
//        for( int i=0;i<degDist.length; i++) {
//        	distbn = distbn + ", " + degDist[i];
//        }
//
//        System.out.println("Distribution: " + dist);
//        System.out.println("Approximation: " + approx);
//        System.out.println("Degree Dist: " + distbn);
//
//    }
}
