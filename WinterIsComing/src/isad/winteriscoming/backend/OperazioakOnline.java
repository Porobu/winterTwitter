package isad.winteriscoming.backend;

import java.util.ArrayList;
import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;

public final class OperazioakOnline {
	private static OperazioakOnline gureOperazioak;

	private OperazioakOnline() {

	}

	public static OperazioakOnline getOperazioak() {
		return gureOperazioak != null ? gureOperazioak : (gureOperazioak = new OperazioakOnline());
	}

	// bigarren metodotik aurrera oraindik amaitzeke/probatzeke daude metodoak

	// textua erakusteko metodoak

	public void timelineDeskargatu() {
		// eginda eta badabil
		// gorde behar da?
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

	public void gustokoakDeskargatu() {
		// eginda eta badabil
		// gustoko tweet bakoitzaren igorlea "norenak" parametroan dago
		// gustoko tweet denak "tweetak" parametroan daude
		try {
			ArrayList<String> norenak = new ArrayList<String>();
			ArrayList<String> tweetak = new ArrayList<String>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> favs = twitter.getFavorites();
			System.out.println("Showing @" + user.getScreenName() + "'s favorites.");
			for (Status fav : favs) {
				System.out.println("@" + fav.getUser().getScreenName() + " - " + fav.getText());
				norenak.add(fav.getUser().getScreenName());
				tweetak.add(fav.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void tweetakDeskargatu() {
		// eginda eta badabil
		// gustoko tweet denak "gustokoak" parametroan daude
		try {
			ArrayList<String> gustokoak = new ArrayList<String>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> tweets = twitter.getUserTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s tweets.");
			for (Status tweet : tweets) {
				// if (tweet.getUser().getScreenName()==uneko erabiltzaileare
				// izena) //retweetak kentzeko
				System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
				gustokoak.add(tweet.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void retweetakDeskargatu() {
		// eginda eta badabil
		// retweet bakoitzaren igorlea "norenak" parametroan dago
		// retweet denak "retweetak" parametroan daude
		try {
			ArrayList<String> norenak = new ArrayList<String>();
			ArrayList<String> retweetak = new ArrayList<String>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> retweets = twitter.getUserTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s retweets.");
			for (Status retweet : retweets) {
				if (retweet.getText().startsWith("RT @")) {
					System.out.println("@" + retweet.getUser().getScreenName() + " - " + retweet.getText());
					norenak.add(retweet.getUser().getScreenName());
					retweetak.add(retweet.getText());
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void aipamenakDeskargatu() {
		// eginda eta badabil
		// mentzio bakoitzaren igorlea "norenak" parametroan dago
		// mentzio denak "mentzioak" parametroan daude
		try {
			ArrayList<String> norenak = new ArrayList<String>();
			ArrayList<String> aipamenak = new ArrayList<String>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> mentions = twitter.getMentionsTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
			for (Status mention : mentions) {
				if (mention.getText().startsWith("RT @")) {
					System.out.println("@" + mention.getUser().getScreenName() + " - " + mention.getText());
					norenak.add(mention.getUser().getScreenName());
					aipamenak.add(mention.getText());
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void jarraitzaileakDeskargatu() {
		// eginda eta badabil
		// jarraitzaile guztien izena "jarraitzaileak" parametroan daude
		try {
			ArrayList<String> jarraitzaileak = new ArrayList<String>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User us = twitter.verifyCredentials();
			long cursor = -1;
			List<User> userList;
			System.out.println("Listing followers:");
			userList = twitter.getFollowersList(us.getId(), cursor);
			for (User user : userList) {
				System.out.println(twitter.showUser(user.getId()).getName());
				jarraitzaileak.add(twitter.showUser(user.getId()).getName());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void jarraituakDeskargatu() {
		// eginda eta badabil
		// jarraitzaile guztien izena "jarraituak" parametroan daude
		try {
			ArrayList<String> jarraituak = new ArrayList<String>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User us = twitter.verifyCredentials();
			long cursor = -1;
			List<User> userList;
			System.out.println("Listing followers:");
			userList = twitter.getFriendsList(us.getId(), cursor);
			for (User user : userList) {
				System.out.println(twitter.showUser(user.getId()).getName());
				jarraituak.add(twitter.showUser(user.getId()).getName());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void zerrendakDeskargatu() {
		// eginda eta badabil
		// zerrenda guztien id-a "zerrendak" parametroan daude
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			ArrayList<String> zerrendak = new ArrayList<String>();
			ArrayList<String> zerrendakoak = new ArrayList<String>();
			long cursor = -1;
			PagableResponseList<User> users;
			ResponseList<UserList> lists = twitter.getUserLists(twitter.getScreenName());
			for (UserList list : lists) {
				users = twitter.getUserListMembers((list.getId()), cursor);
				System.out.println("Izena:" + list.getName() + " / Deskribapena: "
						+ list.getDescription() + " / Jarraituak:");
				for (User user : users) {
					System.out.println("@" + user.getScreenName());
					zerrendakoak.add(user.getScreenName());
				}
				zerrendak.add(Long.toString(list.getId()));
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to list the lists: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void jasotakoMezuakDeskargatu() {
		// eginda eta badabil
		// mezu bakoitzaren igorlea "norenak" parametroan dago
		// mezu denak "mezuak" parametroan daude
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		ArrayList<String> norenak = new ArrayList<String>();
		ArrayList<String> mezuak = new ArrayList<String>();
		try {
			Paging paging = new Paging(1);
			List<DirectMessage> messages;
			do {
				messages = twitter.getDirectMessages(paging);
				for (DirectMessage message : messages) {
					System.out.println("From: @" + message.getSenderScreenName() + " id:" + message.getId() + " - "
							+ message.getText());
					norenak.add(message.getSenderScreenName());
					mezuak.add(message.getText());
				}
				paging.setPage(paging.getPage() + 1);
			} while (messages.size() > 0 && paging.getPage() < 10);
			System.out.println("done.");
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get messages: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void bidalitakoMezuakDeskargatu() {
		// eginda eta badabil
		// mezu bakoitzaren hartzailea "norentzat" parametroan dago
		// mezu denak "mezuak" parametroan daude
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		ArrayList<String> norentzat = new ArrayList<String>();
		ArrayList<String> mezuak = new ArrayList<String>();
		try {
			Paging page = new Paging(1);
			List<DirectMessage> directMessages;
			do {
				directMessages = twitter.getSentDirectMessages(page);
				for (DirectMessage message : directMessages) {
					System.out.println("To: @" + message.getRecipientScreenName() + " id:" + message.getId() + " - "
							+ message.getText());
					norentzat.add(message.getSenderScreenName());
					mezuak.add(message.getText());
				}
				page.setPage(page.getPage() + 1);
			} while (directMessages.size() > 0 && page.getPage() < 10);
			System.out.println("done.");
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get sent messages: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void bilatuTxioetan(String st) {
		// TODO datu basearekin komunikazioa lortu ondoren inplementatu
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			//egiteke
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
}
