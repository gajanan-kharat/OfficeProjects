package com.inetpsa.poc00.application;

import java.io.File;
import java.io.IOException;

import org.seedstack.business.Service;

/**
 * The Class TvvMigrationService.
 */
@Service
public interface TvvMigrationService {

	/**
	 * Excel processor.
	 * 
	 * @param file the file
	 * @param tvvMigrationFileDirectory the tvv migration file directory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void tvvCSVProcessor(File file, String tvvMigrationFileDirectory) throws IOException;

}
