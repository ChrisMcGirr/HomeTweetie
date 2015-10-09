package main;
import java.util.ArrayList;
import java.util.Iterator;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Messages {
	
	private static long lastMsg = 0;
	private static Messages instance = null;
	private static Twitter twitter = null;
	private static String UserId = "";
	private static String breakPoint = "HomeTweetie: I have read all your messages :)";
	private static Paging pg = null;
	
	protected Messages(){

	}
	
	public static Messages getInstance(Twitter twitter, String UserId){
	    if(instance == null) {
	    	instance = new Messages();
	    }
		Messages.twitter = twitter;
		Messages.UserId = UserId;
		init();
		Messages.pg = new Paging(lastMsg);
	    return instance;
	}
	
	private static void init(){
		try{	 
			ResponseList<DirectMessage> msgs = twitter.getDirectMessages();
			System.out.println("Init(): Response List size is "+ msgs.size());
			Iterator<DirectMessage> list = msgs.iterator();
			DirectMessage DM;
			while(list.hasNext()){
					 DM = list.next();
					 if(DM.getSenderScreenName().equals(UserId)){
						if(DM.getText().equals(breakPoint)){
							lastMsg = DM.getId();
							System.out.println("Last Message Id is "+lastMsg);
							break;
						}
					 }
			}
			if(lastMsg == 0){
				twitter.sendDirectMessage(UserId, breakPoint);
			}
		} catch (TwitterException e) {
				System.out.println("Failed to ReadDM() in Messages.java");
				e.printStackTrace();
		}
	}

	public void writeDM(String message){
		
		try {
			twitter.sendDirectMessage(UserId, message);
		} catch (TwitterException e) {
			System.out.println("Failed to WriteDM() in Messages.java");
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("null")
	public ArrayList<DirectMessage> readDM(){
		
		ArrayList<DirectMessage> messages = new ArrayList<DirectMessage>();
		
		try{	 
			ResponseList<DirectMessage> msgs = twitter.getDirectMessages(pg);
			System.out.println("ReadDM(): Response List size is "+ msgs.size());
			Iterator<DirectMessage> list = msgs.iterator();
			DirectMessage DM;
			while(list.hasNext()){
					 DM = list.next();
					 if(DM.getSenderScreenName().equals(UserId)){
						if(DM.getText().equals(breakPoint)){
							lastMsg = DM.getId();
							System.out.println("Last Message Id is "+lastMsg);
						}
						messages.add(DM);
					 }
			}
			writeDM(breakPoint);
		} catch (TwitterException e) {
				System.out.println("Failed to ReadDM() in Messages.java");
				e.printStackTrace();
		}
		
		return messages;
	}
}
