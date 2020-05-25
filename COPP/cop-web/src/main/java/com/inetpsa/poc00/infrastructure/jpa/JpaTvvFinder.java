/*
 * Creation : May 25, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.common.Constants;
import com.inetpsa.poc00.domain.tvv.TVV;
import com.inetpsa.poc00.rest.tvv.TvvFinder;
import com.inetpsa.poc00.rest.tvv.TvvFinderUtil;
import com.inetpsa.poc00.rest.tvv.TvvRepresentation;
import com.inetpsa.poc00.rest.tvv.TvvSearchRepresentation;

/**
 * The Class JpaTvvFinder.
 */
public class JpaTvvFinder implements TvvFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The Constant LOGGER. */
	private static final Logger logger = LoggerFactory.getLogger(JpaTvvFinder.class);

	/** The tvv util. */
	TvvFinderUtil tvvUtil = new TvvFinderUtil();

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc.rest.tvv.TvvFinder#getTVVsWithLabel(java.lang.String)
	 */
	@Override
	public List<TvvRepresentation> getTVVsWithLabel(String label) {
		TypedQuery<TvvRepresentation> query = entityManager.createQuery("select new " + TvvRepresentation.class.getName() + "(t.entityId, t.version,t.label)" + " from TVV t where t.label = '" + label + "'", TvvRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc.rest.tvv.TvvFinder#findTVVsByLabel(java.lang.String)
	 */
	@Override
	public List<TvvRepresentation> findTVVsByLabel(String searchLabel) {
		TypedQuery<TvvRepresentation> query = entityManager.createQuery("select new " + TvvRepresentation.class.getName() + "(t.entityId, t.version,t.label,t.status.entityId,t.status.label,t.status.guiLabel)" + " from TVV t where t.label like'" + searchLabel + "'", TvvRepresentation.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc.rest.tvv.TvvFinder#findByCriteria(com.inetpsa.poc.rest.tvv.TvvSearchRepresentation)
	 */
	@Override
	public List<TvvRepresentation> findByCriteria(TvvSearchRepresentation searchRepresentation) {
		List<TvvRepresentation> tvvList;
		try {
			StringBuilder queryString = new StringBuilder("select  new " + TvvRepresentation.class.getName() + "(t.entityId, t.version,t.label,t.modificationDate,t.status.entityId,t.status.label,t.status.guiLabel,COALESCE(tnr.type,''))" + "from TVV t ");

			// build Join Clause
			tvvUtil.buildJoinClause(searchRepresentation, queryString);

			// build Where Clause
			String whereClauseSearchTVV = tvvUtil.buildWhereclauseSearchTVV(searchRepresentation);
			if (whereClauseSearchTVV.length() > 0) {
				queryString.append(" WHERE ");
				if (whereClauseSearchTVV.trim().endsWith("AND")) {
					queryString.append(whereClauseSearchTVV.substring(0, whereClauseSearchTVV.lastIndexOf("AND")));
				} else
					queryString.append(whereClauseSearchTVV);

			}

			// ORDER BY CLAUSE
			if (searchRepresentation.getSortAlphabetically()) {
				queryString.append(" order by t.label");
			} else if (searchRepresentation.getSortByDate()) {
				queryString.append(" order by t.modificationDate desc");
			}

			// Query Creation
			TypedQuery<TvvRepresentation> typedQuery = null;
			try {
				typedQuery = entityManager.createQuery(queryString.toString(), TvvRepresentation.class);
			} catch (Exception e) {
				logger.error("Error in searching TVV", e);
				return new ArrayList<TvvRepresentation>();
			}

			tvvUtil.setSearchParametersTVV(searchRepresentation, typedQuery);

			// Query Execution
			tvvList = typedQuery.getResultList();
		} catch (Exception e) {
			logger.error("Error in searching TVV", e);
			return new ArrayList<TvvRepresentation>();
		}
		return tvvList;
	}

	/**
	 * This method returns max version for given TVV Label {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvv.TvvFinder#getMaxVersionForLabel(java.lang.String)
	 */
	@Override
	public Double getMaxVersionForLabel(String tvvLabel) {
		Query query = entityManager.createNativeQuery(Constants.MAX_TVV_VERSION_QUERY);

		query.setParameter(1, tvvLabel);

		return ((BigDecimal) query.getSingleResult()).doubleValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.tvv.TvvFinder#getAllTVVData()
	 */
	@Override
	public List<TVV> getAllTVVData() {

		TypedQuery<TVV> query = entityManager.createQuery("SELECT T FROM TVV T ", TVV.class);

		List<TVV> tvvData = query.getResultList();

		logger.info("**************  Size of TvvData is {}", tvvData.size());

		return tvvData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.tvv.TvvFinder#getTvvforVehicle(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TvvRepresentation> getTvvforVehicle(String testNature, String tvvLabel) {

		TypedQuery<TvvRepresentation> query = entityManager.createQuery("select new " + TvvRepresentation.class.getName()
				+ "(t.entityId,t.label,t.technicalCase.emissionStandard.esLabel,t.technicalCase.emissionStandard.version,t.version,t.status.label,t.testNature.entityId,t.testNature.type,COALESCE(pglabel.label,''),pgl.maxDValue,pgl.minDValue,t.technicalCase.entityId) from TVV t LEFT OUTER JOIN t.tvvValuedEsDepPGL es LEFT OUTER JOIN es.pollutantGasLimit pgl LEFT JOIN pgl.pgLabel pglabel where t.label= '"
				+ tvvLabel + "' and t.testNature.entityId in(" + testNature + ")  and UPPER(t.status.label) = '" + Constants.VALID + "' ORDER BY t.version", TvvRepresentation.class);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.inetpsa.poc00.rest.tvv.TvvFinder#findTvvByCategoryId(java.lang.Long)
	 */
	@Override
	public List<TVV> findTvvByCategoryId(Long categoryId) {
		TypedQuery<TVV> query = entityManager.createQuery("select tvv from TVV tvv where tvv.category.entityId=" + categoryId, TVV.class);
		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvv.TvvFinder#getTvvForVehicleFile(long)
	 */
	@Override
	public TVV getTvvForVehicleFile(long vFileId) {
		TypedQuery<TVV> query = entityManager.createQuery("select vehicle.technicalCase.tvv from Vehicle vehicle left join vehicle.vehicleFile vfile where vfile.entityId=" + vFileId, TVV.class);
		return query.getSingleResult();
	}

}
