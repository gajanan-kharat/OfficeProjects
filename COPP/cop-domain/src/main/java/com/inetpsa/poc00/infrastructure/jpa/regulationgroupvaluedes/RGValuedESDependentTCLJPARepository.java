package com.inetpsa.poc00.infrastructure.jpa.regulationgroupvaluedes;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL;
import com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository;

/**
 * The Class RGValuedESDependentTCLJPARepository.
 */
public class RGValuedESDependentTCLJPARepository extends BaseJpaRepository<RGValuedESDependentTCL, Long> implements RGValuedESDependentTCLRepository {

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository#saveRegGrpValEmStdDepTCL(com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCL)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public RGValuedESDependentTCL saveRegGrpValEmStdDepTCL(RGValuedESDependentTCL object) {
		return super.save(object);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository#deleteAll()
	 */
	@Override
	public long deleteAll() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository#count()
	 */
	@Override
	public long count() {

		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.poc00.domain.rgvaluedesdependenttcl.RGValuedESDependentTCLRepository#deleteRegGrpValEmStdDepTCL(long)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void deleteRegGrpValEmStdDepTCL(long rgvaluedDepTclId) {
		super.delete(rgvaluedDepTclId);

	}

    @Override
    @Transactional
    @JpaUnit(Config.JPA_UNIT)
    public RGValuedESDependentTCL loadRGValuedESDependentTCL(long rgValuedId) {
        return super.load(rgValuedId);
    }

}
