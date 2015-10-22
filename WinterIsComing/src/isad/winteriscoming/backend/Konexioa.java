package isad.winteriscoming.backend;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import isad.winteriscoming.frontend.Login;
import isad.winteriscoming.salbuespenak.SentitzenNaizException;
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
	private static Konexioa gureKonexioa;

	private Konexioa() {
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

	public static Konexioa getKonexioa() {
		return gureKonexioa != null ? gureKonexioa : (gureKonexioa = new Konexioa());
	}

	public void logeatu() {
		twitter.setOAuthAccessToken(null);
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch (TwitterException e1) {
			throw new SentitzenNaizException("Ez da token-a lortu");
		}
		accessToken = null;
		try {
			Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
		} catch (UnsupportedOperationException ignore) {
		} catch (IOException ignore) {
		} catch (URISyntaxException e) {
			throw new SentitzenNaizException("Ezin da web gunea ireki");
		}
		gureLogin = new Login();
	}

	private AccessToken kredentzialakKargatu() {
		String token = null;
		String tokenSecret = null;

		try {
			BufferedReader gureBufferedReader = new BufferedReader(
					new FileReader(System.getProperty("user.home") + "/.winterTwitter.txt"));
			token = gureBufferedReader.readLine();
			tokenSecret = gureBufferedReader.readLine();
			gureBufferedReader.close();
		} catch (IOException salbuespena) {
		}
		return new AccessToken(token, tokenSecret);
	}

	public void textuaIdatzi() {
		PrintWriter gurePW = null;
		try {
			gurePW = new PrintWriter(System.getProperty("user.home") + "/.winterTwitter.txt");
		} catch (FileNotFoundException salbuespena) {
			File gureArtxiboa = new File(System.getProperty("user.home") + "/.winterTwitter.txt");
			gureArtxiboa.delete();
			this.textuaIdatzi();
		}
		if (gurePW != null) {
			gurePW.println(accessToken.getToken());
			gurePW.println(accessToken.getTokenSecret());
			gurePW.flush();
			gurePW.close();
		}
		String gureString = System.getProperty("os.name");
		gureString.toLowerCase();
		if (gureString.contains("win") || gureString.contains("Win")) {
			try {
				Runtime.getRuntime().exec("attrib +H " + System.getProperty("user.home") + "/.Sudokula.txt");
			} catch (IOException gureSalbuespena) {
				throw new SentitzenNaizException("Fitxategia ezin da gorde!!!!!");
			}
		}
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
				throw new SentitzenNaizException("Ezin da token-a lortu (401 errorea)");
			} else {
				throw new SentitzenNaizException("Ezin da token-a lortu");
			}
		}
	}

	public Twitter getTwitter() {
		return twitter;
	}
}
