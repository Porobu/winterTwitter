package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class WinterTwitterPanel extends JPanel {
	private JPanel barra;
	private JPanel txioak;
	private static final long serialVersionUID = 6077749617280710593L;

	public WinterTwitterPanel() {
		this.setLayout(new BorderLayout());
		this.txioakEraiki();
		this.barraEraiki();
		this.add(barra, BorderLayout.WEST);
		this.add(txioak, BorderLayout.CENTER);
	}

	private void txioakEraiki() {
		txioak = new JPanel();
	}

	private void barraEraiki() {
		barra = new JPanel();
		barra.setLayout(new GridLayout(0, 10));
	}
}