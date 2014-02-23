package fr.nemolovich.apps.securefolder.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;
import fr.nemolovich.apps.securefolder.logger.ILogger;

/**
 * Create and execute batch command
 * 
 * @author nemo
 * 
 */
public class BatchCommand {

	private ILogger logger = new ClassLogger(BatchCommand.class,
			SecureFolder.logger);
	/**
	 * Current command
	 */
	private String command;

	public BatchCommand(String command) {
		this.command = command;
	}

	/**
	 * Execute the command
	 * @return {@link String} - The console result of the command
	 */
	public String execute() {
		if (this.command == null) {
			logger.error("The command is not correct");
			return null;
		}
		Runtime r = Runtime.getRuntime();
		Process p = null;
		try {
			logger.write("Executing command '" + this.command + "'",
					ILogger.SEVERITY_INFO);
			p = r.exec(this.command);
			logger.write("Waiting for result for command '" + this.command
					+ "'", ILogger.SEVERITY_INFO);
			p.waitFor();
		} catch (IOException e) {
			logger.error("The command '" + this.command
					+ "' can not be executed");
			return null;
		} catch (InterruptedException e) {
			logger.error("The command '" + this.command
					+ "' has been interrupted");
			return null;
		}
		BufferedReader b = new BufferedReader(new InputStreamReader(
				p.getInputStream()));
		String line = "";
		String result = "";

		try {
			while ((line = b.readLine()) != null) {
				result += line + "\n";
			}
		} catch (IOException e) {
			logger.error("Can not get command result for '" + this.command
					+ "'");
			return null;
		}
		logger.write("Command '" + this.command + "' returned: " + result,
				ILogger.SEVERITY_INFO);
		return result;
	}
}
