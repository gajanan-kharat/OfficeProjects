package com.inetpsa.poc00.rest.technicalgroup;

import java.util.List;

import javax.transaction.Transactional;

import org.seedstack.business.finder.Finder;
import org.seedstack.jpa.JpaUnit;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;

/**
 * The Interface TechnicalGroupFinder.
 */
@Finder
@Transactional
@JpaUnit(Config.JPA_UNIT)
public interface TechnicalGroupFinder {

	/**
	 * Gets the technical group.
	 * 
	 * @param label the label
	 * @return the technical group
	 */
	List<TechnicalGroupRepresentation> getTechnicalGroup(String label);

	/**
	 * Gets the tvvs for technical group.
	 * 
	 * @param TechnicalGroupId the technical group id
	 * @return the tvvs for technical group
	 */
	List<TvvRepresentation> getTvvsForTechnicalGroup(long TechnicalGroupId);

	/**
	 * Gets the technical group to delete.
	 * 
	 * @param regulationGroupId the regulation group id
	 * @return the technical group to delete
	 */
	List<TechnicalGroup> getTechnicalGroupToDelete(long regulationGroupId);

	/**
	 * Gets the technical group for technical case.
	 * 
	 * @param entityId the entity id
	 * @return the technical group for technical case
	 */
	TechnicalGroup getTechnicalGroupForTechnicalCase(Long entityId);

	/**
	 * Gets the searched technical group.
	 * 
	 * @param searchRepresentation the search representation
	 * @param searchLabel the search label
	 * @param searchTgRepresentation the search tg representation
	 * @return the searched technical group
	 */
	List<TechnicalGroupRepresentation> getSearchedTechnicalGroup(TvvSearchRepresentation searchRepresentation, String searchLabel, ManageSearchTGRequestDto searchTgRepresentation);

	/**
	 * Gets the regulation group.
	 * 
	 * @param technicalGroupId the technical group id
	 * @return the regulation group
	 */
	List<RegulationGroup> getRegulationGroup(long technicalGroupId);

	/**
	 * Find technical group by id.
	 * 
	 * @param id the id
	 * @return the technical group representation
	 */
	TechnicalGroupRepresentation findTechnicalGroupById(long id);

	Double getMaxVersionForTGLabel(String tgLabel);
}
