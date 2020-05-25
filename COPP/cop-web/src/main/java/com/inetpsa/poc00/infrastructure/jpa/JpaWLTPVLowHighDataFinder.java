package com.inetpsa.poc00.infrastructure.jpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData;
import com.inetpsa.poc00.rest.wtlp.WLTPVLowHighDataFinder;

/**
 * The Class JpaWLTPVLowHighDataFinder.
 */
public class JpaWLTPVLowHighDataFinder implements WLTPVLowHighDataFinder {

	/** The entity manager. */
	@Inject
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.rest.wtlp.WLTPVLowHighDataFinder#getWLTPData()
	 */
	@Override
	public WLTPVLowHighData getWLTPData() {
		String querry = "select new " + WLTPVLowHighData.class.getName() + "(wltp)" + "from WLTPVLowHighData wltp";

		TypedQuery<WLTPVLowHighData> queryResult = entityManager.createQuery(querry, WLTPVLowHighData.class);

		return queryResult.getSingleResult();

	}

}
