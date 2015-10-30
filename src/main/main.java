package main;
import actions.Receiver;
import twitter4j.Twitter;
import actions.*;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class main {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) {
		
		Twitter twitter = ConfigureTwitter.createTwitter();
		Messages messages = Messages.getInstance(twitter, "McGirrSBD");
		MessageListener listener = new MessageListener(messages);
		scheduler.scheduleAtFixedRate(listener, 0, 65, TimeUnit.SECONDS);
		
	}
	
	

	
	
}
