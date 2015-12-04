package isad.winteriscoming.backend;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Excel {
	private Workbook wb;
	private Sheet txioak;
	private Sheet bertxioak;
	private Sheet gustokoak;
	private Sheet jarraituak;
	private Sheet jarraitzaileak;
	private Sheet zerrendak;
	private Sheet mezuak;

	public Excel() {

	}

	public boolean idatzi(String path) {
		wb = new XSSFWorkbook();
		try {
			this.txioakOrriaEgin();
			this.bertxioakOrriaEgin();
			this.gustokoakOrriaEgin();
			this.jarraituakOrriaEgin();
			this.jarraitzaileakOrriaEgin();
			this.zerrendakOrriaEgin();
			this.mezuakOrriaEgin();
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.flush();
			fileOut.close();
			wb.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void txioakOrriaEgin() throws SQLException {
		txioak = wb.createSheet("Txioak");
		txioak.setSelected(true);
		Row row = txioak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Edukia");
		row.createCell(2).setCellValue("Data");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT id, edukia, data FROM TXIOA WHERE MOTA = 'txioa'");
		int i = 1;
		while (nireRS.next()) {
			row = txioak.createRow(i);
			for (int j = 0; j < 3; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void bertxioakOrriaEgin() throws SQLException {
		bertxioak = wb.createSheet("Bertxioak");
		bertxioak.setSelected(true);
		Row row = bertxioak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Edukia");
		row.createCell(2).setCellValue("Data");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT id, edukia, data FROM TXIOA WHERE MOTA = 'bertxioa'");
		int i = 1;
		while (nireRS.next()) {
			row = bertxioak.createRow(i);
			for (int j = 0; j < 3; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void gustokoakOrriaEgin() throws SQLException {
		gustokoak = wb.createSheet("Gustokoak");
		gustokoak.setSelected(true);
		Row row = gustokoak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Edukia");
		row.createCell(2).setCellValue("Data");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT id, edukia, data FROM TXIOA WHERE MOTA = 'gustokoa'");
		int i = 1;

		while (nireRS.next()) {
			row = gustokoak.createRow(i);
			for (int j = 0; j < 3; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void jarraituakOrriaEgin() throws SQLException {
		jarraituak = wb.createSheet("Jarraituak");
		jarraituak.setSelected(true);
		Row row = jarraituak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Izena");
		row.createCell(2).setCellValue("Nick");
		row.createCell(2).setCellValue("ID Erabiltzailea");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu(
				"SELECT id, izena, nick, idErabiltzailea FROM BESTEERABILTZAILEAK WHERE MOTA = 'jarraitua'");
		int i = 1;

		while (nireRS.next()) {
			row = jarraituak.createRow(i);
			for (int j = 0; j < 4; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void jarraitzaileakOrriaEgin() throws SQLException {
		jarraitzaileak = wb.createSheet("Jarraitzaileak");
		jarraitzaileak.setSelected(true);
		Row row = jarraitzaileak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Izena");
		row.createCell(2).setCellValue("Nick");
		row.createCell(2).setCellValue("ID Erabiltzailea");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu(
				"SELECT id, izena, nick, idErabiltzailea FROM BESTEERABILTZAILEAK WHERE MOTA = 'jarraitzailea'");
		int i = 1;
		while (nireRS.next()) {
			row = jarraitzaileak.createRow(i);
			for (int j = 0; j < 4; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void zerrendakOrriaEgin() throws SQLException {
		zerrendak = wb.createSheet("Zerrendak");
		zerrendak.setSelected(true);
		Row row = zerrendak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Izena");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT id, izena FROM ZERRENDA");
		int i = 1;
		while (nireRS.next()) {
			row = zerrendak.createRow(i);
			for (int j = 0; j < 2; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void mezuakOrriaEgin() throws SQLException {
		mezuak = wb.createSheet("Mezuak");
		mezuak.setSelected(true);
		Row row = mezuak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Data");
		row.createCell(2).setCellValue("Edukia");
		row.createCell(3).setCellValue("Bidaltzaile Izena");
		row.createCell(4).setCellValue("Hartzaile Izena");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT * FROM MEZUA");
		int i = 1;
		while (nireRS.next()) {
			row = mezuak.createRow(i);
			for (int j = 0; j < 5; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}
}