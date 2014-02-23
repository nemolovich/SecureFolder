package fr.nemolovich.apps.securefolder.logger;

import java.io.IOException;
/**
 * 
 * This logger is a simple logger that add the class information from a parent
 * logger
 * 
 * @author nemo
 *
 */
public class ClassLogger implements ILogger {
	
	private String className;
	private ILogger parentLogger;
	
	/**
	 * Construct the logger from a class and set the logger in which write
	 * @param c {@link Class} - The class using this logger
	 * @param parentLogger {@link ILogger} - The logger in which write
	 */
	public ClassLogger(Class<?> c, ILogger parentLogger) {
		this.className=c.getName();
		this.parentLogger=parentLogger;
	}

	@Override
	public ILogger init() {
		return this;
	}

	@Override
	public void info(String message) {
		this.parentLogger.info(this.className+": "+message);
	}

	@Override
	public void warn(String message) {
		this.parentLogger.warn(this.className+": "+message);
	}

	@Override
	public void error(String message) {
		this.parentLogger.error(this.className+": "+message);
	}

	@Override
	public void fatal(String message) {
		this.parentLogger.fatal(this.className+": "+message);
	}

	@Override
	public void write(String message, int severity) {
		this.parentLogger.write(this.className+": "+message, severity);
	}

	@Override
	public void write(String message) throws IOException {
		this.parentLogger.write(this.className+": "+message);
	}

	@Override
	public void close() throws IOException {
		this.parentLogger.close();
	}

}
