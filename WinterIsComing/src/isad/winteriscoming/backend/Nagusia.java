package isad.winteriscoming.backend;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import isad.winteriscoming.frontend.AukerakUI;
import isad.winteriscoming.frontend.WinterTwitter;

public class Nagusia {
	public static float BERTSIOA = 0.55F;
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
		AukerakUI nireNUI = new AukerakUI();
		path = nireNUI.hasi();
		DBKS.getDBKS().konektatu(path);
		new WinterTwitter();
	}

	public static String getPath() {
		return path;
	}

	public static String titulua() {
		return "WinterTwitter " + Nagusia.BERTSIOA;
	}
}
