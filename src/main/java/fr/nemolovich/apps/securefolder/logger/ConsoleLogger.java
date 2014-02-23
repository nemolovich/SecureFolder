package fr.nemolovich.apps.securefolder.logger;

import java.io.IOException;

/**
 * This logger is a simple logger that add the class information from a parent
 * logger
 * 
 * @author nemo
 * 
 */
public class ConsoleLogger implements ILogger {
	/**
	 * The information prefix to add before message
	 */
	public static final String PREFIX_INFO = "INFO";
	/**
	 * The warning prefix to add before message
	 */
	public static final String PREFIX_WARNING = "WARNING";
	/**
	 * The error prefix to add before message
	 */
	public static final String PREFIX_ERROR = "ERROR";
	/**
	 * The fatal error prefix to add before message
	 */
	public static final String PREFIX_FATAL = "FATAL";

	public ConsoleLogger() {
	}

	@Override
	public ILogger init() {
		System.out.println("Console logger started.");
		return this;
	}

	@Override
	public void info(String message) {
		try {
			this.write("[" + PREFIX_INFO + "] " + message);
		} catch (IOException e) {
			// WRITING ERROR
		}
	}

	@Override
	public void warn(String message) {
		try {
			this.write("[" + PREFIX_WARNING + "] " + message);
		} catch (IOException e) {
			// WRITING ERROR
		}
	}

	@Override
	public void error(String message) {
		try {
			this.write("[" + PREFIX_ERROR + "] " + message);
		} catch (IOException e) {
			// WRITING ERROR
		}
	}

	@Override
	public void fatal(String message) {
		try {
			this.write("[" + PREFIX_FATAL + "] " + message);
		} catch (IOException e) {
			// WRITING ERROR
		}
	}

	@Override
	public void write(String message, int severity) {
		String prefix = "";
		switch (severity) {
		case SEVERITY_INFO:
			prefix = "[" + PREFIX_INFO + "] ";
			break;
		case SEVERITY_WARNING:
			prefix = "[" + PREFIX_WARNING + "] ";
			break;
		case SEVERITY_ERROR:
			prefix = "[" + PREFIX_ERROR + "] ";
			break;
		case SEVERITY_FATAL:
			prefix = "[" + PREFIX_FATAL + "] ";
			break;
		default:
			break;
		}
		try {
			this.write(prefix + message);
		} catch (IOException e) {
			// WRITING ERROR
		}
	}

	@Override
	public void write(String message) throws IOException {
		System.out.println(message);
	}

	@Override
	public void close() throws IOException {
		System.out.println("Console logger closed.");
	}
}
