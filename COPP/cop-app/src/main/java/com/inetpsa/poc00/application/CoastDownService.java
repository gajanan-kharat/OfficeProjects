package com.inetpsa.poc00.application;

import java.io.File;
import java.io.IOException;

import org.seedstack.business.Service;

/**
 * The Interface CoastDownService.
 */
@Service
public interface CoastDownService {

	/**
	 * Excel processor.
	 *
	 * @param file the file
	 * @param coastDownFileDirectory 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void csvProcessor(File file, String coastDownFileDirectory) throws IOException;

}
