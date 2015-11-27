package isad.winteriscoming.frontend;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import isad.winteriscoming.backend.DBKS;
import isad.winteriscoming.backend.Nagusia;

public class AukerakUI extends JFrame {

	private String path;
	private static final long serialVersionUID = 4542039722413287103L;
	private int aukera;
	private FitxategiOperazioakUI nireFOUI;

	public AukerakUI() {
		aukera = JOptionPane.YES_OPTION;
		nireFOUI = new FitxategiOperazioakUI();
		this.setTitle("WinterTwitter " + Nagusia.BERTSIOA);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.ikonoaJarri();
		this.setVisible(true);
	}

	public String hasi() {
		path = DBKS.getDBKS().getDefaultPath();
		if (path == null)
			hasiEzDefektuzkoDB();
		else
			hasiDefektuzkoDB();
		return path;
	}

	private void hasiEzDefektuzkoDB() {
		String[] aukerak = { "Ireki", "Berria Eraiki" };
		aukera = JOptionPane.showOptionDialog(this, "Datu Basea beste leku batetik ireki edo eraiki nahi duzu?",
				Nagusia.TITULUA, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, aukerak, aukerak[0]);
		this.aukeratu();
	}

	private void hasiDefektuzkoDB() {
		String[] aukerak = { "Beste Bat Erabili", "Berri bat eraiki", "Defektuzkoa Kargatu" };
		aukera = JOptionPane.showOptionDialog(this, "Defektuzko datu basea " + path + " karpetan aurkitu da.",
				Nagusia.TITULUA, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, aukerak,
				aukerak[2]);
		this.aukeratu();
	}

	private void aukeratu() {
		String luzea = "Gogoratu datu basea zure karpeta pertsonalean badago eta\nWinterTwitter izena badu, automatikoki kargatuko da.";
		switch (aukera) {
		case JOptionPane.YES_OPTION:
			path = nireFOUI.getPath();
			break;
		case JOptionPane.NO_OPTION:
			JOptionPane.showMessageDialog(this, luzea, Nagusia.TITULUA, JOptionPane.INFORMATION_MESSAGE);
			path = nireFOUI.datuBaseaGordetzekoPath();
			nireFOUI.datuBaseaEraiki(path);
			break;
		case JOptionPane.CANCEL_OPTION:
			break;
		default:
			System.exit(0);
			break;
		}
	}

	private void ikonoaJarri() {
		InputStream stream = WinterTwitter.class.getResourceAsStream("/isad/winteriscoming/logoa.png");
		BufferedImage logoa;
		try {
			logoa = ImageIO.read(stream);
			this.setIconImage(logoa);
		} catch (IOException e) {
		}
	}

}
