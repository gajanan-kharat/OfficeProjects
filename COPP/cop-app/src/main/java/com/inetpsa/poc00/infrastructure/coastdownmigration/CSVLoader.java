package com.inetpsa.poc00.infrastructure.coastdownmigration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

import com.inetpsa.poc00.domain.coastdown.CoastDownMigrationDto;

/**
 * The Class CSVLoader.
 */
public class CSVLoader {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(CSVLoader.class);

	/** The seprator. */
	private char seprator;

	/**
	 * Instantiates a new CSV loader.
	 */
	public CSVLoader() {
		this.seprator = ';';
	}

	/**
	 * Load csv.
	 * 
	 * @param csvFile the csv file
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<CoastDownMigrationDto> loadCSV(File csvFile) throws IOException {

		List<CoastDownMigrationDto> coastDownDtoList = null;
		String[] nextLine;

		try (FileReader fileReader = new FileReader(csvFile); CSVReader csvReader = new CSVReader(fileReader, this.seprator)) {

			String[] headerRow = csvReader.readNext();

			headerRow = (String[]) ArrayUtils.remove(headerRow, headerRow.length - 1);

			if (null == headerRow) {
				logger.error("HEADER IS NOT PRESENT IN THE EXCEL FILE");
			}

			coastDownDtoList = new ArrayList<>();

			int lineCounter = 0;

			while ((nextLine = csvReader.readNext()) != null) {

				lineCounter++;

				nextLine = (String[]) ArrayUtils.remove(nextLine, nextLine.length - 1);

				CoastDownMigrationDto coastDown = null;

				coastDown = prepareCoastDownObj(nextLine, lineCounter);

				if (coastDown != null) {
					coastDownDtoList.add(coastDown);
				}

			}
		} catch (FileNotFoundException fnf) {
			logger.error("Error in CSVLoader file, Method : loadCSV", fnf);
		} catch (IOException ioe) {
			logger.error("Error in CSVLoader file, Method : loadCSV", ioe);
		}

		return coastDownDtoList;

	}

	/**
	 * Prepare coast down obj.
	 * 
	 * @param nextLine the next line
	 * @param lineCounter the line counter
	 * @return the coast down migration dto
	 */
	private CoastDownMigrationDto prepareCoastDownObj(String[] nextLine, int lineCounter) {

		CoastDownMigrationDto coastDown = null;

		try {

			if (nextLine[0].isEmpty() || nextLine[2].isEmpty() || nextLine[9].isEmpty()) {
				logger.error("***** LINE IGNORED AS PSA REFERENCE OR INERTIA OR VERSION IS EMPTY, LINE NUMBER : {}", lineCounter);
			}

			coastDown = new CoastDownMigrationDto(nextLine[0], nextLine[1], (int) Math.floor(Double.parseDouble(nextLine[2])), NumberFormat.getNumberInstance(Locale.FRANCE).parse(nextLine[3]).doubleValue(), NumberFormat.getNumberInstance(Locale.FRANCE).parse(nextLine[4]).doubleValue(), NumberFormat.getNumberInstance(Locale.FRANCE).parse(nextLine[5]).doubleValue(), NumberFormat
					.getNumberInstance(Locale.FRANCE).parse(nextLine[6]).doubleValue(), NumberFormat.getNumberInstance(Locale.FRANCE).parse(nextLine[7]).doubleValue(), NumberFormat.getNumberInstance(Locale.FRANCE).parse(nextLine[8]).doubleValue(), nextLine[9]);

		} catch (NumberFormatException | ParseException e) {
			logger.error("LINE IGNORED : {}", lineCounter, e);
		}

		return coastDown;
	}

	/**
	 * Gets the seprator.
	 * 
	 * @return the seprator
	 */
	public char getSeprator() {
		return seprator;
	}

	/**
	 * Sets the seprator.
	 * 
	 * @param seprator the new seprator
	 */
	public void setSeprator(char seprator) {
		this.seprator = seprator;
	}

}
