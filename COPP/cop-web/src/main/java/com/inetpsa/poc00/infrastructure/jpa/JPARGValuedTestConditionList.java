package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedTCLFinder;

/**
 * The Class JPARGValuedTestConditionList.
 */
public class JPARGValuedTestConditionList implements RGValuedTCLFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.regulationgroupvaluedes.RGValuedTCLFinder#getRGValuedTestConditionList(long)
	 */
	@Override
	public List<RGValuedESDependentTCL> getRGValuedTestConditionList(long regulationGroupId) {
		TypedQuery<RGValuedESDependentTCL> query = entityManager.createQuery("select t from RGValuedESDependentTCL t where t.regulationGroup.entityId= " + regulationGroupId, RGValuedESDependentTCL.class);

		return query.getResultList();
	}

}
