package isad.winteriscoming.backend;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.conf.Configuration;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import isad.winteriscoming.frontend.Login;

public class Konexioa {
	static Login gureLogin;
	static AccessToken accessToken;
	static Twitter twitter;
	static RequestToken requestToken;
	static Properties prop;
	static InputStream is = null;
	static OutputStream os = null;
	static File file = new File("WinterIsComing.properties");

	public static void logeatu() throws TwitterException {

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
		// System.out.println("Got request token.");
		// System.out.println("Request token: " + requestToken.getToken());
		// System.out.println("Request token secret: " +
		// requestToken.getTokenSecret());
		accessToken = null;

		
			// System.out.println("Open the following URL and grant access
			// to your account:");
			// System.out.println(requestToken.getAuthorizationURL());
			try {
				Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
			} catch (UnsupportedOperationException ignore) {
			} catch (IOException ignore) {
			} catch (URISyntaxException e) {
				throw new AssertionError(e);
			}

			// unai zuretzat:

			// programie hemetik pasetan danien leiho bat zabalduko da (login)
			// hor erabiltzailiek PIN-e sartu biher deu ta OK emon,
			// zuk lortu biher dozune da erabiltzailiek OK-ri emoten dotzonien
			// PIN-e hartzie da ta hemen beien segitzen dan kodigoan sartzie,
			// hor beien dauen "String pin" variable moduen

			// hori loru ta gero twitterrera bidan moduen konektatu dala
			// probatu biher dogu, "test" antzeko bat ein biher dogu, txikitxue

			// horrekaz bi gauzakaz sprint-e finikiteta egongo zan

			gureLogin = new Login();
		
	}

	public static void metodoBerria() {
		String pin = gureLogin.getPIN();
		// hau finikitatu
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

		// hemetik segidu
		try

		{
			prop.setProperty("oauth.accessToken", accessToken.getToken());
			prop.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
			os = new FileOutputStream(file);
			prop.store(os, "twitter4j.properties");
			os.close();
		} catch (

		IOException ioe)

		{
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
		
		//System.exit(0);
	}
}
