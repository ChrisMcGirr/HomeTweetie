package main;
import java.io.File;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MediaTwitter {

	public void postImage(File image, String message, Twitter twitter) throws Exception{
	    try{
	        StatusUpdate status = new StatusUpdate(message);
	        status.setMedia(image);
	        twitter.updateStatus(status);
	        }
	    catch(TwitterException e){
	        System.out.println("Pic Upload error" + e.getErrorMessage().toString());
	        throw e;
	    }
		
	}
}
