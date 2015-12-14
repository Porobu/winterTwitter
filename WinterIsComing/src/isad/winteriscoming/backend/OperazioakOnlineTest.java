package isad.winteriscoming.backend;

import static org.junit.Assert.*;

import java.sql.ResultSet;

import org.junit.Before;
import org.junit.Test;

public class OperazioakOnlineTest {
	OperazioakOnline op = new OperazioakOnline();

	@Before
	public void setUp() throws Exception {
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM TXIOA");
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM ZERRENDA");
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM PAGING");
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM MEZUA");
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM ERABILTZAILEA");
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM DITU");
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM BESTEERABILTZAILEAK");
		DBKS.getDBKS().aginduaExekutatu("DELETE FROM AIPAMENAK");

	}

	@Test
	public void testTxioakDBsartu() {
		String agindua = "";
		op.txioakJaitsi();
		agindua = "SELECT * FROM TXIOA";
		if(DBKS.getDBKS().queryExekutatu(agindua)!=null){
			assertTrue(true);
		}
		else{
			assertTrue(false);
		}
		
	}
	
	public void testJarraituJarraitzaileakDBSartu(){
		//TODO
	}
	
	public void testMezuakJaitsi(){
		//TODO
	}
	
	public void testZerrendakJaitsi(){
		//TODO
	}
	
	public void testAipamenakJaitisi(){
		//TODO
	}

}
