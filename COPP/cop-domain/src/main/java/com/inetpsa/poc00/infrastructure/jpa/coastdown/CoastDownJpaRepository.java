package com.inetpsa.poc00.infrastructure.jpa.coastdown;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.coastdown.CoastDown;
import com.inetpsa.poc00.domain.coastdown.CoastDownRepository;

/**
 * The Class CoastDownJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "CoastDown")
public class CoastDownJpaRepository extends BaseJpaRepository<CoastDown, Long> implements CoastDownRepository {

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(CoastDownJpaRepository.class);

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.coastdown.CoastDownRepository#saveCoastDown(com.inetpsa.poc00.domain.coastdown.CoastDown)
	 */
	@Override
	public CoastDown saveCoastDown(CoastDown coastdown) {

		return super.save(coastdown);
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.coastdown.CoastDownRepository#persistCoastDown(java.lang.String, java.lang.String)
	 */
	@Override
	public void persistCoastDown(String pSAReference, Long inertiaValue, String version) {

		entityManager.createQuery("UPDATE CoastDown c SET latestversion = false where c.pSAReference = '" + pSAReference + "'and c.inertia=" + inertiaValue + " and c.version = '" + version + "'").executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.coastdown.CoastDownRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.coastdown.CoastDownRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.coastdown.CoastDownRepository#getCoastDown(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public CoastDown getCoastDown(String psaReference, Integer inertiaValue, String version) {

		TypedQuery<CoastDown> query = entityManager.createQuery("From CoastDown t WHERE t.pSAReference = ?1 AND t.inertia.value = ?2 AND t.version=?3", CoastDown.class);
		query.setParameter(1, psaReference);
		query.setParameter(2, inertiaValue);
		query.setParameter(3, version);

		List<CoastDown> coastDown = query.getResultList();

		if (!coastDown.isEmpty()) {
			logger.info("Coast Down : {}", coastDown.size());
			return coastDown.get(0);
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.domain.coastdown.CoastDownRepository#getCoastDownByLatestVersion()
	 */
	@Override
	public List<CoastDown> getCoastDownByLatestVersion() {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTCTD A,(SELECT PSA_REFERENCE, INERTIA_ID, MAX(VERSION) AS VERSION FROM COPQTCTD GROUP BY PSA_REFERENCE, INERTIA_ID HAVING (COUNT(*) > 0)) AS B WHERE A.PSA_REFERENCE = B.PSA_REFERENCE AND A.INERTIA_ID = B.INERTIA_ID AND A.VERSION = B.VERSION", CoastDown.class);

		List<CoastDown> coastDownList = query.getResultList();

		return coastDownList;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.coastdown.CoastDownRepository#getMaxVersionForcoastdown(java.lang.String, int)
	 */
	@Override
	public CoastDown getMaxVersionForcoastdown(String psaRef, int inertiaValue) {
		TypedQuery<CoastDown> query = entityManager.createQuery("SELECT new " + CoastDown.class.getName() + "(MAX(t.version),t.entityId) from CoastDown t WHERE t.pSAReference = ?1 AND t.inertia.value = ?2", CoastDown.class);

		query.setParameter(1, psaRef);
		query.setParameter(2, inertiaValue);

		List<CoastDown> maxVersionObj = query.getResultList();

		if (!maxVersionObj.isEmpty()) {
			return maxVersionObj.get(0);
		}

		return null;
	}

}
