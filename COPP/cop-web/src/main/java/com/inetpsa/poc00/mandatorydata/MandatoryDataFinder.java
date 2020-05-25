package com.inetpsa.poc00.mandatorydata;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.rest.emissionstandard.EmissionStandardRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.TvvStructureRepresentation;

/**
 * The Interface MandatoryDataFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface MandatoryDataFinder {

	/**
	 * Gets the ES test status comb list.
	 * 
	 * @param emissionStandard the emission standard
	 * @return the ES test status comb list
	 */
	List<MandatoryDataRepresentation> getESTestStatusCombList(EmissionStandardRepresentation emissionStandard);

	/**
	 * Gets the status nature id.
	 * 
	 * @param entityId the entity id
	 * @param entityId2 the entity id2
	 * @return the status nature id
	 */
	Long getStatusNatureId(Long entityId, Long entityId2);

	/**
	 * Gets the tvv test status comb list.
	 * 
	 * @param tvvStructure the tvv structure
	 * @return the tvv test status comb list
	 */
	List<MandatoryDataRepresentation> getTvvTestStatusCombList(TvvStructureRepresentation tvvStructure);

}
