package isad.winteriscoming.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperazioakOffline {

	public List<String> bilatuTxioetan(String st) {
		List<String> emaitza = new ArrayList<>();
		ResultSet rs = DBKS.getDBKS()
				.queryExekutatu("SELECT EDUKIA, DATA FROM TXIOA WHERE EDUKIA LIKE " + st + " AND MOTA = TXIOA");
		try {
			rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emaitza;
	}

	public boolean konprobatuTokenakDauden() {
		ResultSet emaitza = DBKS.getDBKS().queryExekutatu("SELECT token, tokenSecret FROM ERABILTZAILEA");
		try {
			return emaitza.next();
		} catch (SQLException e) {
		}
		return false;
	}
	
	public String[][] datuakJaso(String mota)
	{
		int kop = 0;
		String agindua = "";
		switch (mota.toLowerCase()) {
		case "txioa":
			agindua = "SELECT edukia, data FROM TXIOA WHERE mota = txioa";
			kop = 2;
			break;
		default:
			break;
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
		return null;
	}
}
