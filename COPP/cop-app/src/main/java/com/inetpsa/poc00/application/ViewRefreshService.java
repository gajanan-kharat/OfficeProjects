package com.inetpsa.poc00.application;

import java.io.IOException;

import org.seedstack.business.Service;

/**
 * The Interface ViewRefreshService.
 */
@Service
public interface ViewRefreshService {

	/**
	 * FileProcessor for all Genome Batch files.
	 * 
	 * @param file : Map of Files to process for Genome Batch
	 * @return void
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void refreshPerfView() ;
}
