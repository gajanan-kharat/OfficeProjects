package com.inetpsa.poc00.rest.referential;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;


/**
 * The Class JpaGlobalReferenceRepresentationFinder.
 */
public class JpaGlobalReferenceRepresentationFinder implements GenomeDataFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The tvv repo. */
	@Inject
	private GenomeDataFinder tvvRepo;

	/** The logger. */
	@Logging
	private Logger logger;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.referential.GenomeDataFinder#getTVVRulebyID(java.lang.Long)
	 */
	@Override
	public List<GenomeTVVRule> getTVVRulebyID(Long id) {

		logger.info("querry running to get TVVRule Object");
		String query1 = "SELECT tvvRule " + "FROM GenomeTVVRule tvvRule " + "WHERE tvvRule.entityId = ?1 ";

		TypedQuery<GenomeTVVRule> queryResult = entityManager.createQuery(query1, GenomeTVVRule.class);

		queryResult.setParameter(1, id);

		List<GenomeTVVRule> results = queryResult.getResultList();
		logger.info("returning TVVRule Object");
		return results;

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.referential.GenomeDataFinder#getTVVRuleObjbyID(java.lang.Long)
	 */
	@Override
	public GenomeTVVRule getTVVRuleObjbyID(Long id) {

		logger.info("Query running to get TVVRule Object");
		
		String query1 = "SELECT tvvRule " + "FROM GenomeTVVRule tvvRule " + "WHERE tvvRule.entityId = ?1 ";

		TypedQuery<GenomeTVVRule> queryResult = entityManager.createQuery(query1, GenomeTVVRule.class);

		queryResult.setParameter(1, id);

		GenomeTVVRule results = queryResult.getSingleResult();
		
		logger.info("returning TVVRule Object");
		
		return results;

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.referential.GenomeDataFinder#getFuelInjectionTypebyID(long)
	 */
	@Override
	public List<FuelInjectionType> getFuelInjectionTypebyID(long id) {

		logger.info("querry running to get FuelInjectionType Object");
		String query1 = "SELECT distinct fit " + "FROM FuelInjectionType fit " + "WHERE fit.entityId = ?1 ";

		TypedQuery<FuelInjectionType> queryResult = entityManager.createQuery(query1, FuelInjectionType.class);

		queryResult.setParameter(1, id);

		List<FuelInjectionType> results = queryResult.getResultList();
		logger.info("returning FuelInjectionType Object");
		return results;

	}

}
