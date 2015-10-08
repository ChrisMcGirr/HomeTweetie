import java.io.File;

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
		
		String statusMsg = "First test of Image Upload!";
		File image = new File("test.jpg");
		
		StatusUpdate status = new StatusUpdate(statusMsg);
		status.setMedia(image);
		try {
			twitter.updateStatus(status);
		} catch (TwitterException e) {
			System.out.println("Twitter network is down or unavailable");
			e.printStackTrace();
		}

	}
	
	

}
