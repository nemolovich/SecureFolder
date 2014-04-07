package fr.nemolovich.apps.securefolder.command;

import java.util.ArrayList;
import java.util.List;

import fr.nemolovich.apps.securefolder.SecureFolder;
import fr.nemolovich.apps.securefolder.batch.exception.BatchException;
import fr.nemolovich.apps.securefolder.command.option.HelpOption;
import fr.nemolovich.apps.securefolder.command.option.ICommandOption;
import fr.nemolovich.apps.securefolder.command.option.SecureBackOption;
import fr.nemolovich.apps.securefolder.command.option.UnzipOption;
import fr.nemolovich.apps.securefolder.command.option.ZipOption;
import fr.nemolovich.apps.securefolder.logger.ClassLogger;

public class CommandLine {

	private ClassLogger logger = new ClassLogger(CommandLine.class,
			SecureFolder.logger);
	private static final List<ICommandOption> options;
	private ICommandOption option;
	private String[] args;

	static {
		options = new ArrayList<ICommandOption>();
		options.add(new HelpOption());
		options.add(new ZipOption());
		options.add(new UnzipOption());
		options.add(new SecureBackOption());
	}

	public CommandLine(String... args) {
		this.args = args;
	}

	public boolean execute() throws BatchException {
		this.logger.setMethodName("execute");
		if(this.args.length<1) {
			this.logger.error("Please specify options");
			return false;
		}
		List<String> params = new ArrayList<String>();
		this.option = null;
		for (String arg : this.args) {
			if (arg.startsWith("--")) {
				if (arg.length() > 2) {
					if (this.option != null) {
						this.option.setParameters((String[]) params.toArray());
						break;
					}
					this.option = CommandLine.getOption(arg);
					if (this.option == null) {
						this.logger.error("Unknown option '" + arg + "'");
						return false;
					}
				} else {
					this.logger.error("Invalid option '" + arg + "'");
					return false;
				}
			} else if (arg.startsWith("-")) {
				if (arg.length() == 2) {
					if (this.option != null) {
						this.option.setParameters((String[]) params.toArray());
						break;
					}
					this.option = CommandLine.getOption(Character.valueOf(arg
							.charAt(1)));
					if (this.option == null) {
						this.logger.error("Unknown option '" + arg + "'");
						return false;
					}
				} else {
					this.logger.error("Invalid option '" + arg + "'");
					return false;
				}
			} else {
				params.add(arg);
			}
		}
		this.option.setParameters((String[]) params.toArray(new String[0]));
		return this.option.execute();
	}

	private static ICommandOption getOption(char option) {
		for (ICommandOption opt : options) {
			if (opt.getCommandChar().equals(option)) {
				return opt;
			}
		}
		return null;
	}

	private static ICommandOption getOption(String option) {
		for (ICommandOption opt : options) {
			if ("--".concat(opt.getCommandName()).equalsIgnoreCase(option)
					|| opt.getCommandName().equalsIgnoreCase(option)) {
				return opt;
			}
		}
		return null;
	}

}
