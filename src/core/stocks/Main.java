package core.stocks;

import algorithms.clustering.Clustering;
import algorithms.connectivity.Triangles;
import algorithms.distribution.DegreeDist;
import core.components.Edge;
import core.components.Vertex;
import core.tools.Tools;
import core.webcrawler.Crawler;
import edu.uci.ics.jung.graph.Graph;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Main program class
 *
 * Created by mike on 19/11/15.
 */
public class Main {

    private final static String[] metrics = {"-tc", "-cc", "-dd", "-dexp"};

    /**
     * Main program entry point, creates the visibility graph for a specified stock.
     * @param args Program arguments (none needed)
     */
    public static void main(String[] args) {

        // Use this to get the stock information. It won't crawl if it has
        // been run in the last 24 hours.
		Crawler crawler = new Crawler();
		crawler.crawl();

        CompanyLoader loader = new CompanyLoader();
        VisibilityGraph visGraph = new VisibilityGraph();

        Scanner scanner = new Scanner(System.in);
        String input;
        boolean exit = false;

        // Allow the user to generate multiple graphs if desired
        while (!exit) {

            System.out.println("Enter a company's TSX symbol to view its visibility graph.\n" +
                    "Enter 'h' to view the company list.");

            input = scanner.nextLine();
            input = input.toUpperCase();

            // Input validation
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

            // Users choice of how long a time series to analyze.
            System.out.print("Enter the number of days you would like the graph to visualize: ");
            int numberOfDays = scanner.nextInt();

            Company comp = null;

            try {
                comp = loader.loadCompany(input);
            } catch (IOException e) {
                System.out.println("Error loading company's data, verify the Results folder is in the root directory.");
                System.exit(-1);
            }

            // Create and view the graph
            Graph<Vertex, Edge> visibilityGraph = visGraph.createGraph(comp, numberOfDays);
            VisibilityGraphViewer.viewGraph(visibilityGraph);

            System.out.println("Would you like to run any metrics on the network? Type -h for a list " +
                               "of metrics that can be run on the network. Enter '-x' to exit.");
            input = scanner.nextLine();

            if (input.equals("-h")) {

                System.out.println("Possible algorithms and metrics, and the argument to provide:");
                System.out.println("Clustering Coefficient : -cc");
                System.out.println("Degree Distribution : -dd");
                System.out.println("Approximating Degree Distribution exponent : -dexp");
                System.out.println("Triangle Counting by Node Iteration : -tc");

                System.out.println("Which metric would you like to run?");

            }

            input = scanner.nextLine();

            while (!Arrays.asList(metrics).contains(input) && !input.equals("-x")) {
                System.out.println("Please enter a valid argument for the metric, of -x to skip this.");
                input = scanner.nextLine();
            }

            switch (input) {
                case "-cc":

                    System.out.println("Which day number do you want to compute the coefficient for:");
                    int number = scanner.nextInt();
                    Vertex v = Tools.getVertex(visibilityGraph, "" + number);

                    Double coeff = Clustering.coefficient(visibilityGraph, v);
                    System.out.println("The clustering coefficient for that vertex is: " + coeff);
                    Double avg = Clustering.average(visibilityGraph);
                    System.out.println("The average clustering coefficient for the network is: " + avg);

                    break;
                case "-dd":

                    System.out.println("What degree would you like to compute the Degree Distribution for?");
                    int degree = scanner.nextInt();

                    double dist = DegreeDist.degreeDist(visibilityGraph, degree);
                    System.out.println("The degree distribution for degree " + degree + " is: " + dist);

                    break;
                case "-dexp":

                    double approx = DegreeDist.aprroxExponent(visibilityGraph);
                    System.out.println("The approximation of the exponent is: " + approx);

                    break;
                case "-tc":

                    int triangles = Triangles.count(visibilityGraph);
                    System.out.println("There are "  + triangles + " in the visibility graph.");

                    break;
            }

            // Option to continue, pops up even though the window is running in the background because
            // java gui's are fun.
            System.out.println("Would you like to view the visibility graph of another company? (Y/N)");
            input = scanner.nextLine();

            while (!input.toLowerCase().equals("n") && !input.toLowerCase().equals("y")) {
                System.out.println("Please enter Y or N.");
                input = scanner.nextLine();
            }

            if (input.toLowerCase().equals("n")) {
                exit = true;
            }

            VisibilityGraphViewer.disposeFrame();

        }
        scanner.close();
    }

}
