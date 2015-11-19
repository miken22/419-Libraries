package core.stocks;

<<<<<<< HEAD
=======
import core.components.Edge;
import core.components.Vertex;
import core.webcrawler.Crawler;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

>>>>>>> fd8efea2ca6d056f255a4286d3d0e8c069513395
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The implementation we used to generate visibility graphs of various
 * companies traded on the TSX S&P 60 Index. The crawler can be run to get
 * as much financial history as possible from the company up to Nov 1, 2015.
 *
 * A specific stock can be specified to have the application create that
 * companies visibility graph.
 *
 */
public class StockGraphBuilder {

	/**
<<<<<<< HEAD
=======
	 * Main program entry point, creates the visibility graph for a specified stock.
	 * @param args Program arguments (none needed)
     */

	// Here's a method that can be used to create the vertices for each stock. The
	// data points stored in a stock vertex are used to
	public static void main(String[] args) {

		StockGraphBuilder builder = new StockGraphBuilder();
		VisibilityGraph visGraph = new VisibilityGraph();

		// Use this to get the stock information. It won't crawl if it has
		// been run in the last 24 hours.
		Crawler crawler = new Crawler();
		crawler.crawl();

		Collection<StockVertex> vertices = builder.getStockVertices();

		StockVertex vertex = builder.getVertex(vertices, "AGU");
		Graph<Vertex, Edge> visibilityGraph = visGraph.createGraph(vertex);

		VisibilityGraphViewer.viewGraph(visibilityGraph);

	}

	/**
>>>>>>> fd8efea2ca6d056f255a4286d3d0e8c069513395
	 * Loads vertices from file and adds them to the graph
	 *
	 * @return - Initialized graph with all vertices
	 * @throws IOException
	 */
	public Collection<Company> loadCompanies() throws IOException {

		Collection<Company> companies = new ArrayList<>();

		int minLength = Integer.MAX_VALUE;

		File directory = new File("Results");
		
		for (String company : TSXCompanies.COMPANIES) {

			File file = new File(directory, company+".txt");
			InputStream ins = new FileInputStream(file);
			BufferedReader reader =  new BufferedReader(new InputStreamReader(ins));
			//First line is headers, skip to the next line.
			reader.readLine();
			String line = reader.readLine();

			int fileLength = getFileLength(file);
			if (fileLength < minLength) {
				minLength = fileLength;
			}

			Company vertex = new Company(company, fileLength);

			while (line != null) {
				String[] data = line.split(",");
				if (data[4] == "") {
					break;
				}
				vertex.addData(Double.valueOf(data[4]));
				line = reader.readLine();
			}
			reader.close();

			vertex.computeAveragePrice();
			vertex.computeVariance();
			vertex.setCompanyName(company);

			companies.add(vertex);

		}

		return companies;
		
	}

	/**
	 * Count number of data points for the file
	 * 
	 * @param file The file to check the length of.
	 * @return The file length
	 * @throws IOException
	 */
	private int getFileLength(File file) throws IOException {

		int fileLength = 0;

		InputStream ins = new FileInputStream(file);
		BufferedReader reader =  new BufferedReader(new InputStreamReader(ins));

		while (reader.readLine() != null) {
			fileLength++;
		}
		reader.close();

		return fileLength-1;

	}

	/**********************************************************************************/

}
