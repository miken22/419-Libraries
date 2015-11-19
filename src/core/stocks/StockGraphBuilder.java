package core.stocks;

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
				if (data[4].equals("")) {
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
