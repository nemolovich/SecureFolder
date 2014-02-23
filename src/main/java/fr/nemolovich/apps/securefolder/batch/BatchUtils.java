/**
 * 
 */
package fr.nemolovich.apps.securefolder.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;
import fr.nemolovich.apps.securefolder.logger.ILogger;

/**
 * 
 * @author nemo
 * 
 */
public class BatchUtils {

	private static ClassLogger logger = new ClassLogger(BatchUtils.class,
			SecureFolder.logger);;
	private static char delimiter = '%';

	public static final String BATCHS_PATH = "resources" + File.separator
			+ "batchs" + File.separator;
	public static BatchUtils INSTANCE = null;
	private Properties properties;

	private BatchUtils(Properties properties) {
		this.properties = properties;
	}

	public static BatchUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = BatchUtils.init();
		}
		return INSTANCE;
	}

	private static BatchUtils init() {
		logger.setMethodName("init");
		String os = System.getProperty("os.name").toLowerCase();
		BatchUtils.logger.write("Operating System is: " + os, ILogger.SEVERITY_INFO);
		File propFile = null;
		for (String file : new File(BATCHS_PATH).list()) {
			if (os.contains(file.substring(0, file.lastIndexOf(".")))) {
				propFile = new File(BATCHS_PATH + file);
				break;
			}
		}
		BatchUtils.logger.write("Properties file to use: ['" + propFile.getAbsolutePath()
				+ "']", ILogger.SEVERITY_INFO);
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(propFile));
		} catch (FileNotFoundException e) {
			BatchUtils.logger.error("The properties file can not be found");
			return null;
		} catch (IOException e) {
			BatchUtils.logger.error("Can not load the properties file");
			return null;
		}
		BatchUtils.logger.write("Properties file ['" + propFile.getAbsolutePath()
				+ "'] loaded", ILogger.SEVERITY_INFO);
		return new BatchUtils(properties);
	}

	public BatchCommand createCommand(String commandKey, String... parameters) {
		logger.setMethodName("createCommand");
		String finalCommand = "";
		BatchUtils.logger.write("Looking for command key: " + commandKey,
				ILogger.SEVERITY_INFO);
		if (!this.properties.containsKey(commandKey)) {
			BatchUtils.logger.error("Properties does not conatin the key '" + commandKey
					+ "'");
			return null;
		}
		finalCommand = this.properties.getProperty(commandKey);
		BatchUtils.logger.write("Command found: " + finalCommand, ILogger.SEVERITY_INFO);
		BatchUtils.logger.write("Applying parameters: " + Arrays.toString(parameters),
				ILogger.SEVERITY_INFO);
		for (int i = 0; i < parameters.length; i++) {
			if (finalCommand.contains(delimiter + String.valueOf(i + 1)
					+ delimiter)) {
				finalCommand = finalCommand.replaceAll(
						delimiter + String.valueOf(i + 1) + delimiter,
						parameters[i].replaceAll("\\\\", "\\\\\\\\"));
			}
		}
		BatchUtils.logger.write("Final Command: " + finalCommand, ILogger.SEVERITY_INFO);
		return new BatchCommand(finalCommand);
	}

}
