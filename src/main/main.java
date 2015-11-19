package main;
import actions.Receiver;
import inference.*;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import actions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;


public class main {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) {
		
		String userID = "McGirrSBD";
		
		System.out.println("Warming up PIR Sensor");
		long start=System.currentTimeMillis();
		long end=0;
		while((end-start) < 5000){
			end=System.currentTimeMillis();
		}
		System.out.println("Starting PIR Sensor");
		MotionDetector test = new MotionDetector();
		
		System.out.println("20 seconds left in program");
		start=System.currentTimeMillis();
		end=0;
		while((end-start) < 20000){
			end=System.currentTimeMillis();
		}
		/*
		 * Twitter twitter = ConfigureTwitter.createTwitter();
		 * Messages messages = Messages.getInstance(twitter, userID);
		 * geoLocation geoLoc = new geoLocation(twitter, userID);
		 * Weather weather = new Weather("Delft", 18);
		 * TwitterFeedListener feedListener = new TwitterFeedListener(twitter, userID);
		 * scheduler.scheduleAtFixedRate(feedListener, 0, 70, TimeUnit.SECONDS);
		 * MessageListener listener = new MessageListener(messages);
		 * scheduler.scheduleAtFixedRate(listener, 35, 70, TimeUnit.SECONDS);
		 */
	}
	
	

	
	
}
