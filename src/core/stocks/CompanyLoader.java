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
public class CompanyLoader {


	/**
	 * Loads a company's closing stock price into a Company object
	 * that can be used for creating visibility graphs as well as
	 * running other metrics on the company's data. The date/price
	 * history is the time series that will be analyzed for the project
	 *
	 * @param company The company stock symbol to load the time series for.
	 * @return A Company object that can be used to create a visibility graph.
	 *
	 * @throws IOException
	 */
	public Company loadCompany(String company) throws IOException {

		File directory = new File("Results");
		File file = new File(directory, company+".txt");

		InputStream ins = new FileInputStream(file);
		BufferedReader reader =  new BufferedReader(new InputStreamReader(ins));
		//First line is headers, skip to the next line.
		reader.readLine();
		String line = reader.readLine();

		int fileLength = getFileLength(file);

		Company comp = new Company(company, fileLength);

		while (line != null) {
			String[] data = line.split(",");
			if (data[4].equals("")) {
				break;
			}
			comp.addData(Double.valueOf(data[4]));
			line = reader.readLine();
		}
		reader.close();

		comp.computeAveragePrice();
		comp.computeVariance();
		comp.setCompanyName(company);

		return comp;

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
