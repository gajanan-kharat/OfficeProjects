package com.inetpsa.poc00.rest.technicalcase;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;

/**
 * The Interface TechnicalCaseFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TechnicalCaseFinder {

	/**
	 * Gets the technical case.
	 * 
	 * @param tvvId the tvv id
	 * @return the technical case
	 */
	List<TechnicalCase> getTechnicalCase(long tvvId);

	/**
	 * Gets the t vv.
	 * 
	 * @param technicalGroupId the technical group id
	 * @return the t vv
	 */
	List<TVV> getTVv(long technicalGroupId);

	/**
	 * Gets the technical caseto delete.
	 * 
	 * @param technicalGroupId the technical group id
	 * @return the technical caseto delete
	 */
	List<TechnicalCase> getTechnicalCasetoDelete(long technicalGroupId);

	/**
	 * Gets the technical cases for TG.
	 * 
	 * @param entityId the entity id
	 * @return the technical cases for TG
	 */
	List<TechnicalCase> getTechnicalCasesForTG(Long entityId);

}
