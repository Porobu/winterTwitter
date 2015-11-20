package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import isad.winteriscoming.backend.Nagusia;

public class WinterTwitter extends JFrame {
	private static final long serialVersionUID = -2685559474031286026L;
	private JPanel barra;
	private JPanel txioak;
	private static JMenuBar menua;

	public WinterTwitter() {
		menua = new Menua();
		InputStream stream = WinterTwitter.class.getResourceAsStream("/isad/winteriscoming/logoa.png");
		BufferedImage logoa;
		try {
			logoa = ImageIO.read(stream);
			this.setIconImage(logoa);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("WinterTwitter " + Nagusia.BERTSIOA + " " + Nagusia.getPath());
		this.setJMenuBar(menua);
		this.barraEraiki();
		this.txioakEraiki();
		this.setMinimumSize(new Dimension(500, 300));
		this.add(barra, BorderLayout.WEST);
		this.add(txioak, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	private void barraEraiki() {
		barra = new JPanel();
		barra.setLayout(new GridLayout(10, 1));
		
	}

	private void txioakEraiki() {
		txioak = new JPanel();
	}
}
