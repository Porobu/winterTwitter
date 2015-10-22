package isad.winteriscoming.backend;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import isad.winteriscoming.salbuespenak.SentitzenNaizException;
import net.ucanaccess.jdbc.UcanaccessDriver;

public class DBKS {
	private static DBKS gureDBKS;
	private Connection konexioa;

	private DBKS() {
	}

	public static DBKS getDBKS() {
		return gureDBKS != null ? gureDBKS : (gureDBKS = new DBKS());
	}

	public String getPath() {
		String path;
		JFileChooser gureFileChooser = new JFileChooser(new File(System.getProperty("user.home")));
		gureFileChooser.setAcceptAllFileFilterUsed(false);
		gureFileChooser.setFileFilter(new FileNameExtensionFilter("Access DatuBaseak", "accdb"));
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

	}

	public void datuBaseaEraiki() {
		// DriverManager.getConnection("jdbc:ucanaccess://"+
		// database.getAbsolutePath()+";newdatabaseversion=V2010");
	}
	/*
	 * public void gehituBezeroa(String izena, int id, String helbidea, int
	 * telefonoa) { try { Statement st = this.konexioa.createStatement();
	 * st.execute("INSERT INTO Bezeroa (Id,Izena,Helbidea,Telefonoa) VALUES( " +
	 * id + ",'" + izena + "','" + helbidea + "'," + telefonoa + ")");
	 * st.close(); } catch (Exception salbuespena) { throw new
	 * SentitzenNaizException(izena + " bezeroa " + id +
	 * " id-arekin datu basearen barruan dago jada."); } }
	 * 
	 * public void gehituPelikula(String izenburua, int id, int urtea, String
	 * deskribapena) { try { Statement st = this.konexioa.createStatement();
	 * st.execute(
	 * "INSERT INTO Pelikula (Id,Izenburua,KaleratzeUrtea,Deskribapena) VALUES( "
	 * + id + ",'" + izenburua + "'," + urtea + ",'" + deskribapena + "')");
	 * st.close(); } catch (Exception salbuespena) { throw new
	 * SentitzenNaizException( izenburua + " pelikula " + id +
	 * " id-arekin datu basearen barruan dago jada."); } }
	 */
}
