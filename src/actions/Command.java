package actions;

public interface Command {
	
	public void execute();
	public String[] getName();
	public String[] getStates();
	public String getCommandName();

}
