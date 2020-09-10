package psyhic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PsuhicUTest {

	@Test
	public void testRandom() {
		//assertEquals(Psychic.findSeedForDaoubleRandom(), java.lang.Math.random(), 0);
		//Psychic.findSeedInMathRandom();
		assertEquals(Psychic.guess(), java.lang.Math.random(), 0);
		assertEquals(Psychic.guess(), java.lang.Math.random(), 0);

	}

	/*@Test
	public void t() {
		Psychic ps = new Psychic();
		ps.findSeed();
	}*/
}
