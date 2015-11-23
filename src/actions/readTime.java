package actions;

public class readTime extends Command{
	
	private Receiver rcv = null;
	private String commandName = "time";
	private String[] name = {"time","get", null};
	private String[] states = {"question", "time"};
	
	public readTime(Receiver input){
		this.rcv = input;
	}

	@Override
	public void execute() {
		rcv.getTime();		
	}
	@Override
	public String[] getName() {
		return name;
	}
	public String getCommandName() {
		return commandName;
	}
	@Override
	public String[] getStates() {
		return states;
	}
}
