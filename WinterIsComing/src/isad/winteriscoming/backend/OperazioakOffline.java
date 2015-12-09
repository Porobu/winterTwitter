package isad.winteriscoming.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperazioakOffline {

	public List<String> bilatuTxioetan(String st) {
		List<String> emaitza = new ArrayList<>();
		ResultSet rs = DBKS.getDBKS()
				.queryExekutatu("SELECT edukia, data FROM TXIOA WHERE edukia LIKE " + st + " AND MOTA = TXIOA");
		try {
			rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emaitza;
	}

	public ArrayList<String[]> txioakBistaratu() {
		ResultSet rs = DBKS.getDBKS().queryExekutatu("SELECT edukia, data FROM TXIOA WHERE mota = 'txioa'");
		ArrayList<String[]> lista = new ArrayList<>();
		try {
			while (rs.next()) {
				lista.add(new String[] { rs.getString(1), rs.getString(2) });
			}
		} catch (SQLException e) {
		}
		return lista;
	}

	public boolean konprobatuTokenakDauden() {
		ResultSet emaitza = DBKS.getDBKS().queryExekutatu("SELECT token, tokenSecret FROM ERABILTZAILEA");
		try {
			return emaitza.next();
		} catch (SQLException e) {
		}
		return false;
	}

	public ArrayList<String[]> datuakJaso(String mota) {
		int kop = 0;
		String agindua = "";
		if (mota.toLowerCase().equals("txioa") || mota.toLowerCase().equals("bertxioa")
				|| mota.toLowerCase().equals("gustokoa")) {
			agindua = "SELECT edukia, data FROM TXIOA WHERE mota = " + mota;
			kop = 2;
		} else if (mota.toLowerCase().equals("jarraitua") || mota.toLowerCase().equals("jarraitzailea")) {
			agindua = "SELECT izena, nick FROM BESTEERABILTZAILEAK WHERE mota = " + mota;
			kop = 2;
		} else if (mota.toLowerCase().equals("zerrendak")) {
			kop = 5;
			agindua = "SELECT ZERRENDA.izena, ZERRENDA.deskribapena, DITU.erabIzena, DITU.zerrendaIzena, DITU.erabNick  FROM ZERRENDA, DITU WHERE ZERRENDA.id = DITU.zerrenId";
		} else {
			agindua = "SELECT data, edukia, bidaltzaileIzena, hartzaileIzena FROM MEZUA";
			kop = 4;
		}
		ArrayList<String[]> emaitza = new ArrayList<>();
		ResultSet rs = DBKS.getDBKS().queryExekutatu(agindua);
		try {
			while (rs.next()) {
				String[] oraingoa = new String[kop];
				for (int i = 1; i <= kop; i++) {
					oraingoa[i] = rs.getString(i);
				}
				emaitza.add(oraingoa);
			}
		} catch (SQLException e) {
		}
		return emaitza;
	}
}
