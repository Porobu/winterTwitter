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
 
public class Konexioa {
	// cb.setDebugEnabled(true)
    //.setOAuthConsumerKey("zgxDQpdlpONlRDZHaUVyzAKE0")
   // .setOAuthConsumerSecret("Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");

//https://xmeng.wordpress.com/2011/07/10/how-to-handle-sign-in-with-twitter-using-twitter4j/
 
    public static void main(String[] args) {
 
        //ConfigurationBuilder cb = new ConfigurationBuilder();
         
         
        //the following is set without accesstoken- desktop client
        //cb.setDebugEnabled(true)
    	//.setOAuthConsumerKey("zgxDQpdlpONlRDZHaUVyzAKE0")
        //.setOAuthConsumerSecret("Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog")
        //.setOAuthAccessToken(null)
        //.setOAuthAccessTokenSecret(null);
      
        
        //ziurtatu acces token/secret behar den moduan dagoela
        //ziurtatu consumer key/secret behar den moduan daudela
        //ziurtatu sistemaren erlojua sinkronizatuta dagoela
        
        //hemen zerbait dago: http://org/en/code-examples.html
        
        File file = new File("WinterIsComing.properties");
        Properties prop = new Properties();
        InputStream is = null;
        OutputStream os = null;
        try {
            if (file.exists()) {
                is = new FileInputStream(file);
                prop.load(is);
                prop.setProperty("oauth.consumerKey", "zgxDQpdlpONlRDZHaUVyzAKE0");
                prop.setProperty("oauth.consumerSecret", "Vm4hoxq8D0DpU7ag540LCN36w8ZzmgmcKNpWjw1iJxVPb7UJog");
                System.out.println("ondo sortu deu");
            }
           if (args.length < 2) {
                if (null == prop.getProperty("oauth.consumerKey")
                        && null == prop.getProperty("oauth.consumerSecret")) {
                    // consumer key/secret are not set in twitter4j.properties
                    System.out.println(
                            "Usage: java twitter4j.examples.oauth.GetAccessToken [consumer key] [consumer secret]");
                    System.exit(-1);
                }
            } else {
                
                os = new FileOutputStream("WinterIsComing.properties");
                prop.store(os, "WinterIsComing.properties");
            }
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
        try {
        	 Twitter twitter = new TwitterFactory().getInstance();
             twitter.setOAuthAccessToken(null);
             RequestToken requestToken = twitter.getOAuthRequestToken();
             System.out.println("Got request token.");
             System.out.println("Request token: " + requestToken.getToken());
             System.out.println("Request token secret: " + requestToken.getTokenSecret());
             AccessToken accessToken = null;
             
 
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                 
               /* while (null == accessToken) {
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
    }*/
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
             //hemetik segidu
             try {
                 prop.setProperty("oauth.accessToken", accessToken.getToken());
                 prop.setProperty("oauth.accessTokenSecret", accessToken.getTokenSecret());
                 os = new FileOutputStream(file);
                 prop.store(os, "twitter4j.properties");
                 os.close();
             } catch (IOException ioe) {
                 ioe.printStackTrace();
                 System.exit(-1);
             } finally {
                 if (os != null) {
                     try {
                         os.close();
                     } catch (IOException ignore) {
                     }
                 }
             }
             System.out.println("Successfully stored access token to " + file.getAbsolutePath() + ".");
             System.exit(0);
         } catch (TwitterException te) {
             te.printStackTrace();
             System.out.println("Failed to get accessToken: " + te.getMessage());
             System.exit(-1);
         } catch (IOException ioe) {
             ioe.printStackTrace();
             System.out.println("Failed to read the system input.");
             System.exit(-1);
         }
     }
}
