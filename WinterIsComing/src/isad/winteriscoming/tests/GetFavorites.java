package isad.winteriscoming.tests;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Lists favorited statuses
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class GetFavorites {
    /**
     * Usage: java twitter4j.examples.favorite.GetFavorites
     *
     * @param args message
     */
    public static void main(String[] args) {
        try {
        	
        	ConfigurationBuilder cb = new ConfigurationBuilder();
        	cb.setOAuthAccessToken("4075787614-5tUiUmlOdThAwJv7iSdq7kajtrc6a3bbmxiU5fz").
        	setOAuthAccessTokenSecret("hLlGsXcy6Q51SiQi9OAVHOByE7KRjTY6rIP9AsUkqHMLq").
        	setOAuthConsumerKey("c17u4cp7OYv5PgGBZIAFUbf7j").
        	setOAuthConsumerSecret("IDG8IamHaTjQaVFIcjkwtDGAWW9AGxyP4t7R4rIpRluReKQ7Ky");
            Twitter twitter = new TwitterFactory(cb.build()).getInstance();
            List<Status> statuses = twitter.getFavorites(new Paging(1, 100));
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
            System.out.println("done.");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get favorites: " + te.getMessage());
            System.exit(-1);
        }
    }
}
