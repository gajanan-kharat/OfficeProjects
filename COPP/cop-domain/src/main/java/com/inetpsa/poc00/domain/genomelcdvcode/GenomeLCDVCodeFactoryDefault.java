package com.inetpsa.poc00.domain.genomelcdvcode;

import org.seedstack.business.domain.BaseFactory;


public class GenomeLCDVCodeFactoryDefault extends BaseFactory<GenomeLCDVCode> implements GenomeLCDVCodeFactory {

	/**
	 * Factory method, create GenomeLCDVCode Object.
	 * 
	 * 
	 * @return the GenomeLCDVCode
	 */
	@Override
	public GenomeLCDVCode createGenomeLCDVCode()
	{
		return new GenomeLCDVCode();
	}
}
