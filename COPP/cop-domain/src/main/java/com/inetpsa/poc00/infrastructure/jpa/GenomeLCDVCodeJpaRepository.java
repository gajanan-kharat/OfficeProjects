package com.inetpsa.poc00.infrastructure.jpa;

import org.seedstack.jpa.BaseJpaRepository;
import org.seedstack.seed.DataSet;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCode;
import com.inetpsa.poc00.domain.genomelcdvcode.GenomeLCDVCodeRepository;

@DataSet(group = Config.JPA_UNIT, name = "GenomeLCDVCode")
public class GenomeLCDVCodeJpaRepository extends BaseJpaRepository<GenomeLCDVCode, Long> implements GenomeLCDVCodeRepository {

	@Override
	public GenomeLCDVCode saveGenomeLCDVCode(GenomeLCDVCode object) {

		return super.save(object);
	}

	@Override
	public void persistGenomeLCDVDictionary(GenomeLCDVCode object) {
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
