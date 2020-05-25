package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.clasz.Clasz;
import com.inetpsa.poc00.domain.clasz.ClaszRepository;
import com.inetpsa.poc00.rest.clasz.ClaszFinder;
import com.inetpsa.poc00.rest.clasz.ClaszRepresentation;

/**
 * The Class JpaClaszFinder.
 */
public class JpaClaszFinder implements ClaszFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The clasz repo. */
	@Inject
	private ClaszRepository claszRepo;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.clasz.ClaszFinder#getAllClasz()
	 */
	@Override
	public List<ClaszRepresentation> getAllClasz() {

		TypedQuery<ClaszRepresentation> query = entityManager.createQuery("SELECT  new " + ClaszRepresentation.class.getName() + "(clasz.entityId, clasz.description,clasz.label) from Clasz clasz ", ClaszRepresentation.class);

		return query.getResultList();

	}

	/**
	 * Gets the all Clasz for copied pg list.
	 * 
	 * @param entityId the entity id
	 * @return the all Clasz for copied pg list
	 */
	@Override
	public List<Clasz> getAllClaszForCopiedPGList(Long entityId) {

		logger.info("Getting Clasz objects for Copied PG List");

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTACS C JOIN COPQTMPS L ON (L.CLASZ_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID =?1", Clasz.class);

		query.setParameter(1, entityId);

		return query.getResultList();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.clasz.ClaszFinder#getAllClaszForCopiedFCList(java.lang.Long)
	 */
	@Override
	public List<Clasz> getAllClaszForCopiedFCList(Long entityId) {

		logger.info("Getting Clasz objects for Copied FC List");

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTACS C JOIN COPQTMFS L ON (L.CLASSID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID = ?1", Clasz.class);

		query.setParameter(1, entityId);

		return query.getResultList();


	}
	
	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.clasz.ClaszFinder#findClaszDataByLabel(java.lang.String)
	 */
	@Override
	public List<ClaszRepresentation> findClaszDataByLabel(String label) {
		logger.info("Query running to check if label present");

		TypedQuery<ClaszRepresentation> query = entityManager.createQuery("select new " + ClaszRepresentation.class.getName() + "(clasz.entityId) from Clasz clasz where clasz.label='" + label + "'", ClaszRepresentation.class);

		return query.getResultList();
	}

}
