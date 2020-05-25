package com.inetpsa.poc00.domain.genomelcdvdictionary;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * Repository interface of dictionary.
 */
public interface GenomeLCDVDictionaryRepository extends GenericRepository<GenomeLCDVDictionary, Long> {

	/**
	 * Saves the GenomeLCDVDictionary.
	 * 
	 * @param dictionary the dictionary to save
	 * @return the dictionary
	 */
	GenomeLCDVDictionary saveGenomeLCDVDictionary(GenomeLCDVDictionary dictionary);

	/**
	 * Saves the List of GenomeLCDVDictionaryDto.
	 * 
	 * @param lcdvDicDto the GenomeLCDVDictionaryDto to save
	 * @param FileName the file name
	 * @return void
	 */

	void saveGenomeLCDVDictionary(List<GenomeLCDVDictionaryDto> lcdvDicDto, String fileName);

	/**
	 * Saves the GenomeLCDVDictionary For General Dictionary File .
	 * 
	 * @param lcdvDicDto the GenomeLCDVDictionaryDto to save
	 * @param FileNameToSave the file name to save
	 * @return void
	 */

	void saveorUpdateGenomeLCDVDictionary(GenomeLCDVDictionaryDto lcdvDicDto, String fileNameToSave);

	/**
	 * Persists the dictionary.
	 * 
	 * @param dictionary the dictionary to persist
	 */
	void persistGenomeLCDVDictionary(GenomeLCDVDictionary dictionary);

	/**
	 * Delete all categories.
	 * 
	 * @return number of categories deleted
	 */
	long deleteAll();

	/**
	 * Count the number of categories in the repository.
	 * 
	 * @return dictionary count
	 */
	long count();

	/**
	 * Saves the Set of GenomeLCDVDictionary.
	 * 
	 * @param entity the entity
	 * @return void
	 */

	void saveOrUpdateGenomeDictionary(List<GenomeLCDVDictionary> entity);

	/**
	 * Execute refresh procedure.
	 */
	void executeRefreshProcedure();

}
