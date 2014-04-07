package fr.nemolovich.apps.securefolder.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.batch.BatchUtils;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;
import fr.nemolovich.apps.securefolder.logger.ILogger;

public class ConfigUtils {

	private static ClassLogger logger = new ClassLogger(ConfigUtils.class,
			SecureFolder.logger);

	private static final String CONFIG_FILE_NAME = File.separator
			.concat("secureFodler.ini");
	public static final String APPLICATION_PATH;

	private static final ConfigUtils INSTANCE;

	private Properties properties;

	static {
		APPLICATION_PATH = getAbsolutePath();
		INSTANCE = new ConfigUtils();
	}

	public static ConfigUtils getInstance() {
		return INSTANCE;
	}

	private static String getAbsolutePath() {
		String path = ConfigUtils.class.getProtectionDomain().getCodeSource()
				.getLocation().getPath().concat("../../..");
		try {
			path = new File(path).getCanonicalPath();
			path = path.replaceAll("%20", " ");
		} catch (IOException e) {
			logger.error("Can not set the canonical path of the application");
		}
		return path;
	}

	public String getProperty(String key) {
		logger.setMethodName("getProperty");
		if (!this.properties.containsKey(key)) {
			logger.error("Can not found properties '" + key + "'");
			return null;
		}
		return (String) this.properties.get(key);
	}

	public List<String> getArrayProperty(String key) {
		logger.setMethodName("getArrayProperty");
		if (!this.properties.containsKey(key)) {
			logger.error("Can not found properties '" + key + "'");
			return null;
		}
		String props = (String) this.properties.get(key);
		return Arrays.asList(props.split(","));
	}

	public boolean checkConfig() {
		logger.setMethodName("checkConfig");
		return this.properties != null;
	}

	private ConfigUtils() {
		logger.setMethodName("Constructor");
		this.properties = null;
		File file = new File(APPLICATION_PATH.concat(CONFIG_FILE_NAME));
		try {
			Properties prop = new Properties();
			prop.load(new FileReader(file));
			this.properties = prop;
		} catch (FileNotFoundException e) {
			logger.error("Can not found properties file ['"
					+ file.getAbsolutePath() + "']");
		} catch (IOException e) {
			logger.error("Can not load properties file ['"
					+ file.getAbsolutePath() + "']");
		}
		if (this.properties == null) {
			logger.write("Creating new configurations file...",
					ILogger.SEVERITY_INFO);
			this.init();
		}
		logger.write("Configurations loaded", ILogger.SEVERITY_INFO);
	}

	public void save() {
		logger.setMethodName("save");
		logger.write("Saving the current configuration...",
				ILogger.SEVERITY_INFO);
		File file = new File(APPLICATION_PATH.concat(CONFIG_FILE_NAME));
		try {
			this.properties
					.store(new FileOutputStream(file),
							"Produced by Nemolovich Software\nSecure Folder Configurations file");
			logger.write("Current configurations saved", ILogger.SEVERITY_INFO);
		} catch (FileNotFoundException e) {
			logger.error("Can not found create file ['"
					+ file.getAbsolutePath() + "']");
			System.exit(0);
		} catch (IOException e) {
			logger.error("Can not save properties file ['"
					+ file.getAbsolutePath() + "']");
			System.exit(0);
		}
	}

	private void init() {
		logger.setMethodName("init");
		this.properties = new Properties();
		this.properties.put("installation.path", ".");
		this.properties.put("batchs.folder", "");
		this.save();
	}

	public void setExecutionPath() {
		logger.setMethodName("setExecutionPath");
		BatchUtils.getInstance();
		this.properties.put("installation.path",
				new File(".").getAbsolutePath());
	}

}
