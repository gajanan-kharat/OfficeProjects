/*
 * Creation : Jan 4, 2017
 */
/**
 * 
 */
package com.inetpsa.poc00.infrastructure.typeapprovalarea;

import java.util.List;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.traceability.TraceabilityService;
import com.inetpsa.poc00.application.typeapprovalarea.TypeApprovalAreaService;
import com.inetpsa.poc00.commonapp.ConstantsApp;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea;
import com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalAreaRepository;

/**
 * Implementation class for TypeApprovalAreaService.
 *
 * @author mehaj
 */

public class TypeApprovalAreaServiceImpl implements TypeApprovalAreaService {

	/** The type approval area repository. */
	@Inject
	TypeApprovalAreaRepository typeApprovalAreaRepository;

	/** The traceability service. */
	@Inject
	TraceabilityService traceabilityService;
	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(TypeApprovalAreaServiceImpl.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.typeapprovalarea.TypeApprovalAreaService#saveTypeApprovalArea(com.inetpsa.poc00.domain.typeapprovalarea.TypeApprovalArea)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public String saveTypeApprovalArea(TypeApprovalArea typeApprovalAreaObj) {

		List<TypeApprovalArea> typeApprovalAreaData = typeApprovalAreaRepository.getTypeApprovalAreaByLabel(typeApprovalAreaObj.getLabel());
		if (!typeApprovalAreaData.isEmpty()) {
			if (typeApprovalAreaData.get(0).getEntityId() == typeApprovalAreaObj.getEntityId()) {

				TypeApprovalArea oldTypeApprovalArea = typeApprovalAreaRepository.load(typeApprovalAreaObj.getEntityId());

				traceabilityService.historyForUpdate(oldTypeApprovalArea, typeApprovalAreaObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
				typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);

			} else {
				return typeApprovalAreaData.get(0).getLabel();
			}
		} else if (typeApprovalAreaObj.getEntityId() == null) {
			if (typeApprovalAreaObj.getLabel() != null && typeApprovalAreaObj.getLabel() != "") {
				// save
				TypeApprovalArea newTypeApprovalArea = typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);
				traceabilityService.historyForSave(newTypeApprovalArea, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			}
		} else {
			// update
			TypeApprovalArea oldTypeApprovalArea = typeApprovalAreaRepository.load(typeApprovalAreaObj.getEntityId());
			traceabilityService.historyForUpdate(oldTypeApprovalArea, typeApprovalAreaObj, ConstantsApp.SPECIFICCOP_SCREEN_ID);
			typeApprovalAreaRepository.saveTypeApprovalArea(typeApprovalAreaObj);

		}

		return ConstantsApp.SUCCESS;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.application.typeapprovalarea.TypeApprovalAreaService#deleteTypeApprovalArea(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public String deleteTypeApprovalArea(long typeApprovalAreaId) {

		TypeApprovalArea objToDelete = typeApprovalAreaRepository.load(typeApprovalAreaId);
		if (objToDelete.getRegulationGroup() == null || objToDelete.getRegulationGroup().isEmpty()) {
			long deletedrows = typeApprovalAreaRepository.deleteTypeApprovalArea(typeApprovalAreaId);
			if (deletedrows > 0) {
				logger.info("Sucessfully deleted TypeApprovalArea data");
				traceabilityService.historyForDelete(objToDelete, ConstantsApp.SPECIFICCOP_SCREEN_ID);
				return ConstantsApp.SUCCESS;
			}
			logger.error(" Error occured while deleting data  :", typeApprovalAreaId);
		}

		return ConstantsApp.FAILURE;

	}
}
