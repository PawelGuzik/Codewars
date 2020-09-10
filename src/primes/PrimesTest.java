package primes;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class PrimesTest {

  @Test
  public void test_0_10() {
    test(0, 10, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
  }
  
  @Test
  public void test_10_10() {
    test(10, 10, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71);
  }
  
  @Test
  public void test_100_10() {
    test(100, 10, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601);
  }
  
  @Test
  public void test_10milion_1() {
    test(10000000, 1, 179424691);

  }
  @Test
  public void test_40milion_1() {
    test(50000000, 1, 776531419);
  }
  @Test
  public void test_1milion_1() {
    test(1000000, 1, 15485867);
  }
  
  @Test
  public void test_1000x1000() {
	  for(int i =0; i<1000; i++) {
		//  System.out.println("Test: " + i);
		  test(1000, 1, 7927);
		 // System.out.println("Test: " + i);
	  }
  }
  @Test
  public void testIsPrime2() {
	  //Primes2.sieveOfEratosthenes(1000000000);
    Primes.stream();
  }
  
  private void test(int skip, int limit, int... expect) {
    int[] found = Primes.stream().skip(skip).limit(limit).toArray();
    assertArrayEquals(expect, found);
  }
  
}