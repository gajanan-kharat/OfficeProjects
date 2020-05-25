/*
 * Creation : Apr 20, 2016
 */
package com.inetpsa.poc00.infrastructure.jpa.pollutantorgas;

import java.util.List;

import javax.persistence.Query;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit;
import com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository;

/**
 * The Class PollutantGasLimitJpaRepository.
 */
public class PollutantGasLimitJpaRepository extends BaseJpaRepository<PollutantGasLimit, Long> implements PollutantGasLimitRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository#savePollutantGasLimit(com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit)
	 */
	@Override
	public PollutantGasLimit savePollutantGasLimit(PollutantGasLimit object) {
		if (object.getEntityId() != null && object.getEntityId() != 0)
			return super.save(object);

		super.persist(object);
		entityManager.flush();

		return this.load(object.getEntityId());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository#persistPollutantGasLimit(com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimit)
	 */
	@Override
	public void persistPollutantGasLimit(PollutantGasLimit object) {
		super.persist(object);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository#deletePollutantGasLimit(long)
	 */
	@Override
	public void deletePollutantGasLimit(long entityId) {
		super.delete(entityId);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.pollutantgaslimit.PollutantGasLimitRepository#getAllPollutantsForDepList(java.lang.Long)
	 */
	@Override
	public List<PollutantGasLimit> getAllPollutantsForDepList(Long entityId) {
		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTPLT C WHERE C.PGLIST_ID = ?1", PollutantGasLimit.class);
		query.setParameter(1, entityId);

		return query.getResultList();
	}

}
