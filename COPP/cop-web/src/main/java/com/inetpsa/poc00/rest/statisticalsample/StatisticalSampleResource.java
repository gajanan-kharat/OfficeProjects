/*
 * Creation : Nov 29, 2016
 */
package com.inetpsa.poc00.rest.statisticalsample;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;

/**
 * The Class StatisticalSampleResource.
 */
@Path("/statisticalsample")
@Transactional
@JpaUnit(Config.JPA_UNIT)
public class StatisticalSampleResource {

	/** The logger. */
	@Logging
	private final Logger logger = LoggerFactory.getLogger(StatisticalSampleResource.class);

	/** The statistical sample finder. */
	@Inject
	private StatisticalSampleFinder statisticalSampleFinder;

	/**
	 * Gets the statistical sample.
	 *
	 * @param tvvLabel the tvv label
	 * @return the statistical sample
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/statisticalsamplelist/{tvvLabel}")
	public Response getStatisticalSample(@PathParam("tvvLabel") String tvvLabel) {
		logger.info("------- Loading Statistical Sample by TvvLabel :", tvvLabel);
		List<StatisticalSampleRepresentation> statisticalSample = statisticalSampleFinder.getStatisticalSampleByTvv(tvvLabel);
		return Response.ok(statisticalSample).build();
	}

	/**
	 * Gets the statistical sample list.
	 *
	 * @param esLabel the es label
	 * @param carFactoryLabel the car factory label
	 * @param statisticalRuleLabel the statistical rule label
	 * @return the statistical sample list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/statisticalsampleselected/{tvvLabel}/{esLabel}/{carFactoryLabel}/{statisticalRuleLabel}")
	public Response getStatisticalSampleList(@PathParam("tvvLabel") String tvvLabel, @PathParam("esLabel") String esLabel, @PathParam("carFactoryLabel") String carFactoryLabel, @PathParam("statisticalRuleLabel") String statisticalRuleLabel) {

		int index = esLabel.lastIndexOf(' ');
		String esVersion = esLabel.substring(index + 2, esLabel.length());

		String newEsLabel = esLabel.substring(0, index);
		logger.info("--------- Loading Statistical Sample by emmisionStandard:", esLabel, "carFactory : ", carFactoryLabel, "StatisticalRule :", statisticalRuleLabel);
		List<StatisticalSampleRepresentation> statisticalSampleObj = statisticalSampleFinder.getStatisticalSample(newEsLabel, esVersion, carFactoryLabel, statisticalRuleLabel, tvvLabel);
		return Response.ok(statisticalSampleObj).build();
	}
}
