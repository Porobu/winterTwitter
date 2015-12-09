package isad.winteriscoming.frontend;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import isad.winteriscoming.backend.Nagusia;

public class WTBuruz extends JPanel {

	private static final long serialVersionUID = 5369380645289619092L;
	private JButton itzuli;
	private JTextArea textua;
	private JTextField bertsioa;

	public WTBuruz() {
		this.setLayout(new BorderLayout());
		this.itzuli = new JButton("Itzuli");
		this.itzuli.addActionListener(gureAE -> WinterTwitter.getOraingoWT().getPanela().nagusiaIpini());
		this.add(itzuli, BorderLayout.PAGE_END);
		this.textua = new JTextArea(this.textua());
		textua.setLineWrap(true);
		textua.setWrapStyleWord(true);
		this.textua.setEditable(false);
		JScrollPane sp = new JScrollPane(textua);
		this.add(sp, BorderLayout.CENTER);
		bertsioa = new JTextField(Nagusia.IZENBURUA);
		bertsioa.setEditable(false);
		this.add(bertsioa, BorderLayout.PAGE_START);
		this.setOpaque(true);
	}

	private String textua() {
		return "WinterTwitter Lizentzia: Copyright (C) 2015  Sergio Pascual, Sergio Santana, Unai Arrizabalaga. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, version 3 of the License. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/";
	}
}