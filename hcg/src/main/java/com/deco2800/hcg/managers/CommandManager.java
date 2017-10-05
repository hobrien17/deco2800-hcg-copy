package com.deco2800.hcg.managers;

import java.util.HashMap;

/**
 * Console command manager
 * 
 * @author Max Crofts
 */
public class CommandManager extends Manager {

	private HashMap<String, Command> commands = new HashMap<>();
	
	/**
	 * Registers command
	 * 
	 * @param name Command name
	 * @param command Command object to be registered
	 * @return <code>true</code> if <code>name</code> was not already in use
	 */
	public boolean registerCommand(String name, Command command) {
		if (commands.get(name) == null) {
			commands.put(name, command);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Deregisters command
	 * 
	 * @param name Command name
	 */
	public void deregisterCommand(String name) {
		commands.remove(name);
	}
	
	/**
	 * Runs specified command
	 * 
	 * <p>
	 * Note <code>args[0]</code> is the command name
	 * </p>
	 * 
	 * @param args Command arguments
	 * @return Command message
	 */
	public String runCommand(String... args) {
		Command command = commands.get(args[0]);
		if (command != null) {
			return command.run(args);
		} else {
			return "Command \"" + args[0] + "\" not found";
		}
	}
	
	/**
	 * Console command interface
	 */
	@FunctionalInterface
	public interface Command {

		/**
		 * Runs the command
		 * 
		 * <p>
		 * Note <code>args[0]</code> is the command name
		 * </p>
		 * 
		 * @param args Command arguments
		 * @return Command message
		 */
		public String run(String... args);
	    
	}
	
}
