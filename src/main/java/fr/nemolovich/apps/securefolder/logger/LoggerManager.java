package fr.nemolovich.apps.securefolder.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a logger that contains many loggers and use them for all
 * operations
 * 
 * @author nemo
 * 
 */
public class LoggerManager implements ILogger {
	/**
	 * The loggers list
	 */
	private List<ILogger> loggers;

	public LoggerManager() {
		this.init();
	}

	/**
	 * Add a logger in the loggers list
	 * @param logger {@link ILogger} - The logger to add
	 */
	public void addLogger(ILogger logger) {
		this.loggers.add(logger);
	}

	@Override
	public ILogger init() {
		this.loggers = new ArrayList<ILogger>();
		return this;
	}

	@Override
	public void info(String message) {
		for (ILogger logger : this.loggers) {
			logger.info(message);
		}
	}

	@Override
	public void warn(String message) {
		for (ILogger logger : this.loggers) {
			logger.warn(message);
		}
	}

	@Override
	public void error(String message) {
		for (ILogger logger : this.loggers) {
			logger.error(message);
		}
	}

	@Override
	public void fatal(String message) {
		for (ILogger logger : this.loggers) {
			logger.fatal(message);
		}
	}

	@Override
	public void write(String message, int severity) {
		for (ILogger logger : this.loggers) {
			logger.write(message, severity);
		}
	}

	@Override
	public void write(String message) throws IOException {
		for (ILogger logger : this.loggers) {
			logger.write(message);
		}
	}

	@Override
	public void close() throws IOException {
		for (ILogger logger : this.loggers) {
			logger.close();
		}
	}

}
