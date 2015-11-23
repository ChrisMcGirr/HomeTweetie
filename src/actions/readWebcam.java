package actions;

public class readWebcam extends Command{

	private Receiver rcv = null;
	private String[] name = {"webcam", "get", "image"};
	private String commandName = "webcam";
	private String[] states = {"webcam", "action"};
	
	public readWebcam(Receiver input){
		this.rcv = input;
	}
	@Override
	public void execute() {
		rcv.getWebCamImage();
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
