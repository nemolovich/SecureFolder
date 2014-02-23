package fr.nemolovich.apps.securefolder.logger;

import java.io.IOException;

/**
 * This interface is used for loggers
 * 
 * @author nemo
 * 
 */
public interface ILogger {

	/**
	 * The severity for information message
	 */
	public static final int SEVERITY_INFO = 0;
	/**
	 * The severity for warning message
	 */
	public static final int SEVERITY_WARNING = 1;
	/**
	 * The severity for error message
	 */
	public static final int SEVERITY_ERROR = 2;
	/**
	 * The severity for fatal error message
	 */
	public static final int SEVERITY_FATAL = 3;

	/**
	 * Initialize the logger resources
	 * 
	 * @return {@link ILogger} - The logger initialized
	 */
	public abstract ILogger init();

	/**
	 * Display information message
	 * 
	 * @param message
	 *            {@link String} - The message to display
	 */
	public abstract void info(String message);

	/**
	 * Display warning message
	 * 
	 * @param message
	 *            {@link String} - The message to display
	 */
	public abstract void warn(String message);

	/**
	 * Display error message
	 * 
	 * @param message
	 *            {@link String} - The message to display
	 */
	public abstract void error(String message);

	/**
	 * Display fatal error message
	 * 
	 * @param message
	 *            {@link String} - The message to display
	 */
	public abstract void fatal(String message);

	/**
	 * Write a message with given severity<br>
	 * Available severities:
	 * <ul>
	 * <li>{@link ILogger#SEVERITY_INFO}: Information severity</li>
	 * <li>{@link ILogger#SEVERITY_WARNING}: Warning severity</li>
	 * <li>{@link ILogger#SEVERITY_ERROR}: Error severity</li>
	 * <li>{@link ILogger#SEVERITY_FATAL}: Fatal error severity</li>
	 * </ul>
	 * 
	 * @param message
	 *            {@link String} - The message to display
	 * @param severity
	 *            {@link Integer int} - The severity
	 */
	public abstract void write(String message, int severity);

	/** 
	 * Write a message
	 * 
	 * @param message
	 * @throws IOException
	 */
	public abstract void write(String message) throws IOException;

	/**
	 * Close logger resources
	 * @throws IOException
	 */
	public abstract void close() throws IOException;

}