package isad.winteriscoming.salbuespenak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.apache.commons.lang.exception.ExceptionUtils;

import isad.winteriscoming.frontend.WinterTwitter;

public class WinterTwitterSalbuespena extends RuntimeException {
	private static final long serialVersionUID = -1014723140002334926L;

	public WinterTwitterSalbuespena(String gureMezua) {
		try {
			PrintWriter gurePW = new PrintWriter(new File(System.getProperty("user.home") + "/Errorea.txt"));
			gurePW.println(gureMezua);
			gurePW.println("Stack Trace:");
			gurePW.write(ExceptionUtils.getStackTrace(this));
			gurePW.flush();
			gurePW.close();
		} catch (FileNotFoundException sentitzenNaiz) {
		}
		String gureString = System.getProperty("os.name");
		if (gureString.contains("win") || gureString.contains("Win"))
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(),
					gureMezua + "\n" + "Stack Trace Laburra:\n" + ExceptionUtils.getRootCauseStackTrace(this)[1]
							+ "\nStack trace " + System.getProperty("user.home") + "\\Errorea.txt" + " gorde da.",
					"ERROREA", JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(),
					gureMezua + "\n" + "Stack Trace Laburra:\n" + ExceptionUtils.getRootCauseStackTrace(this)[1]
							+ "\nStack trace " + System.getProperty("user.home") + "/Errorea.txt" + " gorde da.",
					"ERROREA", JOptionPane.ERROR_MESSAGE);
	}
}