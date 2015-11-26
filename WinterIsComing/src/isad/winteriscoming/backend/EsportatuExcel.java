package isad.winteriscoming.backend;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EsportatuExcel {
	private Workbook wb;
	private Sheet txioak;
	private Sheet bertxioak;
	private Sheet gustokoak;
	private Sheet jarraituak;
	private Sheet jarraitzaileak;
	private Sheet zerrendak;
	private Sheet mezuak;

	public EsportatuExcel() {

	}

	public boolean idatzi(String path) {
		wb = new XSSFWorkbook();
		txioak = wb.createSheet("Txioak");
		bertxioak = wb.createSheet("Bertxioak");
		gustokoak = wb.createSheet("Gustokoak");
		jarraituak = wb.createSheet("Jarraituak");
		jarraitzaileak = wb.createSheet("Jarraitzaileak");
		zerrendak = wb.createSheet("Zerrendak");
		mezuak = wb.createSheet("Mezuak");

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
			fileOut.close();
			wb.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	private void txioakOrriaEgin() throws SQLException {
		Row row = txioak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Edukia");
		row.createCell(2).setCellValue("Data");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT ID, EDUKIA, DATA FROM TXIOA WHERE MOTA = 'TXIOA'");
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
		Row row = bertxioak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Edukia");
		row.createCell(2).setCellValue("Data");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT ID, EDUKIA, DATA FROM TXIOA WHERE MOTA = 'BERTXIOA'");
		int i = 1;

		while (nireRS.next()) {
			row = txioak.createRow(i);
			for (int j = 0; j < 3; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void gustokoakOrriaEgin() throws SQLException {
		Row row = gustokoak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Edukia");
		row.createCell(2).setCellValue("Data");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT ID, EDUKIA, DATA FROM TXIOA WHERE MOTA = 'GUSTOKOA'");
		int i = 1;

		while (nireRS.next()) {
			row = txioak.createRow(i);
			for (int j = 0; j < 3; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void jarraituakOrriaEgin() throws SQLException {
		Row row = jarraituak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Izena");
		row.createCell(2).setCellValue("Nick");
		row.createCell(2).setCellValue("ID Erabiltzailea");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu(
				"SELECT ID, IZENA, NICK, IDERABILTZAILEA FROM BESTEERABILTZAILEAK WHERE MOTA = 'JARRAITUA'");
		int i = 1;

		while (nireRS.next()) {
			row = txioak.createRow(i);
			for (int j = 0; j < 4; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void jarraitzaileakOrriaEgin() throws SQLException {
		Row row = jarraitzaileak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Izena");
		row.createCell(2).setCellValue("Nick");
		row.createCell(2).setCellValue("ID Erabiltzailea");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu(
				"SELECT ID, IZENA, NICK, IDERABILTZAILEA FROM BESTEERABILTZAILEAK WHERE MOTA = 'JARRAITZAILEA'");
		int i = 1;
		while (nireRS.next()) {
			row = txioak.createRow(i);
			for (int j = 0; j < 4; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void zerrendakOrriaEgin() throws SQLException {
		Row row = zerrendak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Izena");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT ID, IZENA FROM ZERRENDA");
		int i = 1;
		while (nireRS.next()) {
			row = txioak.createRow(i);
			for (int j = 0; j < 2; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}

	private void mezuakOrriaEgin() throws SQLException {
		Row row = mezuak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Data");
		row.createCell(2).setCellValue("Edukia");
		row.createCell(3).setCellValue("Bidaltzaile Izena");
		row.createCell(4).setCellValue("Hartzaile Izena");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT * FROM MEZUA");
		int i = 1;
		while (nireRS.next()) {
			row = txioak.createRow(i);
			for (int j = 0; j < 5; j++) {
				row.createCell(j).setCellValue(nireRS.getString(j + 1));
			}
			i++;
		}
	}
}