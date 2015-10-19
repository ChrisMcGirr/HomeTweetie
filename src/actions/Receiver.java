package actions;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import main.Messages;
import org.bulldog.beagleboneblack.BBBNames;
import org.bulldog.core.Signal;
import org.bulldog.core.gpio.DigitalInput;
import org.bulldog.core.platform.Board;
import org.bulldog.core.platform.Platform;
import org.bulldog.core.util.BulldogUtil;
import org.bulldog.devices.switches.Button;
import org.bulldog.devices.switches.ButtonListener;

public class Receiver {
	private static String LED0_PATH = "/sys/class/leds/beaglebone:green:usr0";
	private Messages messages = null;
	Board board = Platform.createBoard();
    DigitalInput buttonSignal = board.getPin(BBBNames.P8_12).as(DigitalInput.class);
    Button button = new Button(buttonSignal, Signal.Low);
	
	public Receiver(Messages messages){
		this.messages = messages;
        button.addListener(new ButtonListener() {
            public void buttonPressed() {
                System.out.println("PRESSED");
            }
            public void buttonReleased() {
                System.out.println("RELEASED");
            }
        });
	}
	
	public void getTemperature(){
		messages.writeDM("HomeTweetie: the temperature is 23^C");
		System.out.println("HomeTweetie: Get Temperature!");
	}
	public void getTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		messages.writeDM("HomeTweetie: the date is "+ dateFormat.format(date));
		System.out.println("HomeTweetie: Time has been sent");
	}
	public void getWebCamImage(){
		messages.writeDM("HomeTweetie: here is the image");
		System.out.println("HomeTweet: Got Image");
	}
	public void turnOnLights(){
		try{
			BufferedWriter bw = new BufferedWriter( new FileWriter (LED0_PATH+"/trigger"));
			bw.write("none");
			bw.close();
			bw = new BufferedWriter( new FileWriter (LED0_PATH+"/brightness"));
			bw.write("1");
			bw.close();
		}
		catch(IOException e){
			System.out.println("Failed to access the Beaglebone LEDs");
		}
		messages.writeDM("HomeTweetie: lights have been turned on");
		System.out.println("HomeTweet: Lights on");
	}
	public void turnOffLights(){
		try{
			BufferedWriter bw = new BufferedWriter( new FileWriter (LED0_PATH+"/trigger"));
			bw.write("none");
			bw.close();
			bw = new BufferedWriter( new FileWriter (LED0_PATH+"/brightness"));
			bw.write("0");
			bw.close();
		}
		catch(IOException e){
			System.out.println("Failed to access the Beaglebone LEDs");
		}
		messages.writeDM("HomeTweetie: lights have been turned off");
		System.out.println("HomeTweet: Lights off");
	}
}
