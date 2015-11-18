package actions;

public class setLightsOn implements Command {

	private Receiver rcv = null;
	private String[] name = {"lights", "set", "on"};
	private String[] states = {"on"};
	
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
}
