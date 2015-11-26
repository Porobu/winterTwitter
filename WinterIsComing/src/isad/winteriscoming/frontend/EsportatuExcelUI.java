package isad.winteriscoming.frontend;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import isad.winteriscoming.backend.EsportatuExcel;
import isad.winteriscoming.backend.Nagusia;
import isad.winteriscoming.salbuespenak.SentitzenNaizException;

public class EsportatuExcelUI {

	public EsportatuExcelUI() {

	}

	public void gorde() {
		JOptionPane.showMessageDialog(null, "Hautatu non eraiki nahi duzun Excel fitxategia",
				"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.INFORMATION_MESSAGE);
		String path = "";
		JFileChooser gureFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
		gureFileChooser.setAcceptAllFileFilterUsed(false);
		gureFileChooser.setFileFilter(new FileNameExtensionFilter("Excel liburuak", "xlsx"));

		int gureZenbakia = gureFileChooser.showSaveDialog(null);
		if (gureZenbakia == JFileChooser.CANCEL_OPTION)
			return;
		try {
			path = gureFileChooser.getSelectedFile().getAbsolutePath();
			if (!path.contains(".xlsx")) {
				path = path + ".xlsx";
			}
		} catch (Exception salbuespena) {
			throw new SentitzenNaizException("Fitxategiak ez du balio.");
		}
		EsportatuExcel nireExcel = new EsportatuExcel();
		nireExcel.idatzi(path);
	}

}
