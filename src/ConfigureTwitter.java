import twitter4j.conf.ConfigurationBuilder;

public class ConfigureTwitter {
	
	public static ConfigurationBuilder configureTwitterCredentials(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("4nvogWxHBC1lhpWwMdBRMwuZW")
		  .setOAuthConsumerSecret("iqDi2iKtjScy4ahWvXEJEm4NIQP2UC2jO6xYIIbyxX2e25Llc8")
		  .setOAuthAccessToken("3832261815-i23uYGbgHqKoNidd2nvct71jMKY1D7PbmO71TrG")
		  .setOAuthAccessTokenSecret("6MnIJmIe6DGD2Xg4O0knMJAKkReaxtqQj4Q6SivjjPuOQ");
		System.out.println("Twitter Configuration Complete");
		return cb;
	  }

}
