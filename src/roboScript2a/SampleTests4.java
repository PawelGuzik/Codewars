package roboScript2a;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SampleTests4 {
	@Test
	public void findPatternTest() {
		RoboScript r = new RoboScript();
		//RoboScript.findPatternsDefinitions("p213(F2LF2R)2q");

	}
	@Test 
	public void ssullPointerException() {
		assertPathEquals("p552LR(R7L7)13L7FP574q"
				+ "L9F((F3RF5L)13(F8LF13RRRF12LF2L)1)9RL"
				+ "p574LR(R(F3RF5L)7)15RF7P931F6F(L(F8LF13RRRF12LF2L)8)9FLFF(L8F)16F6FF3R(L6(F8LF13RRRF12LF2L)7)6LLq"
				+ "p244P606LF(L7F)3FF3RR((F8LF13RRRF12LF2L)11R8)4FRq"
				+ "p931LL(F7(F3RF5L)14)17LF0L1L((F3RF5L)10(F3RF5L)12)14F7F0P343LR(F4(F8LF13RRRF12LF2L)8)10F3RRF(LF7)9FFq"
				+ "p709F2L(L9L)13L9F8P467q"
				+ "F3F9(R6(F3RF5L)9)1F8RL2F2((F3RF5L)1F)12LF"
				+ "p467RF5(F2R9)8L3LFR(L2R)1FFP552q"
				+ "p343P709q"
				+ "RR(R8R5)19L4FF3F9(F6(F8LF13RRRF12LF2L)12)0LL"
				+ "p606P552RF7(RF5)7LF6FF4(L7(F3RF5L)10)12FFF9F((F8LF13RRRF12LF2L)10L9)13F6L6FL((F8LF13RRRF12LF2L)3L6)14F7Rq", "*");
	}
	
	@Test 
	public void xssullPointerException() {
		assertPathEquals("p364RL8((F3RF5L)14L9)2FF5F9R((F8LF13RRRF12LF2L)5R)15FFP471L5L1((F3RF5L)3(F3RF5L)8)11LL1qp732P231qp997FL4(R8F8)0FL1LF9(F8F6)17FRRF(R4R5)19F5LRF(R2(F3RF5L)4)5LRqp231P357P197FL(FL7)2LLRR(L(F3RF5L)8)10RFqp197P732RF(R9(F8LF13RRRF12LF2L)3)2L1L7qp471P997F1F2((F8LF13RRRF12LF2L)5F)3F5F4LL0((F3RF5L)11L)16RF3P732qp357P354qL6L(L7L)19LL0p354F8L(F2F5)0L1LLL1(LL2)10FL3LL((F3RF5L)7R)18RFq", "*");
	}
	@Test
	public void shouldWorkForRS2CompliantPrograms_SampleTestCases() {
		assertPathEquals("(F2LF2R)2FRF4L(F2LF2R)2(FRFL)4(F2LF2R)2",
				"    **   **      *\r\n    **   ***     *\r\n  **** *** **  ***\r\n  *  * *    ** *  \r\n***  ***     ***  ");
	}
	
	  @Test public void longhouldWorkForRS2CompliantPrograms_SampleTestCases() {
	  assertPathEquals(
	  "p922qF3R(RR7)12L1LP922p350FL1(FR)13FL0FR(L1R0)2F0LL6F(R3L)1FL3qp83L1F6(R0L0)1L1LP83F3L(LF5)6LRL6L(RR)5F6F7P350FL(L(F3RF5L)6)9F0Rqp697qLL1(F9R)4F3Rp156qFL((F8LF13RRRF12LF2L)11(F8LF13RRRF12LF2L)8)17FRP697",
	  "    **   **      *\r\n    **   ***     *\r\n  **** *** **  ***\r\n  *  * *    ** *  \r\n***  ***     ***  "
	 ); }
	 

	@Test
	public void shouldProperlyParsePatternDefinition_notCausingAnySideEffect_SampleTestCases() {
		assertPathEquals("p0(F2LF2R)2q", "*");
		assertPathEquals("p312(F2LF2R)2q", "*");
	}

	@Test
	public void shouldExecuteGivenPatternWhenInvoked_SampleTestCases() {
		assertPathEquals("p0(F2LF2R)2qP0", "    *\r\n    *\r\n  ***\r\n  *  \r\n***  ");
		assertPathEquals("p312(F2LF2R)2qP312", "    *\r\n    *\r\n  ***\r\n  *  \r\n***  ");
	}

	@Test
	public void shouldAlwaysParsePatternDefinitionsFirst_SampleTestCases() {
		assertPathEquals("P0p0(F2LF2R)2q", "    *\r\n    *\r\n  ***\r\n  *  \r\n***  ");
		assertPathEquals("P312p312(F2LF2R)2q", "    *\r\n    *\r\n  ***\r\n  *  \r\n***  ");
	}

	@Test
	public void shouldAllowOtherFormsOfRoboScriptCodeAlongsidePatternDefinitionsAndInvocations_SampleTestCases() {
		assertPathEquals("F3P0Lp0(F2LF2R)2qF2",
				"       *\r\n       *\r\n       *\r\n       *\r\n     ***\r\n     *  \r\n******  ");
	}

	@Test
	public void shouldAllowMultipleInvokationOfSamePattern_SampleTestCases() {
		assertPathEquals("(P0)2p0F2LF2RqP0",
				"      *\r\n      *\r\n    ***\r\n    *  \r\n  ***  \r\n  *    \r\n***    ");
	}

	@Test
	public void shouldThrowWhenNonExistingPatternInvoked_SampleTestCases() {
		expectError("Your interpreter should throw an error because pattern \"P1\" does not exist", "p0(F2LF2R)2qP1");
		expectError("Your interpreter should throw an error because pattern \"P0\" does not exist", "P0p312(F2LF2R)2q");
		expectError("Your interpreter should throw an error because pattern \"P312\" does not exist", "P312");
	}



    
    @Test public void shouldProperlyParseMultiplePatternDefinitions_SampleTestCases() {
        assertPathEquals("P1P2p1F2Lqp2F2RqP2P1", "  ***\r\n  * *\r\n*** *");
        assertPathEquals("p1F2Lqp2F2Rqp3P1(P2)2P1q(P3)3", "  *** *** ***\r\n  * * * * * *\r\n*** *** *** *");
    }
	@Test
	public void aashouldThrowWhenNonExistingPatternInvoked_SampleTestCases() {
		expectError("Your interpreter should throw an error because pattern \"P1\" does not exist", "p0(F2LF2R)2qP1");
		expectError("Your interpreter should throw an error because pattern \"P0\" does not exist", "P0p312(F2LF2R)2q");
		expectError("Your interpreter should throw an error because pattern \"P312\" does not exist", "P312");
	}
	
	@Test
	public void shouldThrowWhenInfiniteRecursionOccurs() {
		expectError("Your interpreter should throw an error because pattern ", "p0P1qp1P2qp3P2qp2P5qp5P1qP0");
	}

	private static void assertPathEquals(String code, String expected) {
		String actual = RoboScript.execute(code);
		boolean areEqual = expected.equals(actual);

		if (!areEqual) {
			System.out.println(
					String.format("--------------\nYou returned:\n%s\nExpected path of MyRobot:\n%s\n--------------\n",
							actual, expected));
		}
		assertTrue("Nope...", areEqual);
	}

	private static void expectError(String msg, String code) {
		boolean threw = false;
		try {
			RoboScript.execute(code); 
		} catch (RuntimeException e) {
			System.out.println(e);
			threw = true;
		}
		assertTrue(msg, threw);
	}
}
