package teste;

import org.junit.*;
import proiect.logic.GameState;
import proiect.model.TipCamera;

import static org.junit.Assert.assertEquals;


public class TestHotelAndreiSiBogdan {

    private static GameState state;

    @BeforeClass
    public static void setupBeforeClass() {
        System.out.println("before class");
        state = new GameState();
    }
    @Before
    public void setupBeforeTest() {
        state.init();
    }

    @Test
    public void testProceseazaClient(){

        System.out.println("in test1");
        TipCamera tip = TipCamera.DOUBLE;
        int nrCamere = 10;
        //int bugetInitial = state.getBuget();
        for (int i = 0; i < nrCamere; i++) {
            state.cumparaCamere0(tip);
            state.genereazaClient();
        }
        System.out.println(state.getNrCamere());
        int numarInitialClienti= state.getNrClientiCoada();
        System.out.println(numarInitialClienti);
        state.proceseazaClient();
        assertEquals(nrCamere-1, state.getNrCamereGoale());
        assertEquals(numarInitialClienti-1, state.getNrClientiCoada());

    }

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
