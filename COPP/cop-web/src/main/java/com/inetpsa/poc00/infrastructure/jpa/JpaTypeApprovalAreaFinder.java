package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaFinder;
import com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaRepresentation;

/**
 * The Class JpaTypeApprovalAreaFinder.
 */
public class JpaTypeApprovalAreaFinder implements TypeApprovalAreaFinder {

	/** The entity manager. */
	@Inject
	EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaFinder#getAllTypeApprovalArea()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<TypeApprovalAreaRepresentation> getAllTypeApprovalArea() {

		TypedQuery<TypeApprovalAreaRepresentation> query = entityManager.createQuery("select new " + TypeApprovalAreaRepresentation.class.getName() + "(tap.entityId ,tap.label,tap.description)" + " from TypeApprovalArea tap ", TypeApprovalAreaRepresentation.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaFinder#getAllAreaNames()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<String> getAllAreaNames() {
		TypedQuery<String> query = entityManager.createQuery("select tap.label from TypeApprovalArea tap ", String.class);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.typeapprovalarea.TypeApprovalAreaFinder#findTypeApprovalAreaDataByLabel(java.lang.String)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<TypeApprovalAreaRepresentation> findTypeApprovalAreaDataByLabel(String label) {

		TypedQuery<TypeApprovalAreaRepresentation> query = entityManager.createQuery("select new " + TypeApprovalAreaRepresentation.class.getName() + "(f.entityId) from TypeApprovalArea f where f.label='" + label + "'", TypeApprovalAreaRepresentation.class);

		return query.getResultList();
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<TypeApprovalAreaRepresentation> getTypeApprovalAreaData() {

		TypedQuery<TypeApprovalAreaRepresentation> query = entityManager.createQuery("select new " + TypeApprovalAreaRepresentation.class.getName() + "(f.entityId,f.label) from TypeApprovalArea f", TypeApprovalAreaRepresentation.class);

		return query.getResultList();
	}

	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public List<TypeApprovalAreaRepresentation> getAllTypeApprovalAreaForRg() {
		TypedQuery<TypeApprovalAreaRepresentation> query = entityManager.createQuery("select new " + TypeApprovalAreaRepresentation.class.getName() + "(tap.entityId ,tap.label,tap.description)" + " from TypeApprovalArea tap ", TypeApprovalAreaRepresentation.class);

		return query.getResultList();

	}

}
