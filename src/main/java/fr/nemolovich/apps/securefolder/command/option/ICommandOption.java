package fr.nemolovich.apps.securefolder.command.option;

import fr.nemolovich.apps.securefolder.batch.exception.BatchException;


public interface ICommandOption {
	
	public void setParameters(String... parameters);
	
	public boolean execute() throws BatchException;
	
	public String getCommandName();
	
	public Character getCommandChar();

}
