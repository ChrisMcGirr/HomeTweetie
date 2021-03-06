package actions;

public class setLightsOff extends Command {

	private Receiver rcv = null;
	private String[] name = {"lights", "set", "off"};
	private String commandName = "lightOff";
	private String[] states = {"light", "action", "off"};
	
	public setLightsOff(Receiver input){
		this.rcv = input;
	}
	@Override
	public void execute() {
		rcv.turnOffLights();
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
