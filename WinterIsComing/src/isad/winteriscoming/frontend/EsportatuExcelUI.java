package isad.winteriscoming.frontend;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import isad.winteriscoming.backend.Excel;
import isad.winteriscoming.backend.Nagusia;
import isad.winteriscoming.salbuespenak.SentitzenNaizException;

public class EsportatuExcelUI {

	public EsportatuExcelUI() {

	}

	public void gorde() {
		JOptionPane.showMessageDialog(null, "Hautatu non eraiki nahi duzun Excel liburua",
				"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.INFORMATION_MESSAGE);
		String path = "";
		JFileChooser gureFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
		gureFileChooser.setAcceptAllFileFilterUsed(false);
		gureFileChooser.setFileFilter(new FileNameExtensionFilter("Excel liburuak", "xlsx"));
		int aukera = JOptionPane.NO_OPTION;
		while (aukera == JOptionPane.NO_OPTION) {
			int gureZenbakia = gureFileChooser.showSaveDialog(null);
			if (gureZenbakia == JFileChooser.CANCEL_OPTION)
				return;
			try {
				path = gureFileChooser.getSelectedFile().getAbsolutePath();
				if (!path.contains(".xlsx")) {
					path = path + ".xlsx";
				}
				File f = new File(path);
				if (f.exists()) {
					String[] aukerak = { "Bai", "Ez" };
					aukera = JOptionPane.showOptionDialog(null, "Liburua existitzen da jada.\nEzabatu nahi duzu?",
							"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE, null, aukerak, aukerak[0]);
				} else
					aukera = JOptionPane.YES_OPTION;
			} catch (Exception salbuespena) {
				throw new SentitzenNaizException("Fitxategiak ez du balio.");
			}
		}
		Excel nireExcel = new Excel();
		if (nireExcel.idatzi(path))
			JOptionPane.showMessageDialog(null, "Excel liburua " + path + " karpetan gorde da",
					"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.INFORMATION_MESSAGE);

	}

}
