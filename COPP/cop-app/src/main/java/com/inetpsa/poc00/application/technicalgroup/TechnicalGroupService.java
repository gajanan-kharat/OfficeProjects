/*
 * Creation : Nov 8, 2016
 */
package com.inetpsa.poc00.application.technicalgroup;

import java.util.Date;
import java.util.List;

import org.seedstack.business.Service;

import com.inetpsa.poc00.domain.regulationgroup.RegulationGroup;
import com.inetpsa.poc00.domain.status.Status;
import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.technicalgroup.TechnicalGroup;

/**
 * The Interface TechnicalGroupService.
 */
@Service
public interface TechnicalGroupService {

	/**
	 * Save tech group.
	 * 
	 * @param technicalGroup the technical group
	 * @return the technical group
	 */
	public TechnicalGroup saveTechGroup(TechnicalGroup technicalGroup);

	/**
	 * Save edited technical group.
	 * 
	 * @param technicalGroup the technical group
	 * @param status the status
	 * @param newVersion the new version
	 * @param samplingLabel the sampling label
	 * @param creattionDate the creattion date
	 * @param regulationGroup the regulation group
	 * @return the technical group
	 */
	public TechnicalGroup saveEditedTechnicalGroup(TechnicalGroup technicalGroup, Status status, boolean newVersion, String samplingLabel, Date creattionDate, RegulationGroup regulationGroup);

	/**
	 * Load technical group.
	 * 
	 * @param technicalGroupId the technical group id
	 * @return the technical group
	 */
	public TechnicalGroup loadTechnicalGroup(long technicalGroupId);

	/**
	 * Delete technical group.
	 * 
	 * @param TechnicalCase the technical case
	 * @param technicalGroupId the technical group id
	 */
	public void deleteTechnicalGroup(List<TechnicalCase> TechnicalCase, long technicalGroupId);

	/**
	 * Delete tvv from tg.
	 * 
	 * @param tvvEntityId the tvv entity id
	 */
	public void deleteTvvFromTG(long tvvEntityId);

	/**
	 * Gets the versioned technical group.
	 * 
	 * @param technicalGroup the technical group
	 * @param samplingLabel the sampling label
	 * @param creattionDate the creattion date
	 * @return the versioned technical group
	 */
	public TechnicalGroup getVersionedTechnicalGroup(TechnicalGroup technicalGroup, String samplingLabel, Date creattionDate);

	/**
	 * Checks if is technical group exist.
	 * 
	 * @param TechGroupLabel the tech group label
	 * @return true, if is technical group exist
	 */
	public boolean isTechnicalGroupExist(String TechGroupLabel);

	/**
	 * Gets the status for technical group.
	 * 
	 * @return the status for technical group
	 */
	public List<Status> getStatusForTechnicalGroup();

	/**
	 * Save technical case.
	 * 
	 * @param entityId the entity id
	 * @param technicalGroup the technical group
	 * @param isWorstCase the is worst case
	 * @return the technical case
	 */
	public TechnicalCase saveTechnicalCase(long entityId, TechnicalGroup technicalGroup, boolean isWorstCase);

}
