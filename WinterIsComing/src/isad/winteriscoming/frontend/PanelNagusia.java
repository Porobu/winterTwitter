package isad.winteriscoming.frontend;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PanelNagusia extends JPanel {
	private static final long serialVersionUID = -8320951993218177160L;

	public PanelNagusia() {
		this.setOpaque(true);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.panelaDBHutsik();
	}

	private void panelaDBHutsik() {
		String textua = "Ongi etorri WinterTwitter mundura. Berria zara hemen beraz, jarraitu gurekin bide honetan eta zure eskakizunak burutuko ditugu.";
		JTextArea textuArea = new JTextArea(textua);
		textuArea.setLineWrap(true);
		textuArea.setWrapStyleWord(true);
		textuArea.setEditable(false);
		textuArea.setOpaque(false);
		this.add(textuArea);
	}
}