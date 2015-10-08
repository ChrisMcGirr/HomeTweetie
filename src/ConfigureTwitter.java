import twitter4j.conf.ConfigurationBuilder;

public class ConfigureTwitter {
	
	public static ConfigurationBuilder configureTwitterCredentials(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("x18bUB18h0Nelv2XQ8kiHSKx4")
		  .setOAuthConsumerSecret("ISBwwryUBUO8vWCtKgRlzuft0U7FAbcU4r6kheR3qvstKPgXSX")
		  .setOAuthAccessToken("1190927394-st6Yjs2lqDvPgO5wXafoeHn1YMp8OlxVle75Svh")
		  .setOAuthAccessTokenSecret("3ClHjyrfCqCSgzDXAGG28unCzzZIqf263hM4XZacsmGHf");
		System.out.println("Twitter Configuration Complete");
		return cb;
	  }

}
