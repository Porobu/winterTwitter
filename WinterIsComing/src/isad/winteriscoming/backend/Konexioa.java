package isad.winteriscoming.backend;

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
 
public class Konexioa {
	// cb.setDebugEnabled(true)
    //.setOAuthConsumerKey("zgxDQpdlpONlRDZHaUVyzAKE0")
   // .setOAuthConsumerSecret("Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");

//https://xmeng.wordpress.com/2011/07/10/how-to-handle-sign-in-with-twitter-using-twitter4j/
 
    public static void main(String[] args) {
 
        ConfigurationBuilder cb = new ConfigurationBuilder();
         
         
        //the following is set without accesstoken- desktop client
        cb.setDebugEnabled(true)
      .setOAuthConsumerKey("zgxDQpdlpONlRDZHaUVyzAKE0")
      .setOAuthConsumerSecret("Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog")
      .setOAuthAccessToken(null)
      .setOAuthAccessTokenSecret(null);
        
        //ziurtatu acces token/secret behar den moduan dagoela
        //ziurtatu consumer key/secret behar den moduan daudela
        //ziurtatu sistemaren erlojua sinkronizatuta dagoela
        
        //hemen zerbait dago: http://org/en/code-examples.html
        
   
        try {
            TwitterFactory tf = new TwitterFactory(cb.build());
            Twitter twitter = tf.getInstance();
             
            try {
 
                // get request token.
                // this will throw IllegalStateException if access token is already available
                // this is oob, desktop client version
                RequestToken requestToken = twitter.getOAuthRequestToken();
 
                System.out.println("Got request token.");
                System.out.println("Request token: " + requestToken.getToken());
                System.out.println("Request token secret: " + requestToken.getTokenSecret());
 
                System.out.println("|-----");
 
                AccessToken accessToken = null;
 
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                 
                while (null == accessToken) {
                    System.out.println("Open the following URL and grant access to your account:");
                    System.out.println(requestToken.getAuthorizationURL());
                    try {
                        Desktop.getDesktop().browse(new URI(requestToken.getAuthorizationURL()));
                    } catch (UnsupportedOperationException ignore) {
                    } catch (IOException ignore) {
                    } catch (URISyntaxException e) {
                        throw new AssertionError(e);
                    }
                    System.out.print("Enter the PIN(if available) and hit enter after you granted access.[PIN]:");
                    String pin = br.readLine();
                    //konparatu 
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
                }
                System.out.println("Got access token.");
                System.out.println("Access token: " + accessToken.getToken());
                System.out.println("Access token secret: " + accessToken.getTokenSecret());
                //
                cb.setOAuthAccessToken(accessToken.getToken());
                cb.setOAuthAccessTokenSecret(accessToken.getTokenSecret());
                System.out.println("Successfully stored access token to configuration Builder.");
                System.exit(0);
                //
            } catch (IllegalStateException ie) {
                // access token is already available, or consumer key/secret is not set.
                if (!twitter.getAuthorization().isEnabled()) {
                    System.out.println("OAuth consumer key/secret is not set.");
                    System.exit(-1);
                }
            }
           // beheko lerro biak komentatuta daude READ ONLY baimenekin 
           // ezin delako twiterreko egoera eguneratu (WRITE)
            
           //Status status = twitter.updateStatus(testStatus);
           //System.out.println("Successfully updated the status to [" + status.getText() + "].");
 
           System.out.println("ready exit");
             
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Failed to read the system input.");
            System.exit(-1);
        }
    }
}
