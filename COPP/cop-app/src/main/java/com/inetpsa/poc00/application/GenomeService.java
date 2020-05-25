package com.inetpsa.poc00.application;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.seedstack.business.Service;

/**
 * GenomeService, Genome Batch Service.
 */

@Service
public interface GenomeService {
	
	 /**
		 * FileProcessor for all Genome Batch files
		 * 
		 * @param file : Map of Files to process for Genome Batch
		 * @throws IOException
		 * @return void
		 */
		public void fileProcessor(Map<String,File> file) throws IOException;

}
