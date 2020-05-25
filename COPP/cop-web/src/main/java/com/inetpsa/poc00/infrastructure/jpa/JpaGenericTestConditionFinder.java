/*
 * Creation : Apr 1, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.generictc.GenericTestCondition;
import com.inetpsa.poc00.mandatorydata.MandatoryDataRepresentation;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder;
import com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionRepresentation;

/**
 * The Class JpaGenericTestConditionFinder.
 */
public class JpaGenericTestConditionFinder implements GenericTestConditionFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder#getAllConditionsForList(long)
	 */
	@Override
	public List<GenericTestConditionRepresentation> getAllConditionsForList(long entityId) {
		TypedQuery<GenericTestConditionRepresentation> query = entityManager.createQuery("select new " + GenericTestConditionRepresentation.class.getName() + "(c.entityId, c.dataType,c.defaultValue,c.label,c.unit.entityId,c.unit.value)" + " from GenericTestCondition c where c.tvvDepTCL.entityId= " + entityId, GenericTestConditionRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder#getAllConditionsForEMSDepList(long)
	 */
	@Override
	public List<GenericTestConditionRepresentation> getAllConditionsForEMSDepList(long entityId) {
		TypedQuery<GenericTestConditionRepresentation> query = entityManager.createQuery("select new " + GenericTestConditionRepresentation.class.getName() + "(c.entityId, c.dataType,c.defaultValue,c.label,c.unit.entityId,c.unit.value)" + " from GenericTestCondition c where c.emsDepTCL.entityId= " + entityId, GenericTestConditionRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder#getAllGenericConditionsForList(long)
	 */
	@Override
	public List<GenericTestCondition> getAllGenericConditionsForList(long entityId) {
		TypedQuery<GenericTestCondition> query = entityManager.createQuery("select c from GenericTestCondition c where c.tvvDepTCL.entityId= " + entityId, GenericTestCondition.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvdeptcl.GenericTestConditionFinder#getAllGenericConditionsForEmsList(long)
	 */
	@Override
	public List<GenericTestCondition> getAllGenericConditionsForEmsList(long entityId) {
		TypedQuery<GenericTestCondition> query = entityManager.createQuery("select c from GenericTestCondition c where c.emsDepTCL.entityId= " + entityId, GenericTestCondition.class);
		return query.getResultList();
	}

	@Override
	public List<MandatoryDataRepresentation> getAllGenericTestConditionData(String emsVersion, long emsId) {

		TypedQuery<MandatoryDataRepresentation> query = entityManager.createQuery(
				"select new " + MandatoryDataRepresentation.class.getName() + "(d.entityId,d.label ,d.emsDepTCL.label, d.emsDepTCL.emissionStandard.esLabel,'Condition d’essai',d.mandatoryIdValues) from GenericTestCondition d Where d.emsDepTCL.emissionStandard.version = " + emsVersion + " AND d.emsDepTCL.emissionStandard.entityId = " + emsId + " AND d.emsDepTCL.entityId IS NOT NULL",
				MandatoryDataRepresentation.class);

		return query.getResultList();

	}

	@Override
	public List<MandatoryDataRepresentation> getAllTvvGenericTestConditionData(long tvvDepTCLId) {

		TypedQuery<MandatoryDataRepresentation> queryforTVV = entityManager.createQuery("select new " + MandatoryDataRepresentation.class.getName() + "(d.entityId,d.label ,d.tvvDepTCL.label,'','Condition d’essai',d.mandatoryIdValues) from GenericTestCondition d where d.tvvDepTCL.entityId = " + tvvDepTCLId + " AND d.emsDepTCL.entityId IS NULL", MandatoryDataRepresentation.class);

		return queryforTVV.getResultList();

	}

	@Override
	public List<GenericTestConditionRepresentation> getGenericTestLabel(String label, long tvvDepTCLId) {

		TypedQuery<GenericTestConditionRepresentation> queryforTVV = entityManager.createQuery("select new " + GenericTestConditionRepresentation.class.getName() + "(t.entityId) from GenericTestCondition t where t.label ='" + label + "' and t.tvvDepTCL.entityId = " + tvvDepTCLId, GenericTestConditionRepresentation.class);

		return queryforTVV.getResultList();

	}

}