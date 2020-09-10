package fibonacci;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static java.math.BigInteger.ONE;
import static java.math.BigInteger.ZERO;
import static java.math.BigInteger.valueOf;

import java.lang.annotation.Retention;

public class Fibonacci {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime = System.nanoTime();
		BigInteger testBetterFib = betterFib(BigInteger.valueOf(3523569L));
		System.out.printf("%, d", testBetterFib);
		long endTime = System.nanoTime();
		long totalTime = endTime - startTime;
		long seconds = TimeUnit.NANOSECONDS.toMillis(totalTime);
		System.out.println("\n" + "Seconds: " + seconds);

		startTime = System.nanoTime();
		// System.out.println(fib(BigInteger.valueOf(70L)));
		BigInteger testNormalFib = fib(BigInteger.valueOf(3523569L));
		System.out.printf("%, d", testNormalFib);
		System.out.println("\n" + testBetterFib.compareTo(testNormalFib));
		endTime = System.nanoTime();
		totalTime = endTime - startTime;
		seconds = TimeUnit.NANOSECONDS.toMillis(totalTime);

		System.out.println("\n" + "Seconds: " + seconds);

	}

	public static BigInteger betterFib(BigInteger n) {
		boolean negate = false;
		if (n.compareTo(ZERO) == -1) {
			negate = true;
			n= n.negate();
		}
		if (n.intValue() < 100000) {
			List<BigInteger> result = fibList(n, ZERO, ONE);
			return negate && n.intValue() % 2 == 0 ?  
					result.get(result.size() - 1).negate() : 
						result.get(result.size() - 1);
		}
		long rest = n.longValue() - (n.longValue() - (n.longValue() % 100000));
		List<BigInteger> firstElements = calculateFirstStep(valueOf(rest));
		BigInteger[][] coef = calculateCoef(valueOf(10000L));

		int expectedPos = n.intValue() / 10000;
		int currentPos = expectedPos % 10;
		List<BigInteger> nextElements = new ArrayList<>(3);

		while (expectedPos > currentPos) {

			for (int i = 0; i < 3; i++) {
				BigInteger tempRes = BigInteger.valueOf(0);
				for (int j = 0; j < 3; j++) {
					tempRes = tempRes.add(firstElements.get(j).multiply(coef[i][j]));
				}
				nextElements.add(tempRes);
			}
			firstElements.removeAll(firstElements);
			for (int i = 0; i < 3; i++) {
				firstElements.add(nextElements.get(i));
			}
			nextElements.removeAll(nextElements);
			currentPos++;
		}
		if (negate && n.intValue() % 2 == 0) {
			return firstElements.get(2).negate();
		}
		return firstElements.get(2);
	}

	public static List<BigInteger> calculateFirstStep(BigInteger firstFibelement) {

		List<BigInteger> startCase = fibList(firstFibelement, ZERO, ONE);
		return startCase;
	}

	public static BigInteger[][] calculateCoef(BigInteger minDistance) {
		BigInteger[][] coefficients = new BigInteger[3][3];

		List<BigInteger> subFibo = fibList(minDistance.subtract(valueOf(3)), ZERO, valueOf(2L));
		for (int i = 2; i >= 0; i--) {
			coefficients[i][0] = subFibo.get(i);
		}
		subFibo = fibList(minDistance.subtract(valueOf(3)), valueOf(2L), ONE);
		for (int i = 2; i >= 0; i--) {
			coefficients[i][1] = subFibo.get(i);
		}
		subFibo = fibList(minDistance.subtract(valueOf(3)), ONE, ZERO);
		for (int i = 2; i >= 0; i--) {
			coefficients[i][2] = subFibo.get(i);
		}
		return coefficients;
	}

	public static List<BigInteger> fibList(BigInteger n, BigInteger firstFiboElement, BigInteger secondFiboElement) {

		BigInteger f1 = firstFiboElement;
		BigInteger f2 = secondFiboElement;
		List<BigInteger> result = new ArrayList<>(3);

		if (n.equals(BigInteger.ZERO)) {
			result.add(BigInteger.ZERO);
			return result;
		} else if (n.equals(BigInteger.ONE)) {
			result.add(BigInteger.ZERO);
			result.add(BigInteger.ONE);
			return result;
		}
		result.add(null);
		result.add(null);
		result.add(null);
		n = n.add(BigInteger.valueOf(2L));
		BigInteger f3 = null;
		if (n.compareTo(BigInteger.ZERO) == 1) {
			for (int i = 2; i <= n.intValue(); i++) {
				f3 = f1.add(f2);
				f1 = f2;
				f2 = f3;
				if (i >= n.intValue() - 2) {
					// result.add(f3);
					result.set(n.intValue() - i, f3);
				}
			}
			return result;
		}
		return result;
	}

	public static BigInteger fib(BigInteger n) {
		BigInteger f1 = BigInteger.valueOf(0L);
		BigInteger f2 = BigInteger.valueOf(1L);

		if (n.equals(ZERO)) {
			return f1;
		} else if (n.equals(ONE)) {
			return f2;
		}
		BigInteger f3 = null;
		if (n.compareTo(ZERO) == 1) {
			for (int i = 2; i <= n.intValue(); i++) {

				f3 = f1.add(f2);

				f1 = f2;
				f2 = f3;
			}
			return f3;
		} else {
			f3 = BigInteger.ZERO;
			for (int i = 2; i <= n.intValue() * (-1); i++) {

				f1 = f3.subtract(f2);
				f3 = f2;
				f2 = f1;
			}
		}
		return f1;
	}
}
