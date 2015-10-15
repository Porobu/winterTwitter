package isad.winteriscoming.backend;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import isad.winteriscoming.frontend.Login;

public class Konexioa {
	private Login gureLogin;
	private AccessToken accessToken;
	private Twitter twitter;
	private RequestToken requestToken;
	private Properties prop;
	private InputStream is = null;
	private OutputStream os = null;
	private File file = new File("WinterIsComing.properties");
	private static Konexioa gureKonexioa;

	private Konexioa() {
		gureLogin = null;
		accessToken = null;
		twitter = null;
		requestToken = null;
		prop = null;
		is = null;
		os = null;
		file = new File("WinterIsComing.properties");
	}

	public static Konexioa getKonexioa() {
		return gureKonexioa != null ? gureKonexioa : (gureKonexioa = new Konexioa());
	}

	public void logeatu() throws TwitterException {
		prop = new Properties();

		try {
			if (file.exists()) {
				is = new FileInputStream(file);
				prop.load(is);
				prop.setProperty("oauth.consumerKey", "zgxDQpdlpONlRDZHaUVyzAKE0");
				prop.setProperty("oauth.consumerSecret", "Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");
			}
			os = new FileOutputStream("WinterIsComing.properties");
			prop.store(os, "WinterIsComing.properties");
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException ignore) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException ignore) {
				}
			}
		}
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthAccessToken(null);
		requestToken = twitter.getOAuthRequestToken();
		accessToken = null;
		try {
			Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
		} catch (UnsupportedOperationException ignore) {
		} catch (IOException ignore) {
		} catch (URISyntaxException e) {
			throw new AssertionError(e);
		}
		gureLogin = new Login();
	}

	public void tokenaLortu() {
		String pin = gureLogin.getPIN();
		try {
			if (pin.length() > 0) {
				accessToken = twitter.getOAuthAccessToken(requestToken, pin);
			} else {
				accessToken = twitter.getOAuthAccessToken(requestToken);
			}
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				System.out.println("Unable to get the access token.");
			} else {
				te.printStackTrace();
			}
		}
		try {
			prop.setProperty("oauth.accessToken", accessToken.getToken());
			prop.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
			os = new FileOutputStream(file);
			prop.store(os, "twitter4j.properties");
			os.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.exit(-1);
		} finally

		{
			if (os != null) {
				try {
					os.close();
				} catch (IOException ignore) {
				}
			}
		}
	}
}
