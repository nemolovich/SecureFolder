package fr.nemolovich.apps.securefolder.command.option;


public class HelpOption extends CommandOption {

	public HelpOption() {
		super("help", 'h');
	}

	@Override
	public boolean execute() {
		this.logger.setMethodName("execute");
		if (this.parameters == null || this.parameters.isEmpty()) {
			this.logger.info("\nUsage:\t--" + this.commandName + ",-"
					+ this.commandChar + ":\t\t" + "Display commands list\n"
					+ "\t--" + this.commandName + ",-" + this.commandChar
					+ " <command>:\t" + "Display specific command help");
			return true;
		} else {
			this.parameters.clear();
			return this.execute();
		}
	}

}
