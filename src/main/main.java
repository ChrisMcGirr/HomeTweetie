package main;

import twitter4j.Twitter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.ds.v4l4j.V4l4jDriver;


public class main {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	/*Used for the Webcam Capture Library from Sarxos on the Raspberry Pi
	 * At the moment it is not used. */
	/*
	static {
	    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
	    System.setProperty("org.slf4j.simpleLogger.log.com.github.sarxos.webcam.ds.v4l4j", "trace");
	    Webcam.setDriver(new V4l4jDriver());
	}
	*/
	
	public static void main(String[] args) {
		
		/*Twitter ID of the Home Owner Twitter Account*/
		String userID = "McGirrSBD";
		
		
		/*The PIR needs to stabilize first give it 5 seconds*/
		System.out.println("Warming up PIR Sensor");
		long start=System.currentTimeMillis();
		long end=0;
		while((end-start) < 5000){
			end=System.currentTimeMillis();
		}

		/*Create Twitter object based on App Keys in Configure Twitter*/
		Twitter twitter = ConfigureTwitter.createTwitter();
		/*Create Message object for writing and read from UserID messages and HomeTweetie's*/
		Messages messages = Messages.getInstance(twitter, userID);
		
		/*Initialize the geolocation object and weather objects before the listeners*/
		geoLocation geoLoc = new geoLocation(twitter, userID);
		Weather weather = new Weather("Delft", 18);
		
		/*Initialize the static media object*/
		MediaTwitter media = new MediaTwitter(twitter);
		
		/*Start the Twitter Feed Listener for inferring commands from tweets*/
		TwitterFeedListener feedListener = new TwitterFeedListener(twitter, userID);
		scheduler.scheduleAtFixedRate(feedListener, 0, 70, TimeUnit.SECONDS);
		
		/*Schedule Direct Command Receiving from User's Direct Messages*/
		MessageListener listener = new MessageListener(messages, userID);
		scheduler.scheduleAtFixedRate(listener, 35, 70, TimeUnit.SECONDS);
		
		/*Start the Motion Detecting Thread to detect motion*/
		System.out.println("Starting PIR Sensor");
		MotionDetector test = new MotionDetector(listener.getReceiver());
		System.out.println("PIR Sensor has Started");
	}
	
	

	
	
}
