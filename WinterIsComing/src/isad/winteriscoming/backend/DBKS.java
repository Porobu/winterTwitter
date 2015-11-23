package isad.winteriscoming.backend;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import isad.winteriscoming.salbuespenak.SentitzenNaizException;
import net.ucanaccess.jdbc.UcanaccessDriver;

public final class DBKS {
	private static DBKS gureDBKS;
	private Connection konexioa;

	private DBKS() {
	}

	public static DBKS getDBKS() {
		return gureDBKS != null ? gureDBKS : (gureDBKS = new DBKS());
	}

	public String getDefaultPath() {
		File f = new File(System.getProperty("user.home") + "/WinterTwitter.accdb");
		if (f.exists())
			return f.getAbsolutePath();
		else
			return null;
	}

	public String getPath() {
		String path;
		JFileChooser gureFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
		gureFileChooser.setAcceptAllFileFilterUsed(false);
		gureFileChooser.setFileFilter(new FileNameExtensionFilter("Access Datu Baseak", "accdb"));
		int gureZenbakia = gureFileChooser.showOpenDialog(null);
		if (gureZenbakia == JFileChooser.CANCEL_OPTION)
			System.exit(0);
		try {
			path = gureFileChooser.getSelectedFile().getAbsolutePath();
		} catch (Exception salbuespena) {
			throw new SentitzenNaizException("Fitxategiak ez du balio!!!!!");
		}
		return path;
	}

	public void konektatu(String path) {
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException salbuespena) {
			throw new SentitzenNaizException("Driverra ez da aurkitu");
		}
		try {
			String konexioaString = UcanaccessDriver.URL_PREFIX + path;
			this.konexioa = DriverManager.getConnection(konexioaString + ";Openexclusive=true", "", "");
		} catch (SQLException gureSalbuespena) {
			throw new SentitzenNaizException("Ezin da datu basera konektatu");
		}
		boolean ondo = this.datubaseaKonprobatu();
		if (!ondo) {
			this.konektatu(this.getPath());
		}
	}

	public String datuBaseaGordetzekoPath() {
		String path;
		JFileChooser gureFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
		gureFileChooser.setAcceptAllFileFilterUsed(false);
		gureFileChooser.setFileFilter(new FileNameExtensionFilter("Access Datu Baseak", "accdb"));
		gureFileChooser.setSelectedFile(new File("WinterTwitter"));
		int gureZenbakia = gureFileChooser.showSaveDialog(null);
		if (gureZenbakia == JFileChooser.CANCEL_OPTION)
			System.exit(0);
		try {
			path = gureFileChooser.getSelectedFile().getAbsolutePath();
			if (!path.contains(".accdb")) {
				path = path + ".accdb";
			}
		} catch (Exception salbuespena) {
			throw new SentitzenNaizException("Fitxategiak ez du balio!!!!!");
		}
		return path;
	}

	public void datuBaseaEraiki(String path) {
		int aukera = JOptionPane.YES_OPTION;
		File fitxategia = new File(path);
		if (fitxategia.exists())
			aukera = JOptionPane.showConfirmDialog(null,
					"Datu basea existitzen da izen horrekin, jarraitzen baduzu ezabatu egingo da.",
					"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.YES_NO_OPTION);
		if (aukera == JOptionPane.NO_OPTION || aukera == JOptionPane.CLOSED_OPTION)
			System.exit(1);
		fitxategia.delete();
		try {
			exportResource("/isad/winteriscoming/DatuBasea.accdb", path);
		} catch (Exception e) {
			new SentitzenNaizException("Ezin da fitxategia esportatu");
		}
		JOptionPane.showMessageDialog(null, "Datu basea " + path + " karpetan gorde da.",
				"WinterTwitter " + Nagusia.BERTSIOA, JOptionPane.INFORMATION_MESSAGE);
	}

	public ResultSet queryExekutatu(String agindua) {
		ResultSet emaitza = null;
		try {
			Statement st = this.konexioa.createStatement();
			emaitza = st.executeQuery(agindua);
			st.close();
		} catch (Exception salbuespena) {
			throw new SentitzenNaizException("Ezin da " + agindua + " exekutatu.");
		}
		return emaitza;
	}

	public void aginduaExekutatu(String agindua) {
		try {
			Statement st = this.konexioa.createStatement();
			st.execute(agindua);
			st.close();
		} catch (Exception salbuespena) {
			throw new SentitzenNaizException("Ezin da " + agindua + " exekutatu.");
		}
	}

	public String exportResource(String resourceName, String path) {
		InputStream stream = null;
		OutputStream resStreamOut = null;
		String jarFolder;
		try {
			stream = DBKS.class.getResourceAsStream(resourceName);
			if (stream == null) {
				throw new SentitzenNaizException("Ez da datu basea aurkitu .jar fitxategian");
			}
			int readBytes;
			byte[] buffer = new byte[4096];
			File f = new File(ClassLoader.getSystemClassLoader().getResource(".").toURI());
			jarFolder = f.toString();
			resStreamOut = new FileOutputStream(path);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (Exception ex) {
			throw new SentitzenNaizException("Ezin da fitxategia kopiatu");
		} finally {
			try {
				stream.close();
				resStreamOut.close();
			} catch (IOException e) {

			}
		}
		return jarFolder + resourceName;
	}

	public void konexioaItxi() {
		if (this.konexioa != null)
			try {
				this.konexioa.commit();
				this.konexioa.close();
				gureDBKS = new DBKS();
			} catch (SQLException e) {

			}
	}

	private boolean datubaseaKonprobatu() {
		Statement st;
		try {
			st = konexioa.createStatement();
			st.executeQuery("SELECT ID, EDUKIA, DATA, MOTA FROM TXIOA");
			st.executeQuery("SELECT ID, IZENA, MOTA, IDERABILTZAILEA, NICK FROM BESTEERABILTZAILEAK");
			st.executeQuery("SELECT ERABID, ZERRENID FROM DITU");
			st.executeQuery("SELECT NICK, IZENA, EMAIL, ID FROM ERABILTZAILEA");
			st.executeQuery("SELECT TXIOID, ERABID, DATA, EDUKIA FROM AIPAMENAK");
			st.executeQuery("SELECT ID, IZENA FROM ZERRENDA");
			st.executeQuery("SELECT ID, DATA, EDUKIA, BIDALTZAILEIZENA, HARTZAILEIZENA FROM MEZUA");
			st.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Datu basea ez da baliozkoa!", "WinterTwitter " + Nagusia.BERTSIOA,
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
