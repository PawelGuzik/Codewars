package smaller;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

import java.util.Random;

import org.junit.runners.JUnit4;

public class SolutionTest {
	@Test
	public void initialTests() {
		 assertArrayEquals(new int[] {0,0,0},
			 Smaller.smaller(new int[] {99, 199, 63, 16, 191, 42, 78, 67, 108, 70, 81, 34, 23, 199, 169, 
					 32, 0, 147, 24, 74, 126, 145, 109, 58, 151, 169, 167, 118, 162, 163, 175, 60, 196, 
					 100, 22, 28, 3, 148, 192, 191, 57, 66, 196, 149, 89, 136, 2, 18, 87, 139, 32, 194, 
					 7, 27, 136, 180, 126, 185, 41, 179, 180, 142, 160, 135, 70, 184, 90, 123, 111, 191, 
					 169, 79, 166, 70, 162, 93, 40, 77, 119, 1, 123, 4, 53, 113, 164, 2, 92, 92, 101, 42, 
					 190, 183, 79, 143, 22,  }));
		assertArrayEquals(new int[] {4, 3, 2, 1,0}, Smaller.smaller(new int[]
		 {5,4,3,2,1}));
		 assertArrayEquals(new int[] {0,0,0}, Smaller.smaller(new int[] {1,2,3}));
		 assertArrayEquals(new int[] {1,1,0}, Smaller.smaller(new int[] {1,2,0}));
		 assertArrayEquals(new int[] {15,15,15,15,15,7, 6, 7, 9, 10, 2, 2, 1, 1, 1, 0, 2, 2, 0, 0, }, Smaller.smaller(new int[] {10,10,10,10,10,4, 3, 5, 7, 9, 1, 1, 0, 2, 2, -1, 6, 8, 4, 5})); 
		assertArrayEquals(new int[] {0,1,0}, Smaller.smaller(new int[] {1,2,1}));
		 assertArrayEquals(new int[] {3,3,0,0,0}, Smaller.smaller(new int[]
		 {1,1,-1,0,0}));
		Random rd = new Random();
		int[] numbers = new int[7000];
		for (int i = 0; i < 7000; i++) {
			numbers[i] = rd.nextInt(700);

		}
		// assertArrayEquals(new int[] {3,3,0,0,0}, Smaller.smaller(numbers));
	}
}