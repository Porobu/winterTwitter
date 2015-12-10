package isad.winteriscoming.frontend;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;

import isad.winteriscoming.externals.ZebraJTable;

public class TaulaPanela extends JPanel {

	private static final long serialVersionUID = 2602675352838564349L;
	private JButton itzuli;

	public TaulaPanela(String mota) {
		this.setLayout(new BorderLayout());
		TaulaModeloa gureModeloa = new TaulaModeloa(mota);
		JTable gureTaula = new ZebraJTable(gureModeloa);
		gureTaula.setRowSorter(new TableRowSorter<>(gureModeloa));
		gureTaula.setFont(new Font("Arial", Font.PLAIN, 14));
		JScrollPane gureJScrollPane = new JScrollPane(gureTaula);
		this.add(gureJScrollPane, BorderLayout.CENTER);
		this.itzuli = new JButton("Itzuli");
		this.itzuli.addActionListener(gureAE -> WinterTwitter.getOraingoWT().getPanela().nagusiaIpini());
		this.add(itzuli, BorderLayout.PAGE_END);
		this.setOpaque(true);
	}
}