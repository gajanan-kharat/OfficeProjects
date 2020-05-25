package com.inetpsa.poc00.rest.regulationgroup;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedESDependentTCLRepresentation;
import com.inetpsa.poc00.rest.technicalgroup.TechnicalGroupRepresentation;

/**
 * The Interface RegulationGroupFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface RegulationGroupFinder {

	/**
	 * Gets the regulation group.
	 * 
	 * @param label the label
	 * @return the regulation group
	 */
	List<RegulationGroupRepresentation> getRegulationGroup(String label);

	/**
	 * Gets the max version for RG label.
	 * 
	 * @return the max version for RG label
	 */
	Double getMaxVersionForRGLabel(String label);

	/**
	 * Gets the selected technical groups for rg.
	 * 
	 * @param regulationGrpId the regulation grp id
	 * @return the selected technical groups for rg
	 */
	List<TechnicalGroupRepresentation> getSelectedTechnicalGroupsForRg(Long regulationGrpId);

	/**
	 * Gets the RG valued esd TC.
	 * 
	 * @param regulationGrpId the regulation grp id
	 * @return the RG valued esd TC
	 */
	List<RGValuedESDependentTCLRepresentation> getRGValuedEsdTC(Long regulationGrpId);

	/**
	 * Gets the regulation group for TG.
	 * 
	 * @param technicalGroupId the technical group id
	 * @return the regulation group for TG
	 */
	RegulationGroup getRegulationGroupForTG(long technicalGroupId);

	/**
	 * Find all regulation groups.
	 * 
	 * @return the list
	 */
	List<RegulationGroupRepresentation> findAllRegulationGroups();

	/**
	 * Gets the searched regulation group.
	 * 
	 * @param tvvLableList the tvv lable list
	 * @param searchLabel the search label
	 * @param sortAlphabetically the sort alphabetically
	 * @param sortByDate the sort by date
	 * @return the searched regulation group
	 */
	List<RegulationGroupRepresentation> getSearchedRegulationGroup(List<String> tvvLableList, String searchLabel, boolean sortAlphabetically, boolean sortByDate);

	/**
	 * Gets the searched regulation group.
	 * 
	 * @param searchLabel the search label
	 * @param searchRGRepresentation the search RG representation
	 * @return the searched regulation group
	 */
	List<RegulationGroupRepresentation> getSearchedRegulationGroup(String searchLabel, ManageSearchRGRequestDto searchRGRepresentation);

}
