package com.inetpsa.poc00.infrastructure.jpa.expertiseresult;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;

import com.inetpsa.poc00.domain.expertiseresult.ExpertiseResult;
import com.inetpsa.poc00.domain.expertiseresult.ExpertiseResultRepository;

/**
 * The Class ExpertiseResultJpaRepository.
 */
public class ExpertiseResultJpaRepository extends BaseJpaRepository<ExpertiseResult, Long> implements ExpertiseResultRepository {

	/** The logger. */
	@Logging
	private Logger logger;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.ExpertiseResult.ExpertiseResultRepository#saveExpertiseResult(com.inetpsa.poc00.domain.ExpertiseResult.ExpertiseResult)
	 */
	@Override
	public ExpertiseResult saveExpertiseResult(ExpertiseResult ExpertiseResult) {

		super.save(ExpertiseResult);
		return null;
	}

	/*	 (non-Javadoc)
		 * @see com.inetpsa.poc00.domain.ExpertiseResult.ExpertiseResultRepository#deleteAll()
		 
		@Override
		public long deleteAll() {
			return 0;
		}
	
		 (non-Javadoc)
		 * @see com.inetpsa.poc00.domain.ExpertiseResult.ExpertiseResultRepository#count()
		 
		@Override
		public long count() {
			return 0;
		}*/
	/*
		 (non-Javadoc)
		 * @see com.inetpsa.poc00.domain.ExpertiseResult.ExpertiseResultRepository#getExpertiseResultByValue(int)
		 
		@Override
		public ExpertiseResult getExpertiseResultByValue(Integer value) {
	
			String queryString = "SELECT ina FROM ExpertiseResult ina WHERE ina.value = ?1 ";
	
			TypedQuery<ExpertiseResult> queryResult = entityManager.createQuery(queryString, ExpertiseResult.class);
	
			queryResult.setParameter(1, value);
	
			List<ExpertiseResult> results = queryResult.getResultList();
	
			if (!results.isEmpty()) {
				return results.get(0);
			} else
				return null;
	
		}
	
		*//**
			 * {@inheritDoc}
			 * 
			 * @see com.inetpsa.poc00.domain.ExpertiseResult.ExpertiseResultRepository#getExpertiseResultObj(int)
			 *//*
			@Override
			public List<ExpertiseResult> getExpertiseResultObj(int value) {
			logger.info("Query running to get ExpertiseResult Object where value:", value);
			
			String query1 = "SELECT ina " + "FROM ExpertiseResult ina " + "WHERE ina.value = ?1 ";
			
			TypedQuery<ExpertiseResult> queryResult = entityManager.createQuery(query1, ExpertiseResult.class);
			
			queryResult.setParameter(1, value);
			
			List<ExpertiseResult> results = queryResult.getResultList();
			logger.info("Returning ExpertiseResult Object");
			return results;
			}*/

}
