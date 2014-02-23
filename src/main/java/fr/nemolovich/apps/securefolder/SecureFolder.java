/**
 * 
 */
package fr.nemolovich.apps.securefolder;

import java.io.File;
import java.io.IOException;

import fr.nemolovich.apps.securefolder.logger.ConsoleLogger;
import fr.nemolovich.apps.securefolder.logger.FileLogger;
import fr.nemolovich.apps.securefolder.logger.LoggerManager;

/**
 * @author nemo
 * 
 */
public class SecureFolder {

	public static final LoggerManager logger = new LoggerManager();

	static {
		logger.addLogger(new ConsoleLogger().init());
		logger.addLogger(new FileLogger("log" + File.separator + "logFile.log")
				.init());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		FileUtils.hideFile("/home/nemo/Documents/Tests/", "fileTest");
//		FileUtils.unHideFile("/home/nemo/Documents/Tests/", "fileTest");
		try {
			logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
