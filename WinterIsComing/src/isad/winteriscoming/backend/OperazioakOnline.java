package isad.winteriscoming.backend;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
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
	
	//paginazioarekin egin meodoak
	//sinceId() ere erabili--> Paging psge = new Paging(pagenumber, count, sinceId)

	public static OperazioakOnline getOperazioak() {
		return gureOperazioak != null ? gureOperazioak : (gureOperazioak = new OperazioakOnline());
	}

	public void gustokoakDeskargatu() {
		// eginda
		// paging egin behar da
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> favs = twitter.getFavorites();
			System.out.println("Showing @" + user.getScreenName() + "'s favorites.");
			int orria = 1;
			Paging paging;
			for (Status fav : favs) {
				paging = new Paging(orria, 50);
				System.out.println("@" + fav.getId() + " - " + fav.getText());
				//String agindua = "INSERT INTO TXIOA VALUES ('" + fav.getId() + "', '" + fav.getText() + "', '"
				//		+ "04/11/2015" + ", gustokoa')";
				//DBKS.getDBKS().aginduaExekutatu(agindua);
				orria ++;
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}
	
	public void gustokoakDeskargatuSinceID(long id) {
		
	}

	public void txioakDeskargatu() {
		// eginda eta badabil
		// tweet denak "tweetak" parametroan daude
		// tweet bakoitzaren sorrera data "datak" parametroan dago
		try {
			ArrayList<String> txioak = new ArrayList<String>();
			ArrayList<Date> datak = new ArrayList<Date>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> tweets = twitter.getUserTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s tweets.");
			for (Status tweet : tweets) {
				System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
				txioak.add(tweet.getText());
				datak.add(tweet.getCreatedAt());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}
	
	public void bertxioakDeskargatu() {
		// eginda eta badabil
		// retweet bakoitzaren igorlea "norenak" parametroan dago
		// retweet denak "retweetak" parametroan daude
		// retweet bakoitzaren sorrera data "datak" parametroan dago
		try {
			ArrayList<String> norenak = new ArrayList<String>();
			ArrayList<String> retweetak = new ArrayList<String>();
			ArrayList<Date> datak = new ArrayList<Date>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> retweets = twitter.getUserTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s retweets.");
			for (Status retweet : retweets) {
				if (retweet.getText().startsWith("RT @")) {
					System.out.println("@" + retweet.getUser().getScreenName() + " - " + retweet.getText());
					norenak.add(retweet.getUser().getScreenName());
					retweetak.add(retweet.getText());
					datak.add(retweet.getCreatedAt());
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	public void aipamenakDeskargatu() {
		// eginda eta badabil
		// aiamen bakoitzaren igorlea "norenak" parametroan dago
		// aipamen denak "mentzioak" parametroan daude
		// aipamen bakoitzaren sorrera data "datak" parametroan dago
		try {
			ArrayList<String> norenak = new ArrayList<String>();
			ArrayList<String> aipamenak = new ArrayList<String>();
			ArrayList<Date> datak = new ArrayList<Date>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> mentions = twitter.getMentionsTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
			for (Status mention : mentions) {
				if (mention.getText().startsWith("RT @")) {
					System.out.println("@" + mention.getUser().getScreenName() + " - " + mention.getText());
					norenak.add(mention.getUser().getScreenName());
					aipamenak.add(mention.getText());
					datak.add(mention.getCreatedAt());
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}
	
	public void jarraitzaileakDeskargatu() {
		// eginda eta badabil
		// jarraitzaile guztien izena "jarraitzaileak" parametroan daude
		try {
			String agindua = "";
			String jarraitzailea = "jarraitzailea";
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			long cursor = -1;
			List<User> userList;
			System.out.println("Listing followers:");
			userList = twitter.getFollowersList(erabiltzailea.getId(), cursor);
			for (User jarraitzaile : userList) {
				agindua = "INSERT INTO BESTEERABILTZAILEAK (ID, IZENA, MOTA, IDERABILTZAILEA, NICK) VALUES ('"
						+ jarraitzaile.getId() + "', '" + Charset.forName("UTF-8").encode(jarraitzaile.getName())
						+ "', '" + jarraitzailea + "','" + erabiltzailea.getId() + "','" + jarraitzaile.getScreenName()
						+ "')";
				DBKS.getDBKS().aginduaExekutatu(agindua);
			}

		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: " + te.getMessage());
		}
	}

	public void jarraituakDeskargatu() {
		// eginda eta badabil
		// jarraitzaile guztien izena "jarraituak" parametroan daude
		try {
			String agindua = "";
			String jarraitua = "jarraitua";
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			long cursor = -1;
			List<User> userList;
			System.out.println("Listing followers:");
			userList = twitter.getFriendsList(erabiltzailea.getId(), cursor);
			for (User jarraitu : userList) {
				agindua = "INSERT INTO BESTEERABILTZAILEAK (ID, IZENA, MOTA, IDERABILTZAILEA, NICK) VALUES ('"
						+ String.valueOf(jarraitu.getId()) + "', '" + jarraitu.getName() + "', '" + jarraitua + "','"
						+ String.valueOf(erabiltzailea.getId()) + "','" + jarraitu.getScreenName() + "')";
				DBKS.getDBKS().aginduaExekutatu(agindua);
			}

		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: " + te.getMessage());
		}
	}

	public void zerrendakDeskargatu() {
		// eginda eta badabil
		// zerrenda guztiak eta bertan dauden jarraituak "zerrendak" parametroan
		// gordetzen dira
		// zerrenda bakoitzak hasieran "." bat izango du
		// hurrengo izen guztiak zerrenda horretako jarraituak izango dira,
		// "."-dun bat aurkitu arte
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			ArrayList<String> zerrendak = new ArrayList<String>();
			long cursor = -1;
			PagableResponseList<User> users;
			ResponseList<UserList> lists = twitter.getUserLists(twitter.getScreenName());
			for (UserList list : lists) {
				users = twitter.getUserListMembers((list.getId()), cursor);
				zerrendak.add("." + (list.getName()));
				System.out.println(
						"Izena:" + list.getName() + " / Deskribapena: " + list.getDescription() + " / Jarraituak:");
				for (User user : users) {
					System.out.println("@" + user.getScreenName());
					zerrendak.add(user.getScreenName());
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to list the lists: " + te.getMessage());
		}
	}

	public void jasotakoMezuakDeskargatu() {
		// eginda eta badabil
		// mezu bakoitzaren igorlea "norenak" parametroan dago
		// mezu denak "mezuak" parametroan daude
		// mezu bakoitzaren sorrera data "datak" parametroan dago
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		ArrayList<String> norenak = new ArrayList<String>();
		ArrayList<String> mezuak = new ArrayList<String>();
		ArrayList<Date> datak = new ArrayList<Date>();
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
					datak.add(message.getCreatedAt());
				}
				paging.setPage(paging.getPage() + 1);
			} while (messages.size() > 0 && paging.getPage() < 10);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get messages: " + te.getMessage());
		}
	}

	public void bidalitakoMezuakDeskargatu() {
		// eginda eta badabil
		// mezu bakoitzaren hartzailea "norentzat" parametroan dago
		// mezu denak "mezuak" parametroan daude
		// mezu bakoitzaren sorrera data "datak" parametroan dago
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		ArrayList<String> norentzat = new ArrayList<String>();
		ArrayList<String> mezuak = new ArrayList<String>();
		ArrayList<Date> datak = new ArrayList<Date>();
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
					datak.add(message.getCreatedAt());
				}
				page.setPage(page.getPage() + 1);
			} while (directMessages.size() > 0 && page.getPage() < 10);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get sent messages: " + te.getMessage());
		}
	}

}
