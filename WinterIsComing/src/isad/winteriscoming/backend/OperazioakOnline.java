package isad.winteriscoming.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

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

	// since id eta max id erabili paging-etan
	// mezuak bajatzekoa konpondu

	public static OperazioakOnline getOperazioak() {
		return gureOperazioak != null ? gureOperazioak : (gureOperazioak = new OperazioakOnline());
	}

	public void gustokoakJaitsi() {
		int orria = 0;
		boolean amaituta = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> favs;
			String benetakoData;
			Long zaharrena = hartuID("Txioa", "gustokoa", "ASC");
			Long berriena = hartuID("Txioa", "gustokoa", "DESC");
			// momentu honetan salbuespen bat pantailaratu daiteke ResultSet-a
			// hutsik dagoelako, hori normala da
			if (zaharrena.equals(Long.MAX_VALUE)) {
				// kasu honetan erabiltzaileak ez du gustokorik datu basean
				System.out.println("1. else-an:\n");
				while (!amaituta) {
					orria++;
					favs = twitter.getFavorites(new Paging(orria, 20));
					if (favs.isEmpty()) {
						amaituta = true;
					}
					for (Status fav : favs) {
						String id = String.valueOf(fav.getId());
						benetakoData = itzuliBenetakoData(fav.getCreatedAt());
						String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
								+ this.replace(fav.getText()) + "', '" + benetakoData + "', 'gustokoa')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
					System.out.println("Orri zenbakia: " + orria);
					System.out.println("Orriaren luzera: " + favs.size());
					System.out.println("Amaituta? --> " + amaituta + "\n");
				}
			} else {
				System.out.println("2. else-an:\n");
				// kasu honetan erabiltzaileak baditu gustokorik datu-basean
				// While honetan datu basean ez dauden txio berrienak sartuko
				// dira
				System.out.println("1. while-an:\n");
				while (!amaituta) {
					orria++;
					favs = twitter.getFavorites(new Paging(orria, 20, berriena));
					if (favs.isEmpty()) {
						amaituta = true;
					}
					for (Status fav : favs) {
						String id = String.valueOf(fav.getId());
						benetakoData = itzuliBenetakoData(fav.getCreatedAt());
						String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
								+ this.replace(fav.getText()) + "', '" + benetakoData + "', 'gustokoa')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
					System.out.println("Orri zenbakia: " + orria);
					System.out.println("Orriaren luzera: " + favs.size());
					System.out.println("Amaituta? --> " + amaituta);
				}
				System.out.println("2. while-an:\n");
				// while honetan datu basean ez dauden txio zaharrak sartuko
				// dira
				orria = 0;
				amaituta = false;
				while (!amaituta) {
					orria++;
					favs = twitter.getFavorites(new Paging(orria, 20, 1L, zaharrena));
					if (favs.isEmpty()) {
						amaituta = true;
					}
					for (Status fav : favs) {
						String id = String.valueOf(fav.getId());
						if (!id.equals(String.valueOf(zaharrena))) {
							benetakoData = itzuliBenetakoData(fav.getCreatedAt());
							String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
									+ this.replace(fav.getText()) + "', '" + benetakoData + "', 'gustokoa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
					System.out.println("Orri zenbakia: " + orria);
					System.out.println("Orriaren luzera: " + favs.size());
					System.out.println("Amaituta? --> " + amaituta);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				int minutuak = segunduak / 60;
				segunduak = segunduak % 60;
				JOptionPane
						.showMessageDialog(null,
								"Ezin izan da zure eskakizuna bete, itxaron " + minutuak + " minutu eta " + segunduak
										+ " segundu.",
								"Eskakizun kopuru maximoa gainditua", JOptionPane.WARNING_MESSAGE);

			} else
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
			if (te.exceededRateLimitation()) {

				// sartutak azkenengo id-a hartu eta gorde Datu-Basean.
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				int minutuak = segunduak / 60;
				segunduak = segunduak % 60;
				JOptionPane
						.showMessageDialog(null,
								"Ezin izan da zure eskakizuna bete, itxaron " + minutuak + " minutu eta " + segunduak
										+ " segundu.",
								"Eskakizun kopuru maximoa gainditua", JOptionPane.WARNING_MESSAGE);
				// leiho bat zabaldu eta falta den denbora bistaratu
				// erabiltzaileari
			}

		}
	}

	public void bertxioakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> bertxioak;
			String benetakoData;
			for (int orria = 1; orria < 2; orria++) {
				bertxioak = twitter.getUserTimeline(new Paging(orria, 100));
				for (Status bertxio : bertxioak) {
					if (bertxio.getText().startsWith("RT @")) {
						String bertxioa = "bertxioa";
						String idea = String.valueOf(bertxio.getId());
						benetakoData = itzuliBenetakoData(bertxio.getCreatedAt());
						String agindua = "INSERT INTO TXIOA(id, edukia, data, mota)" + "VALUES ('" + idea + "', '"
								+ bertxio.getText() + "', '" + benetakoData + "', '" + bertxioa + "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

	public void aipamenakDeskargatu() {
		// egiten
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			List<Status> aipamenak;
			String benetakoData;
			for (int orria = 1; orria < 2; orria++) {
				aipamenak = twitter.getMentionsTimeline(new Paging(orria, 100));
				for (Status aipamen : aipamenak) {
					String idea = String.valueOf(aipamen.getCurrentUserRetweetId());

					benetakoData = itzuliBenetakoData(aipamen.getCreatedAt());
					String agindua = "INSERT INTO AIPAMENAK(txioId, erabId, data, edukia)" + "VALUES ('"
							+ aipamen.getId() + "', '" + idea + "', '" + benetakoData + "' , '" + aipamen.getText()
							+ "')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
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
			for (int orria = 1; orria < 2; orria++) {
				jarraitzaileak = twitter.getFollowersList(erabiltzailea.getId(), -1);
				for (User jarraitzaile : jarraitzaileak) {
					String idea = String.valueOf(jarraitzaile.getId());
					String agindua = "INSERT INTO BESTEERABILTZAILEAK(id, izena, mota, nick)" + "VALUES ('" + idea
							+ "', '" + jarraitzaile.getScreenName() + "', '" + jarraitzailea + "', '"
							+ jarraitzaile.getName() + "')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

	public void jarraituakDeskargatu() {
		// egiten
		try {
			String jarraitzailea = "jarraitzailea";
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			List<User> jarraituak;
			for (int orria = 1; orria < 2; orria++) {
				jarraituak = twitter.getFriendsList(erabiltzailea.getId(), -1);
				for (User jarraitu : jarraituak) {
					String idea = String.valueOf(jarraitu.getId());
					String agindua = "INSERT INTO BESTEERABILTZAILEAK(id, izena, mota, nick)" + "VALUES ('" + idea
							+ "', '" + jarraitu.getScreenName() + "', '" + jarraitzailea + "', '" + jarraitu.getName()
							+ "')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
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
				String ideaList = String.valueOf(list.getId());
				String agindua1 = "INSERT INTO ZERRENDA(id, izena)" + "VALUES ('" + ideaList + "','" + list.getName()
						+ "')";
				DBKS.getDBKS().aginduaExekutatu(agindua1);
				for (User user : users) {
					// "list" zerrenda bakoitzeko jarraituriko erabiltzaieak:

					String idea = String.valueOf(user.getId());
					String agindua = "INSERT INTO DITU(erabId, zerrenId, erabiltzaileIzena, erabiltzaileNick)"
							+ "VALUES ('" + idea + "','" + ideaList + "','" + user.getScreenName() + "', '"
							+ user.getName() + "')";

					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
				// zerrenda bakoitza datu-basean sartu
			}
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

	public void mezuakDeskargatu() {
		// egiten
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		try {
			Paging paging = new Paging(1, 100);
			List<DirectMessage> messages;
			do {
				messages = twitter.getDirectMessages(paging);

				for (DirectMessage message : messages) {
					// hemen agindua datu basera sartzeko
					String benetakoData = itzuliBenetakoData(message.getCreatedAt());
					String idea = String.valueOf(message.getId());
					String agindua = "INSERT INTO MEZUA(id, data, edukia, bidaltzaileIzena, hartzaileIzena)"
							+ "VALUES ('" + idea + "', '" + benetakoData + "','" + message.getText() + "', '"
							+ message.getSenderScreenName() + "'," + " '" + message.getRecipientScreenName() + "')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
				paging.setPage(paging.getPage() + 1);
			} while (messages.size() > 0 && paging.getPage() < 10);
		} catch (TwitterException te) {
			te.printStackTrace();
		}
	}

	public long hartuID(String taula, String mota, String ordena) {
		ResultSet emaitza = DBKS.getDBKS()
				.queryExekutatu("SELECT ID FROM " + taula + " WHERE MOTA = '" + mota + "' ORDER BY ID " + ordena);
		try {
			if (emaitza.next())
				return emaitza.getLong(1);
			else if (ordena.equals("ASC"))
				return 1L;
			else
				return Long.MAX_VALUE;
		} catch (SQLException e) {
			return 1L;
		}
	}

	private int hartuPaging(String mota) {
		// hau konpondu
		ResultSet emaitza = DBKS.getDBKS().queryExekutatu("SELECT Orria FROM PAGING WHERE MOTA = '" + mota + "'");
		try {
			if (emaitza.next())
				return emaitza.getInt(1);
			else
				return 1;
		} catch (SQLException e) {
			return 1;
		}
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
