package com.inetpsa.poc00.domain.genomelcdvcodevalue;

import org.seedstack.business.domain.BaseFactory;



public class GenomeLCDVCodeValueFactoryDefault extends BaseFactory<GenomeLCDVCodeValue> implements GenomeLCDVCodeValueFactory {

	/**
	 * Factory method, create GenomeLCDVCodeValue Object.
	 * 
	 * 
	 * @return the GenomeLCDVCodeValue
	 */
	@Override
	public GenomeLCDVCodeValue createGenomeLCDVCodeValue()
	 {
		return new GenomeLCDVCodeValue();
	}


}
