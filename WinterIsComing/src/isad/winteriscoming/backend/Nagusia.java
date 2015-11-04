package isad.winteriscoming.backend;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import isad.winteriscoming.frontend.WinterTwitter;

public class Nagusia {
	public static float BERTSIOA = 0.25F;
	private static JFrame frame;
	private static String path;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
		}
		String[] aukerak = { "Ireki", "Eraiki", "Itxi" };
		frame = new JFrame("WinterTwitter " + BERTSIOA);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		int aukera = JOptionPane.showOptionDialog(frame, "Datu Basea ireki edo eraiki nahi duzu?",
				"WinterTwitter " + BERTSIOA, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				aukerak, aukerak[0]);
		switch (aukera) {
		case JOptionPane.YES_OPTION:
			path = DBKS.getDBKS().getPath();
			DBKS.getDBKS().konektatu(path);
			break;
		case JOptionPane.NO_OPTION:
			path = DBKS.getDBKS().datuBaseaGordetzekoPath();
			DBKS.getDBKS().datuBaseaEraiki(path);
			DBKS.getDBKS().konektatu(path);
			break;
		default:
			System.exit(0);
			break;
		}
		new WinterTwitter();
	}

	public static String getPath() {
		return path;
	}
	
	public static JFrame getFrame()
	{
		return frame;
	}
}
