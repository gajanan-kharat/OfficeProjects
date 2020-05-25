package com.inetpsa.poc00.common;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class PropertiesLang.
 */
public class PropertiesLang {

	/** The config prop. */
	private final Properties configProp = new Properties();

	/** The input eng. */
	InputStream inputEng;

	/** The input fr. */
	InputStream inputFr;

	/** The input pl. */
	InputStream prepFileListProperty;

	/** The Constant logger. */
	@Logging
	private static final Logger logger = LoggerFactory.getLogger(PropertiesLang.class);

	/** The instance. */
	private static PropertiesLang instance;

	/**
	 * Instantiates a new properties lang.
	 */
	private PropertiesLang() {
		try {

			inputEng = this.getClass().getClassLoader().getResourceAsStream("properties/messages_en.properties");
			inputFr = this.getClass().getClassLoader().getResourceAsStream("properties/messages_fr.properties");
			prepFileListProperty = this.getClass().getClassLoader().getResourceAsStream("properties/preparationlist.properties");

			String language = "fr";

			logger.info("Loading French Property File, language : {}", language);

			if (Constants.FRENCH.equals(language)) {
				configProp.load(inputFr);
			} else if (Constants.ENGLISH.equals(language)) {
				configProp.load(inputEng);
			}

			configProp.load(prepFileListProperty);

		} catch (Exception e) {
			logger.error("Error in loading Property Files", e);
		}
	}

	/**
	 * Gets the single instance of PropertiesLang.
	 * 
	 * @return single instance of PropertiesLang
	 */
	public static PropertiesLang getInstance() {
		if (instance == null) {
			instance = new PropertiesLang();
		}
		return instance;
	}

	/**
	 * Gets the property.
	 * 
	 * @param key the key
	 * @return the property
	 */
	public String getProperty(String key) {
		return configProp.getProperty(key);
	}

	/**
	 * This method is a getter to return.
	 * 
	 * @return Set Of String Value based Property Names passed.
	 */
	public Set<String> getAllPropertyNames() {
		return configProp.stringPropertyNames();
	}

	/**
	 * This method is a used to check whether key passed exists.
	 * 
	 * @param key is String value passed from property file
	 * @return boolean value if key is there
	 */
	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}

}
