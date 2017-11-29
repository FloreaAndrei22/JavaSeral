package teste;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import proiect.model.Client;

public class TesteIosif {
	
	@Test
	public void testClient () {
		
		Client c = new Client("Controlor", 5);
		for (int i=0; i < 10; i++) {
			c.decrementNrDays();
		}
		assertTrue((c.getNrDays() >= -8 ) && (c.getNrDays()<=0));
	}
	

}
