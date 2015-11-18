package main;
import actions.Receiver;
import inference.*;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import twitter4j.Twitter;
import actions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;


public class main {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) {
		
		/*
		Twitter twitter = ConfigureTwitter.createTwitter();
		Messages messages = Messages.getInstance(twitter, "McGirrSBD");
		geoLocation geoLoc = new geoLocation(twitter, "McGirrSBD");
		/*
		MessageListener listener = new MessageListener(messages);
		scheduler.scheduleAtFixedRate(listener, 0, 65, TimeUnit.SECONDS);
		*/
		
		Weather weather = new Weather("Delft", 18);
		Inferrer infer = new Inferrer("dictionary.json");
		infer.initialProbability();
		
		turnOnHeat command = new turnOnHeat(null);
		Classifier classifyHeating = new Classifier(infer);
		boolean result = classifyHeating.isValidCommand("Today was a cold day and rainy day. Going to go back home to a nice warm fire :P.", command);
		System.out.println("The Result is "+result);
	}
	
	

	
	
}
