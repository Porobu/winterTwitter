package isad.winteriscoming.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import isad.winteriscoming.backend.DBKS;

public class DatuBaseaTest {
	private String path;

	@Before
	public void setUp() throws Exception {
		path = System.getProperty("user.home") + "/test.accdb";
		File fitxategia = new File(path);
		fitxategia.delete();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDatuBasea() {
		DBKS.getDBKS().datuBaseaEraiki(path);
		//INSERTAK PROBATU
		DBKS.getDBKS().aginduaExekutatu("INSERT INTO TXIOA(id, edukia, data, mota) VALUES ('123', 'txioa', '01/01/2016', 'gustokoa')");
		DBKS.getDBKS().konexioaItxi();
		File fitxategia = new File(System.getProperty("user.home") + "/test.laccdb");
		if (fitxategia.exists())
			fail();
	}
}
