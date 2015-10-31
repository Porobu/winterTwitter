package isad.winteriscoming.backend;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class OperazioakOffline {
	
	public void bilatuTxioetan(String st) {
		// TODO datu basearekin komunikazioa lortu ondoren inplementatu
		try {
			Twitter twitter = Konexioa.getKonexioa().getTwitter();
			User user = twitter.verifyCredentials();
			// egiteke
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}
	}
}
