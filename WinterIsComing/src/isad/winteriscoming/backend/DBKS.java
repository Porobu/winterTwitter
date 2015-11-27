package isad.winteriscoming.backend;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

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
			// ARREGLAR
			// this.konektatu();
			// POR EL MOMENTO
			// FIXME
			System.exit(1);
		}
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
