/*
 * Creation : Jun 21, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;
import com.inetpsa.poc00.rest.gnomedictionary.GenomeLCDVDictionaryFinder;

/**
 * The Class JpaGnomeLCDVDictionaryFinder.
 */
public class JpaGnomeLCDVDictionaryFinder implements GenomeLCDVDictionaryFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.gnomedictionary.GenomeLCDVDictionaryFinder#getDictionaryForTVV(java.lang.String)
	 */
	@Override
	public List<GenomeLCDVDictionary> getDictionaryForTVV(String tvvLabel) {
		TypedQuery<GenomeLCDVDictionary> query = entityManager.createQuery("select d from GenomeLCDVDictionary d join d.genomeLcdvCodeList lcdvcode join lcdvcode.genomeLcdvCodeValueList codeValue where lcdvcode.codeName ='T1A' and codeValue.z2Label like'" + tvvLabel + "'", GenomeLCDVDictionary.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.gnomedictionary.GenomeLCDVDictionaryFinder#getKmatDictionaryForTVV(java.lang.String)
	 */
	@Override
	public List<String> getKmatDictionaryForTVV(String tvvLabel) {
		TypedQuery<String> query = entityManager.createQuery("select distinct d.kmat from GenomeLCDVDictionary d join d.genomeLcdvCodeList lcdvcode join lcdvcode.genomeLcdvCodeValueList codeValue where lcdvcode.codeName ='T1A' and codeValue.frLabel = ?1 ", String.class);
		query.setParameter(1, tvvLabel);
		return query.getResultList();
	}

}
