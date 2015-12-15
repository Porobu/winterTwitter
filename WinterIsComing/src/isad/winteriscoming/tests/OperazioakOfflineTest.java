package isad.winteriscoming.tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sqlite.core.DB;

import isad.winteriscoming.backend.DBKS;
import isad.winteriscoming.backend.OperazioakOffline;
import isad.winteriscoming.frontend.FitxategiOperazioakUI;

public class OperazioakOfflineTest {

	@Before
	public void setUp() throws Exception {
		String path = System.getProperty("user.home") + "/test.db";
		File fitxategia = new File(path);
		fitxategia.delete();
		FitxategiOperazioakUI nireFO = new FitxategiOperazioakUI();
		nireFO.datuBaseaEraiki(path);
		DBKS.getDBKS().konektatu(path);
	}

	@After
	public void tearDown() throws Exception {
		DBKS.getDBKS().konexioaItxi();
	}

	@Test
	public void testDatuakJaso() {
		DBKS.getDBKS().aginduaExekutatu(
				"INSERT INTO TXIOA(id, edukia, data, mota) VALUES('123', 'txioa', '01/01/2016', 'gustokoa')");
		DBKS.getDBKS().aginduaExekutatu(
				"INSERT INTO TXIOA(id, edukia, data, mota) VALUES('1235', 'txioa', '01/01/2016', 'txioa')");
		OperazioakOffline nireOFF = new OperazioakOffline();
		if (nireOFF.datuakJaso("txioa", "", false).isEmpty())
			fail();
		if (nireOFF.datuakJaso("gustokoa", "", false).isEmpty())
			fail();
		if (!nireOFF.datuakJaso("txioa", "jkdshgduvh", true).isEmpty())
			fail();
		this.testkonprobatuTokenakDauden();
	}

	public void testkonprobatuTokenakDauden() {
		OperazioakOffline nireOFF = new OperazioakOffline();
		if(nireOFF.konprobatuTokenakDauden())
			fail();
		DBKS.getDBKS().aginduaExekutatu(
				"INSERT OR REPLACE INTO ERABILTZAILEA(id,nick,izena,token,tokenSecret) VALUES('666','ggg','feo','ftfthftf','ggggggg')");
		if(!nireOFF.konprobatuTokenakDauden())
			fail();
	}

}
