package isad.winteriscoming.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OperazioakOffline {

	public boolean konprobatuTokenakDauden() {
		ResultSet emaitza = DBKS.getDBKS().queryExekutatu("SELECT token, tokenSecret FROM ERABILTZAILEA");
		try {
			return emaitza.next();
		} catch (SQLException e) {
		}
		return false;
	}

	public ArrayList<String[]> datuakJaso(String mota, String filtroa, boolean bilaketa) {
		int kop = 0;
		String agindua = "";
		mota = mota.toLowerCase();
		if (!bilaketa) {
			if (mota.equals("txioa") || mota.equals("bertxioa") || mota.equals("gustokoa")) {
				agindua = "SELECT edukia, data FROM TXIOA WHERE mota = '" + mota + "'";
				kop = 2;
			} else if (mota.equals("jarraitua") || mota.equals("jarraitzailea")) {
				agindua = "SELECT izena, nick FROM BESTEERABILTZAILEAK WHERE mota = '" + mota + "'";
				kop = 2;
			} else if (mota.equals("zerrenda")) {
				kop = 5;
				agindua = "SELECT ZERRENDA.izena, ZERRENDA.deskribapena, DITU.erabIzena, DITU.zerrendaIzena, DITU.erabNick  FROM ZERRENDA, DITU WHERE ZERRENDA.id = DITU.zerrenId";
			} else if (mota.equals("aipamena")) {
				agindua = "SELECT edukia, data FROM AIPAMENAK";
				kop = 2;
			} else {
				agindua = "SELECT data, edukia, bidaltzaileIzena, hartzaileIzena FROM MEZUA";
				kop = 4;
			}
		} else {
			if (mota.equals("txioa") || mota.equals("bertxioa") || mota.equals("gustokoa")) {
				agindua = "SELECT edukia, data FROM TXIOA WHERE mota = '" + mota + "' and edukia like '%" + filtroa
						+ "%'";
				kop = 2;
			} else if (mota.equals("jarraitua") || mota.equals("jarraitzailea")) {
				agindua = "SELECT izena, nick FROM BESTEERABILTZAILEAK WHERE mota = '" + mota + "' and nick like '%"
						+ filtroa + "%'";
				kop = 2;
			} else if (mota.equals("zerrenda")) {
				kop = 5;
				agindua = "SELECT ZERRENDA.izena, ZERRENDA.deskribapena, DITU.erabIzena, DITU.zerrendaIzena, DITU.erabNick  FROM ZERRENDA, DITU WHERE ZERRENDA.id = DITU.zerrenId and DITU.zerrendaIzena like '%"
						+ filtroa + "%'";
			} else if (mota.equals("aipamena")) {
				agindua = "SELECT edukia, data FROM AIPAMENAK WHERE edukia like '%" + bilaketa + "%'";
				kop = 2;
			} else {
				agindua = "SELECT data, edukia, bidaltzaileIzena, hartzaileIzena FROM MEZUA WHERE edukia like '%"
						+ filtroa + "%'";
				kop = 4;
			}
		}
		ResultSet rs = DBKS.getDBKS().queryExekutatu(agindua);
		return this.rsKopiatu(rs, kop);
	}

	private ArrayList<String[]> rsKopiatu(ResultSet rs, int kop) {
		ArrayList<String[]> emaitza = new ArrayList<>();
		try {
			while (rs.next()) {
				String[] oraingoa = new String[kop];
				for (int i = 0; i < kop; i++) {
					oraingoa[i] = rs.getString(i + 1);
				}
				emaitza.add(oraingoa);
			}
		} catch (SQLException e) {
		}
		return emaitza;
	}

}
