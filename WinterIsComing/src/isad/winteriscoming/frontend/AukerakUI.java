package isad.winteriscoming.frontend;

import javax.swing.JOptionPane;

import isad.winteriscoming.backend.DBKS;
import isad.winteriscoming.backend.Nagusia;

public class AukerakUI {

	private String path;
	private int aukera;
	private FitxategiOperazioakUI nireFOUI;

	public AukerakUI() {
		aukera = JOptionPane.YES_OPTION;
		nireFOUI = new FitxategiOperazioakUI();
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
		String[] aukerak = { "Ireki", "Berria Sortu" };
		aukera = JOptionPane.showOptionDialog(WinterTwitter.getOraingoWT(),
				"Datu Basea beste leku batetik ireki edo berria sortu nahi duzu?\nAplikazioa erabili duzun lehenengo aldia bada, berria sortu sakatu.",
				Nagusia.TITULUA, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, aukerak, aukerak[0]);
		this.aukeratu();
	}

	private void hasiDefektuzkoDB() {
		String[] aukerak = { "Beste Bat Erabili", "Berri bat sortu", "Defektuzkoa Kargatu" };
		aukera = JOptionPane.showOptionDialog(WinterTwitter.getOraingoWT(),
				"Defektuzko datu basea " + path + " karpetan aurkitu da.", Nagusia.TITULUA,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, aukerak, aukerak[2]);
		this.aukeratu();
	}

	private void aukeratu() {
		String luzea = "Gogoratu datu basea zure karpeta pertsonalean badago eta\nWinterTwitter izena badu, automatikoki kargatuko da.";
		switch (aukera) {
		case JOptionPane.YES_OPTION:
			path = nireFOUI.getPath();
			break;
		case JOptionPane.NO_OPTION:
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), luzea, Nagusia.TITULUA,
					JOptionPane.INFORMATION_MESSAGE);
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
}
