package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import isad.winteriscoming.backend.Nagusia;

public class WTBuruz extends JPanel {

	private static final long serialVersionUID = 5369380645289619092L;
	private JButton itzuli;
	private JTextArea textuaArea;
	private JTextField bertsioa;

	public WTBuruz() {
		String textua = "WinterTwitter Lizentzia: Copyright (C) 2015  Sergio Pascual, Sergio Santana, Unai Arrizabalaga, Jonathan Nieto. This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, version 3 of the License. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/";
		this.setLayout(new BorderLayout());
		this.itzuli = new JButton("Itzuli");
		this.itzuli.addActionListener(gureAE -> WinterTwitter.getOraingoWT().getPanela().nagusiaIpini());
		this.add(itzuli, BorderLayout.PAGE_END);
		this.textuaArea = new JTextArea(textua);
		textuaArea.setLineWrap(true);
		textuaArea.setWrapStyleWord(true);
		this.textuaArea.setEditable(false);
		textuaArea.setBackground(new Color(217, 251, 243));
		JScrollPane sp = new JScrollPane(textuaArea);
		this.add(sp, BorderLayout.CENTER);
		bertsioa = new JTextField(Nagusia.IZENBURUA);
		bertsioa.setEditable(false);
		bertsioa.setBackground(new Color(217, 251, 243));
		this.add(bertsioa, BorderLayout.PAGE_START);
		this.setOpaque(true);
	}
}