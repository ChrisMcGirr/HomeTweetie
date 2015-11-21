package actions;

public class readTemperature implements Command {

	private Receiver rcv = null;
	private String[] name = {"temperature", "get", null};
	private String commandName = "temperature";
	private String[] states = {"action", "question", "temperature"};
	
	public readTemperature(Receiver input){
		this.rcv = input;
	}
	@Override
	public void execute() {
		rcv.getTemperature();
	}
	@Override
	public String[] getName() {
		return name;
	}
	@Override
	public String[] getStates() {
		return states;
	}
	@Override
	public String getCommandName() {
		return commandName;
	}

}
