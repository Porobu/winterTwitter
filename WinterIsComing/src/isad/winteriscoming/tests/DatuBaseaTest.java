package isad.winteriscoming.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import isad.winteriscoming.backend.DBKS;

public class DatuBaseaTest {
	
	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	
	@Test
	public void testDatuBaseaEraiki() {
		DBKS.getDBKS().datuBaseaEraiki(System.getProperty("user.home")  + "test.accdb");
		
	}

	@Test
	public void testKonektatu() {
		String path = System.getProperty("user.home")  + "test.accdb";
		DBKS.getDBKS().konektatu(path);
		
		
	}


	@Test
	public void testKonexioaItxi() {
		fail("Not yet implemented");
	}

}
