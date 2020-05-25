package com.inetpsa.poc00.infrastructure.viewrefresh;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.ViewRefreshService;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository;

/**
 * The Class ViewRefreshImpl.
 */
public class ViewRefreshImpl implements ViewRefreshService {

	/** The genome repo. */
	@Inject
	GenomeLCDVDictionaryRepository genomeRepo;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.application.ViewRefreshService#refreshPerfView()
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void refreshPerfView() {

		genomeRepo.executeRefreshProcedure();

	}

}
