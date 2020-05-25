package com.inetpsa.poc00.domain.genomelcdvcode;

import org.seedstack.business.domain.GenericFactory;

public interface GenomeLCDVCodeFactory extends GenericFactory<GenomeLCDVCode> {

	 /**
     * Factory create method.
     *
     * @param name 
     * @return the GenomeLCDVCode
     */
	GenomeLCDVCode createGenomeLCDVCode();
}
