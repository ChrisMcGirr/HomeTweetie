package actions;

public class setLightsOn extends Command {

	private Receiver rcv = null;
	private String[] name = {"lights", "set", "on"};
	private String commandName = "lightOn";
	private String[] states = {"light", "action", "on"};
	
	public setLightsOn(Receiver input){
		this.rcv = input;
	}
	@Override
	public void execute() {
		rcv.turnOnLights();
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
