/**
 * 
 */
package fr.nemolovich.apps.securefolder;

import java.io.File;
import java.io.IOException;

import fr.nemolovich.apps.securefolder.command.CommandLine;
import fr.nemolovich.apps.securefolder.logger.ConsoleLogger;
import fr.nemolovich.apps.securefolder.logger.FileLogger;
import fr.nemolovich.apps.securefolder.logger.LoggerManager;

/**
 * @author nemo
 * 
 */
public class SecureFolder {

	public static final String EXTENSION_LOCK = "sflock";
	public static final String EXTENSION_UNLOCK = "sfunlock";

	public static final LoggerManager logger = new LoggerManager();

	static {
		logger.addLogger(new ConsoleLogger().init());
		logger.addLogger(new FileLogger("resources".concat(File.separator)
				.concat("log").concat(File.separator).concat("logFile.log"))
				.init());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CommandLine commandLine = new CommandLine(args);
		commandLine.execute();
		try {
			logger.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
