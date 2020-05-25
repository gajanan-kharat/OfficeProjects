
package com.inetpsa.poc00.tirebrand;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.it.SeedITRunner;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.tirebrand.TireBrandService;
import com.inetpsa.poc00.domain.tirebrand.TireBrand;
import com.inetpsa.poc00.domain.tirebrand.TireBrandFactory;
import com.inetpsa.poc00.domain.tirebrand.TireBrandRepository;

/**
 * The Class TireBrandIT.
 */
@RunWith(SeedITRunner.class)
public class TireBrandIT {

	/** The tire brand repository. */
	@Inject
	private TireBrandRepository tireBrandRepository;

	/** The logger. */
	@Logging
	private Logger logger;

	/** The label. */
	String label = "testTeamlabel" + System.currentTimeMillis();

	/** The tire brand service. */
	@Inject
	private TireBrandService tireBrandService;

	/**
	 * My_service_is_injectable.
	 */
	@Test
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void my_service_is_injectable() {
		Assertions.assertThat(tireBrandService).isNotNull();
		logger.info("*********Service Injected Successfully**********");
	}

	/**
	 * Test delete.
	 */
	@Test
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void testDelete() {
		TireBrand tireBrand = TireBrandFactory.createTireBrand();
		tireBrand.setLabel(label);
		TireBrand newTireBrand = tireBrandService.saveTireBrand(tireBrand);
		logger.info("**********Update tested successfully*********" + newTireBrand.getEntityId());
		assertNotNull(newTireBrand);
		tireBrandService.deleteTireBrand(newTireBrand.getEntityId());
		List<TireBrand> factoryList = tireBrandRepository.getTireBrandByLabel(label);
		assertEquals(factoryList.size(), 0);
		logger.info("**********DELETE tested successfully*********");

	}

	/**
	 * Test update.
	 */
	@Test
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void testUpdate() {
		TireBrand tireBrand = TireBrandFactory.createTireBrand();

		tireBrand.setLabel(label);
		assertNotNull(tireBrandService);
		TireBrand newTireBrand = tireBrandService.saveTireBrand(tireBrand);
		assertNotNull(newTireBrand);
		logger.info(newTireBrand.getLabel());
		assertEquals(newTireBrand.getLabel(), label);
		logger.info("**********Update tested successfully*********");

	}

}
