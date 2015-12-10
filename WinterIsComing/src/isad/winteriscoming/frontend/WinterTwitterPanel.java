package isad.winteriscoming.frontend;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class WinterTwitterPanel extends JPanel {
	private static final long serialVersionUID = -3288838876090363252L;
	private JPanel barra;
	private PanelNagusia nagusia;
	private JPanel dagoena;

	public WinterTwitterPanel() {
		nagusia = new PanelNagusia();
		this.setOpaque(true);
		this.setLayout(new BorderLayout());
		barra = new EzkerrekoPanela();
		this.add(barra, BorderLayout.LINE_START);
		this.add(nagusia, BorderLayout.CENTER);
		dagoena = nagusia;
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