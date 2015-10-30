package isad.winteriscoming.frontend;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import isad.winteriscoming.backend.Nagusia;
import isad.winteriscoming.salbuespenak.SentitzenNaizException;

public class SegurtasunKopia {
	public SegurtasunKopia()
	{
		
	}

	public void kopiaEgin() {
		JOptionPane.showMessageDialog(null, "Hautatu non egin nahi duzun datu basearen kopia",
				"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.INFORMATION_MESSAGE);
		String path = Nagusia.getPath();
		JFileChooser gureFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
		gureFileChooser.setAcceptAllFileFilterUsed(false);
		gureFileChooser.setFileFilter(new FileNameExtensionFilter("Access Datu Baseak", "accdb"));
		while (path == Nagusia.getPath()) {
			int gureZenbakia = gureFileChooser.showSaveDialog(null);
			if (gureZenbakia == JFileChooser.CANCEL_OPTION)
				return;
			try {
				path = gureFileChooser.getSelectedFile().getAbsolutePath();
				if (!path.contains(".accdb")) {
					path = path + ".accdb";
				}
			} catch (Exception salbuespena) {
				throw new SentitzenNaizException("Fitxategiak ez du balio!!!!!");
			}
			if (path == Nagusia.getPath())
				JOptionPane.showMessageDialog(gureFileChooser,
						"Hautatu duzun fitxategia kargatu duzunaren berdina da.\nMesedez, aukeratu beste bat",
						"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.WARNING_MESSAGE);
		}
		File oraingoa = new File(Nagusia.getPath());
		File berria = new File(path);
		try {
			Files.copy(oraingoa.toPath(), berria.toPath(), COPY_ATTRIBUTES);
		} catch (IOException e) {
			throw new SentitzenNaizException("Ezin da fitxategia kopiatu");
		}
	}

}
