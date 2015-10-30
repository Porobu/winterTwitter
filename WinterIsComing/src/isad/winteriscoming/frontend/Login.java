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
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import isad.winteriscoming.backend.Konexioa;
import isad.winteriscoming.externals.SpringUtilities;

public class Login extends JFrame implements KeyListener {

	private static final long serialVersionUID = -5514346274613273871L;
	private JLabel pin;
	private JTextField pinField;
	private JPanel gurePanela;
	private JButton ok;
	private JCheckBox gorde;
	private static Login gureLogin;
	private boolean tokenakGorde;
	private String pinString;

	public Login() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.gurePanela = new JPanel(new SpringLayout());
		this.setTitle("Login");
		pinString = "PIN: ";
		this.pin = new JLabel(pinString, SwingConstants.TRAILING);
		this.gurePanela.add(pin);
		this.pinField = new JTextField(7);
		this.pin.setLabelFor(pinField);
		this.gurePanela.add(pinField);
		this.gorde = new JCheckBox("PINa gorde");
		SpringUtilities.makeCompactGrid(this.gurePanela, 1, 2, 6, 6, 6, 6);
		this.add(this.gurePanela, BorderLayout.NORTH);
		this.add(this.gorde, BorderLayout.CENTER);
		this.ok = new JButton("Sartu");
		this.ok.addActionListener(gureAE -> this.datuakGorde());
		this.ok.addKeyListener(this);
		this.pinField.addKeyListener(this);
		this.add(this.ok, BorderLayout.SOUTH);
		this.setMinimumSize(new Dimension(210, 110));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		Login.gureLogin = this;
	}

	public static Login getLogin() {
		return Login.gureLogin;
	}

	public String getPIN() {
		return this.pinString;
	}

	private void datuakGorde() {
		this.pinString = new String(this.pinField.getText());
		this.setVisible(false);
		Konexioa.getKonexioa().tokenaLortu();
		//Operazioak.timelineErakutsi();
		//Operazioak.gustokoakErakutsi();
		//Operazioak.tweetakErakutsi();
		//Operazioak.retweetakErakutsi();
		//Operazioak.mentzioakErakutsi();
		//Operazioak.jarraitzaileakErakutsi();
		//Operazioak.jarraituakErakutsi();
		//Operazioak.zerrendakErakutsi();
		//Operazioak.mezuakErakutsi();
		//Operazioak.bilatuTxioetan("egiteke");
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