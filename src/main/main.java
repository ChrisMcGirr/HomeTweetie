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
		
		
		Twitter twitter = ConfigureTwitter.createTwitter();
		Messages messages = Messages.getInstance(twitter, "McGirrSBD");
		geoLocation location = geoLocation.getGeoLocation(twitter, "McGirrSBD");
	
		/*MessageListener listener = new MessageListener(messages);
		scheduler.scheduleAtFixedRate(listener, 0, 65, TimeUnit.SECONDS);
		*/
		/*
		Parser test = new Parser();
		TurnOnHeat bb = (TurnOnHeat) test.createGraph();
		//test.printWords(bb.getAssociatedWords());
		Inferrer infer = new Inferrer();
		infer.initialProbability();
		infer.nextProbability("Today was a cold day. Can't wait to get home!");
		infer.nextProbability("Finally got home! Time to watch some Walking Dead :P");
		//infer.printStates();
		*/
		 // declaring object of "OpenWeatherMap" class
        OpenWeatherMap owm = new OpenWeatherMap("");
        owm.setApiKey("ffccc8ed983eb48e66409bf5564f9adc");

        // getting current weather data for the "London" city
        CurrentWeather cwd =null;
		try {
			cwd = owm.currentWeatherByCityName("Delft");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //printing city name from the retrieved data
        System.out.println("City: " + cwd.getCityName());

        // printing the max./min. temperature
        System.out.println("Temperature: " + cwd.getMainInstance().getMaxTemperature()
                            + "/" + cwd.getMainInstance().getMinTemperature() + "\'F");
	}
	
	

	
	
}
