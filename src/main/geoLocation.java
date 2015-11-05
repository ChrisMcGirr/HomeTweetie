package main;

import javax.management.Query;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class geoLocation {
	
	public static geoLocation getGeoLocation( Twitter twitter, String UserId ){
		 try {
			ResponseList<Status> abc = twitter.getHomeTimeline();
			Status sineone =abc.get(0);
			GeoLocation myGeoLocation = sineone.getGeoLocation();
			Place myPlace = sineone.getPlace();
			
			// System.out.print(sineone.toString());
			System.out.print(myGeoLocation.toString());
			 System.out.print(myPlace.getName());
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//query(UserID); 
		  //query.setRpp(1); 
		

		return null;
	}

}
