package isad.winteriscoming.salbuespenak;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.apache.commons.lang3.exception.ExceptionUtils;

import isad.winteriscoming.backend.DBKS;

public class SentitzenNaizException extends RuntimeException {
	private static final long serialVersionUID = -1014723140002334926L;

	public SentitzenNaizException(String gureMezua) {
		DBKS.getDBKS().konexioaItxi();
		try {
			PrintWriter gurePW = new PrintWriter(new File(System.getProperty("user.home") + "/SentitzenNaiz.txt"));
			gurePW.println("SENTITZEN NAIZ BAINA ERROREA!!!!!");
			gurePW.println(gureMezua);
			gurePW.println("Stack Trace:");
			gurePW.write(ExceptionUtils.getStackTrace(this));
			gurePW.flush();
			gurePW.close();
		} catch (FileNotFoundException sentitzenNaiz) {
		}
		String gureString = System.getProperty("os.name");
		if (gureString.contains("win") || gureString.contains("Win"))
			JOptionPane.showMessageDialog(null,
					gureMezua + "\n" + "Stack Trace Laburra:\n" + ExceptionUtils.getRootCauseStackTrace(this)[1]
							+ "\nStack trace " + System.getProperty("user.home") + "\\SentitzenNaiz.txt" + " gorde da.",
					"SENTITZEN NAIZ BAINA ERROREA!!!!!", JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(null,
					gureMezua + "\n" + "Stack Trace Laburra:\n" + ExceptionUtils.getRootCauseStackTrace(this)[1]
							+ "\nStack trace " + System.getProperty("user.home") + "/SentitzenNaiz.txt" + " gorde da.",
					"SENTITZEN NAIZ BAINA ERROREA!!!!!", JOptionPane.ERROR_MESSAGE);	}
}