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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return emaitza;
	}
}