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
import isad.winteriscoming.salbuespenak.WinterTwitterSalbuespena;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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
	//private long userID;
	//fav, txio eta rt kopuru guztiak hemen gorde, erabiltzaileari pantailaratzeko

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
	}

	private AccessToken kredentzialakKargatu() {
		String token = null;
		String tokenSecret = null;
		ResultSet rs = DBKS.getDBKS().queryExekutatu("SELECT TOKEN, TOKENSECRET FROM ERABILTZAILEA");
		try {
			rs.next();
			token = rs.getString(1);
			tokenSecret = rs.getString(2);
		} catch (SQLException e) {
			return null;
		}
		return new AccessToken(token, tokenSecret);
	}

	private void kredentzialakGorde(String token, String tokenSecret) {
		int id = 0;
		DBKS.getDBKS().aginduaExekutatu("INSERT INTO ERABILTZAILEA(ID,NICK,IZENA,EMAIL,TOKEN,TOKENSECRET) VALUES('" + id
				+ "','0','0','0','" + token + "','" + tokenSecret + "')");
	}

	public void tokenarekinKonektatu() {
		twitter.setOAuthAccessToken(this.kredentzialakKargatu());
		Menua.botoiakKonektatzean();
	}

	public void tokenaLortu() {
		String pin = gureLogin.getPIN();
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
		Menua.botoiakKonektatzean();
	}

	public void deskonektatu() {
		gureKonexioa = new Konexioa();
		JOptionPane.showMessageDialog(null, "Twitteretik deskonektatu zara.", Nagusia.IZENBURUA,
				JOptionPane.INFORMATION_MESSAGE);
		Menua.botoiakHasieranEtaDeskonektatzean();
	}

	public Twitter getTwitter() {
		return twitter;
	}
}
