package fr.nemolovich.apps.securefolder.command.option;


public interface ICommandOption {
	
	public void setParameters(String... parameters);
	
	public boolean execute();
	
	public String getCommandName();
	
	public Character getCommandChar();

}
