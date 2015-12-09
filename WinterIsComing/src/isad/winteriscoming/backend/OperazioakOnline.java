package isad.winteriscoming.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import isad.winteriscoming.frontend.WinterTwitter;
import isad.winteriscoming.salbuespenak.WinterTwitterSalbuespena;
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

	public static OperazioakOnline getOperazioak() {
		return gureOperazioak != null ? gureOperazioak : (gureOperazioak = new OperazioakOnline());
	}

	private OperazioakOnline() {
	}

	// aipamenakSartu metodoa egiteke
	// erabiltzaileakSartu metodoa egiteke (jarraitzaile & jarraituak)
	// mezuakSartu metodoa egiteke
	// zerrendaSartu metodoa egiteke

	public void aipamenakJaitsi() {
		int orria = 0;
		boolean amaituta = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> aipamenak;
			String benetakoData;
			Long zaharrena = hartuID("Txioa", "bertxioa", "ASC");
			Long berriena = hartuID("Txioa", "bertxioa", "DESC");
			while (!amaituta) {
				orria++;
				aipamenak = twitter.getMentionsTimeline(new Paging(orria, 20, zaharrena));
				if (aipamenak.isEmpty()) {
					amaituta = true;
				}
				for (Status aipamen : aipamenak) {
					String id = String.valueOf(aipamen.getCurrentUserRetweetId());

					benetakoData = itzuliBenetakoData(aipamen.getCreatedAt());
					String agindua = "INSERT OR REPLACE INTO AIPAMENAK(txioId, erabId, data, edukia)" + "VALUES ('"
							+ aipamen.getId() + "', '" + id + "', '" + benetakoData + "' , '"
							+ replace(aipamen.getText()) + "')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
				}
			}
			orria = 0;
			amaituta = false;
			while (!amaituta) {
				orria++;
				aipamenak = twitter.getMentionsTimeline(new Paging(orria, 20, 1L, berriena));
				if (aipamenak.isEmpty()) {
					amaituta = true;
				}
				for (Status aipamen : aipamenak) {
					String id = String.valueOf(aipamen.getId());
					if (!id.equals(String.valueOf(zaharrena))) {
						benetakoData = itzuliBenetakoData(aipamen.getCreatedAt());
						String agindua = "INSERT OR REPLACE INTO AIPAMENAK(txioId, erabId, data, edukia)" + "VALUES ('"
								+ aipamen.getId() + "', '" + id + "', '" + benetakoData + "' , '"
								+ replace(aipamen.getText()) + "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
			}
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Aipamenak jaisten amaitu da.",
					Nagusia.IZENBURUA, JOptionPane.INFORMATION_MESSAGE);
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				throw new WinterTwitterSalbuespena("Ezin dira aipamenak jaitsi");
		}
	}

	public void gustokoakJaitsi() {
		int orria = 0;
		boolean amaituta = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> favs;
			Long zaharrena = hartuID("Txioa", "gustokoa", "ASC");
			Long berriena = hartuID("Txioa", "gustokoa", "DESC");
			while (!amaituta) {
				orria++;
				favs = twitter.getFavorites(new Paging(orria, 20, zaharrena));
				if (favs.isEmpty()) {
					amaituta = true;
				} else
					this.txioakDBsartu(favs, "gustokoa");
			}
			orria = 0;
			amaituta = false;
			while (!amaituta) {
				orria++;
				favs = twitter.getFavorites(new Paging(orria, 20, 1L, berriena));
				if (favs.isEmpty()) {
					amaituta = true;
				} else
					this.txioakDBsartu(favs, "gustokoa");
			}
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Gustokoak jaisten amaitu da.",
					Nagusia.IZENBURUA, JOptionPane.INFORMATION_MESSAGE);
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				throw new WinterTwitterSalbuespena("Ezin dira gustokoak jaitsi");
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
		if (dataTxarra.contains("CET")) {
			data[6] = dataTxarra.charAt(24);
			data[7] = dataTxarra.charAt(25);
			data[8] = dataTxarra.charAt(26);
			data[9] = dataTxarra.charAt(27);
		} else {
			data[6] = dataTxarra.charAt(25);
			data[7] = dataTxarra.charAt(26);
			data[8] = dataTxarra.charAt(27);
			data[9] = dataTxarra.charAt(28);
		}
		return String.copyValueOf(data);
	}

	private String itzuliHilabetea(String st) {
		switch (st.toLowerCase()) {
		case "jan":
			return "01";
		case "feb":
			return "02";
		case "mar":
			return "03";
		case "apr":
			return "04";
		case "may":
			return "05";
		case "jun":
			return "06";
		case "jul":
			return "07";
		case "aug":
			return "08";
		case "sep":
			return "09";
		case "oct":
			return "10";
		case "nov":
			return "11";
		default:
			return "12";
		}
	}

	public void jarraituakJaitsi() {
		long zenb = 1L;
		int count = 20;
		boolean hasierakoak = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			long jarraituKopTwitter = erabiltzailea.getFriendsCount();
			long jarraituKopDB = jarraituKopTwitter;
			ResultSet jKDB = DBKS.getDBKS()
					.queryExekutatu("SELECT COUNT(*) FROM BESTEERABILTZAILEAK WHERE mota='jarraitua'");
			try {
				if (jKDB.next()) {
					jarraituKopDB = jKDB.getLong(1);
				} else
					jarraituKopDB = 0;
			} catch (SQLException e) {
			}
			String nextCursor = ("SELECT kurtsoreBalioa FROM PAGING WHERE mota='jarraitua'");
			ResultSet emaitza = DBKS.getDBKS().queryExekutatu(nextCursor);
			try {
				if (emaitza.next())
					zenb = emaitza.getLong(1);
				else
					zenb = -1L;
			} catch (SQLException e) {
			}
			if (!(jarraituKopDB == jarraituKopTwitter)) {
				if (jarraituKopTwitter > jarraituKopDB && zenb == 0L) {
					zenb = -1L;
					count = (int) (jarraituKopTwitter - jarraituKopDB);
					hasierakoak = true;
				}
				do {
					PagableResponseList<User> following = twitter.getFriendsList(erabiltzailea.getId(), zenb, count);
					if (hasierakoak)
						count = count - 20;
					jarraituJarraitzaileakDBSartu(following, "jarraitua");
					zenb = following.getNextCursor();
				} while (zenb > 0);
				DBKS.getDBKS().aginduaExekutatu(
						"INSERT OR REPLACE INTO PAGING(kurtsoreBalioa, mota)" + "VALUES ('0', 'jarraitua')");
			}
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Jarraituak jaisten amaitu da.",
					Nagusia.IZENBURUA, JOptionPane.INFORMATION_MESSAGE);
		} catch (TwitterException te) {
			if (te.exceededRateLimitation()) {
				DBKS.getDBKS().aginduaExekutatu(
						"INSERT OR REPLACE INTO PAGING(kurtsoreBalioa, mota)" + "VALUES ('" + zenb + "', 'jarraitua')");
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				throw new WinterTwitterSalbuespena("Ezin dira jarraituak jaitsi");
		}
	}

	/**
	 * Erabiltzaileak adierazitako jarraitu edo jarraitzaileak datu basera
	 * sartuko dira.
	 * 
	 * @param mota
	 */
	public void jarraituJarraitzaileakDBSartu(PagableResponseList<User> jarraituJarraitzaileak, String mota) {
		String agindua;
		for (User jarraituJarraitzaile : jarraituJarraitzaileak) {
			String id = String.valueOf(jarraituJarraitzaile.getId());
			if (mota.equals("jarraitzailea")) {
				agindua = "INSERT OR REPLACE INTO BESTEERABILTZAILEAK(id, izena, mota, nick)" + "VALUES ('" + id
						+ "', '" + replace(jarraituJarraitzaile.getName()) + "', '" + mota + "', '"
						+ replace(jarraituJarraitzaile.getScreenName()) + "')";
			} else {
				agindua = "INSERT OR REPLACE INTO BESTEERABILTZAILEAK(id, izena, mota, nick)" + "VALUES ('" + id
						+ "', '" + replace(jarraituJarraitzaile.getName()) + "', '" + mota + "', '"
						+ replace(jarraituJarraitzaile.getScreenName()) + "')";
			}
			DBKS.getDBKS().aginduaExekutatu(agindua);
		}
	}

	public void jarraitzaileakJaitsi() {
		long zenb = -1L;
		int count = 20;
		boolean hasierakoak = false;
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
				} else
					jarraitzaileKopDB = 0;
			} catch (SQLException e) {
			}
			String nextCursor = ("SELECT kurtsoreBalioa FROM PAGING WHERE mota='jarraitzailea'");
			ResultSet emaitza = DBKS.getDBKS().queryExekutatu(nextCursor);
			try {
				if (emaitza.next())
					zenb = emaitza.getLong(1);
				else
					zenb = -1L;
			} catch (SQLException e) {
			}
			if (!(jarraitzaileKopDB == jarraitzaileKopTwitter)) {
				if (jarraitzaileKopTwitter > jarraitzaileKopDB && zenb == 0L) {
					zenb = -1L;
					count = (int) (jarraitzaileKopTwitter - jarraitzaileKopDB);
					hasierakoak = true;
				}
				do {
					PagableResponseList<User> followers = twitter.getFollowersList(erabiltzailea.getId(), zenb, count);
					if (hasierakoak)
						count = count - 20;
					jarraituJarraitzaileakDBSartu(followers, "jarraitzailea");
					zenb = followers.getNextCursor();
				} while (zenb > 0);
				DBKS.getDBKS().aginduaExekutatu(
						"INSERT OR REPLACE INTO PAGING(kurtsoreBalioa, mota)" + "VALUES ('0', 'jarraitzailea')");
			}

			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Jarraitzaileak jaisten amaitu da.",
					Nagusia.IZENBURUA, JOptionPane.INFORMATION_MESSAGE);
		} catch (TwitterException te) {
			if (te.exceededRateLimitation()) {
				DBKS.getDBKS().aginduaExekutatu("INSERT OR REPLACE INTO PAGING(kurtsoreBalioa, mota)" + "VALUES ('"
						+ zenb + "', 'jarraitzailea')");
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				throw new WinterTwitterSalbuespena("Ezin dira jarraitzaileak jaitsi");
		}
	}

	public void mezuakJaitsi() {
		// twitter4j-ren errore batengatik bakarrik bueltatzen dir
		// urte bateko denbora tartean bidali eta jasotako mezuak
		Twitter twitter = Konexioa.getKonexioa().getTwitter();
		int orria = 1;
		String nextCursor = ("SELECT kurtsoreBalioa FROM PAGING WHERE mota='mezuak'");
		ResultSet emaitza = DBKS.getDBKS().queryExekutatu(nextCursor);
		try {
			if (emaitza.next())
				orria = (int) emaitza.getLong(1);
		} catch (SQLException e) {
		}
		try {
			if (orria != 0) {
				Paging paging = new Paging(orria, 20);
				List<DirectMessage> messages, sentMessages;
				do {
					sentMessages = twitter.getSentDirectMessages(paging);
					messages = twitter.getDirectMessages(paging);
					for (DirectMessage message : messages) {
						String benetakoData = itzuliBenetakoData(message.getCreatedAt());
						String id = String.valueOf(message.getId());
						String agindua = "INSERT OR REPLACE INTO MEZUA(id, data, edukia, bidaltzaileIzena, hartzaileIzena)"
								+ "VALUES ('" + id + "', '" + benetakoData + "','" + message.getText() + "', '"
								+ message.getSenderScreenName() + "'," + " '" + message.getRecipientScreenName() + "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
					for (DirectMessage sentMessage : sentMessages) {
						String benetakoData = itzuliBenetakoData(sentMessage.getCreatedAt());
						String id = String.valueOf(sentMessage.getId());
						String agindua = "INSERT OR REPLACE INTO MEZUA(id, data, edukia, bidaltzaileIzena, hartzaileIzena)"
								+ "VALUES ('" + id + "', '" + benetakoData + "','" + sentMessage.getText() + "', '"
								+ sentMessage.getSenderScreenName() + "'," + " '" + sentMessage.getRecipientScreenName()
								+ "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
					paging.setPage(++orria);
				} while (messages.size() > 0 || sentMessages.size() > 0);
			}
			DBKS.getDBKS()
					.aginduaExekutatu("INSERT OR REPLACE INTO PAGING(kurtsoreBalioa, mota)" + "VALUES ('0', 'mezuak')");
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Mezuak jaisten amaitu da.", Nagusia.IZENBURUA,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (TwitterException te) {
			if (te.exceededRateLimitation()) {
				DBKS.getDBKS().aginduaExekutatu(
						"INSERT OR REPLACE INTO PAGING(kurtsoreBalioa, mota)" + "VALUES ('" + orria + "', 'mezuak')");
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				throw new WinterTwitterSalbuespena("Ezin dira mezuak jaitsi");
		}
	}

	private void rateLimitMezua(int segunduak) {
		int minutuak = segunduak / 60;
		segunduak = segunduak % 60;
		JOptionPane.showMessageDialog(
				WinterTwitter.getOraingoWT(), "Ezin izan da zure eskakizuna guztiz bete, itxaron " + minutuak
						+ " minutu eta " + segunduak + " segundu.",
				"Eskakizun kopuru maximoa gainditua", JOptionPane.WARNING_MESSAGE);
	}

	public String replace(String aldatzekoa) {
		return aldatzekoa.replace("'", "''");
	}

	private void txioakDBsartu(List<Status> txioak, String mota) {
		String benetakoData = "";
		String bertxio = "bertxioa";
		String agindua = "";
		for (Status txio : txioak) {
			String id = String.valueOf(txio.getId());
			benetakoData = itzuliBenetakoData(txio.getCreatedAt());
			if (mota.equals("txioa") && txio.isRetweet()) {
				agindua = "INSERT OR REPLACE INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
						+ replace(txio.getText()) + "', '" + benetakoData + "', '" + bertxio + "')";
			} else {
				agindua = "INSERT OR REPLACE INTO TXIOA(id, edukia, data, mota) VALUES ('" + id + "', '"
						+ replace(txio.getText()) + "', '" + benetakoData + "', '" + mota + "')";
			}
			DBKS.getDBKS().aginduaExekutatu(agindua);
		}
	}

	public void txioakJaitsi() {
		int orriZenb = 0;
		boolean amaituta = false;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			List<Status> txioak;
			long zaharrena = hartuID("Txioa", "txioa", "ASC");
			long berriena = hartuID("Txioa", "txioa", "DESC");
			while (!amaituta) {
				orriZenb++;
				txioak = twitter.getUserTimeline(new Paging(orriZenb, 20, zaharrena));
				if (txioak.isEmpty())
					amaituta = true;
				else
					this.txioakDBsartu(txioak, "txioa");
			}
			orriZenb = 0;
			amaituta = false;
			while (!amaituta) {
				orriZenb++;
				txioak = twitter.getUserTimeline(new Paging(orriZenb, 20, 1L, berriena));
				if (txioak.isEmpty())
					amaituta = true;
				else
					this.txioakDBsartu(txioak, "txioa");
			}
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Txioak jaisten amaitu da.", Nagusia.IZENBURUA,
					JOptionPane.INFORMATION_MESSAGE);
		} catch (TwitterException te) {
			te.printStackTrace();
			if (te.exceededRateLimitation()) {
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				throw new WinterTwitterSalbuespena("Ezin dira txioak jaitsi");
		}
	}

	public void zerrendakJaitsi() {
		int orria = -1;
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User erabiltzailea = twitter.verifyCredentials();
			ResponseList<UserList> zerrendak = twitter.getUserLists(erabiltzailea.getId());
			String nextCursor = ("SELECT kurtsoreBalioa FROM PAGING WHERE mota='zerrendak'");
			ResultSet emaitza = DBKS.getDBKS().queryExekutatu(nextCursor);
			try {
				if (emaitza.next())
					orria = (int) emaitza.getLong(1);
			} catch (SQLException e) {
			}
			if (orria != 0) {
				for (UserList zerrenda : zerrendak) {
					String agindua = "INSERT OR REPLACE INTO ZERRENDA(id, izena, deskribapena)" + "VALUES ('"
							+ String.valueOf(zerrenda.getId()) + "', '" + zerrenda.getName() + "', '"
							+ zerrenda.getDescription() + "')";
					DBKS.getDBKS().aginduaExekutatu(agindua);
					ResponseList<User> zerrendaKideak = twitter.getUserListMembers(zerrenda.getId(), orria);
					for (User zerrendaKidea : zerrendaKideak) {
						agindua = "INSERT OR REPLACE INTO DITU(erabId, zerrenId, erabNick, zerrendaIzena, erabIzena)"
								+ "VALUES ('" + String.valueOf(erabiltzailea.getId()) + "', '"
								+ String.valueOf(zerrenda.getId()) + "','" + this.replace(zerrendaKidea.getScreenName())
								+ "','" + this.replace(zerrenda.getName()) + "', '"
								+ this.replace(zerrendaKidea.getName()) + "')";
						DBKS.getDBKS().aginduaExekutatu(agindua);
					}
				}
			}
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Zerrendak jaisten amaitu da.",
					Nagusia.IZENBURUA, JOptionPane.INFORMATION_MESSAGE);
		} catch (TwitterException te) {
			if (te.exceededRateLimitation()) {
				DBKS.getDBKS().aginduaExekutatu("INSERT OR REPLACE INTO PAGING(kurtsoreBalioa, mota)" + "VALUES ('"
						+ orria + "', 'zerrendak')");
				int segunduak = te.getRateLimitStatus().getSecondsUntilReset();
				rateLimitMezua(segunduak);
			} else
				throw new WinterTwitterSalbuespena("Ezin dira zerrendak jaitsi");
		}
	}
}
