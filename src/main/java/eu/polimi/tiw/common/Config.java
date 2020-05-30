package eu.polimi.tiw.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Andrea Ruffo
 * @since 0.0.1-SNAPSHOT
 * 
 *        This class will handle the properties load and store.
 */
public class Config {

	private final String configName = "config.properties";
	private static Config instance;

	private Config() {
	}

	// Lazy initialization singleton pattern
	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	/**
	 * @param propertyName
	 * @return the value associated to the property name.
	 */
	public String getProperty(String propertyName) {

		String propValue = "";
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(configName)) {

			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// Return properties value
			propValue = prop.getProperty(propertyName);

		} catch (IOException ex) {
			System.err.println("There was an error during the property load.");
			ex.printStackTrace();
		}

		return propValue;
	}

}
