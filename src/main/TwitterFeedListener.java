package main;

import actions.turnOnHeat;
import inference.Classifier;
import inference.Inferrer;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterFeedListener implements Runnable {
	
	private Twitter twitter = null;
	private String userID = null;
	private Status latestTweet = null;
	private ResponseList<Status> cache = null;
	private Inferrer infer = null;
	private turnOnHeat command = null;
	private Classifier classifyHeating = null;
	
	public TwitterFeedListener(Twitter input, String name){
		twitter = input;
		userID = name;
		infer = new Inferrer("dictionary.json");
		command = new turnOnHeat(null);
		classifyHeating = new Classifier(infer);
	}
	
	public void run(){
		ResponseList<Status> listTweets = null;
		try {
			listTweets = twitter.getUserTimeline(userID);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		Status top = listTweets.get(0);
		if(cache==null){
			System.out.println("Initializing the Twitter Feed and Populating Tweet Cache");
			updateCache(listTweets);
			latestTweet = top;
		}
		else{
			if(top.equals(latestTweet)){
				//do nothing
				System.out.println("No new Tweets");
			}
			else{
				//Execute to see if can infer a new command
				System.out.println("New Tweet");
				updateCache(listTweets);
				latestTweet = top;
				System.out.println("Tweet Cache Successfully Updated");
				geoLocation.getGeoLocation(top);
				boolean result = classifyHeating.isValidCommand(top.getText(), command);
				System.out.println("We have inferrence status "+result);
				
			}
		}
	}
	
	public void updateCache(ResponseList<Status> newList){
		if(cache != null){
			System.out.println("Tweet Cache is not empty");
			int index = 0;
			for(int i=0; i<newList.size(); i++){
				if(cache.contains(newList.get(i))){
					break;
				}
				index++;
			}
			for(int i=index-1; i>=0; i--){
				cache.add(newList.get(i));
				System.out.println(newList.get(i).getText());
			}
		}
		else{
			System.out.println("Tweet Cache is empty");
			cache = newList;
			infer.initialProbability(toStringArray());
		}
	}
	
	private String[] toStringArray(){
		String[] tweets = new String[cache.size()];
		for(int i=0; i<cache.size(); i++){
			tweets[i] = cache.get(i).getText();
		}
		return tweets;
	}
	
	public ResponseList<Status> getUserFeed(){
		ResponseList<Status> listTweets = null;
		try {
			listTweets = twitter.getUserTimeline(userID);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		latestTweet = listTweets.get(0);
		return listTweets;
	}

}
