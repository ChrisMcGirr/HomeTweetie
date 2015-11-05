package actions;

public class turnOnHeat implements Command{
	
	private Receiver rcv = null;
	private String[] name = {"heat", "turn", "on"};
	private String commandName = "turnOnHeat";
	private String[] states = {"weather", "home", "going"};
	
	public turnOnHeat(Receiver input){
		this.rcv = input;
	}

	@Override
	public void execute() {
		rcv.turnHeatOn();
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
