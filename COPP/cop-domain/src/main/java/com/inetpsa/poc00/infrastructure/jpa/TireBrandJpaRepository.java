package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.tirebrand.TireBrand;
import com.inetpsa.poc00.domain.tirebrand.TireBrandRepository;

/**
 * The Class TireBrandJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "TireBrand")
public class TireBrandJpaRepository extends BaseJpaRepository<TireBrand, Long> implements TireBrandRepository {

	/** The jpa entity manager. */
	@Inject
	private EntityManager jpaEntityManager;

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(TireBrandJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandRepository#deleteAll()
	 */
	@Override
	public int deleteAll() {
		try {

			return jpaEntityManager.createQuery("DELETE FROM TireBrand t ").executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandRepository#count()
	 */
	@Override
	public long count() {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandRepository#saveTireBrand(com.inetpsa.poc00.domain.tirebrand.TireBrand)
	 */
	@Override
	public TireBrand saveTireBrand(TireBrand tireBrand) {
		if (tireBrand.getEntityId() == null || tireBrand.getEntityId() == 0)
			return super.save(tireBrand);

		return jpaEntityManager.merge(tireBrand);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandRepository#persistTireBrand(com.inetpsa.poc00.domain.tirebrand.TireBrand)
	 */
	@Override
	public void persistTireBrand(TireBrand tireBrand) {
		saveTireBrand(tireBrand);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandRepository#getAllTireBrands()
	 */
	@Override

	public List<TireBrand> getAllTireBrands() {
		TypedQuery<TireBrand> query = jpaEntityManager.createQuery("SELECT t FROM TireBrand t ", TireBrand.class);

		return query.getResultList();

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandRepository#deleteTireBrand(java.lang.Long)
	 */
	@Override

	public int deleteTireBrand(Long id) {

		try {

			return jpaEntityManager.createQuery("DELETE FROM TireBrand t where t.entityId = " + id).executeUpdate();

		} catch (Exception e) {
			logger.error(" Error occured while deleting data ", e);
			return 0;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.tirebrand.TireBrandRepository#getTireBrandByLabel(java.lang.String)
	 */
	@Override
	public List<TireBrand> getTireBrandByLabel(String label) {

		return entityManager.createQuery("SELECT tb FROM TireBrand tb where tb.label=:plabel").setParameter("plabel", label).getResultList();

	}

}
