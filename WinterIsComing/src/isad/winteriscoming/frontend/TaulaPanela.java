package isad.winteriscoming.frontend;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TaulaPanela extends JPanel {

	private static final long serialVersionUID = 2602675352838564349L;

	public TaulaPanela(String mota) {
		this.setLayout(new BorderLayout(0, 1));
		this.setOpaque(true);
		TaulaModeloa gureModeloa = new TaulaModeloa(mota);
		JTable gureTaula = new JTable(gureModeloa);
		JScrollPane gureJScrollPane = new JScrollPane(gureTaula);
		this.add(gureJScrollPane);
	}
}