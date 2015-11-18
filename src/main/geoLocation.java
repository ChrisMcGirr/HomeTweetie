package main;

import javax.management.Query;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class geoLocation {
	
	private static String location = null;
	private static String work = "TU-Campus";
	private static Twitter twitter = null;
	private static String UserId = null;
	
	public geoLocation(Twitter twitter, String UserId){
		this.twitter = twitter;
		this.UserId = UserId;
	}
	
	public static void getGeoLocation(){
		 try {
			ResponseList<Status> abc = twitter.getHomeTimeline();
			Status sineone =abc.get(0);
			GeoLocation myGeoLocation = sineone.getGeoLocation();
			Place myPlace = sineone.getPlace();
			
			// System.out.print(sineone.toString());
			//System.out.print(myGeoLocation.toString());
			location = myPlace.getName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void getGeoLocation(Status sineone){
		 try {
			GeoLocation myGeoLocation = sineone.getGeoLocation();
			Place myPlace = sineone.getPlace();
			
			// System.out.print(sineone.toString());
			//System.out.print(myGeoLocation.toString());
			location = myPlace.getName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static boolean isAtWork(){
		getGeoLocation();
		if(location.equals(work)){
			return true;
		}
		else{
			return false;
		}
	}

}
