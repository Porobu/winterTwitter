package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import isad.winteriscoming.backend.Nagusia;

public class WinterTwitter extends JFrame {
	private static final long serialVersionUID = -2685559474031286026L;
	private JPanel informazioa;
	private JPanel txioak;

	public WinterTwitter() {
		this.setLayout(new BorderLayout());
		this.setTitle("WinterTwitter " + Nagusia.BERTSIOA);
		this.setJMenuBar(new Menua());
		this.informazioaEraiki();
		this.txioakEraiki();
		this.setMinimumSize(new Dimension(300, 200));
		this.add(informazioa, BorderLayout.NORTH);
		this.add(txioak, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private void informazioaEraiki() {
		informazioa = new JPanel();
	}

	private void txioakEraiki() {
		txioak = new JPanel();
	}
}
