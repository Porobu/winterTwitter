package isad.winteriscoming.backend;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import isad.winteriscoming.frontend.WinterTwitter;

public class Nagusia {
	public static float BERTSIOA = 0.2F;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		new WinterTwitter();
		DBKS.getDBKS().konektatu(DBKS.getDBKS().getPath());
		Konexioa.getKonexioa().logeatu();
	}
}
