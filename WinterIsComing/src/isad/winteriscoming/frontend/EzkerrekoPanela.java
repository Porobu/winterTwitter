package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EzkerrekoPanela extends JPanel implements KeyListener, MouseListener {
	private static final long serialVersionUID = -7717207637408579203L;
	private JTextField bilatu;
	private JComboBox<String> lista;
	private String aukeratutakoa;

	public EzkerrekoPanela() {
		this.setLayout(new BorderLayout());
		this.bilaketaBarra();
		this.setBackground(new Color(94, 169, 221));
		this.setOpaque(true);
	}

	private void bilaketaBarra() {
		bilatu = new JTextField("Bilatu...");
		aukeratutakoa = "txioa";
		this.add(bilatu, BorderLayout.PAGE_START);
		String[] aukerak = new String[] { "Txioa", "Bertxioa", "Gustokoa", "Aipamena", "Jarraitua", "Jarraitzailea",
				"Mezua", "Zerrenda" };
		lista = new JComboBox<>(aukerak);
		lista.setEditable(false);
		lista.addActionListener(gureAE -> this.aukeratua());
		bilatu.addKeyListener(this);
		bilatu.addMouseListener(this);
		this.add(lista, BorderLayout.CENTER);
	}

	private void aukeratua() {
		aukeratutakoa = (String) lista.getSelectedItem();
		if (!bilatu.getText().equals("Bilatu...")) {
			WinterTwitter.getOraingoWT().getPanela()
					.panelaAldatu(new TaulaPanela(aukeratutakoa.toLowerCase(), bilatu.getText(), true));
		}

	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		WinterTwitter.getOraingoWT().getPanela()
				.panelaAldatu(new TaulaPanela(aukeratutakoa.toLowerCase(), bilatu.getText(), true));
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (bilatu.getText().equals("Bilatu..."))
			bilatu.setText("");
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
