package isad.winteriscoming.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import isad.winteriscoming.backend.DBKS;
import isad.winteriscoming.frontend.FitxategiOperazioakUI;

public class DatuBaseaTest {
	private String path;

	@Before
	public void setUp() throws Exception {
		path = System.getProperty("user.home") + "/test.db";
		File fitxategia = new File(path);
		fitxategia.delete();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDatuBasea() {
		FitxategiOperazioakUI nireFO = new FitxategiOperazioakUI();
		nireFO.datuBaseaEraiki(path);
		DBKS.getDBKS().konektatu(path);
		// INSERT PROBATU
		DBKS.getDBKS().aginduaExekutatu(
				"INSERT INTO TXIOA(id, edukia, data, mota) VALUES('123', 'txioa', '01/01/2016', 'gustokoa')");
		// SELECT PROBATU
		ResultSet nireRS = DBKS.getDBKS().queryExekutatu("SELECT * FROM TXIOA");
		try {
			if (!nireRS.next())
				fail();
		} catch (SQLException e) {
		}
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM TXIOA");
		nireRS = DBKS.getDBKS().queryExekutatu("SELECT * FROM TXIOA");
		try {
			if (nireRS.next())
				fail();
		} catch (SQLException e) {
		}
		DBKS.getDBKS().konexioaItxi();
		File fitxategia = new File(System.getProperty("user.home") + "/test.laccdb");
	}
}
