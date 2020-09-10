package largeSquareRoot;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class KataTest {
	
	@Test
	public void addTests() {
		//Kata.subtractBitSet(Kata.convertToBitSet(10), Kata.convertToBitSet(5));
		//Kata.calculateResultFromParts(new long[] {100L});
		Kata.multiplyBigNumbers(Kata.longToIntTable(4611686018427387904L), Kata.longToIntTable(4611686018427387904L));
		//Kata.birSetTry();
		/*Kata.power(Long.MAX_VALUE, 2);
		Kata.bigToString();
		Kata.toStringBitSet(Kata.convert(Long.MAX_VALUE));
		assertEquals(101, Kata.addBitSets(Kata.convert(Long.MAX_VALUE), Kata.convert(Long.MAX_VALUE)).toLongArray()[0]);
		*///assertEquals(25,Kata.multiplyBitSets(Kata.convert(Long.MAX_VALUE), Kata.convert(2)).toLongArray()[0]);
	}

	@Test
	public void basicTests() {
		//assertEquals("100000000000000000000000000000000000000000000000000",
		//		Kata.integerSquareRoot("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001"));
		//assertEquals("1", Kata.integerSquareRoot("969282338766292180121671595392578981360545020209"));
		assertEquals("214358881", Kata.integerSquareRoot("38320972670429256224669493542912203568587165109"));
		assertEquals("4", Kata.integerSquareRoot("17"));
		assertEquals("10", Kata.integerSquareRoot("100"));
		assertEquals("31", Kata.integerSquareRoot("1000"));
		assertEquals("152421548093487868711992623730429930751178496967",
				Kata.integerSquareRoot("23232328323215435345345345343458098856756556809400840980980980980809092343243243243243098799634"));
		assertEquals("3510457208086937291253621317073222057793129",
				Kata.integerSquareRoot("12323309809809534545458098709854808654685688665486860956865654654654324238000980980980"));
		//assertEquals("000000000000000000000000000000000000000000000000000000000000000000000",
		//		Kata.integerSquareRoot("512816459341555090103147548278331531689031236676"));

	}
}