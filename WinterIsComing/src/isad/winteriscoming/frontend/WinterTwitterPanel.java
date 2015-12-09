package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import isad.winteriscoming.backend.Nagusia;

public class WinterTwitterPanel extends JPanel {
	private JPanel barra;
	private JPanel txioak;
	private static final long serialVersionUID = 6077749617280710593L;

	public WinterTwitterPanel() {
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.panelNagusiaEraiki();
		this.barraEraiki();
		this.add(barra, BorderLayout.LINE_START);
		this.add(txioak, BorderLayout.CENTER);
	}

	private void panelNagusiaEraiki() {
		txioak = new JPanel();
	}

	private void barraEraiki() {
		barra = new JPanel();
		barra.setLayout(new BorderLayout());
		barra.setOpaque(true);
		barra.add(new JLabel(Nagusia.IZENBURUA), BorderLayout.PAGE_START);
		barra.setBackground(new Color(94, 169, 221));
	}
}