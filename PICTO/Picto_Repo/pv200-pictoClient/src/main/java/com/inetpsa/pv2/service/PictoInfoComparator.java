package com.inetpsa.pv2.service;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.beans.PictoInformation;

/**
 * 
 * To get list of pictos by name.
 *
 */
public class PictoInfoComparator implements Comparator<PictoInformation> {
	/**
	 * Logger log4j to write messages
	 */
	private static Logger logger = LoggerFactory
			.getLogger(PictoInfoComparator.class);

	/**
	 * Method to compare pictos by name.
	 */

	public int compare(PictoInformation pictoInfo1, PictoInformation pictoInfo2) {
		logger.info("Starting comparing pictos by name in compare method of PictoInfoComparator class");
		return (pictoInfo1 != null && pictoInfo2 != null) ? pictoInfo1
				.getPictoName().compareTo(pictoInfo2.getPictoName()) : null;

	}
}
