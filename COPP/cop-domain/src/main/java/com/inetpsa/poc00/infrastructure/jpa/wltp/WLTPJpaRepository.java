package com.inetpsa.poc00.infrastructure.jpa.wltp;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData;
import com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighDataRepository;

/**
 * The Class WLTPJpaRepository.
 */
public class WLTPJpaRepository extends BaseJpaRepository<WLTPVLowHighData, Long> implements WLTPVLowHighDataRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighDataRepository#saveWLTPVLowHighData(com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighData)
	 */
	@Override
	public WLTPVLowHighData saveWLTPVLowHighData(WLTPVLowHighData object) {
		if (object.getEntityId() != null && object.getEntityId() != 0)
			return super.save(object);

		super.persist(object);
		entityManager.flush();
		return super.load(object.getEntityId());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighDataRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.wltpvaluedlowhighdata.WLTPVLowHighDataRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

}
