package main;
import java.io.File;

import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MediaTwitter {
	static Twitter twitter = null;
	
	public MediaTwitter(Twitter input){
		twitter = input;
	}

	/*
	 * Posts an Image to the Twitter feed of HomeTweetie given a path to the image
	 * and an message to be posted with the tweet. 
	 */
	public static void postImage(File image, String message) throws Exception{
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
	/*
	 * Assuming this function is called just after the image has been posted, this
	 * returns the links of the image from the tweet and returns it in an string
	 * array.
	 */
	public static String[] getImageLink(){
		String links[] = null;
		try {
			Status latestTweet = twitter.getHomeTimeline().get(0);
			MediaEntity[] images = latestTweet.getMediaEntities();
			links = new String[images.length];
			for(int i=0; i< images.length; i++){ 
			    links[i] = images[i].getMediaURL(); 
			}
		} catch (TwitterException e) {
			System.out.println("Error getting Media Links from Tweet");
			e.printStackTrace();
		}
		return links;
	}
}
