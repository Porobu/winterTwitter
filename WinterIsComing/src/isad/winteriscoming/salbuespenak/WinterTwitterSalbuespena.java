package isad.winteriscoming.salbuespenak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.apache.commons.lang.exception.ExceptionUtils;

import isad.winteriscoming.backend.Nagusia;
import isad.winteriscoming.frontend.WinterTwitter;

public class WinterTwitterSalbuespena extends RuntimeException {
	private static final long serialVersionUID = -9212558550853305997L;

	public WinterTwitterSalbuespena(String mezua) {
		String izenburua = Nagusia.IZENBURUA + " ERROREA!!!!!";
		String fitxategia = System.getProperty("user.home") + "/ERROREA.txt";
		if (System.getProperty("os.name").toLowerCase().contains("win"))
			fitxategia = System.getProperty("user.home") + "\\ERROREA.txt";
		try {
			PrintWriter gurePW = new PrintWriter(new File(fitxategia));
			gurePW.println(izenburua);
			gurePW.println(mezua);
			gurePW.println("Stack Trace:");
			gurePW.write(ExceptionUtils.getStackTrace(this));
			gurePW.flush();
			gurePW.close();
		} catch (FileNotFoundException sentitzenNaiz) {
		}
		String stackLaburra = ExceptionUtils.getRootCauseStackTrace(this)[1];
		stackLaburra = stackLaburra.substring(4, stackLaburra.length());
		JOptionPane.showMessageDialog(WinterTwitter.getOraingoWT(), mezua + "\nStack Trace Laburra:\n" + stackLaburra
				+ "\nStack trace " + fitxategia + " fitxategian gorde da.", izenburua, JOptionPane.ERROR_MESSAGE);
	}
}