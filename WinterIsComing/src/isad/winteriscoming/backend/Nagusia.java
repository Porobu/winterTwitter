package isad.winteriscoming.backend;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import isad.winteriscoming.frontend.WinterTwitter;

public class Nagusia {
	public static float BERTSIOA = 0.3F;
	private static JFrame frame;
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
		frame = new JFrame("WinterTwitter " + BERTSIOA);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
		path = DBKS.getDBKS().getDefaultPath();
		String[] aukerak2 = { "Kargatu", "Beste bat erabili" };
		int aukera = JOptionPane.YES_OPTION;
		if (aukera == JOptionPane.CLOSED_OPTION)
			System.exit(0);
		if (path != null) {
			aukera = JOptionPane.showOptionDialog(frame, "Defektuzko datu basea " + path + " karpetan aurkitu da.",
					"WinterTwitter " + BERTSIOA, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					aukerak2, aukerak2[0]);
		}
		if (path == null || aukera == JOptionPane.NO_OPTION) {
			String[] aukerak = { "Ireki", "Eraiki", "Itxi" };
			aukera = JOptionPane.showOptionDialog(frame, "Datu Basea beste leku batetik ireki edo eraiki nahi duzu?",
					"WinterTwitter " + BERTSIOA, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
					aukerak, aukerak[0]);
			switch (aukera) {
			case JOptionPane.YES_OPTION:
				path = DBKS.getDBKS().getPath();
				break;
			case JOptionPane.NO_OPTION:
				JOptionPane.showMessageDialog(frame,
						"Gogoratu datu basea zure karpeta pertsonalean badago eta\nWinterTwitter izena badu, automatikoki kargatuko da.",
						"WinterTwitter " + BERTSIOA, JOptionPane.INFORMATION_MESSAGE);
				path = DBKS.getDBKS().datuBaseaGordetzekoPath();
				DBKS.getDBKS().datuBaseaEraiki(path);

				break;
			default:
				System.exit(0);
				break;
			}
		}
		DBKS.getDBKS().konektatu(path);
		frame.dispose();
		new WinterTwitter();
	}

	public static String getPath() {
		return path;
	}
}
