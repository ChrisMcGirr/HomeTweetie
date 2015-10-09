package main;
import java.util.ArrayList;
import actions.Receiver;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import twitter4j.DirectMessage;
import twitter4j.Twitter;


public class main {
	private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public static void main(String[] args) {
		Twitter twitter = ConfigureTwitter.createTwitter();
		Messages messages = Messages.getInstance(twitter, "HomeTweetie");
		MessageListener listener = new MessageListener(messages);
		scheduler.scheduleAtFixedRate(listener, 0, 60, TimeUnit.SECONDS);

	}
	
	

	
	
}
