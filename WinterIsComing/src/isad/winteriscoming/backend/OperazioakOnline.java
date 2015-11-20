package isad.winteriscoming.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
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

	// favs = twitter.getFavorites(new Paging(orria, 100));
	// 16 bider egiten bada hau, petatu egiten du
	// beraz, paging(orriZenb, 100) egiten badugu, 15 birako loop bat ipini
	// behar dugu,
	// gero erabiltzaileari abisatu eta nonbaiten gorde geratzen den denbora

	// sinceId() ere erabili--> Paging page = new Paging(pagenumber, count,
	// sinceId)

	public static OperazioakOnline getOperazioak() {
		return gureOperazioak != null ? gureOperazioak : (gureOperazioak = new OperazioakOnline());
	}

	public void gustokoakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> favs;
			String benetakoData;
			// amaieran orria < 16 jarri behar da
			for (int orria = 1; orria < 500; orria++) {
				favs = twitter.getFavorites(new Paging(orria, 20));
				for (Status fav : favs) {
					String id = String.valueOf(fav.getId());
					benetakoData = itzuliBenetakoData(fav.getCreatedAt());
					String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
							+ this.replace(fav.getText()) + "', '" + benetakoData + "', 'gustokoa')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	public void txioakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> txioak;
			String benetakoData;
			for (int orria = 1; orria < 2; orria++) {
				txioak = twitter.getUserTimeline(new Paging(orria, 100));
				for (Status txioa : txioak) {
					String id = String.valueOf(txioa.getId());
					benetakoData = itzuliBenetakoData((txioa.getCreatedAt()));
					String agindua = "INSERT INTO TXIOA(id, edukia, data, mota)" + "VALUES ('" + id + "', '"
							+ this.replace(txioa.getText()) + "', '" + benetakoData + "', 'txioa')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	public void bertxioakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> bertxioak;
			String benetakoData;
			System.out.println("Showing @" + user.getScreenName() + "'s retweets.");
			for (int orria = 1; orria < 2; orria++) {
				bertxioak = twitter.getUserTimeline(new Paging(orria, 100));
				for (Status bertxio : bertxioak) {
					if (bertxio.getText().startsWith("RT @")) {
						String bertxioa = "bertxioa";
						String idea = String.valueOf(bertxio.getId());
						System.out.println("@" + bertxio.getId() + " - " + bertxio.getText());
						benetakoData = itzuliBenetakoData(bertxio.getCreatedAt());
						String agindua = "INSERT INTO TXIOA(id, edukia, data, mota)" + "VALUES ('" + idea + "', '"
								+ bertxio.getText() + "', '" + benetakoData + "', '" + bertxioa + "')";
						System.out.println(agindua);
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	public void aipamenakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> aipamenak;
			String benetakoData;
			System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
			for (int orria = 1; orria < 2; orria++) {
				aipamenak = twitter.getMentionsTimeline(new Paging(orria, 100));
				for (Status aipamen : aipamenak) {
					String idea = String.valueOf(aipamen.getCurrentUserRetweetId());
					System.out.println("@" + aipamen.getUser().getScreenName() + " - " + aipamen.getText());
					benetakoData = itzuliBenetakoData(aipamen.getCreatedAt());
					String agindua = "INSERT INTO AIPAMENAK(txioId, erabId, data, edukia)" + "VALUES ('"
							+ aipamen.getId() + "', '" + idea + "', '" + benetakoData + "' , '" + aipamen.getText()
							+ "')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	// hemen noa
	public void jarraitzaileakDeskargatu() {
		// egiten
		try {
			String jarraitzailea = "jarraitzailea";
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			List<User> jarraitzaileak;
			System.out.println("Listing followers:");
			for (int orria = 1; orria < 2; orria++) {
				jarraitzaileak = twitter.getFollowersList(erabiltzailea.getId(), -1);
				for (User jarraitzaile : jarraitzaileak) {
					String idea = String.valueOf(jarraitzaile.getId());
					String agindua = "INSERT INTO BESTEERABILTZAILEAK(id, izena, mota, nick)" + "VALUES ('" + idea
							+ "', '" + jarraitzaile.getScreenName() + "', '" + jarraitzailea + "', '"
							+ jarraitzaile.getName() + "')";
					System.out.println(agindua);
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: " + te.getMessage());
		}
	}

	public void jarraituakDeskargatu() {
		// egiten
		try {
			String jarraitzailea = "jarraitzailea";
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			List<User> jarraituak;
			System.out.println("Listing followed:");
			for (int orria = 1; orria < 2; orria++) {
				jarraituak = twitter.getFriendsList(erabiltzailea.getId(), -1);
				for (User jarraitu : jarraituak) {
					String idea = String.valueOf(jarraitu.getId());
					String agindua = "INSERT INTO BESTEERABILTZAILEAK(id, izena, mota, nick)" + "VALUES ('" + idea
							+ "', '" + jarraitu.getScreenName() + "', '" + jarraitzailea + "', '" + jarraitu.getName()
							+ "')";
					System.out.println(agindua);
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followed' ids: " + te.getMessage());
		}
	}

	public void zerrendakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			PagableResponseList<User> users;
			ResponseList<UserList> lists;
			lists = twitter.getUserLists(twitter.getScreenName());
			for (UserList list : lists) {
				users = twitter.getUserListMembers((list.getId()), -1);
				System.out.println(
						"Izena:" + list.getName() + " / Deskribapena: " + list.getDescription() + " / Jarraituak:");
				String ideaList = String.valueOf(list.getId());
				String agindua1 = "INSERT INTO ZERRENDA(id, izena)" + "VALUES ('" + ideaList + "','" + list.getName()
						+ "')";
				DBKS.getDBKS().aginduaExekutatu(agindua1);
				for (User user : users) {
					// "list" zerrenda bakoitzeko jarraituriko erabiltzaieak:
					System.out.println("@" + user.getScreenName());
					String idea = String.valueOf(user.getId());
					String agindua = "INSERT INTO DITU(erabId, zerrenId, erabiltzaileIzena, erabiltzaileNick)"
							+ "VALUES ('" + idea + "','" + ideaList + "','" + user.getScreenName() + "', '"
							+ user.getName() + "')";
					System.out.println(agindua);
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
				// zerrenda bakoitza datu-basean sartu
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to list the lists: " + te.getMessage());
		}
	}

	public void jasotakoMezuakDeskargatu() {
		// egiten
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		try {
			Paging paging = new Paging(1);
			List<DirectMessage> messages;
			do {
				messages = twitter.getDirectMessages(paging);
				for (DirectMessage message : messages) {
					System.out.println("From: @" + message.getSenderScreenName() + " id:" + message.getId() + " - "
							+ message.getText());
					// hemen agindua datu basera sartzeko
					String idea = String.valueOf(message.getId());
					String agindua = "INSERT INTO MEZUA(bidaltzaileIid, hartzaileId, data, edukia, bidaltzaileIzena, bidaltzaileNick, hartzaileIzena, hartzaileNick)"
							+ "VALUES ('" + idea + "')";
					System.out.println(agindua);
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
				paging.setPage(paging.getPage() + 1);
			} while (messages.size() > 0 && paging.getPage() < 10);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get messages: " + te.getMessage());
		}
	}

	public void bidalitakoMezuakDeskargatu() {
		// egiten
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		try {
			Paging paging = new Paging(1);
			List<DirectMessage> messages;
			do {
				messages = twitter.getSentDirectMessages(paging);
				for (DirectMessage message : messages) {
					System.out.println("From: @" + message.getRecipientScreenName() + " id:" + message.getId() + " - "
							+ message.getText());
					// hemen agindua datu basera sartzeko
				}
				paging.setPage(paging.getPage() + 1);
			} while (messages.size() > 0 && paging.getPage() < 10);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get sent messages: " + te.getMessage());
		}
	}

	public long hartuID(String taula, String mota, String ordena) {
		ResultSet emaitza = DBKS.getDBKS()
				.queryExekutatu("SELECT ID FROM " + taula + " WHERE MOTA = '" + mota + "' ORDER BY ID " + ordena);
		try {
			emaitza.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println(emaitza.getLong(1));
			return emaitza.getLong(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1L;
	}

	private String itzuliBenetakoData(Date data2) {
		String dataTxarra = String.valueOf(data2);
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

	public String replace(String aldatzekoa) {
		
		return aldatzekoa.replace("'", "''");

	}
}
