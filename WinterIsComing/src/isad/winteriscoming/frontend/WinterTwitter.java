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
		Nagusia.getFrame().dispose();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("WinterTwitter " + Nagusia.BERTSIOA + " " + Nagusia.getPath());
		this.setJMenuBar(new Menua());
		this.informazioaEraiki();
		this.txioakEraiki();
		this.setMinimumSize(new Dimension(500, 300));
		this.add(informazioa, BorderLayout.NORTH);
		this.add(txioak, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void informazioaEraiki() {
		informazioa = new JPanel();
	}

	private void txioakEraiki() {
		txioak = new JPanel();
	}
}
