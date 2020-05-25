package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValue;
import com.inetpsa.poc00.domain.genomelcdvcodevalue.GenomeLCDVCodeValueRepository;

@DataSet(group = Config.JPA_UNIT, name = "GenomeLCDVCodeValue")
public class GenomeLCDVCodeValueJPARepository extends BaseJpaRepository<GenomeLCDVCodeValue, Long> implements GenomeLCDVCodeValueRepository {

	@Override
	public GenomeLCDVCodeValue saveGenomeLCDVCodeValue(GenomeLCDVCodeValue object) {

		return super.save(object);
	}

	@Override
	public void persistGenomeLCDVCodeValue(GenomeLCDVCodeValue object) {
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
