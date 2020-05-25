package com.inetpsa.poc00.infrastructure.jpa;

import java.util.List;

import javax.persistence.Query;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository;
import com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType;

/**
 * The Class FuelInjectionTypeJpaRepository.
 */
@DataSet(group = Config.JPA_UNIT, name = "FuelInjectionType")
public class FuelInjectionTypeJpaRepository extends BaseJpaRepository<FuelInjectionType, Long> implements FuelInjctnTypeRepository {

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(FuelInjectionTypeJpaRepository.class);

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository#saveFuelInjctnType(com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjectionType)
	 */
	@Override
	public FuelInjectionType saveFuelInjctnType(FuelInjectionType fit) {

		if (fit.getEntityId() == null || fit.getEntityId() == 0)
			return super.save(fit);

		return entityManager.merge(fit);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository#deleteFuelInjctnType(java.lang.Long)
	 */
	@Override
	public int deleteFuelInjctnType(Long id) {
		try {
			return entityManager.createQuery("DELETE FROM FuelInjectionType fit where fit.entityId = " + id).executeUpdate();
		} catch (Exception e) {

			logger.error(" Error occured while deleting data ", e);
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository#getAllFITypeForCopiedFCList(java.lang.Long)
	 */
	@Override
	public List<FuelInjectionType> getAllFITypeForCopiedFCList(Long entityId) {

		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTFIT C JOIN COPQTMFI L ON (L.FUELITID = C.ID) JOIN COPQTFLS FCL ON (FCL.ID =  L.FCLISTID) WHERE FCL.ID =?1", FuelInjectionType.class);

		query.setParameter(1, entityId);

		List<FuelInjectionType> result = query.getResultList();

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.fuelinjectiontype.FuelInjctnTypeRepository#getAllFITypeForCopiedPGList(java.lang.Long)
	 */
	@Override
	public List<FuelInjectionType> getAllFITypeForCopiedPGList(Long entityId) {
		Query query = entityManager.createNativeQuery("SELECT * FROM COPQTFIT C JOIN COPQTMPI L ON (L.FUELIT_ID = C.ID) JOIN COPQTPLL PGL ON (PGL.ID = L.PGLIST_ID) WHERE PGL.ID = ?1", FuelInjectionType.class);

		query.setParameter(1, entityId);

		List<FuelInjectionType> result = query.getResultList();

		return result;
	}

}
