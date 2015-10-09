package actions;

public class readTime implements Command{
	
	private Receiver rcv = null;
	
	public readTime(Receiver input){
		this.rcv = input;
	}

	@Override
	public void execute() {
		rcv.getTime();		
	}

}
