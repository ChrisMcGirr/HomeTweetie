package actions;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import main.Messages;

public class Receiver {
	
	private Messages messages = null;
	
	public Receiver(Messages messages){
		this.messages = messages;
	}
	
	public void getTemperature(){
		
	}
	public void getTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		messages.writeDM("HomeTweetie: the date is "+dateFormat.format(date));
		System.out.println("Receiver: Time has been sent");
	}
	public void getWebCamImage(){
		System.out.println("HomeTweet: Got Image");
	}
	public void turnOnLights(){
		System.out.println("HomeTweet: Lights on");
	}
	public void turnOffLights(){
		System.out.println("HomeTweet: Lights off");
	}
}
