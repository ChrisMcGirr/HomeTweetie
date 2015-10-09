package main;

import java.util.ArrayList;
import java.util.Iterator;

import actions.Receiver;
import twitter4j.DirectMessage;


public class MessageListener implements Runnable{
	
	private Messages messages = null;
	private ArrayList<DirectMessage> msgList = null;
	private Iterator<DirectMessage> list = null;
	private Receiver actions = null;
	
	public MessageListener(Messages input){
		messages = input;
		actions = new Receiver(messages);
	}
	
	public void run(){

		msgList = messages.readDM();
		list = msgList.iterator();
		
		while(list.hasNext()){
			String text = list.next().getText();
			if(text.contains("what time")){
				actions.getTime();
			}
		}
	}

}
