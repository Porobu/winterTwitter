package isad.winteriscoming.frontend;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelNagusia extends JPanel {
	private static final long serialVersionUID = -8320951993218177160L;

	public PanelNagusia() {
		this.setOpaque(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.panelaDBHutsik();

	}

	private void panelaDBHutsik() {
		String textua1 = "Ongi etorri WinterTwitter mundura.";
		String textua2 = "Berria zara hemen beraz,";
		String textua3 = "jarraitu gurekin bide honetan eta zure eskakizunak burutuko ditugu.";
		Font f = new Font("Arial", Font.PLAIN, 16);
		JLabel ongiEtorri = new JLabel(textua1);
		JLabel ongiEtorri2 = new JLabel(textua2);
		JLabel ongiEtorri3 = new JLabel(textua3);
		ongiEtorri.setFont(f);
		ongiEtorri2.setFont(f);
		ongiEtorri3.setFont(f);
		this.add(ongiEtorri);
		this.add(ongiEtorri2);
		this.add(ongiEtorri3);
		
	}

}
