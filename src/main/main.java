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
	static {
	    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "info");
	    System.setProperty("org.slf4j.simpleLogger.log.com.github.sarxos.webcam.ds.v4l4j", "trace");
	    Webcam.setDriver(new V4l4jDriver());
	}
	
	public static void main(String[] args) {
		
		String userID = "McGirrSBD";
		
		
		System.out.println("Warming up PIR Sensor");
		long start=System.currentTimeMillis();
		long end=0;
		while((end-start) < 5000){
			end=System.currentTimeMillis();
		}

		
		Twitter twitter = ConfigureTwitter.createTwitter();
		Messages messages = Messages.getInstance(twitter, userID);
		
		geoLocation geoLoc = new geoLocation(twitter, userID);
		Weather weather = new Weather("Delft", 18);
		MediaTwitter media = new MediaTwitter(twitter);
		
		/*
		TwitterFeedListener feedListener = new TwitterFeedListener(twitter, userID);
		scheduler.scheduleAtFixedRate(feedListener, 0, 70, TimeUnit.SECONDS);
		*/
		
		MessageListener listener = new MessageListener(messages, userID);
		scheduler.scheduleAtFixedRate(listener, 35, 70, TimeUnit.SECONDS);
		
		/*
		System.out.println("Starting PIR Sensor");
		MotionDetector test = new MotionDetector(listener.getReceiver());
		System.out.println("PIR Sensor has Started");
		*/
	}
	
	

	
	
}
