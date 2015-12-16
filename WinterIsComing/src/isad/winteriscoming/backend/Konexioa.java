package isad.winteriscoming.backend;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import isad.winteriscoming.frontend.Login;
import isad.winteriscoming.frontend.Menua;
import isad.winteriscoming.frontend.WinterTwitter;
import isad.winteriscoming.salbuespenak.WinterTwitterSalbuespena;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class Konexioa {
	private Login gureLogin;
	private AccessToken accessToken;
	private Twitter twitter;
	private RequestToken requestToken;
	private boolean konektatuta;
	private static Konexioa gureKonexioa;

	private Konexioa() {
		konektatuta = false;
		gureLogin = null;
		accessToken = null;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setOAuthConsumerKey("zgxDQpdlpONlRDZHaUVyzAKE0");
		cb.setOAuthConsumerSecret("Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");
		Configuration configuration = cb.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		twitter = factory.getInstance();
		requestToken = null;
	}

	public boolean isKonektatuta() {
		return konektatuta;
	}

	public static Konexioa getKonexioa() {
		return gureKonexioa != null ? gureKonexioa : (gureKonexioa = new Konexioa());
	}

	public void logeatu() {
		twitter.setOAuthAccessToken(null);
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e1) {
			throw new WinterTwitterSalbuespena("Ez da token-a lortu");
		}
		accessToken = null;
		try {
			Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
		} catch (UnsupportedOperationException ignore) {
		} catch (IOException ignore) {
		} catch (URISyntaxException e) {
			throw new WinterTwitterSalbuespena("Ezin da web gunea ireki");
		}
		gureLogin = new Login();
		WinterTwitter.getOraingoWT().getPanela().panelaAldatu(gureLogin);
	}

	private AccessToken kredentzialakKargatu() {
		String token = null;
		String tokenSecret = null;
		ResultSet rs = DBKS.getDBKS().queryExekutatu("SELECT token, tokenSecret FROM ERABILTZAILEA");
		try {
			rs.next();
			token = rs.getString(1);
			tokenSecret = rs.getString(2);
		} catch (SQLException e) {
			return null;
		}
		return new AccessToken(token, tokenSecret);
	}

	/**
	 * Erabiltzaile berri bat sortzean bera kautotzeko erabili diren tokenak
	 * datu basean gordeko dira.
	 * 
	 * @param token
	 *            Twittereko tokena
	 * @param tokenSecret
	 *            Twitterreko token sekretua
	 */
	private void kredentzialakGorde(String token, String tokenSecret) {
		User us = null;
		try {
			us = twitter.verifyCredentials();
		} catch (TwitterException e) {
		}
		DBKS.getDBKS()
				.aginduaExekutatu("INSERT OR REPLACE INTO ERABILTZAILEA(id,nick,izena,token,tokenSecret) VALUES('"
						+ us.getId() + "','" + us.getScreenName() + "','" + us.getName() + "','" + token + "','"
						+ tokenSecret + "')");
	}

	public void tokenarekinKonektatu() {
		twitter.setOAuthAccessToken(this.kredentzialakKargatu());
		this.konexioaMezua();
		Menua.botoiakKonektatzean();
	}

	public void tokenaLortu() {
		String pin = gureLogin.getPIN();
		WinterTwitter.getOraingoWT().getPanela().nagusiaIpini();
		try {
			if (pin.length() > 0) {
				accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			} else {
				accessToken = twitter.getOAuthAccessToken(requestToken);
			}
			twitter.setOAuthAccessToken(accessToken);
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				throw new WinterTwitterSalbuespena("Ezin da token-a lortu (401 errorea)");
			} else {
				throw new WinterTwitterSalbuespena("Ezin da token-a lortu");
			}
		}
		konektatuta = true;
		if (Login.getLogin().getGordetzeko())
			this.kredentzialakGorde(accessToken.getToken(), accessToken.getTokenSecret());
		this.konexioaMezua();
		Menua.botoiakKonektatzean();
	}

	public void deskonektatu() {
		gureKonexioa = new Konexioa();
		JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), "Twitteretik deskonektatu zara.", Nagusia.IZENBURUA,
				JOptionPane.INFORMATION_MESSAGE);
		Menua.botoiakHasieranEtaDeskonektatzean();
	}

	public Twitter getTwitter() {
		return twitter;
	}

	private void konexioaMezua() {
		User user = null;
		try {
			user = twitter.verifyCredentials();
		} catch (TwitterException e) {
		}
		if (user == null)
			throw new WinterTwitterSalbuespena("Ezin da Twitterrera konektatu gordetako erabiltzailearerkin!");
		String izena = user.getName();
		String nick = user.getScreenName();
		JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(),
				"Twitterera konektatu zara " + izena + " (" + nick + ") kontuarekin", Nagusia.IZENBURUA,
				JOptionPane.INFORMATION_MESSAGE);
	}
}
