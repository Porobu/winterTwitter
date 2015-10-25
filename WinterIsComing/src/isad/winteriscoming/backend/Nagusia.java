package isad.winteriscoming.backend;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Nagusia {
	public static float BERTSIOA = 0.2F;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}

		int aukera = JOptionPane.showConfirmDialog(null, "Datu Basea ireki edo eraiki nahi duzu?", "WinterTwitter",
				JOptionPane.YES_NO_CANCEL_OPTION);
		switch (aukera) {
		case JOptionPane.YES_OPTION:
			DBKS.getDBKS().konektatu(DBKS.getDBKS().getPath());
			break;
		case JOptionPane.NO_OPTION:
			DBKS.getDBKS().datuBaseaEraiki();
		default:
			System.exit(0);
			break;
		}
		Konexioa.getKonexioa().logeatu();
		DBKS.getDBKS().konexioaItxi();
	}
}
