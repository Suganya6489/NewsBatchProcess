package com.scansee.batch.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * The class for reading property values through PropertiesReader
 * 
 * @author vaidehi.ne
 */
public final class PropertiesReader {
	/**
	 * Variable prop declared as Properties.
	 */
	private static Properties prop; // Properties
	/**
	 * Get logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(PropertiesReader.class);

	/**
	 * Private constructor to avoid instantiation of this object.
	 */
	private PropertiesReader() {
	}

	/**
	 * This method takes the property name and returns the value of it in the
	 * properties file.
	 * 
	 * @param strPropertyName
	 *            The property name
	 * @return strPropertyValue The property value
	 */

	public static synchronized String getPropertyValue(final String strPropertyName) {
		String strPropertyValue = "";
		final InputStream inputStream = null;
		try {
			if (prop == null) {
				prop = new Properties();
				FileInputStream input = new FileInputStream("newsTemplateBatch.properties");
				prop.load(input);
			}
			strPropertyValue = (String) prop.get(strPropertyName);
		} catch (IOException ioe) {
			LOG.error(ioe.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOG.error("Exception:" + e.toString());
				}
			}
		}
		return strPropertyValue;
	}
}
