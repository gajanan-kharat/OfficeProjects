package com.inetpsa.poc00.genomeBatch.utility;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.constant.genomeconstant.GenomeConstant;

public class GenomeUtil implements GenomeConstant {

	/** The Constant logger. */
	@Logging
	private final static Logger logger = LoggerFactory.getLogger(GenomeUtil.class);

	/**
	 * 
	 * @return
	 */
	public static DateTime getTime() {
		return new DateTime();
	}

	/**
	 * 
	 * @param start
	 * @param end
	 */
	public static void timeDuration(DateTime start, DateTime end, String message) {

		try {

			logger.info("=============================================================================");

			logger.info("TIME DURATION FOR : " + message.toUpperCase(Locale.ENGLISH));

			logger.info(Days.daysBetween(start, end).getDays() + " Days, " + Hours.hoursBetween(start, end).getHours() % 24 + " Hours, " + Minutes.minutesBetween(start, end).getMinutes() % 60 + " Minutes, " + Seconds.secondsBetween(start, end).getSeconds() % 60 + " Seconds .");

			logger.info("=============================================================================\n");

		} catch (Exception e) {
			logger.error("Error While Computing Time Duration", e);
		}

	}

}