package core.stocks;

import core.components.Edge;
import core.components.Vertex;
import core.visualizer.VisibilityGraphViewer;
import edu.uci.ics.jung.graph.Graph;
import generators.visibility.VisibilityGraph;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

/**
 * Main program class
 *
 * Created by mike on 19/11/15.
 */
public class Main {

    /**
     * Main program entry point, creates the visibility graph for a specified stock.
     * @param args Program arguments (none needed)
     */
    public static void main(String[] args) {

        CompanyLoader loader = new CompanyLoader();
        VisibilityGraph visGraph = new VisibilityGraph();

        // Use this to get the stock information. It won't crawl if it has
        // been run in the last 24 hours.
//		Crawler crawler = new Crawler();
//		crawler.crawl();

        System.out.println("Enter a company's TSX symbol to view its visibility graph.\n" +
                           "Enter 'h' to view the company list.");

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!Arrays.asList(TSXCompanies.COMPANIES).contains(input)) {

            if (input.toLowerCase().equals("h")) {
                for (String comp : TSXCompanies.COMPANIES) {
                    System.out.println(comp);
                }
            }

            System.out.println("Enter a company's TSX symbol to view its visibility graph.\n" +
                               "Enter 'h' to view the company list.");

            input = scanner.nextLine();
        }

        System.out.print("Enter the number of days you would like the graph to visualize: ");
        int numberOfDays = scanner.nextInt();

        scanner.close();

        try {

            Collection<Company> companies = loader.loadCompanies();
            Company comp = getCompany(companies, input);
            Graph<Vertex, Edge> visibilityGraph = visGraph.createGraph(comp, numberOfDays);
            VisibilityGraphViewer.viewGraph(visibilityGraph);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /********************
     * Helper method    *
     ********************/

    /**
     * Given a collection of vertices find the specified vertex.
     * @param vertices The collection of vertices to search in.
     * @param id The String identifier of the vertex.
     * @return The specified vertex, or null if it does not exist.
     */
    private static Company getCompany(Collection<Company> vertices, String id) {
        for (Company vertex : vertices) {
            if (vertex.getId().equals(id)) {
                return vertex;
            }
        }
        return null;
    }

}
