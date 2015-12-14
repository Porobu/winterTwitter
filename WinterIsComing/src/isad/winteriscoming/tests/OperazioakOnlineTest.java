package isad.winteriscoming.tests;

import static org.junit.Assert.*;

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
		String path = System.getProperty("user.home") + "/test.accdb";
		File fitxategia = new File(path);
		fitxategia.delete();
		FitxategiOperazioakUI nireFO = new FitxategiOperazioakUI();
		nireFO.datuBaseaEraiki(path);
		DBKS.getDBKS().konektatu(path);
	}

	@Test
	public void testTxioakDBsartu() {
		String agindua = "";
		op.txioakJaitsi();
		agindua = "SELECT * FROM TXIOA";
		try {
			if (!DBKS.getDBKS().queryExekutatu(agindua).next())
				fail();
		} catch (SQLException e) {
		}

	}

	public void testJarraituJarraitzaileakDBSartu() {
		// TODO
	}

	public void testMezuakJaitsi() {
		// TODO
	}

	public void testZerrendakJaitsi() {
		// TODO
	}

	public void testAipamenakJaitisi() {
		// TODO
	}

}
