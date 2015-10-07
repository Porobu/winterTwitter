package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import isad.winteriscoming.externals.SpringUtilities;

public class Login extends JFrame implements KeyListener {

	private static final long serialVersionUID = -5514346274613273871L;
	private JLabel erabiltzailea, pasahitza;
	private JTextField erabiltzaileaField;
	private JPasswordField pasahitzaField;
	private JPanel gurePanela;
	private JButton ok;
	private JCheckBox gorde;
	private static Login gureLogin;
	private boolean datuakgorde;

	private String erabiltzaileaString, pasahitzaString;

	public Login() {
		this.setLayout(new BorderLayout());
		this.gurePanela = new JPanel(new SpringLayout());
		this.setTitle("Login");
		String[] izenak = { "Erabiltzailea:", "Pasahitza:" };
		this.erabiltzailea = new JLabel(izenak[0], SwingConstants.TRAILING);
		this.gurePanela.add(erabiltzailea);
		this.erabiltzaileaField = new JTextField(15);
		this.erabiltzailea.setLabelFor(erabiltzaileaField);
		this.gurePanela.add(erabiltzaileaField);
		this.pasahitza = new JLabel(izenak[1], SwingConstants.TRAILING);
		this.gurePanela.add(pasahitza);
		this.pasahitzaField = new JPasswordField(15);
		this.pasahitza.setLabelFor(pasahitzaField);
		this.gurePanela.add(pasahitzaField);
		this.gorde = new JCheckBox("Erabiltzailea gorde");
		SpringUtilities.makeCompactGrid(this.gurePanela, 2, 2, 6, 6, 6, 6);
		this.add(this.gurePanela, BorderLayout.NORTH);
		this.add(this.gorde, BorderLayout.CENTER);
		this.ok = new JButton("Sartu");
		this.ok.addActionListener(gureAE -> this.datuakGorde());
		this.ok.addKeyListener(this);
		this.pasahitzaField.addKeyListener(this);
		this.erabiltzaileaField.addKeyListener(this);
		this.add(this.ok, BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(220, 140));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		Login.gureLogin = this;
	}

	public static Login getLogin() {
		return Login.gureLogin;
	}

	public String getErabiltzailea() {
		return this.erabiltzaileaString;
	}

	public String getPasahitza() {
		return this.pasahitzaString;
	}

	private void datuakGorde() {
		this.erabiltzaileaString = this.erabiltzaileaField.getText();
		this.pasahitzaString = new String(this.pasahitzaField.getPassword());
		this.dispose();
	}

	@Override
	public void keyPressed(KeyEvent teklaSakatuta) {
		if (teklaSakatuta.getKeyCode() == KeyEvent.VK_ENTER)
			this.datuakGorde();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
