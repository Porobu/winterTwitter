package isad.winteriscoming.backend;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import isad.winteriscoming.salbuespenak.WinterTwitterSalbuespena;

public final class DBKS {
	private static DBKS gureDBKS;
	private Connection konexioa;

	private DBKS() {
	}

	public static DBKS getDBKS() {
		return gureDBKS != null ? gureDBKS : (gureDBKS = new DBKS());
	}

	public String getDefaultPath() {
		File f = new File(System.getProperty("user.home") + "/WinterTwitter.db");
		if (f.exists())
			return f.getAbsolutePath();
		else
			return null;
	}

	public void konektatu(String path) {
		try {
			Class.forName("org.sqlite.JDBC").newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException salbuespena) {
			throw new WinterTwitterSalbuespena("Driverra ez da aurkitu");
		}
		try {
			this.konexioa = DriverManager.getConnection("jdbc:sqlite:" + path);
		} catch (SQLException gureSalbuespena) {
			throw new WinterTwitterSalbuespena("Ezin da datu basera konektatu");
		}
		this.datubaseaKonprobatu();
	}

	public ResultSet queryExekutatu(String agindua) {
		ResultSet emaitza = null;
		try {
			Statement st = this.konexioa.createStatement();
			emaitza = st.executeQuery(agindua);
		} catch (Exception salbuespena) {
			throw new WinterTwitterSalbuespena("Ezin da " + agindua + " exekutatu.");
		}
		return emaitza;
	}

	public void aginduaExekutatu(String agindua) {
		try {
			Statement st = this.konexioa.createStatement();
			st.execute(agindua);
		} catch (Exception salbuespena) {
			throw new WinterTwitterSalbuespena("Ezin da " + agindua + " exekutatu.");
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

	private void datubaseaKonprobatu() {
		Statement st;
		try {
			st = konexioa.createStatement();
			st.executeQuery("SELECT id, edukia, data, mota FROM TXIOA");
			st.executeQuery("SELECT id, izena, mota, nick, idErabiltzailea FROM BESTEERABILTZAILEAK");
			st.executeQuery("SELECT erabId, zerrenId, erabNick, zerrendaIzena, erabIzena FROM DITU");
			st.executeQuery("SELECT id, nick, izena, token, tokenSecret FROM ERABILTZAILEA");
			st.executeQuery("SELECT txioId, erabId, data, edukia FROM AIPAMENAK");
			st.executeQuery("SELECT id, izena, deskribapena FROM ZERRENDA");
			st.executeQuery("SELECT id, data, edukia, bidaltzaileIzena, hartzaileIzena FROM MEZUA");
			st.executeQuery("SELECT mota, kurtsoreBalioa FROM PAGING");
			st.close();
		} catch (SQLException e) {
			throw new WinterTwitterSalbuespena("Datu basea ez da baliozkoa!");
		}
	}
}
