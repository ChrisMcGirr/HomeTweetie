import java.io.File;

import twitter4j.DirectMessage;
import twitter4j.ResponseList;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class main {

	public static void main(String[] args) {
		
		ConfigurationBuilder cb = ConfigureTwitter.configureTwitterCredentials();
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		
		String statusMsg = " This is the first message from embedded app";
		String directMsg = " This is the second message, send by using API" ; 
		String UserId    = "McGirrSBD";
		//File image = new File("test.jpg");
		
		StatusUpdate status = new StatusUpdate(statusMsg);
		//status.setMedia(image);
	//	DirectMessage message = new DirectMessage();
		System.out.println(" New status is uploaded");
		try {
			//twitter.updateStatus(status);
			//twitter.sendDirectMessage(UserId,directMsg);
			 ResponseList<DirectMessage> msgs = twitter.getDirectMessages();
			 while(msgs.iterator().hasNext()){
				 System.out.println(msgs.iterator().next().getText());
				 msgs.iterator().remove();
			 }
		} catch (TwitterException e) {
			System.out.println("Twitter network is down or unavailable");
			e.printStackTrace();
		}

	}
	
	

	
	
}
