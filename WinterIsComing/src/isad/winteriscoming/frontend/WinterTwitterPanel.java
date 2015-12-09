package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import isad.winteriscoming.backend.Nagusia;

public class WinterTwitterPanel extends JPanel {
	private static final long serialVersionUID = -3288838876090363252L;
	private JPanel barra;
	private PanelNagusia nagusia;
	private JPanel dagoena;

	public WinterTwitterPanel() {
		nagusia = new PanelNagusia();
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		this.barraEraiki();
		this.add(barra, BorderLayout.LINE_START);
		this.add(nagusia, BorderLayout.CENTER);
		dagoena = nagusia;
	}

	private void barraEraiki() {
		barra = new JPanel();
		barra.setLayout(new BorderLayout());
		barra.setOpaque(true);
		barra.add(new JLabel(Nagusia.IZENBURUA), BorderLayout.PAGE_START);
		barra.setBackground(new Color(94, 169, 221));
	}

	public void panelaAldatu(JPanel berria) {
		this.remove(nagusia);
		this.remove(dagoena);
		dagoena = berria;
		this.add(berria, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	
	public void nagusiaIpini() {
		this.remove(dagoena);
		this.add(nagusia, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
}