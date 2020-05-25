package com.inetpsa.poc00.infrastructure.jpa.regulationgroupvaluedtv;

import org.seedstack.jpa.BaseJpaRepository;

import com.inetpsa.poc00.domain.rgvaluedtestcondition.RegGrpValdTestCondition;
import com.inetpsa.poc00.domain.rgvaluedtestcondition.RegGrpValdTestCondtnRepository;

public class RegGrpValdTestCondtnJpaRepository extends BaseJpaRepository<RegGrpValdTestCondition,Long> implements RegGrpValdTestCondtnRepository  {

	@Override
	public RegGrpValdTestCondition saveRegGrpValdTestCondition(
			RegGrpValdTestCondition object) {
		// TODO Auto-generated method stub
		return super.save(object);
	}

	@Override
	public void persistRegGrpValdTestCondition(RegGrpValdTestCondition object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long deleteAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

}
