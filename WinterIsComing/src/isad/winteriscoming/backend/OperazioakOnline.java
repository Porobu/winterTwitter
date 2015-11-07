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

	//favs = twitter.getFavorites(new Paging(orria, 100));
	//16 bider egiten bada hau, petatu egiten du
	//beraz, paging(orriZenb, 100) egiten badugu, 15 birako loop bat ipini behar dugu,
	//gero erabiltzaileari abisatu eta nonbaiten gorde geratzen den denbora
	
	// sinceId() ere erabili--> Paging page = new Paging(pagenumber, count, sinceId)

	public static OperazioakOnline getOperazioak() {
		return gureOperazioak != null ? gureOperazioak : (gureOperazioak = new OperazioakOnline());
	}

	public void gustokoakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> favs;
			System.out.println("Showing @" + user.getScreenName() + "'s favorites.");
			String benetakoData;
			// amaieran orria < 16 jarri behar da
			for (int orria = 1; orria < 2; orria++) {
				favs = twitter.getFavorites(new Paging(orria, 100));
				for (Status fav : favs) {
					System.out.println("@" + fav.getId() + " - " + fav.getText());
					benetakoData = itzuliBenetakoData(String.valueOf(fav.getCreatedAt()));
					System.out.println(" Data ondo: " + benetakoData);
					// String agindua = "INSERT INTO TXIOA VALUES ('" +
					// fav.getId() + "', '" + fav.getText() + "', '"
					// + benetakoData + ", gustokoa')";
					// DBKS.getDBKS().aginduaExekutatu(agindua);
				}
				System.out.println(orria + ". orria");
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	public void gustokoakDeskargatuSinceID(long id) {
		// TODO Auto-generated method stub
	}

	public void txioakDeskargatu() {
		// eginda
		// createdAt modifikatu
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> tweets = twitter.getUserTimeline();
			System.out.println("Showing @" + user.getScreenName() + "'s tweets.");
			for (Status tweet : tweets) {
				System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
				// txioak.add(tweet.getText());
				// datak.add(tweet.getCreatedAt());
				// String agindua = "INSERT INTO TXIOA VALUES ('" +
				// tweet.getId() + "', '" + tweet.getText() + "', '"
				// + "04/11/2015" + ", txioa')";
				// DBKS.getDBKS().aginduaExekutatu(agindua);
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
	
	private String itzuliBenetakoData(String dataTxarra) {
		System.out.println(dataTxarra + ":");
		char[] data = new char[10];
		data[0] = dataTxarra.charAt(8);
		data[1] = dataTxarra.charAt(9);
		data[2] = '/';
		char[] hil = new char[3];
		hil[0] = dataTxarra.charAt(4);
		hil[1] = dataTxarra.charAt(5);
		hil[2] = dataTxarra.charAt(6);
		String hilabete = itzuliHilabetea(String.copyValueOf(hil));
		data[3] = hilabete.charAt(0);
		data[4] = hilabete.charAt(1);
		data[5] = '/';
		data[6] = dataTxarra.charAt(24);
		data[7] = dataTxarra.charAt(25);
		data[8] = dataTxarra.charAt(26);
		data[9] = dataTxarra.charAt(27);
		return String.copyValueOf(data);
	}

	private String itzuliHilabetea(String st) {
		if (st.equalsIgnoreCase("Jan"))
			return "01";
		else if (st.equalsIgnoreCase("Feb"))
			return "02";
		else if (st.equalsIgnoreCase("Mar"))
			return "03";
		else if (st.equalsIgnoreCase("Apr"))
			return "04";
		else if (st.equalsIgnoreCase("May"))
			return "05";
		else if (st.equalsIgnoreCase("Jun"))
			return "06";
		else if (st.equalsIgnoreCase("Jul"))
			return "07";
		else if (st.equalsIgnoreCase("Aug"))
			return "08";
		else if (st.equalsIgnoreCase("Sep"))
			return "09";
		else if (st.equalsIgnoreCase("Oct"))
			return "10";
		else if (st.equalsIgnoreCase("Nov"))
			return "11";
		else
			return "12";
	}
}
