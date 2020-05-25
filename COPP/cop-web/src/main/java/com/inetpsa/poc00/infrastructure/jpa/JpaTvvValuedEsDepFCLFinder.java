/*
 * Creation : Jun 16, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.tvvvaluedfcl.TvvValuedEsDepFCL;
import com.inetpsa.poc00.rest.sampling.SamplingRuleRepresentation;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLAssembler;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLFinder;
import com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLRepresentation;

/**
 * The Class JpaTvvValuedEsDepFCLFinder.
 */
public class JpaTvvValuedEsDepFCLFinder implements TvvValuedEsDepFCLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/** The tvv valued es dep fcl assembler. */
	@Inject
	TvvValuedEsDepFCLAssembler tvvValuedEsDepFCLAssembler;

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLFinder#getAllValuedEsDepFCL(long, long)
	 */
	@Override
	public List<TvvValuedEsDepFCL> getAllValuedEsDepFCL(long tvvId, long emsId) {
		TypedQuery<TvvValuedEsDepFCL> query = entityManager.createQuery("select t from TvvValuedEsDepFCL t where t.tvvObj.entityId =" + tvvId + " and t.emissionStandard.entityId = " + emsId, TvvValuedEsDepFCL.class);
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.rest.tvvvaluedfcl.TvvValuedEsDepFCLFinder#getAllValuedEsDepFCLForTVV(long)
	 */
	@Override
	public List<TvvValuedEsDepFCLRepresentation> getAllValuedEsDepFCLForTVV(long tvvId) {
		TypedQuery<TvvValuedEsDepFCL> query = entityManager.createQuery("select t from TvvValuedEsDepFCL t where t.tvvObj.entityId =" + tvvId, TvvValuedEsDepFCL.class);
		List<TvvValuedEsDepFCL> tvvValuedEsDepFCList = query.getResultList();
		List<TvvValuedEsDepFCLRepresentation> listTvvValuedEsDepFCLRepresentation = new ArrayList<>();
		for (TvvValuedEsDepFCL tvvValuedEsDepFCLObj : tvvValuedEsDepFCList) {
			TvvValuedEsDepFCLRepresentation tvvValuedEsDepFCLRepresentation = new TvvValuedEsDepFCLRepresentation();
			tvvValuedEsDepFCLAssembler.doAssembleDtoFromAggregate(tvvValuedEsDepFCLRepresentation, tvvValuedEsDepFCLObj);
			listTvvValuedEsDepFCLRepresentation.add(tvvValuedEsDepFCLRepresentation);
		}
		return listTvvValuedEsDepFCLRepresentation;
	}

	@Override
	public List<SamplingRuleRepresentation> getSamplingRuleForTG(String samplingRuleLabel) {

		TypedQuery<SamplingRuleRepresentation> query = entityManager.createQuery("select new " + SamplingRuleRepresentation.class.getName() + "(s.entityId, s.amtOrPercent,s.amtType,s.description,s.frequency,s.higherLimit,s.higherSymbol,s.lowerLimit,s.lowerSymbol,s.neededAmt,s.label,s.version)" + " from SamplingRule s where s.label= '" + samplingRuleLabel + "'", SamplingRuleRepresentation.class);

		return query.getResultList();

	}

	@Override
	public List<TvvValuedEsDepFCL> getAllValuedEsDepFCL(long tvvId) {
		TypedQuery<TvvValuedEsDepFCL> query = entityManager.createQuery("select t from TvvValuedEsDepFCL t where t.tvvObj.entityId =" + tvvId, TvvValuedEsDepFCL.class);
		return query.getResultList();

	}
}
