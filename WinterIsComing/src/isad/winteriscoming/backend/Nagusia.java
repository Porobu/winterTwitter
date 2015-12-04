package isad.winteriscoming.backend;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import isad.winteriscoming.frontend.AukerakUI;
import isad.winteriscoming.frontend.WinterTwitter;

public class Nagusia {
	public static final float BERTSIOA = 0.6F;
	public static final String IZENBURUA = "WinterTwitter " + Nagusia.BERTSIOA;
	private static String path;
	private static WinterTwitter wtFrame;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			try {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e1) {
			}
		}
		//PARA LOS MAC
		if(System.getProperty("os.name").contains("Mac OS X")){
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException e) {
			}
		}
		wtFrame = new WinterTwitter();
		wtFrame.dekoratuGabeHasieratu();
		AukerakUI nireAUI = new AukerakUI();
		path = nireAUI.hasi();
		DBKS.getDBKS().konektatu(path);
		wtFrame.dekoratu();
	}

	public static String getPath() {
		return path;
	}
}
