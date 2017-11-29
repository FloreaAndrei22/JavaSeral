package teste;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import proiect.AiDatFalimentException;
import proiect.logic.GameState;
import proiect.model.TipCamera;

public class TestHotelFlorin {

	private static GameState state;

	@BeforeClass
	public static void setupBeforeClass() {
		state = new GameState();
	}

	// se ruleaza inainte de fiecare test
	@Before
	public void setupBeforeTest() {
		state.init();
	}

	@Test
	public void testBuget() {
		int nrCamere = 50;
		int bugetInitial = state.getBuget();
		TipCamera tip = TipCamera.DOUBLE;
		int totalCheltuit = nrCamere * tip.getPret();
		for (int i = 0; i < nrCamere; i++) {
			state.cumparaCamere0(tip);
		}
		int bugetFinal = state.getBuget();
		int bugetFinalCalculat = bugetInitial - totalCheltuit;
		if (bugetFinalCalculat < 0) {
			assertEquals(0, state.getBuget());
		} else {
			assertEquals(bugetFinalCalculat, state.getBuget());
			assertEquals(nrCamere, state.getNrCamere());
		}
	}

	@Test(expected = AiDatFalimentException.class)
	public void testBugetFaliment() {
		int nrCamere = 1001;
		TipCamera tip = TipCamera.DOUBLE;
		for (int i = 0; i < nrCamere; i++) {
			state.cumparaCamere0(tip);
		}
	}

	@Test
	public void testHotel() {
		assertEquals(0, state.getNrCamere());
	}

	@Test
	public void testClienti() {
		for (int i = 0; i < 1000; i++) {
			state.genereazaClient();
		}
		int nrClienti = state.getNrClientiCoada();
		if (nrClienti < 80 || nrClienti > 120) {
			Assert.fail("nu sunt generati clientii ok " + nrClienti);
		} else {
			Assert.assertTrue("nr clienti: " + nrClienti, true);
		}
	}

	// se ruleaza dupa fiecare test
	@After
	public void endTest() {
		System.out.println("===== end test");
		// state = null;
	}

	@AfterClass
	public static void endTestClassMethods() {
		state = null;
	}

}
