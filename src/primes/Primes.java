package primes;

import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.IntStream;

public class Primes {
	static int limit = 983000000;
	static BitSet setOfPrimes;

	public static BitSet primesPagination(int n) {
		setOfPrimes = new BitSet(n + 1);

		removeSimpleDiv();

		int p = 7;
		int q = 7;
		int k = 1;
		long x = power(p, k) * q;
		long tmp;

		do {
			tmp = p;
			while (x <= n) {
				setOfPrimes.set((int) x);
				tmp = p * tmp;
				x = tmp * q;
				k++;
			}
			q = setOfPrimes.nextClearBit(q + 2);

			if ((p * q) <= n) {

			} else {
				q = setOfPrimes.nextClearBit(p + 2);
				p = q;
			}
			k = 1;
			x = p * q;
		} while (x <= n);
		setOfPrimes.flip(0, limit + 1);
		return setOfPrimes;
	}

	private static void removeSimpleDiv() {
		setOfPrimes.set(1);
		for (int i = 0; i <= limit; i = i + 2) {
			setOfPrimes.set(i);
		}
		for (int i = 0; i <= limit; i = i + 3) {
			setOfPrimes.set(i);
		}
		for (int i = 15; i <= limit; i = i + 10) {
			setOfPrimes.set(i);
		}
		setOfPrimes.flip(2);
		setOfPrimes.flip(3);
	}

	public static IntStream stream() {
		return BitSetOfPrimes.getStream();
	}

	private static long power(int a, int b) {
		long res = 1;
		while (b > 0) {
			if ((b & 1) == 0) {
				a *= a;
				b >>>= 1;
			} else {
				res *= a;
				b--;
			}
		}
		return res;
	}

	private static final class BitSetOfPrimes {

		private static volatile BitSetOfPrimes INSTANCE;
		private static int[] finalPrimes;

		private BitSetOfPrimes() {
			finalPrimes = primesPagination(limit).stream().toArray();
		}

		public static BitSetOfPrimes getInstance() {
			if (INSTANCE == null) {
				synchronized (BitSetOfPrimes.class) {
					if (INSTANCE == null) {
						INSTANCE = new BitSetOfPrimes();
					}
				}
			}
			return INSTANCE;
		}

		private static IntStream getStream() {

			getInstance();
			return Arrays.stream(finalPrimes);
		}
	}
}
