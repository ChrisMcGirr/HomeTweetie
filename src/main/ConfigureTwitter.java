package main;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigureTwitter {
	
	/*
	 * Here is where you set the credentials of the twitter application. These keys
	 * are taken from the Twitter Dev Panel. Make sure if HomeTweetie is a different
	 * account, that these values are changed. 
	 */
	private static ConfigurationBuilder configureTwitterCredentials(){
		System.out.println("Twitter Configuration Started");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("Gd7LhwFERYOi4oXYbMc28K3bY")
		  .setOAuthConsumerSecret("RMb0fPgQWH3Zr1kWzXSEBEUp2k9zL8dOGVAFUo0ZbyyVratI1U")
		  .setOAuthAccessToken("3907814673-Kz80Jg8SRHAFStjeiqvwLvpnX5CgTslK9rQTQls")
		  .setOAuthAccessTokenSecret("67roN00oLFcnLGG1fDPHAIqkjHJQDK9c47SwX5TLCGpdC");
		System.out.println("Twitter Configuration Complete");
		return cb;
	  }
	
	/*
	 * Simply creates the twitter instance where all operations to twitter can be done.
	 */
	public static Twitter createTwitter(){
		ConfigurationBuilder cb = ConfigureTwitter.configureTwitterCredentials();
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		return twitter;
	}

}
