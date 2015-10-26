package isad.winteriscoming.backend;

import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

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
			List<Status> mentions = twitter.getMentionsTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
			for (Status mention : mentions) {
				if (mention.getText().startsWith("RT @"))
				System.out.println("@" + mention.getUser().getScreenName() + " - " + mention.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void jarraitzaileakErakutsi() {
		//eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
            User us = twitter.verifyCredentials();
            long cursor = -1;
            List<User> userList;
            System.out.println("Listing followers:");
            userList= twitter.getFollowersList(us.getId(), cursor);
            for (User user : userList) {
                System.out.println(twitter.showUser(user.getId()).getName());
            }
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	public static void jarraituakErakutsi() {
		//eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
            User us = twitter.verifyCredentials();
            long cursor = -1;
            List<User> userList;
            System.out.println("Listing followers:");
            userList= twitter.getFriendsList(us.getId(), cursor);
            for (User user : userList) {
                System.out.println(twitter.showUser(user.getId()).getName());
            }
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get followers' ids: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	public static void zerrendakErakutsi() {
		//TODO hau amaitzeke
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			UserList list = twitter.showUserList(user.getId());
			System.out.println("id:" + list.getId() + ", name:" + list.getName() + ", description:"
                    + list.getDescription() + ", slug:" + list.getSlug() + "");
			System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public static void mezuakErakutsi() {
		//TODO hau amaitzeke
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
        try {
            List<DirectMessage> messages;
            do {
                messages = twitter.getDirectMessages();
                for (DirectMessage message : messages) {
                    System.out.println("From: @" + message.getSenderScreenName() + " id:" + message.getId() + " - "
                            + message.getText());
                }
            } while (messages.size() > 0);
            System.out.println("done.");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get messages: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	public static void bidalitakoMezuakErakutsi() {
		//TODO hau amaitzeke
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		try {
            Paging page = new Paging(1);
            List<DirectMessage> directMessages;
            do {
                directMessages = twitter.getSentDirectMessages(page);
                for (DirectMessage message : directMessages) {
                    System.out.println("To: @" + message.getRecipientScreenName() + " id:" + message.getId() + " - "
                            + message.getText());
                }
                page.setPage(page.getPage() + 1);
            } while (directMessages.size() > 0 && page.getPage() < 10);
            System.out.println("done.");
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get sent messages: " + te.getMessage());
            System.exit(-1);
        }
	}
	
	//datu-basearekin zerikusia duten metodoak
	//TODO
	
	public static void bilatuTxioetan(String st) {
		//TODO hau ondo begiratu, azkenerako utzi, zailena da
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
}
