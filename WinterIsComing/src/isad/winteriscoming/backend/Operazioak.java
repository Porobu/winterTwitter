package isad.winteriscoming.backend;

import java.util.List;

import twitter4j.IDs;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class Operazioak {
	
	//bigarren metodotik aurrera oraindik amaitzeke/probatzeke daude metodoak
	
	//textua erakusteko metodoak

	public static void timelineErakutsi() {
		//eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
			for (Status status : statuses) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void gustokoakErakutsi() {
		//eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> favs = twitter.getFavorites();
			System.out.println("Showing @" + user.getScreenName() + "'s favorites.");
			for (Status fav : favs) {
				System.out.println("@" + fav.getUser().getScreenName() + " - " + fav.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void tweetakErakutsi() {
		//eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> tweets = twitter.getUserTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s tweets.");
			for (Status tweet : tweets) {
				System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void retweetakErakutsi() {
		//eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> retweets = twitter.getUserTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s retweets.");
			for (Status retweet : retweets) {
				if (retweet.getText().startsWith("RT @"))
				System.out.println("@" + retweet.getUser().getScreenName() + " - " + retweet.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void mentzioakErakutsi() {
		//eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> retweets = twitter.getMentionsTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
			for (Status retweet : retweets) {
				if (retweet.getText().startsWith("RT @"))
				System.out.println("@" + retweet.getUser().getScreenName() + " - " + retweet.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void jarraitzaileakErakutsi() {
		//TODO ID-ak izenagaitik aldatu
		try {
            Twitter twitter = new TwitterFactory().getInstance();
            long cursor = -1;
            IDs ids;
            System.out.println("Listing followers's ids.");
            do {
                ids = twitter.getFollowersIDs(cursor);
                for (long id : ids.getIDs()) {
                    System.out.println(id);
                }
            } while ((cursor = ids.getNextCursor()) != 0);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	public static void jarraituakErakutsi() {
		//TODO ID-ak izenagaitik aldatu
		try {
            Twitter twitter = new TwitterFactory().getInstance();
            long cursor = -1;
            IDs ids;
            System.out.println("Listing followers's ids.");
            do {
                ids = twitter.getFriendsIDs(cursor);
                for (long id : ids.getIDs()) {
                    System.out.println(id);
                }
            } while ((cursor = ids.getNextCursor()) != 0);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	public static void zerrendakErakutsi() {
		//TODO hau igual
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
			for (Status status : statuses) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void mezuakErakutsi() {
		//TODO hau =
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
			for (Status status : statuses) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void bilatuTxioetan(String st) {
		//TODO hau ondo begiratu
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s home timeline.");
			for (Status status : statuses) {
				System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	//datu-basearekin zerikusia duten metodoak
	//TODO
}
