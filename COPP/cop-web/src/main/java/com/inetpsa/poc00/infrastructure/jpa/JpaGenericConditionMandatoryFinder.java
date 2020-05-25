/*
 * Creation : Apr 29, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import com.inetpsa.poc00.rest.tvvdeptcl.GenericConditionMandatoryFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericConditionMandatoryRepresentation;

/**
 * The Class JpaGenericConditionMandatoryFinder.
 */
public class JpaGenericConditionMandatoryFinder implements GenericConditionMandatoryFinder {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.GenericConditionMandatoryFinder#getManadatoryListForCondition(long)
	 */
	@Override
	public List<GenericConditionMandatoryRepresentation> getManadatoryListForCondition(long conditionId) {
		List<GenericConditionMandatoryRepresentation> genmand = new ArrayList<GenericConditionMandatoryRepresentation>();
		// TypedQuery<GenericConditionMandatoryRepresentation> query = entityManager.createQuery("select new " +
		// GenericConditionMandatoryRepresentation.class.getName() + "(d.entityId,
		// d.value,d.statusNature.entityId,d.statusNature.status.label,d.statusNature.testNature.type) from
		// GenericTestConditionMandatory d where d.genericTestCondition.entityId= " + conditionId,
		// GenericConditionMandatoryRepresentation.class);
		// return query.getResultList();
		return genmand;
	}

}
