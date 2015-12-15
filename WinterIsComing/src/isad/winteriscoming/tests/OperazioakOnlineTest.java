package isad.winteriscoming.tests;

import static org.junit.Assert.fail;

import java.io.File;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import isad.winteriscoming.backend.DBKS;
import isad.winteriscoming.backend.OperazioakOnline;
import isad.winteriscoming.frontend.FitxategiOperazioakUI;

public class OperazioakOnlineTest {
	OperazioakOnline op = OperazioakOnline.getOperazioak();

	@Before
	public void setUp() throws Exception {
		String path = System.getProperty("user.home") + "/test.db";
		File fitxategia = new File(path);
		fitxategia.delete();
		FitxategiOperazioakUI nireFO = new FitxategiOperazioakUI();
		nireFO.datuBaseaEraiki(path);
		DBKS.getDBKS().konektatu(path);
		// Konexioa.getKonexioa().tokenarekinKonektatu();
	}

	@Test
	public void testTxioakDBsartu() {
		String agindua = "";
		op.txioakJaitsi();
		op.gustokoakJaitsi();
		agindua = "SELECT * FROM TXIOA";
		try {
			if (!DBKS.getDBKS().queryExekutatu(agindua).next())
				fail();
		} catch (SQLException e) {
		}

	}

	@Test
	public void testJarraituJarraitzaileakDBSartu() {
		String agindua = "";
		op.jarraituakJaitsi();
		op.jarraitzaileakJaitsi();
		agindua = "SELECT * FROM BESTEERABILTZAILEAK";
		try {
			if (!DBKS.getDBKS().queryExekutatu(agindua).next())
				fail();
		} catch (SQLException e) {
		}
	}

	@Test
	public void testMezuakJaitsi() {
		String agindua = "";
		op.mezuakJaitsi();
		agindua = "SELECT * FROM MEZUA";
		try {
			if (!DBKS.getDBKS().queryExekutatu(agindua).next())
				fail();
		} catch (SQLException e) {
		}
	}

	@Test
	public void testZerrendakJaitsi() {
		String agindua = "";
		op.zerrendakJaitsi();
		agindua = "SELECT * FROM ZERRENDA";
		try {
			if (!DBKS.getDBKS().queryExekutatu(agindua).next())
				fail();
		} catch (SQLException e) {
		}
	}

	@Test
	public void testAipamenakJaitisi() {
		String agindua = "";
		op.aipamenakJaitsi();
		agindua = "SELECT * FROM AIPAMENAK";
		try {
			if (!DBKS.getDBKS().queryExekutatu(agindua).next())
				fail();
		} catch (SQLException e) {
		}
	}

}
