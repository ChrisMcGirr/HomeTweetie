package actions;

public class setLightsOff implements Command {

	private Receiver rcv = null;
	private String[] name = {"lights", "set", "off"};
	
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
		// TODO Auto-generated method stub
		return null;
	}

}
