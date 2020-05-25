/*
 * Creation : Mar 29, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.generictd.GenericTechnicalData;
import com.inetpsa.poc00.mandatorydata.MandatoryDataRepresentation;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder;
import com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataRepresentation;

/**
 * The Class JpaGenericTechnicalDataFinder.
 */
public class JpaGenericTechnicalDataFinder implements GenericTechnicalDataFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder#getAllDataForList(long)
	 */
	@Override
	public List<GenericTechnicalDataRepresentation> getAllDataForList(long entityId) {
		TypedQuery<GenericTechnicalDataRepresentation> query = entityManager.createQuery("select new " + GenericTechnicalDataRepresentation.class.getName() + "(d.entityId, d.dataType,d.defaultValue,d.label,d.unit.entityId,d.unit.value)" + " from GenericTechnicalData d where d.tvvDepTDL.entityId= " + entityId, GenericTechnicalDataRepresentation.class);
		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder#getAllGenericDataForList(long)
	 */
	@Override
	public List<GenericTechnicalData> getAllGenericDataForList(long entityId) {
		TypedQuery<GenericTechnicalData> query = entityManager.createQuery("select d from GenericTechnicalData d where d.tvvDepTDL.entityId= " + entityId, GenericTechnicalData.class);
		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder#getAllDataForEMSDepList(long)
	 */
	@Override
	public List<GenericTechnicalDataRepresentation> getAllDataForEMSDepList(long entityId) {
		TypedQuery<GenericTechnicalDataRepresentation> query = entityManager.createQuery("select new " + GenericTechnicalDataRepresentation.class.getName() + "(d.entityId, d.dataType,d.defaultValue,d.label,d.unit.entityId,d.unit.value)" + " from GenericTechnicalData d where d.emsDepTDL.entityId= " + entityId, GenericTechnicalDataRepresentation.class);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder#getAllGenericDataForEmsList(long)
	 */
	@Override
	public List<GenericTechnicalData> getAllGenericDataForEmsList(long entityId) {
		TypedQuery<GenericTechnicalData> query = entityManager.createQuery("select d from GenericTechnicalData d where d.emsDepTDL.entityId= " + entityId, GenericTechnicalData.class);
		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder#getAllGenericTechnicalData()
	 */
	@Override
	public List<MandatoryDataRepresentation> getAllGenericTechnicalData(String esVersion, long emsId) {

		TypedQuery<MandatoryDataRepresentation> query = entityManager.createQuery(
				"select new " + MandatoryDataRepresentation.class.getName() + "(d.entityId,d.label ,d.emsDepTDL.label, d.emsDepTDL.emissionStandard.esLabel,'Donnée Technique',d.mandatoryIdValues) from GenericTechnicalData d Where d.emsDepTDL.emissionStandard.version = " + esVersion + " AND " + "d.emsDepTDL.emissionStandard.entityId = " + emsId + " AND d.emsDepTDL.entityId IS NOT NULL",
				MandatoryDataRepresentation.class);

		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder#getAllTvvGenericTechnicalData()
	 */
	@Override
	public List<MandatoryDataRepresentation> getAllTvvGenericTechnicalData(long tvvDepTDLId) {

		TypedQuery<MandatoryDataRepresentation> queryforTVV = entityManager.createQuery("select new " + MandatoryDataRepresentation.class.getName() + "(d.entityId,d.label ,d.tvvDepTDL.label,' ','Donnée Technique',d.mandatoryIdValues) from GenericTechnicalData d Where d.tvvDepTDL.entityId = " + tvvDepTDLId + " AND d.emsDepTDL.entityId IS NULL", MandatoryDataRepresentation.class);
		return queryforTVV.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvdeptdl.GenericTechnicalDataFinder#getGenericDataLabel(java.lang.String, long)
	 */
	@Override
	public List<GenericTechnicalDataRepresentation> getGenericDataLabel(String label, long tvvDepTDL) {

		TypedQuery<GenericTechnicalDataRepresentation> query = entityManager.createQuery("select new " + GenericTechnicalDataRepresentation.class.getName() + "(t.entityId) from GenericTechnicalData t where t.label ='" + label + "' and t.tvvDepTDL.entityId = " + tvvDepTDL, GenericTechnicalDataRepresentation.class);
		return query.getResultList();

	}
}