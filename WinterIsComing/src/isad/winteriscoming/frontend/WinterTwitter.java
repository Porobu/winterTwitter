package isad.winteriscoming.frontend;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import isad.winteriscoming.backend.Nagusia;

public class WinterTwitter extends JFrame {
	private static final long serialVersionUID = -2685559474031286026L;
	private static WinterTwitter nireWT;
	public static WinterTwitter getOraingoWT() {
		return nireWT == null ? (nireWT = new WinterTwitter()) : nireWT;
	}
	private WinterTwitterPanel nagusia;

	private JMenuBar menua;

	public WinterTwitter() {
		nireWT = this;
	}

	public void dekoratu() {
		this.dispose();
		this.setUndecorated(false);
		this.setTitle(Nagusia.IZENBURUA + " " + Nagusia.getPath());
		this.menua = new Menua();
		this.nagusia = new WinterTwitterPanel();
		this.setJMenuBar(menua);
		this.setContentPane(nagusia);
		this.setMinimumSize(new Dimension(600, 300));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		nireWT = this;
	}

	public void dekoratuGabeHasieratu() {
		this.ikonoaJarri();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle(Nagusia.IZENBURUA);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setVisible(true);
		nireWT = this;
	}

	public WinterTwitterPanel getPanela() {
		return nagusia;
	}

	private void ikonoaJarri() {
		InputStream stream = WinterTwitter.class.getResourceAsStream("/isad/winteriscoming/logoa.png");
		BufferedImage logoa;
		try {
			logoa = ImageIO.read(stream);
			this.setIconImage(logoa);
		} catch (IOException e) {
		}
	}
}
