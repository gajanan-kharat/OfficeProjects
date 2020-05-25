package com.inetpsa.poc00.rest.finalreduction;

import java.util.List;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;


/**
 * The Interface FinalReductionFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface FinalReductionFinder {

	/**
	 * Find all final reduction data.
	 * 
	 * @return the list
	 */
	List<FinalReductionRepresentation> findAllFinalReductionData();

	/**
	 * Find final reduction ratio data by label.
	 * 
	 * @param finalReductionlabel the final reductionlabel
	 * @return the list
	 */
	List<FinalReductionRepresentation> findFinalReductionRatioDataByLabel(String finalReductionlabel);

	/**
	 * Final reductionfrom kmat.
	 * 
	 * @param kmat the kmat
	 * @return the list
	 */

	List<FinalReductionRepresentation> getAllFinalReductionForTvv(List<String> kmat);

	/**
	 * Gets the all final reduction data.
	 * 
	 * @return the all final reduction data
	 */
	List<FinalReductionRepresentation> getAllFinalReductionData();
}
