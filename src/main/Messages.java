package main;
import java.util.ArrayList;
import java.util.Iterator;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class Messages {
	
	private static long lastMsg = 0;
	private static Messages instance = null;
	private static Twitter twitter = null;
	private static String UserId = "";
	private static String UserIdHomeTweetie = "HomeTweetie";
	public final static String breakPoint = "HomeTweetie: I have read all your messages :)";
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
	/*
	 * Assumes HomeTweetie has direct messages with only one other user, if there are more,
	 * then we will have to filter to have only messages from HomeTweetie and the user. 
	 */
	private static void init(){
		try{	 
			ResponseList<DirectMessage> msgs = twitter.getDirectMessages();
			System.out.println("Init(): Response List size is "+ msgs.size());
			Iterator<DirectMessage> list = msgs.iterator();
			DirectMessage DM;
			int i = 0; 
			while(list.hasNext()){
					 DM = list.next();
					 if(DM.getSenderScreenName().equals(UserIdHomeTweetie)){
						if(DM.getText().equals(breakPoint)){
							lastMsg = DM.getId();
							System.out.println("Last Message Id is " + lastMsg);
							System.out.println("Last Message: "+DM.getText());
							break;
						}
					 }
					 i++;
			}
			if(i>0){
				writeDM(breakPoint);
			}
		} catch (TwitterException e) {
				System.out.println("Failed to ReadDM() in Messages.java");
				e.printStackTrace();
		}
	}

	public static void writeDM(String message){
		DirectMessage dm = null;
		try {
			dm = twitter.sendDirectMessage(UserId, message);
			if(message.equals(breakPoint)){
				lastMsg = dm.getId();
			}
			
		} catch (TwitterException e) {
			System.out.println("Failed to WriteDM() in Messages.java");
			e.printStackTrace();
		}
	}
	

	public ArrayList<DirectMessage> readDM(){
		
		ArrayList<DirectMessage> messages = new ArrayList<DirectMessage>();
		ResponseList<DirectMessage> msgs = null;
		
		try{
			if(lastMsg !=0){
				msgs = twitter.getDirectMessages(pg);
			}
			else{
				msgs = twitter.getDirectMessages();
			}
			System.out.println("ReadDM(): Response List size is "+ msgs.size());
			Iterator<DirectMessage> list = msgs.iterator();
			DirectMessage DM;
			while(list.hasNext()){
					DM = list.next();
					if(DM.getSenderScreenName().equals(UserIdHomeTweetie)){
						if(DM.getText().equals(breakPoint)){
							lastMsg = DM.getId();
							pg.setSinceId(lastMsg);
							break;
						}
					}
					if(DM.getSenderScreenName().equals(UserId)){
						messages.add(DM);	
					}
					lastMsg = DM.getId();
					pg.setSinceId(lastMsg);
			}
		} catch (TwitterException e) {
				System.out.println("Failed to ReadDM() in Messages.java");
				e.printStackTrace();
		}
		
		return messages;
	}
	
	private void findBreakPoint(Iterator<DirectMessage> list, ResponseList<DirectMessage> msgs){
		DirectMessage DM = null;
		while(list.hasNext()){
			 DM = list.next();
			 if(DM.getSenderScreenName().equals(UserIdHomeTweetie)){
				if(DM.getText().equals(breakPoint)){
					lastMsg = DM.getId();
					pg.setSinceId(lastMsg);
					System.out.println("Last Message Id is "+lastMsg);
					System.out.println("Last Message: "+DM.getText());
				}
			 }
		}
		if(msgs.size() > 0){
			if(!DM.getText().equals(breakPoint)){
				writeDM(breakPoint);
			}
		}
	}
}
