package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import isad.winteriscoming.backend.Nagusia;

public class EzkerrekoPanela extends JPanel implements KeyListener{
	private static final long serialVersionUID = -7717207637408579203L;
	private JTextField bilatu;	
	public EzkerrekoPanela() {
		this.setLayout(new BorderLayout());
		this.add(new JLabel(Nagusia.IZENBURUA), BorderLayout.PAGE_START);
		this.bilaketaBarra();
		this.setBackground(new Color(94, 169, 221));
		this.setOpaque(true);
		
		
	}
	
	private void bilaketaBarra()
	{
		bilatu = new JTextField("Bilatu...");
		bilatu.addKeyListener(this);
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
