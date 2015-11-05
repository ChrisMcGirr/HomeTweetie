package actions;

public class readTime implements Command{
	
	private Receiver rcv = null;
	private String[] name = {"time","get", null};
	
	public readTime(Receiver input){
		this.rcv = input;
	}

	@Override
	public void execute() {
		rcv.getTime();		
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
