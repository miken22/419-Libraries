package algorithms.connectivity;

import core.components.Edge;
import core.components.Vertex;
import core.visualizer.Visualizer;

import java.util.*;

import edu.uci.ics.jung.graph.Graph;
import generators.random.Barabasi;

public class SplitGraph< V, E extends Edge> 
{
//    public static void main(String[] args) 
//    {
//        Graph<Vertex, Edge> randomGraph = new Barabasi<>().getGraph(2, 10, true);
//        Graph<Vertex, Edge> splitGraph = splitGraph( randomGraph );
//        Visualizer.viewGraph( splitGraph );
//        Visualizer.viewGraph( randomGraph );
//    }

	/**
	 * Generate a split graph from a given graph
	 * using the minimum number of edge deletion/additions.
	 * 
	 * @param graph The graph to construct a split graph of.
	 * @return The split graph we constructed from the graph passed as a parameter.
	 */
	public static Graph<Vertex, Edge> splitGraph( Graph<Vertex, Edge> graph)
	{
		// Make sure we actually have a graph to work with.
		if ( graph.getVertexCount() == 0 || graph.getEdgeCount() == 0 )
		{
			return null;
		}
		
		// Get our degree sequence.
		List<Vertex> degreeSequence = new ArrayList<Vertex>();

		for( Vertex vertex : graph.getVertices() )
		{
			vertex.addAttribute("degree", Integer.toString( graph.degree( vertex ) ) );
			degreeSequence.add(vertex);
		}
		Collections.sort( degreeSequence, new Comparator<Vertex>()
			{
				public int compare( Vertex a, Vertex b )
				{
					int dA =  Integer.parseInt( a.getAttribute("degree" ) );
					int dB =  Integer.parseInt( b.getAttribute("degree" ) );
					return dB - dA;
				}
			}
		);

		// Compute m.
		int index = 1;
		Integer m = null;
		
		while( m == null )
		{		
			int degree = Integer.parseInt(degreeSequence.get(index).getAttribute("degree"));
			
			//Looking for d_k !>= k-1
			if (degree < index-1)
			{
				m = degree;
			}
			index++;
		}
		
		// Construct every pair {i,j}, 1 <= i,j, <= m, an edge
		// In English, make every vertex from d_1 to d_m adjacent to each other.
		for( int i = 1; i <= m; i++ )
		{
			for( int j = 1; j <= m; j++ )
			{
				// Can't make an edge between the same vertex.
				if( i != j )
				{
					// First check there isn't already an edge there.
					Vertex v1 = degreeSequence.get( i );
					Vertex v2 = degreeSequence.get( j );
					Edge v1v2 =  graph.findEdge( v1, v2 );
					 
					// If there isn't an edge create one.
					if( v1v2 == null )
					{
						Edge newEdge = new Edge();
						graph.addEdge( newEdge, v1, v2 );
					}
				}
			}
		}
		
		// Now for each i, j with j > m, delete the edge
		for( int i = 1; i < m; i++ )
		{
			for( int j = m+1; j < graph.getVertexCount(); j++ )
			{
				Edge edge =  graph.findEdge( degreeSequence.get(i), degreeSequence.get(j) );
				 
				if( edge != null )
				{
					graph.removeEdge( edge );
				}
			}
		}
			
		return graph;
	}
}
