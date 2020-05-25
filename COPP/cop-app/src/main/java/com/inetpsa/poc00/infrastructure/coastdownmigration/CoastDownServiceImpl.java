package com.inetpsa.poc00.infrastructure.coastdownmigration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.CoastDownService;
import com.inetpsa.poc00.domain.coastdown.CoastDown;
import com.inetpsa.poc00.domain.coastdown.CoastDownFactory;
import com.inetpsa.poc00.domain.coastdown.CoastDownMigrationAssembler;
import com.inetpsa.poc00.domain.coastdown.CoastDownMigrationDto;
import com.inetpsa.poc00.domain.coastdown.CoastDownRepository;
import com.inetpsa.poc00.domain.inertia.Inertia;
import com.inetpsa.poc00.domain.inertia.InertiaRepository;
import com.inetpsa.poc00.genomeBatch.utility.GenomeUtil;

/**
 * The Class CoastDownSeviceImpl.
 */
public class CoastDownServiceImpl implements CoastDownService {

	/** The coast down repo. */
	@Inject
	CoastDownRepository coastDownRepo;

	/** The coast down assembler. */
	@Inject
	CoastDownMigrationAssembler coastDownAssembler;

	/** The coast down factory. */
	@Inject
	CoastDownFactory coastDownFactory;

	/** The inertia repo. */
	@Inject
	InertiaRepository inertiaRepo;

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(CoastDownServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.application.CoastDownService#ExcelProcessor(java.io.File)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void csvProcessor(File inputCSVFile, String fileDirectory) throws IOException {

		List<CoastDownMigrationDto> coastDownList = new ArrayList<CoastDownMigrationDto>();

		Set<Integer> inertiaValueList = new HashSet<Integer>();

		CSVLoader csvLoader = new CSVLoader();

		try {
			coastDownList = csvLoader.loadCSV(inputCSVFile);

			DateTime batchStartTime = GenomeUtil.getTime();

			// Collecting the InertiaValues to Save
			if (coastDownList != null) {
				for (CoastDownMigrationDto obj : coastDownList) {
					inertiaValueList.add(obj.getInertiaValue());
				}
			}

			// Saving Inertia Value if not exists in the database
			if (!inertiaValueList.isEmpty()) {
				for (Integer inertiaValue : inertiaValueList) {
					Inertia inertia = inertiaRepo.getInertiaByValue(inertiaValue);

					if (inertia == null) {
						inertia = new Inertia(inertiaValue);
						inertia = inertiaRepo.save(inertia);
						logger.info("Saved Inertial Value {} with Id {}", inertia.getEntityId(), inertia.getValue());
					}
				}
			}

			GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "TIME TAKEN TO SAVE INERTIA VALUES");

			batchStartTime = GenomeUtil.getTime();

			// Saving Coast Down
			if (coastDownList != null) {
				logger.info("Started saving Coast Down Values, Coast Down List size : {}", coastDownList.size());

				for (CoastDownMigrationDto obj : coastDownList) {

					CoastDown coastDown = coastDownFactory.createCoastDown();

					coastDownAssembler.doMergeAggregateWithDto(coastDown, obj);

					// Getting Inertia Object using Inertia Value to Set in coastDown Object
					Inertia inertia = inertiaRepo.getInertiaByValue(obj.getInertiaValue());
					if (inertia != null) {
						coastDown.setInertia(inertia);
					}

					// Checking if Coast Down exists with provided PSA_REFERENCE, INERTIAL VALUE & VERSION
					CoastDown temp = coastDownRepo.getCoastDown(coastDown.getpSAReference(), coastDown.getInertia().getValue(), coastDown.getVersion());

					if (temp != null) {
						logger.info("Coast Down already Exists with value Psa Reference {}, Inertia Value {}, Version {}", coastDown.getpSAReference(), coastDown.getInertia().getValue(), coastDown.getVersion());
						continue;
					} else {
						coastDown = coastDownRepo.save(coastDown);
						logger.info("Coast Down value saved with the id : {}", coastDown.getEntityId());
					}

				}
			}

			GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "TIME TAKEN TO SAVE ALL COAST DOWN");

			batchStartTime = GenomeUtil.getTime();

			// Getting All the Coast Down of Latest Version
			List<CoastDown> dataList = coastDownRepo.getCoastDownByLatestVersion();

			if (!dataList.isEmpty()) {

				for (CoastDown temp : dataList) {

					logger.info("Loading Coast Down of Id : {}", temp.getEntityId());

					CoastDown coastDown = coastDownRepo.load(temp.getEntityId());
					coastDown.setLatestversion(Boolean.TRUE);

					// Saving the Coast Down After Setting Latest Version Flag to TRUE
					coastDownRepo.saveCoastDown(coastDown);
				}
			}

			logger.info("Completed saving Coast Down Values, Coast Down List size : {}", coastDownList.size());

			GenomeUtil.timeDuration(batchStartTime, GenomeUtil.getTime(), "TIME TAKEN TO SET LATEST VERSION FLAG");

		} catch (IOException e) {
			logger.error("Error While saving Coast down", e);
		}

	}

}
