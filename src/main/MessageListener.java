package main;

import java.util.ArrayList;
import java.util.Iterator;

import actions.Command;
import actions.Receiver;
import actions.readTemperature;
import actions.readTime;
import actions.readWebcam;
import actions.setLightsOff;
import actions.setLightsOn;
import twitter4j.DirectMessage;


public class MessageListener implements Runnable{
	
	private Messages messages = null;
	private ArrayList<DirectMessage> msgList = null;
	private Iterator<DirectMessage> list = null;
	private Receiver rcv = null;
	private MessageInterpreter mi = null;
	private String value = null;
	private DirectMessage dm = null;
	
	/*
	 * Add all the functions available to the listener and the message
	 * interpreter. 
	 */
	public MessageListener(Messages input, String userID){
		messages = input;
		rcv = new Receiver(messages, userID);
		readTemperature rtr = new readTemperature(rcv);
		readTime rtm = new readTime(rcv);
		readWebcam rwc = new readWebcam(rcv);
		setLightsOn slon = new setLightsOn(rcv);
		setLightsOff sloff = new setLightsOff(rcv);
		
		ArrayList<Command> commands = new ArrayList<Command>();
		commands.add(sloff);
		commands.add(slon);
		commands.add(rwc);
		commands.add(rtm);
		commands.add(rtr);
		mi = new MessageInterpreter(commands);
	}
	
	/*
	 * Function that runs when the class is ran as a thread. Simply checks
	 * the direct messages of HomeTweetie and sees if there are any new 
	 * messages. If there are we call the message Interpreter and check to see
	 * if it is a valid command. 
	 * @see java.lang.Runnable#run()
	 */
	public void run(){

		msgList = messages.readDM();
		list = msgList.iterator();
		
		while(list.hasNext()){
			dm = list.next();
			value = mi.getType(dm);
			if(!value.equals("Valid Command")){
				Messages.writeDM("Invalid Command -> " + dm.getText());
			}
			System.out.println(value);
		}
		if(msgList.size() > 0){
			Messages.writeDM(Messages.breakPoint);
		}
	}
	
	public Receiver getReceiver(){
		return rcv;
	}

}
