/*
 * Creation : Apr 15, 2016
 */
package com.inetpsa.pv2.props;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/***
 * PropertiesCache Class to load various properties ,config properties and
 * supports ii8n
 */
public final class PropertiesCache {
	private final Properties configProp = new Properties();
	InputStream in_PictoConfig = null;
	/*
	 * Loggers for PropertiesCache class
	 */
	@Logging
	private static final Logger logger = LoggerFactory
	.getLogger(PropertiesCache.class);

	/*
	 * Private Constructor of PropertiesCache Class to enable lazy loading and
	 * Singleton Feature
	 */
	private PropertiesCache() {
		try {
			logger.info("Start constructor PropertiesCache for loading property Files in PropertiesCache class...");
			in_PictoConfig = this.getClass().getClassLoader()
					.getResourceAsStream("properties/PictoConfig.properties");
			if (in_PictoConfig != null) {
				logger.info("Loading PictoConfig.properties started..");
				/**
				 * This method configProp is used to load specified property
				 * file passed as method parameter in English
				 * 
				 * @param Imput
				 *            Stream of property file
				 * @return String value based on key passed
				 */
				configProp.load(in_PictoConfig);
			}
			logger.info("Loading PictoConfig.properties completed sucessfully..");
			logger.info("Loading all Property Files in PropertiesCache constructor in PropertiesCache class completed sucessfully...");
		} catch (Exception e) {

			logger.error(
					"Error in loading Property Filesin  PropertiesCache Constructor of PropertiesCache class ",
					e);
		}
	}

	/**
	 * This static lazy loader method to lazily load PropertiesCache class
	 * 
	 */
	private static class LazyHolder {
		private LazyHolder() {
		}

		private static final PropertiesCache INSTANCE = new PropertiesCache();
	}

	/**
	 * This method is used returns Property Cache Class instance
	 * 
	 * @return PropertiesCache Instance
	 */
	public static PropertiesCache getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * This method is a getter to return
	 * 
	 * @param key
	 *            is String value passed from property file
	 * @return String Value based on key passed.
	 */
	public String getProperty(String key) {
		return configProp.getProperty(key);
	}

	/**
	 * This method is a getter to return
	 * 
	 * @param key
	 *            is String value passed from property file
	 * @return Set Of String Value based Property Names passed.
	 */
	public Set<String> getAllPropertyNames() {
		return configProp.stringPropertyNames();
	}

	/**
	 * This method is a used to check whether key passed exists
	 * 
	 * @param key
	 *            is String value passed from property file
	 * @return boolean value if key is there
	 * 
	 */
	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}
}