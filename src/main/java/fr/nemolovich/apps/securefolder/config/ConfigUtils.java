package fr.nemolovich.apps.securefolder.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;

public class ConfigUtils {

	private static ClassLogger logger = new ClassLogger(ConfigUtils.class,
			SecureFolder.logger);

	private static final String CONFIG_FILE_PATH = "./secureFodler.properties";

	private static final ConfigUtils INSTANCE;

	private Properties properties;

	static {
		INSTANCE = new ConfigUtils();
	}

	public static ConfigUtils getInstance() {
		return INSTANCE;
	}

	public String getProperty(String key) {
		if (!this.properties.containsKey(key)) {
			logger.error("Can not found properties '" + key + "'");
			return null;
		}
		return (String) this.properties.get(key);
	}

	private ConfigUtils() {
		this.properties = new Properties();
		File file = new File(CONFIG_FILE_PATH);
		try {
			this.properties.load(new FileReader(file));
		} catch (FileNotFoundException e) {
			logger.error("Can not found properties file ['"
					+ file.getAbsolutePath() + "']");
			System.exit(0);
		} catch (IOException e) {
			logger.error("Can not load properties file ['"
					+ file.getAbsolutePath() + "']");
			System.exit(0);
		}
	}

}
