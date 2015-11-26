package isad.winteriscoming.backend;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import isad.winteriscoming.frontend.AukerakUI;
import isad.winteriscoming.frontend.WinterTwitter;

public class Nagusia {
	public static final float BERTSIOA = 0.55F;
	public static final String TITULUA = "WinterTwitter " + Nagusia.BERTSIOA;
	private static String path;

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
		AukerakUI nireAUI = new AukerakUI();
		path = nireAUI.hasi();
		DBKS.getDBKS().konektatu(path);
		new WinterTwitter();
	}

	public static String getPath() {
		return path;
	}
}
