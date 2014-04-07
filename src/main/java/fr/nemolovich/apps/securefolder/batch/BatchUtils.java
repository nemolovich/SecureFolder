/**
 * 
 */
package fr.nemolovich.apps.securefolder.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.Properties;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.batch.exception.BatchException;
import fr.nemolovich.apps.securefolder.config.ConfigUtils;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;
import fr.nemolovich.apps.securefolder.logger.ILogger;

/**
 * 
 * @author nemo
 * 
 */
public class BatchUtils {

	private static ClassLogger logger = new ClassLogger(BatchUtils.class,
			SecureFolder.logger);
	private static char delimiter = '%';

	private static final String BATCHS_PATH;
	private static final String BATCHS_INI = "batchs.ini";
	private static BatchUtils INSTANCE = null;
	private Properties properties;

	static {
		String path = ConfigUtils.getInstance().getProperty("batchs.folder");
		BATCHS_PATH = path.isEmpty() ? "batchs" : path;
		try {
			INSTANCE = init();
		} catch (BatchException e) {
			logger.setMethodName("<init>");
			BatchUtils.logger.error("Can not initialize BatchUtils");
		}
	}

	private BatchUtils(Properties properties) {
		this.properties = properties;
	}

	public static BatchUtils getInstance() {
		return INSTANCE;
	}

	private static BatchUtils init() throws BatchException {
		logger.setMethodName("init");
		String os = System.getProperty("os.name").toLowerCase();
		BatchUtils.logger.write("Operating System is: " + os,
				ILogger.SEVERITY_INFO);

		Properties prop = new Properties();
		String fileName = BATCHS_PATH.concat(File.separator).concat(BATCHS_INI);
		try {
			BatchUtils.logger.write(
					"Loading config file: ['".concat(BATCHS_PATH)
							.concat(File.separator).concat(BATCHS_INI)
							.concat("']"), ILogger.SEVERITY_INFO);
			String streamPath = "/".concat(fileName.replaceAll("\\\\", "/"));
			Reader r = new InputStreamReader(
					BatchUtils.class.getResourceAsStream(streamPath));
			prop.load(r);
		} catch (IOException e) {
			try {
				System.out.println((new File(BATCHS_PATH).getAbsolutePath())
						.concat(fileName));
				prop.load(new FileReader(new File(new File("./")
						.getCanonicalPath().concat(File.separator)
						.concat(fileName))));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		InputStream propFile = null;
		String propFileName = null;
		for (Object file : prop.keySet()) {
			propFileName = ((String) prop.get(file));
			if (os.contains(propFileName.substring(0,
					propFileName.lastIndexOf(".")))) {
				String path = "/".concat(BATCHS_PATH).concat(File.separator)
						.concat(propFileName).replaceAll("\\\\", "/");
				propFile = BatchUtils.class.getResourceAsStream(path);
				break;
			}
		}
		if (propFileName == null || propFile == null) {
			BatchUtils.logger
					.error("Could not load Operating System properties file");
			System.exit(0);
		}
		BatchUtils.logger.write("Properties file to use: ['" + propFileName
				+ "']", ILogger.SEVERITY_INFO);
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(propFile));
		} catch (FileNotFoundException e) {
			BatchUtils.logger.error("The properties file can not be found");
			return null;
		} catch (IOException e) {
			BatchUtils.logger.error("Can not load the properties file");
			return null;
		}
		BatchUtils.logger.write("Properties file ['" + propFileName
				+ "'] loaded", ILogger.SEVERITY_INFO);
		return new BatchUtils(properties);
	}

	public BatchCommand createCommand(String commandKey, String... parameters) {
		logger.setMethodName("createCommand");
		String finalCommand = "";
		BatchUtils.logger.write("Looking for command key: " + commandKey,
				ILogger.SEVERITY_INFO);
		if (!this.properties.containsKey(commandKey)) {
			BatchUtils.logger.error("Properties does not conatin the key '"
					+ commandKey + "'");
			return null;
		}
		finalCommand = this.properties.getProperty(commandKey);
		BatchUtils.logger.write("Command found: " + finalCommand,
				ILogger.SEVERITY_INFO);
		BatchUtils.logger.write(
				"Applying parameters: " + Arrays.toString(parameters),
				ILogger.SEVERITY_INFO);
		for (int i = 0; i < parameters.length; i++) {
			if (finalCommand.contains(delimiter + String.valueOf(i + 1)
					+ delimiter)) {
				finalCommand = finalCommand.replaceAll(
						delimiter + String.valueOf(i + 1) + delimiter,
						parameters[i].replaceAll("\\\\", "\\\\\\\\"));
			}
		}
		BatchUtils.logger.write("Final Command: " + finalCommand,
				ILogger.SEVERITY_INFO);
		return new BatchCommand(finalCommand);
	}

}
