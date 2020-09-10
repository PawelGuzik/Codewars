package roboScript2a;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runners.JUnit4;

public class SolutionTest {

	 @Test public void sampleTests3() {
	        assertPathEquals("L8F9R3F4L(L(F3RF5L)15(F3RF5L)1(F8LF13RRRF12LF2L)1L10RR2(F8LF13RRRF12LF2L)14(F3RF5L)5(F8LF13RRRF12LF2L)14)3LLR3RR", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ");

	     //   assertPathEquals("LF5(RF3)(RF3R)F7", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ");
	     //   assertPathEquals("(L(F5(RF3))(((R(F3R)F7))))", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ");
	     //  assertPathEquals("F4LF4RF4RF4LF4LF4RF4RF4LF4LF4RF4RF4", "    *****   *****   *****\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n*****   *****   *****   *");
	     //   assertPathEquals("F4L((F4R)2(F4L)2)2(F4R)2F4", "    *****   *****   *****\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n    *   *   *   *   *   *\r\n*****   *****   *****   *");
	    }
	@Test
	public void sampleTests() {
	//	assertPathEquals("", "*");
	//	assertPathEquals("", "*");
	//	assertPathEquals("FFFFF", "******");
	//	assertPathEquals("", "*");
	//	assertPathEquals("FFFFFLFFFFFLFFFFFLFFFFFL", "******\r\n*    *\r\n*    *\r\n*    *\r\n*    *\r\n******");
	//	assertPathEquals("", "*");
	//	assertPathEquals("LFFFFFRFFFRFFFRFFFFFFF",
	//			"    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ");
		/*assertPathEquals("", "*");
		assertPathEquals("LF5RF3RF3RF7", "    ****\r\n    *  *\r\n    *  *\r\n********\r\n    *   \r\n    *   ");
		assertPathEquals("", "*");
		assertPathEquals("RFFFFFLFLLFLF", "* \r\n* \r\n* \r\n* \r\n* \r\n**\r\n* ");
		assertPathEquals("RF5LF1LLF1LF1", "* \r\n* \r\n* \r\n* \r\n* \r\n**\r\n* ");
		assertPathEquals("RF6L2FRLRF", "* \r\n* \r\n* \r\n* \r\n* \r\n**\r\n* ");
		assertPathEquals("LLRRF1RRL2L2F1", "**");*/
		// assertPathEquals("F2R218F3LLF4R2F5L2R2L2F6LLF7LLF8LLF9LLF10", "***********");
		// assertPathEquals("FFF402F", "****");

		System.out.println("END TEST");

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

}