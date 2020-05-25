package com.inetpsa.pv2.props;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.pv2.batch.PictoConstants;
import com.inetpsa.pv2.service.AdminDetails;

/**
 * The Class PropertiesLang.
 */
public class PropertiesLang {

	/** The config prop. */
	private final Properties configProp = new Properties();

	/** The input eng. */
	InputStream inputEng = null;

	/** The input fr. */
	InputStream inputFr = null;

	/** The Constant logger. */
	@Logging
	private static final Logger logger = LoggerFactory
			.getLogger(PropertiesLang.class);

	/** The instance. */
	private static PropertiesLang instance = null;

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
	 * Instantiates a new properties lang.
	 */
	private PropertiesLang() {
		try {
			inputEng = this.getClass().getClassLoader()
					.getResourceAsStream("properties/Messages_en.properties");
			inputFr = this.getClass().getClassLoader()
					.getResourceAsStream("properties/Messages_fr.properties");

			String language = AdminDetails.getInstance().getLanguage();
			if (PictoConstants.FRENCH.equals(language)) {
				logger.info("Loading Messages_fr.properties started.");
				configProp.load(inputFr);
				logger.info("Loading Messages_fr.properties completed.");
			} else {
				logger.info("Loading Messages_en.properties started.");
				configProp.load(inputEng);
				logger.info("Loading Messages_en.properties completed.");
			}

		} catch (Exception e) {
			logger.error("Error in loading Property Files", e);
		}
	}

	/**
	 * Gets the property.
	 *
	 * @param key
	 *            the key
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
	 * @param key
	 *            is String value passed from property file
	 * @return boolean value if key is there
	 */
	public boolean containsKey(String key) {
		return configProp.containsKey(key);
	}

}
