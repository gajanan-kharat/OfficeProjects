package com.inetpsa.poc00.domain.genomelcdvdictionary;

import org.seedstack.business.domain.GenericFactory;

/**
 * Category Factory interface.
 */
public interface GenomeLCDVDictionaryFactory extends GenericFactory<GenomeLCDVDictionary> {

	/**
	 * Factory create method.
	 * 
	 * @param name the dictionary name
	 * @return the dictionary
	 */
	GenomeLCDVDictionary createGenomeLCDVDictionary();

	/**
	 * Factory create method.
	 * 
	 * @param name EntityId, LcdvDicFrLabel, DictionaryValue, Kmat
	 * @return the dictionary
	 */

}
