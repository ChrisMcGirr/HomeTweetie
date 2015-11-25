package actions;

public class Command {
	
	/*
	 * Main Parent Class of all commands. All commands follow similar format
	 */
	private Receiver rcv; /*Where the functionality is stored*/
	private String[] name; /*the words associated for the default direct command pattern*/
	private String commandName; /*command name*/
	private String[] states; /*states associated with the command*/
	
	/*Executes the functionality*/
	public void execute() {
	}
	
	/*Returns the array of words associated with the direct command*/
	public String[] getName() {
		return null;
	}
	/*returns the states associated with this command*/
	public String[] getStates() {
		return null;
	}
	/*returns the name of this command*/
	public String getCommandName() {
		return null;
	}

}
