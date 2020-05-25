package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.technicalcase.TechnicalCase;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.rest.technicalcase.TechnicalCaseFinder;

/**
 * The Class JpaTechnicalCaseFinder.
 */
public class JpaTechnicalCaseFinder implements TechnicalCaseFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.technicalcase.TechnicalCaseFinder#getTechnicalCase(long)
	 */
	@Override
	public List<TechnicalCase> getTechnicalCase(long tvvId) {

		String querry = "select new " + TechnicalCase.class.getName() + "(techcase.entityId, techcase.tvvWorstCase,techcase.tvv,techcase.emissionStandard,techcase.technicalGroup)" + "from TechnicalCase techcase where techcase.tvv.entityId=?1";
		TypedQuery<TechnicalCase> queryResult = entityManager.createQuery(querry, TechnicalCase.class);

		queryResult.setParameter(1, tvvId);
		return queryResult.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.technicalcase.TechnicalCaseFinder#getTVv(long)
	 */
	@Override
	public List<TVV> getTVv(long technicalGroupId) {
		return null;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.technicalcase.TechnicalCaseFinder#getTechnicalCasetoDelete(long)
	 */
	@Override
	public List<TechnicalCase> getTechnicalCasetoDelete(long technicalGroupId) {
		String querry = "select new " + TechnicalCase.class.getName() + "(technicalCase)" + "from TechnicalCase technicalCase where technicalCase.technicalGroup.entityId=?1";
		TypedQuery<TechnicalCase> queryResult = entityManager.createQuery(querry, TechnicalCase.class);

		queryResult.setParameter(1, technicalGroupId);
		return queryResult.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.technicalcase.TechnicalCaseFinder#getTechnicalCasesForTG(java.lang.Long)
	 */
	@Override
	public List<TechnicalCase> getTechnicalCasesForTG(Long tgId) {
		String querry = "select distinct techcase from TechnicalCase techcase where techcase.technicalGroup.entityId=?1";
		TypedQuery<TechnicalCase> queryResult = entityManager.createQuery(querry, TechnicalCase.class);

		queryResult.setParameter(1, tgId);
		return queryResult.getResultList();
	}

}
