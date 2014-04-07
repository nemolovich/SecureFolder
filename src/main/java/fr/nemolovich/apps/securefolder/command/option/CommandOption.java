package fr.nemolovich.apps.securefolder.command.option;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;

public abstract class CommandOption implements ICommandOption {

	protected ClassLogger logger;

	protected String commandName;
	protected Character commandChar;
	protected ConcurrentLinkedQueue<String> parameters;

	private CommandOption() {
		this.commandName = null;
		this.commandChar = null;
		this.parameters = null;
		this.logger = new ClassLogger(this.getClass(), SecureFolder.logger);
	}

	public CommandOption(String commandName, char commandChar) {
		this();
		this.commandName = commandName;
		this.commandChar = commandChar;
	}

	public void setParameters(String... parameters) {
		this.parameters = new ConcurrentLinkedQueue<String>(
				Arrays.asList(parameters));
	}

	public String getCommandName() {
		return this.commandName;
	}

	public Character getCommandChar() {
		return this.commandChar;
	}

}
