package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCode;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryAssembler;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryDto;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryFactory;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository;

/**
 * Repository Class for GenomeLCDVDictionary Entity.
 */
@DataSet(group = Config.JPA_UNIT, name = "GenomeLCDVDictionary")
public class GenomeLCDVDictionaryJpaRepository extends BaseJpaRepository<GenomeLCDVDictionary, Long> implements GenomeLCDVDictionaryRepository {

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(GenomeLCDVDictionaryJpaRepository.class);

	/** The dic assembler. */
	@Inject
	private GenomeLCDVDictionaryAssembler dicAssembler;

	/** The dic factory. */
	@Inject
	private GenomeLCDVDictionaryFactory dicFactory;

	/**
	 * Save GenomeLCDVDictionary .
	 * 
	 * @param lcdvDic the lcdv dic
	 * @return GenomeLCDVDictionary which is saved
	 */

	@Override
	public GenomeLCDVDictionary saveGenomeLCDVDictionary(GenomeLCDVDictionary lcdvDic) {

		GenomeLCDVDictionary obj = super.save(lcdvDic);

		logger.debug("Object Saved with Id : {} ", obj.getEntityId());

		return obj;
	}

	/**
	 * Save List of GenomeLCDVDictionaryDto .
	 * 
	 * @param lcdvDicDtoList the Object to save in database
	 * @param fileName - File Name for data is being saved
	 * 
	 * @return void
	 * 
	 *         This Method is being used for saving Family Dictionary File data for Genome Batch
	 */
	@Override
	public void saveGenomeLCDVDictionary(List<GenomeLCDVDictionaryDto> lcdvDicDtoList, String fileName) {

		for (GenomeLCDVDictionaryDto dicObject : lcdvDicDtoList) {

			logger.info("Saving Family Dictionary File Data : {}", dicObject != null ? dicObject.getDictionaryValue() : null);

			saveorUpdateGenomeLCDVDictionary(dicObject, fileName);
		}
	}

	/**
	 * Save List of GenomeLCDVDictionary Object.
	 * 
	 * @param lcdvDicList the lcdv dic list
	 * @return void.
	 * 
	 *         This Method is being used for KMAT FAM Dictionary File data for Genome Batch
	 */

	@Override
	public void saveOrUpdateGenomeDictionary(List<GenomeLCDVDictionary> lcdvDicList) {

		for (GenomeLCDVDictionary gdy : lcdvDicList) {

			GenomeLCDVDictionary dictionaryObj = getDictionaryByKmat(entityManager, gdy.getKmat());

			if (null == dictionaryObj) {

				logger.info("Saving Dictionary Object, KMAT Value : {}", gdy.getKmat());

				saveGenomeLCDVDictionary(gdy);
			} else {

				dictionaryObj.setGenomeLcdvCodeList(gdy.getGenomeLcdvCodeList());
				dictionaryObj.setFrLabel(gdy.getFrLabel());
				dictionaryObj.setDictionaryValue(gdy.getDictionaryValue());

				Long i = entityManager.merge(dictionaryObj).getEntityId();

				logger.info("Updating Dictionary Object, Id : {} ", i);
			}

		}

	}

	/**
	 * Fetch GenomeLCDVDictionary Object from DataBase.
	 * 
	 * @param em - EntityManager
	 * @param kmat - KMAT Value to search for
	 * @return GenomeLCDVDictionary.
	 * 
	 *         This Method is being used for KMAT FAM Dictionary File data for Genome Batch
	 */

	public GenomeLCDVDictionary getDictionaryByKmat(EntityManager em, String kmat) {

		TypedQuery<GenomeLCDVDictionary> query = em.createQuery("SELECT gdy FROM GenomeLCDVDictionary gdy WHERE gdy.kmat = ?1", GenomeLCDVDictionary.class);

		query.setParameter(1, kmat);

		List<GenomeLCDVDictionary> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			return results.get(0);
		} else {
			return null;
		}

	}

	/**
	 * Saves or Update Genome Dictionary Object.
	 * 
	 * @param dicObject - GenomeLCDVDictionaryDto Object
	 * @param fileNameToSave - File Name for which data to save
	 * @return void.
	 * 
	 *         This Method is being used for KMAT FAM Dictionary File data for Genome Batch
	 */

	@Override
	public void saveorUpdateGenomeLCDVDictionary(GenomeLCDVDictionaryDto dicObject, String fileNameToSave) { // GDF

		GenomeLCDVDictionary persistedDictionaryObj;

		if ("FAMILY_DICTIONARY.dat".equalsIgnoreCase(fileNameToSave)) {
			persistedDictionaryObj = getGeneralDictionaryByDicValue(dicObject.getDictionaryValue(), fileNameToSave);

			if (persistedDictionaryObj != null) {
				dicAssembler.doMergeAggregateWithDto(persistedDictionaryObj, dicObject);
				entityManager.merge(persistedDictionaryObj).getEntityId();
			} else {
				logger.info("Dictionary Not Found : {}", dicObject.getDictionaryValue());
			}

		} else {
			// The below code is for General Dictionary File
			persistedDictionaryObj = getGeneralDictionaryByDicValue(dicObject.getDictionaryValue());

			if (persistedDictionaryObj == null) {

				GenomeLCDVDictionary newDicObject = dicFactory.createGenomeLCDVDictionary();

				dicAssembler.doMergeAggregateWithDto(newDicObject, dicObject);

				super.save(newDicObject);

			} else {

				dicAssembler.doMergeAggregateWithDto(persistedDictionaryObj, dicObject);

				entityManager.merge(persistedDictionaryObj).getEntityId();

			}

		}

	}

	/**
	 * Load GenomeLCDVDictionary Object as per the Dictionary Value Provided. Specifically used while saving General
	 * Dictionary File
	 * 
	 * @param lcdvDictionaryValue the lcdv dictionary value
	 * @return GenomeLCDVDictionary.
	 */
	public GenomeLCDVDictionary getGeneralDictionaryByDicValue(String lcdvDictionaryValue) {

		TypedQuery<GenomeLCDVDictionary> queryforDic = entityManager.createQuery("SELECT gdy FROM GenomeLCDVDictionary gdy WHERE gdy.dictionaryValue = :dictionaryValueArg", GenomeLCDVDictionary.class);
		queryforDic.setParameter("dictionaryValueArg", lcdvDictionaryValue);

		List<GenomeLCDVDictionary> lcdvDictionaryList = queryforDic.getResultList();

		if (lcdvDictionaryList != null && !lcdvDictionaryList.isEmpty()) {

			List<GenomeLCDVCode> persistedCodeList = new ArrayList<GenomeLCDVCode>(lcdvDictionaryList.get(0).getGenomeLcdvCodeList());

			for (GenomeLCDVCode cv : persistedCodeList) {

				TypedQuery<GenomeLCDVCodeValue> queryForCdVal = entityManager.createQuery("SELECT codeValue FROM GenomeLCDVCodeValue codeValue WHERE codeValue.genomeLcdvCode.entityId =  ?1 ", GenomeLCDVCodeValue.class);
				queryForCdVal.setParameter(1, cv.getEntityId());

				List<GenomeLCDVCodeValue> lcdvCodeValueList = queryForCdVal.getResultList();

				cv.setGenomeLcdvCodeValueList(lcdvCodeValueList);

			}
		}

		if (lcdvDictionaryList != null && !lcdvDictionaryList.isEmpty())
			return lcdvDictionaryList.get(0);
		return null;
	}

	/**
	 * Load GenomeLCDVDictionary Object as per the Dictionary Value Provided. Specifically used while saving Family
	 * Dictionary File
	 * 
	 * @param lcdvDictionaryValue the lcdv dictionary value
	 * @param fileName the file name
	 * @return GenomeLCDVDictionary.
	 * 
	 *         This is overloaded method of getGeneralDictionaryByDicValue(String)
	 */
	public GenomeLCDVDictionary getGeneralDictionaryByDicValue(String lcdvDictionaryValue, String fileName) {

		/**
		 * Fetching GenomeLCDVDictionary Object associated with lcdvDictionaryValue
		 */
		TypedQuery<GenomeLCDVDictionary> queryforDic = entityManager.createQuery("SELECT gdy FROM GenomeLCDVDictionary gdy WHERE gdy.dictionaryValue = :dictionaryValueArg", GenomeLCDVDictionary.class);
		queryforDic.setParameter("dictionaryValueArg", lcdvDictionaryValue);

		List<GenomeLCDVDictionary> lcdvDictionayList = queryforDic.getResultList();

		GenomeLCDVDictionary dicObject = null;

		if (lcdvDictionayList != null && !lcdvDictionayList.isEmpty()) {

			dicObject = lcdvDictionayList.get(0);

			/**
			 * Fetching GenomeLCDVCode Object associated with dicObject
			 */
			TypedQuery<GenomeLCDVCode> queryforCode = entityManager.createQuery("SELECT lcdvcode FROM GenomeLCDVCode lcdvcode WHERE lcdvcode.genomeLCDVDictionary.entityId =  ?1 ", GenomeLCDVCode.class);
			queryforCode.setParameter(1, dicObject.getEntityId());

			List<GenomeLCDVCode> lcdvCodeList = queryforCode.getResultList();

			if (lcdvCodeList != null && !lcdvCodeList.isEmpty()) {

				for (GenomeLCDVCode cv : lcdvCodeList) {

					/**
					 * Fetching GenomeLCDVCodeValue Object associated with cv
					 */
					TypedQuery<GenomeLCDVCodeValue> queryForValue = entityManager.createQuery("SELECT codeValue FROM GenomeLCDVCodeValue codeValue WHERE codeValue.genomeLcdvCode.entityId =  ?1 ", GenomeLCDVCodeValue.class);
					queryForValue.setParameter(1, cv.getEntityId());

					List<GenomeLCDVCodeValue> lcdvCodeValueList = queryForValue.getResultList();

					cv.setGenomeLcdvCodeValueList(lcdvCodeValueList);

				}
			}

			dicObject.setGenomeLcdvCodeList(lcdvCodeList);

		}

		return dicObject;

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository#persistGenomeLCDVDictionary(com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary)
	 */
	@Override
	public void persistGenomeLCDVDictionary(GenomeLCDVDictionary object) {
		logger.info("Persist {}", GenomeLCDVDictionary.class);
		super.persist(object);
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {
		return entityManager.createQuery("DELETE FROM GenomeLCDVDictionary").executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository#count()
	 */
	@Override
	public long count() {
		Query query = entityManager.createQuery("select count(*) from GenomeLCDVDictionary");
		Long count = (Long) query.getSingleResult();
		return count;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository#executeRefreshProcedure()
	 */
	@Override
	public void executeRefreshProcedure() {
		logger.info("Start : Executing Refresh Procedure");

		Query query = entityManager.createNativeQuery("CALL COPQPREF()");

		query.executeUpdate();

		logger.info("Complete : Executing Refresh Procedure");
	}

}
