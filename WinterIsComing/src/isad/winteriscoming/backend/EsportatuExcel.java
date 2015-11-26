package isad.winteriscoming.backend;

import java.io.FileOutputStream;
import java.io.IOException;
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

	public void idatzi(String path) {
		wb = new XSSFWorkbook();
		txioak = wb.createSheet("Txioak");
		bertxioak = wb.createSheet("Bertxioak");
		gustokoak = wb.createSheet("Gustokoak");
		jarraituak = wb.createSheet("Jarraituak");
		jarraitzaileak = wb.createSheet("Jarraitzaileak");
		zerrendak = wb.createSheet("Zerrendak");
		mezuak = wb.createSheet("Mezuak");
		this.txioOrriaEgin();
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			wb.write(fileOut);
			fileOut.close();
			wb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void txioOrriaEgin() {
		Row row = txioak.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Edukia");
		row.createCell(2).setCellValue("Data");
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT ID, EDUKIA, DATA FROM TXIOA WHERE MOTA = 'TXIOA'");
		int i = 1;
		try {
			while (nireRS.next()) {
				row = txioak.createRow(i);
				for (int j = 0; j < 3; j++) {
					row.createCell(j).setCellValue(nireRS.getString(j+1));
				}
				i++;
			}
		} catch (SQLException e) {
		}
	}

}
