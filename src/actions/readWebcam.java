package actions;

public class readWebcam implements Command{

	private Receiver rcv = null;
	private String[] name = {"webcam", "get", "image"};
	
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

}