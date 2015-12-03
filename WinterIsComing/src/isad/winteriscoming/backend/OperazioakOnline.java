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

	public void txioakJaitsi() {
		int orria = 0;
		boolean amaituta = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> txioak;
			String benetakoData;
			Long zaharrena = hartuID("Txioa", "txioa", "ASC");
			Long berriena = hartuID("Txioa", "txioa", "DESC");
			// momentu honetan salbuespen bat pantailaratu daiteke ResultSet-a
			// hutsik dagoelako, hori normala da
			if (zaharrena.equals(Long.MAX_VALUE)) {
				// kasu honetan erabiltzaileak ez du txiorik datu basean
				while (!amaituta) {
					orria++;
					txioak = twitter.getUserTimeline(new Paging(orria, 20));
					if (txioak.isEmpty()) {
						amaituta = true;
					}
					for (Status txio : txioak) {
						if (!txio.isRetweet()) {
							String id = String.valueOf(txio.getId());
							benetakoData = itzuliBenetakoData(txio.getCreatedAt());
							String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
									+ replace(txio.getText()) + "', '" + benetakoData + "', 'txioa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
			} else {
				// kasu honetan erabiltzaileak badu txiorik datu-basean
				// While honetan datu basean ez dauden txio berrienak sartuko
				// dira
				while (!amaituta) {
					orria++;
					txioak = twitter.getUserTimeline(new Paging(orria, 20, berriena));
					if (txioak.isEmpty()) {
						amaituta = true;
					}
					for (Status txio : txioak) {
						if (!txio.isRetweet()) {
							String id = String.valueOf(txio.getId());
							benetakoData = itzuliBenetakoData(txio.getCreatedAt());
							String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
									+ replace(txio.getText()) + "', '" + benetakoData + "', 'txioa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
				// while honetan datu basean ez dauden txio zaharrak sartuko
				// dira
				orria = 0;
				amaituta = false;
				while (!amaituta) {
					orria++;
					txioak = twitter.getUserTimeline(new Paging(orria, 20, 1L, zaharrena));
					if (txioak.isEmpty()) {
						amaituta = true;
					}
					for (Status txio : txioak) {
						String id = String.valueOf(txio.getId());
						if (!id.equals(String.valueOf(zaharrena)) && !txio.isRetweet()) {
							benetakoData = itzuliBenetakoData(txio.getCreatedAt());
							String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
									+ replace(txio.getText()) + "', '" + benetakoData + "', 'txioa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	public void bertxioakJaitsi() {
		int orria = 0;
		boolean amaituta = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> bertxioak;
			String benetakoData;
			Long zaharrena = hartuID("Txioa", "bertxioa", "ASC");
			Long berriena = hartuID("Txioa", "bertxioa", "DESC");
			// momentu honetan salbuespen bat pantailaratu daiteke ResultSet-a
			// hutsik dagoelako, hori normala da
			if (zaharrena.equals(Long.MAX_VALUE)) {
				// kasu honetan erabiltzaileak ez du bertxiorik datu basean
				while (!amaituta) {
					orria++;
					bertxioak = twitter.getUserTimeline(new Paging(orria, 20));
					if (bertxioak.isEmpty()) {
						amaituta = true;
					}
					for (Status bertxio : bertxioak) {
						if (bertxio.isRetweet()) {
							String id = String.valueOf(bertxio.getId());
							benetakoData = itzuliBenetakoData(bertxio.getCreatedAt());
							String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
									+ replace(bertxio.getText()) + "', '" + benetakoData + "', 'bertxioa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
			} else {
				// kasu honetan erabiltzaileak badu bertxiorik datu-basean
				// While honetan datu basean ez dauden bertxio berrienak sartuko
				// dira
				while (!amaituta) {
					orria++;
					bertxioak = twitter.getUserTimeline(new Paging(orria, 20, berriena));
					if (bertxioak.isEmpty()) {
						amaituta = true;
					}
					for (Status bertxio : bertxioak) {
						if (bertxio.isRetweet()) {
							String id = String.valueOf(bertxio.getId());
							benetakoData = itzuliBenetakoData(bertxio.getCreatedAt());
							String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
									+ replace(bertxio.getText()) + "', '" + benetakoData + "', 'bertxioa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
				// while honetan datu basean ez dauden bertxio zaharrak sartuko
				// dira
				orria = 0;
				amaituta = false;
				while (!amaituta) {
					orria++;
					bertxioak = twitter.getUserTimeline(new Paging(orria, 20, 1L, zaharrena));
					if (bertxioak.isEmpty()) {
						amaituta = true;
					}
					for (Status bertxio : bertxioak) {
						String id = String.valueOf(bertxio.getId());
						if (!id.equals(String.valueOf(zaharrena)) && bertxio.isRetweet()) {
							benetakoData = itzuliBenetakoData(bertxio.getCreatedAt());
							String agindua = "INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
									+ replace(bertxio.getText()) + "', '" + benetakoData + "', 'bertxioa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				System.out.println("Failed to get timeline: " + te.getMessage());
		}
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
								+ replace(fav.getText()) + "', '" + benetakoData + "', 'gustokoa')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
			} else {
				// kasu honetan erabiltzaileak badu gustokorik datu-basean
				// While honetan datu basean ez dauden txio berrienak sartuko
				// dira
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
								+ replace(fav.getText()) + "', '" + benetakoData + "', 'gustokoa')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
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
									+ replace(fav.getText()) + "', '" + benetakoData + "', 'gustokoa')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	public void aipamenakJaitsi() {
		int orria = 0;
		boolean amaituta = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> aipamenak;
			String benetakoData;
			Long zaharrena = hartuID("Txioa", "bertxioa", "ASC");
			Long berriena = hartuID("Txioa", "bertxioa", "DESC");
			// momentu honetan salbuespen bat pantailaratu daiteke ResultSet-a
			// hutsik dagoelako, hori normala da
			if (zaharrena.equals(Long.MAX_VALUE)) {
				// kasu honetan erabiltzaileak ez du aipamenik datu basean
				while (!amaituta) {
					orria++;
					aipamenak = twitter.getMentionsTimeline(new Paging(orria, 20));
					if (aipamenak.isEmpty()) {
						amaituta = true;
					}
					for (Status aipamen : aipamenak) {
						String id = String.valueOf(aipamen.getCurrentUserRetweetId());

						benetakoData = itzuliBenetakoData(aipamen.getCreatedAt());
						String agindua = "INSERT INTO AIPAMENAK(txioId, erabId, data, edukia)" + "VALUES ('"
								+ aipamen.getId() + "', '" + id + "', '" + benetakoData + "' , '" + replace(aipamen.getText())
								+ "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
			} else {
				// kasu honetan erabiltzaileak badu aipamenik datu-basean
				// While honetan datu basean ez dauden aipamen berrienak sartuko
				// dira
				while (!amaituta) {
					orria++;
					aipamenak = twitter.getMentionsTimeline(new Paging(orria, 20, berriena));
					if (aipamenak.isEmpty()) {
						amaituta = true;
					}
					for (Status aipamen : aipamenak) {
						String id = String.valueOf(aipamen.getCurrentUserRetweetId());

						benetakoData = itzuliBenetakoData(aipamen.getCreatedAt());
						String agindua = "INSERT INTO AIPAMENAK(txioId, erabId, data, edukia)" + "VALUES ('"
								+ aipamen.getId() + "', '" + id + "', '" + benetakoData + "' , '" + replace(aipamen.getText())
								+ "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
				// while honetan datu basean ez dauden aipamen zaharrak sartuko
				// dira
				orria = 0;
				amaituta = false;
				while (!amaituta) {
					orria++;
					aipamenak = twitter.getMentionsTimeline(new Paging(orria, 20, 1L, zaharrena));
					if (aipamenak.isEmpty()) {
						amaituta = true;
					}
					for (Status aipamen : aipamenak) {
						String id = String.valueOf(aipamen.getId());
						if (!id.equals(String.valueOf(zaharrena))) {
							benetakoData = itzuliBenetakoData(aipamen.getCreatedAt());
							String agindua = "INSERT INTO AIPAMENAK(txioId, erabId, data, edukia)" + "VALUES ('"
									+ aipamen.getId() + "', '" + id + "', '" + benetakoData + "' , '"
									+ replace(aipamen.getText()) + "')";
							DBKS.getDBKS().aginduaExekutatu(agindua);
						}
					}
				}
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				System.out.println("Failed to get timeline: " + te.getMessage());
		}
	}

	// hemetik segi konponketarekin
	public void jarraitzaileakDeskargatu() {
		long zenb = 0L;
		int count = 20;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			long jarraitzaileKopTwitter = erabiltzailea.getFollowersCount();
			long jarraitzaileKopDB = jarraitzaileKopTwitter;
			ResultSet jKDB = DBKS.getDBKS()
					.queryExekutatu("SELECT COUNT(*) FROM BESTEERABILTZAILEAK WHERE mota='jarraitzailea'");
			try {
				if (jKDB.next()) {
					jarraitzaileKopDB = jKDB.getLong(1);
				}
				else jarraitzaileKopDB = 0;
			} catch (SQLException e) {
			}
			String nextCursor = ("SELECT kurtsoreBalioa FROM PAGING WHERE mota='jarraitzailea'");
			ResultSet emaitza = DBKS.getDBKS().queryExekutatu(nextCursor);
			try {
				if (emaitza.next()) {
					if (emaitza.getLong(1) != 0)
						zenb = emaitza.getLong(1);
				}
				else zenb = -1L;
			} catch (SQLException e) {
			}
			if (!(jarraitzaileKopDB==jarraitzaileKopTwitter)) {
				if (jarraitzaileKopTwitter>jarraitzaileKopDB && zenb == 0) {
					zenb = -1L;
					count = (int) (jarraitzaileKopTwitter-jarraitzaileKopDB);
				}
				do {
					// jarraitzaileak =
					// twitter.getFollowersList(erabiltzailea.getId(),
					// nextCursor);
					PagableResponseList<User> usersResponse = twitter.getFollowersList(erabiltzailea.getId(), zenb, count);
					for (User user : usersResponse) {
						String id = String.valueOf(user.getId());
						String agindua = "INSERT INTO BESTEERABILTZAILEAK(id, izena, mota, nick)" + "VALUES ('" + id
								+ "', '" + replace(user.getScreenName()) + "', ' jarraitzailea ', '" + replace(user.getName()) + "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
					zenb = usersResponse.getNextCursor();
				} while (zenb > 0);
				DBKS.getDBKS().aginduaExekutatu("UPDATE PAGING SET kurtsoreBalioa=" + zenb + " WHERE mota='jarraitzailea'");
			}	
		} catch (TwitterException te) {
			if (te.exceededRateLimitation()) {
				DBKS.getDBKS()
						.aginduaExekutatu("UPDATE PAGING SET kurtsoreBalioa=" + zenb + " WHERE mota='jarraitzailea'");
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				System.out.println("Failed to get timeline: " + te.getMessage());
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
			else if (ordena.equals("DESC"))
				return 1L;
			else
				return Long.MAX_VALUE;
		} catch (SQLException e) {
			return 1L;
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

	private void rateLimitMezua(int segunduak) {
		int minutuak = segunduak / 60;
		segunduak = segunduak % 60;
		JOptionPane
				.showMessageDialog(
						null, "Ezin izan da zure eskakizuna guztiz bete, itxaron " + minutuak + " minutu eta "
								+ segunduak + " segundu.",
						"Eskakizun kopuru maximoa gainditua", JOptionPane.WARNING_MESSAGE);
	}
}
