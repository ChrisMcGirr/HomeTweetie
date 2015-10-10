package actions;

public class readTemperature implements Command {

	private Receiver rcv = null;
	private String[] name = {"temperature", "get", null};
	
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

}
