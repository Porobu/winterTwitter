package isad.winteriscoming.backend;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import isad.winteriscoming.frontend.Login;
import isad.winteriscoming.salbuespenak.SentitzenNaizException;

public class Konexioa {
	private Login gureLogin;
	private AccessToken accessToken;
	private Twitter twitter;
	private RequestToken requestToken;
	private Properties prop;
	private InputStream is = null;
	private OutputStream os = null;
	// private File file = new File("WinterIsComing.properties");
	private static Konexioa gureKonexioa;

	private Konexioa() {
		gureLogin = null;
		accessToken = null;
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.setOAuthConsumerKey("zgxDQpdlpONlRDZHaUVyzAKE0");
		builder.setOAuthConsumerSecret("Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");
		Configuration configuration = builder.build();
		TwitterFactory factory = new TwitterFactory(configuration);
		twitter = factory.getInstance();
		//twitter = TwitterFactory.getSingleton();
		//twitter.setOAuthConsumer(null, null);
		//twitter.setOAuthConsumer("zgxDQpdlpONlRDZHaUVyzAKE0", "Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");
		requestToken = null;
		prop = null;
		is = null;
		os = null;
		// file = new File("WinterIsComing.properties");
	}

	public static Konexioa getKonexioa() {
		return gureKonexioa != null ? gureKonexioa : (gureKonexioa = new Konexioa());
	}

	public void logeatu() {
		// prop = new Properties();
		//
		// try {
		// if (file.exists()) {
		// is = new FileInputStream(file);
		// prop.load(is);
		// prop.setProperty("oauth.consumerKey", "zgxDQpdlpONlRDZHaUVyzAKE0");
		// prop.setProperty("oauth.consumerSecret",
		// "Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");
		// }
		// os = new FileOutputStream("WinterIsComing.properties");
		// prop.store(os, "WinterIsComing.properties");
		// } catch (IOException ioe) {
		// throw new SentitzenNaizException("Ezin da fitxategia erabili");
		// } finally {
		// if (is != null) {
		// try {
		// is.close();
		// } catch (IOException ignore) {
		// }
		// }
		// if (os != null) {
		// try {
		// os.close();
		// } catch (IOException ignore) {
		// }
		// }
		// }
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
			gurePW = new PrintWriter(System.getProperty("user.home") + "/.Sudokula.txt");
		} catch (FileNotFoundException salbuespena) {
			File gureArtxiboa = new File(System.getProperty("user.home") + "/.Sudokula.txt");
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
		// try {
		// //prop.setProperty("oauth.accessToken", accessToken.getToken());
		// //prop.setProperty("oauth.accessTokenSecret",
		// accessToken.getTokenSecret());
		// os = new FileOutputStream(file);
		// prop.store(os, "twitter4j.properties");
		// os.close();
		// twitter.setOAuthAccessToken(accessToken);
		// //twitter.setOAuthConsumer("zgxDQpdlpONlRDZHaUVyzAKE0",
		// "Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");
		//
		// } catch (IOException ioe) {
		// throw new SentitzenNaizException("Ezin da fitxategia aldatu");
		// } finally {
		// if (os != null) {
		// try {
		// os.close();
		// } catch (IOException ignore) {
		// }
		// }
		// }
	}

	public Twitter getTwitter() {
		return twitter;
	}

	private static void storeAccessToken() {

	}
}
