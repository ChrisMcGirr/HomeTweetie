package actions;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

import main.MediaTwitter;
import main.Messages;
import main.Weather;


public class Receiver {
	private Messages messages = null;
	private MediaTwitter media = null;
	private String userID = null;
	
	/*
	 * Constructor Method initializes phillips hue bulb and sets variables
	 */
	public Receiver(Messages messages, String id){
		lightBulb.init_me(); //Start the phillips hue light
		this.messages = messages;
		userID = id;
	}
	
	/*Returns to the Home Owner the temperature outside*/
	public void getTemperature(){
		messages.writeDM("HomeTweetie: The current temperature outside is " + Weather.getTemperature() +"^C");
		System.out.println("HomeTweetie: Get Temperature!");
	}
	/*Returns to the Home Owner the current Time. This method was used for debugging and
	 * testing mainly*/
	public void getTime(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		messages.writeDM("HomeTweetie: the date is "+ dateFormat.format(date));
		System.out.println("HomeTweetie: Time has been sent");
	}
	/*Post Image from Webcam to HomeTweetie twitter feed*/
	public void getWebCamImage(){
		//save image to PNG file from
		//https://github.com/sarxos/webcam-capture/blob/master/webcam-capture/src/example/java/TakePictureExample.java
		/*try {
			Webcam webcam = Webcam.getDefault();
			webcam.open();
			ImageIO.write(webcam.getImage(), "PNG", new File("hello-world.png"));
			webcam.close();
		} catch (IOException e) {
			System.out.println("Failed to Save Image");
			e.printStackTrace();
		}
		*/
		/*Real work around code here. Due to the incompatibility of the debian arm version for Raspberry Pi
		 * we had to use a script instead of the friendly library. Essential the script saves the image
		 * to the local filesystem and we read that file back in java and post it to the twitter feed
		 * Another drawback is the fact that media in Direct Messages are not implemented in the public api*/
		try {
			ProcessBuilder pb = new ProcessBuilder("/home/pi/Desktop/grabImage.sh");
			Process p = pb.start();     // Start the process.
			p.waitFor();                // Wait for the process to finish.
			System.out.println("Script executed successfully");
			MediaTwitter.postImage(new File("/home/pi/Desktop/image.jpg"), "Image from HomeTweetie " + "@" + userID);
		} catch (Exception e) {
			System.out.println("Failed to execute Script");
			e.printStackTrace();
		}
		String[] links = MediaTwitter.getImageLink();
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < links.length; i++){
			   strBuilder.append( links[i] );
		}
		String link = strBuilder.toString();
		
		messages.writeDM("HomeTweetie: here is are the links "+link);
		System.out.println("HomeTweet: Got Image");
	}
	/*
	 * Posts image to HomeTweetie Twitter Feed with the status string associated with it. And Sends the link to the
	 * home owner in case the @ reply does not work.
	 * */
	public void getWebCamImage(String status){
		try {
			ProcessBuilder pb = new ProcessBuilder("/home/pi/Desktop/grabImage.sh");
			Process p = pb.start();     // Start the process.
			p.waitFor();                // Wait for the process to finish.
			System.out.println("Script executed successfully");
			MediaTwitter.postImage(new File("/home/pi/Desktop/image.jpg"), status +" " + "@" + userID + " @itsmuneebyousaf");
		} catch (Exception e) {
			System.out.println("Failed to execute Script");
			e.printStackTrace();
		}
		String[] links = MediaTwitter.getImageLink();
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < links.length; i++){
			   strBuilder.append( links[i] );
		}
		String link = strBuilder.toString();
		
		messages.writeDM("HomeTweetie: motion has been detected in your home. Here is the "+link);
		System.out.println("HomeTweet: Got Image");
	}
	/*Turns on the lights*/
	public void turnOnLights(){
		messages.writeDM("HomeTweetie: lights have been turned on");
		System.out.println("HomeTweet: Lights on");
		lightBulb.TurnOnLights();
	}
	/*turns off the lights*/
	public void turnOffLights(){
		messages.writeDM("HomeTweetie: lights have been turned off");
		System.out.println("HomeTweet: Lights off");
		lightBulb.TurnOffLights();
	}
	/*Turns on the heat*/
	public void turnHeatOn(){
		messages.writeDM("HomeTweetie: Heating has been turned on");
		System.out.println("HomeTweet: Heating has been turned on");
	}
}
