package com.inetpsa.poc00.rest.regulationgroupvaluedes;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;


/**
 * The Interface RGValuedTCLFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface RGValuedTCLFinder {
	
	/**
	 * Gets the RG valued test condition list.
	 *
	 * @param regulationGroupId the regulation group id
	 * @return the RG valued test condition list
	 */
	public List<RGValuedESDependentTCL> getRGValuedTestConditionList(long regulationGroupId);
}
