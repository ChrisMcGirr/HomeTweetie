package actions;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

import main.Messages;
import twitter4j.DirectMessage;

public class Receiver {
	private Messages messages = null;
	
	public Receiver(Messages messages){
		lightBulb.init_me(); //Start the phillips hue light
		this.messages = messages;
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
		// save image to JPEG file from
		//https://github.com/sarxos/webcam-capture/blob/master/webcam-capture/src/example/java/TakePictureExample.java
		try {
			Webcam webcam = Webcam.getDefault();
			webcam.open();
			BufferedImage image = webcam.getImage();
			ImageIO.write(image, "JPEG", new File("image.jpeg"));
			webcam.close();
		} catch (IOException e) {
			System.out.println("Failed to Save Image");
			e.printStackTrace();
		}
		
		messages.writeDM("HomeTweetie: here is the image");
		System.out.println("HomeTweet: Got Image");
	}
	public void turnOnLights(){
		messages.writeDM("HomeTweetie: lights have been turned on");
		System.out.println("HomeTweet: Lights on");
		lightBulb.TurnOnLights();
	}
	public void turnOffLights(){
		messages.writeDM("HomeTweetie: lights have been turned off");
		System.out.println("HomeTweet: Lights off");
		lightBulb.TurnOnLights();
	}
	public void turnHeatOn(){
		messages.writeDM("HomeTweetie: Heating has been turned on");
		System.out.println("HomeTweet: Heating has been turned on");
	}
}
