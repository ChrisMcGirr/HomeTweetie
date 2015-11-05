package actions;

public class setLightsOn implements Command {

	private Receiver rcv = null;
	private String[] name = {"lights", "set", "on"};
	
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
		// TODO Auto-generated method stub
		return null;
	}
}
