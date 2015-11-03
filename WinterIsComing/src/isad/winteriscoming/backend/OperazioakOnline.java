package isad.winteriscoming.backend;

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

	public static OperazioakOnline getOperazioak() {
		return gureOperazioak != null ? gureOperazioak : (gureOperazioak = new OperazioakOnline());
	}

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

	public void ezabatuSnails() {
		// eginda eta badabil
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getUserTimeline();
			for (Status status : statuses) {
				if (status.getText().contains("Snail!"))
					twitter.destroyStatus(status.getId());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void ezabatuSnails2() {
		for (int i = 0; i < 16; i++)
			ezabatuSnails();
	}

	public void gustokoakDeskargatu() {
		// eginda eta badabil
		// gustoko tweet bakoitzaren igorlea "norenak" parametroan dago
		// gustoko tweet denak "tweetak" parametroan daude
		// gustoko tweet bakoitzaren sorrera data "datak" parametroan dago
		try {
			ArrayList<String> norenak = new ArrayList<String>();
			ArrayList<String> tweetak = new ArrayList<String>();
			ArrayList<Date> datak = new ArrayList<Date>();
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> favs = twitter.getFavorites();
			System.out.println("Showing @" + user.getScreenName() + "'s favorites.");
			for (Status fav : favs) {
				System.out.println("@" + fav.getUser().getScreenName() + " - " + fav.getText());
				norenak.add(fav.getUser().getScreenName());
				tweetak.add(fav.getText());
				datak.add(fav.getCreatedAt());
			}
			gustokoakDBraSartu(norenak, tweetak, datak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}

	public void gustokoakDBraSartu(ArrayList<String> norenak, ArrayList<String> txioak, ArrayList<Date> datak) {
		String agindua = "";
		for (int i = 0; i < norenak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO TXIOA " + "VALUES ('" + norenak + "', '" + txioak + "', '" + datak + "')";
			DBKS.getDBKS().aginduaExekutatu(agindua);
		}	}

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
			txioakDBraSartu(txioak, datak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void txioakDBraSartu(ArrayList<String> txioak, ArrayList<Date> datak) {
		String agindua = "";
		for (int i = 0; i < txioak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO etc"+ txioak.get(i) + "datak";
			DBKS.getDBKS().aginduaExekutatu(agindua);
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
			bertxioakDBraSartu(norenak, retweetak, datak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void bertxioakDBraSartu(ArrayList<String> norenak, ArrayList<String> bertxioak, ArrayList<Date> datak) {
		String agindua = "";
		for (int i = 0; i < bertxioak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO TXIOA " + "VALUES ('" + norenak + "', '" + bertxioak + "', '" + datak + "')";
			DBKS.getDBKS().aginduaExekutatu(agindua);
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
			aipamenakDBraSartu(norenak, aipamenak, datak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void aipamenakDBraSartu(ArrayList<String> norenak, ArrayList<String> aipamenak, ArrayList<Date> datak) {
		String agindua = "";
		for (int i = 0; i < aipamenak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INT TXIOA " + "VALUES ('" + norenak + "', '" + aipamenak + "', '" + datak + "')";
			DBKS.getDBKS().aginduaExekutatu(agindua);
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
			jarraitzaileakDBraSartu(jarraitzaileak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void jarraitzaileakDBraSartu(ArrayList<String> jarraitzaileak) {
		String agindua = "";
		for (int i = 0; i < jarraitzaileak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO BESTEERABILTZAILEAK " + "VALUES ('" + jarraitzaileak + "')";
			DBKS.getDBKS().aginduaExekutatu(agindua);
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
			jarraituakDBraSartu(jarraituak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void jarraituakDBraSartu(ArrayList<String> jarraituak) {
		String agindua = "";
		for (int i = 0; i < jarraituak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO BESTEERABILTZAILEAK " + "VALUES ('" + jarraituak + "')";
			DBKS.getDBKS().aginduaExekutatu(agindua);
		}
	}

	public void zerrendakDeskargatu() {
		// eginda eta badabil
		// zerrenda guztiak eta bertan dauden jarraituak "zerrendak" parametroan gordetzen dira
		// zerrenda bakoitzak hasieran "." bat izango du
		// hurrengo izen guztiak zerrenda horretako jarraituak izango dira, "."-dun bat aurkitu arte
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
			zerrendakDBraSartu(zerrendak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to list the lists: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void zerrendakDBraSartu(ArrayList<String> zerrendak) {
		//zerrenda bakoitzeko for bat egin
		//zerrendaren izena eta jarraituak banatu if startsWith(".") batekin
		String agindua = "";
		for (int i = 0; i < zerrendak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO ZERRENDAK " + "VALUES ('" + zerrendak + "')";
			DBKS.getDBKS().aginduaExekutatu(agindua);
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
			jasotakoMezuakDBraSartu(norenak, mezuak, datak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get messages: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void jasotakoMezuakDBraSartu(ArrayList<String> norenak, ArrayList<String> mezuak, ArrayList<Date> datak) {
		String agindua = "";
		for (int i = 0; i < mezuak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO etc"+ mezuak.get(i) + "datak";
			DBKS.getDBKS().aginduaExekutatu(agindua);
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
			bidalitakoMezuakDBraSartu(norentzat, mezuak, datak);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get sent messages: " + te.getMessage());
			System.exit(-1);
		}
	}
	
	public void bidalitakoMezuakDBraSartu(ArrayList<String> norentzat, ArrayList<String> mezuak, ArrayList<Date> datak) {
		String agindua = "";
		for(int i = 0; i < mezuak.size(); i++) {
			//beheko agindu hau egin modu egokian
			agindua = "INSERT INTO etc"+ mezuak.get(i) + "datak";
			DBKS.getDBKS().aginduaExekutatu(agindua);
		}
	}
}
