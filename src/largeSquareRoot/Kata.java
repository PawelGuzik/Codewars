package largeSquareRoot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.stream.IntStream;

public class Kata {

	public static String integerSquareRoot(String n) {
		System.out.println("n: " + n);
		BitSet resultAsBitSet = bitSetSquareRoot((squareChunk(n)));
		String finalResult = getResultAsString(resultAsBitSet);
		return finalResult;
	}
	
	protected static BitSet bitSetSquareRoot(BitSet[] chunks) {
		BitSet tenAsBs = convertToBitSet(10);
		BitSet hundredAsBs  = convertToBitSet(100);
		BitSet result = BitSet.valueOf(new long[] { (long) Math.sqrt(convertToLong(chunks[0])) });
		BitSet sub = multiplyBitSets(result, result);
		if (chunks.length == 1) {
			return result;
		}

		BitSet next = addBitSets(
				multiplyBitSets((subtractBitSet(chunks[0], sub)),hundredAsBs),
				chunks[1]);
		List<BitSet> intermediateResults = new ArrayList<>(2);
		for (int i = 1; i < chunks.length; i++) {
			intermediateResults = findNextSub(result, next);
			sub = intermediateResults.get(1);
			if (i < chunks.length - 1) {
				if (sub.length() != 0) {
					next = addBitSets(multiplyBitSets((subtractBitSet(next, sub)), hundredAsBs),
							chunks[i + 1]);
					result = addBitSets(multiplyBitSets(result, tenAsBs), intermediateResults.get(0));
				} else {
					next = addBitSets(multiplyBitSets(next, hundredAsBs), chunks[i + 1]);
					;
					result = addBitSets(multiplyBitSets(result, tenAsBs), intermediateResults.get(0));
				}
			} else {

				result = addBitSets(multiplyBitSets(result, tenAsBs), intermediateResults.get(0));
				return result;
			}
		}
		return result;
	}

	private static List<BitSet> findNextSub(BitSet currentrResult, BitSet border) {
		currentrResult = multiplyBitSets(currentrResult, convertToBitSet(20));
		List<BitSet> result = new ArrayList<>();
		for (int i = 1; i <= 10; i++) {
			if (compare(multiplyBitSets((addBitSets(currentrResult, convertToBitSet(i))), convertToBitSet(i)),
					border) < 0) {
				result.add(convertToBitSet(i - 1));
				result.add(multiplyBitSets(addBitSets(currentrResult, result.get(0)), result.get(0)));
				return result;
			}
		}
		return result;
	}


	public static long[] getLongParts(BitSet bs) {

		int partsCount = (-1) * Math.floorDiv((-1) * bs.length(), 62);
		long[] parts = new long[partsCount];
		for (int i = 0; i < partsCount - 1; i++) {
			parts[i] = Long.parseLong(toBinaryString(bs.get(i * 62, i * 62 + 62)), 2);
		}
		partsCount--;
		parts[partsCount] = Long.parseLong(toBinaryString(bs.get(partsCount * 62, partsCount * 62 + 62)), 2);
		return parts;
	}

	public static int[] calculateResultFromParts(BitSet bs) {
		long pow62 = 4611686018427387904L;
		int[] pow62Decimal = longToIntTable(pow62);
		long[] parts = getLongParts(bs);
		int[] result = new int[101];
		int[] tempResult = new int[101];
		for (int i = 0; i < parts.length; i++) {
			tempResult = addBigNumbers(result,
					multiplyBigNumbers(longToIntTable(parts[i]), powerBiGNumber(pow62Decimal, i)));
			result = Arrays.copyOf(tempResult, tempResult.length);
		}
		return result;
	}

	public static String getResultAsString(BitSet bs) {
		int[] resFinal = calculateResultFromParts(bs);
		IntStream is = Arrays.stream(resFinal);
		String result = is.collect(StringBuilder::new, (sb, i) -> sb.append(i), StringBuilder::append).reverse()
				.toString();
		result = result.replaceFirst("^0+(?!$)", "");
		return result;
	}

	public static int[] powerBiGNumber(int[] number, int power) {
		int[] result = new int[1];
		if (power == 0) {
			result[0] = 1;
			return result;
		} else if (power == 1) {
			return number;
		}
		int[] tempResult = Arrays.copyOf(number, number.length);
		for (int i = 1; i < power; i++) {
			result = multiplyBigNumbers(tempResult, number);
			tempResult = Arrays.copyOf(result, result.length);
		}
		return result;

	}

	public static int[] multiplyBigNumbers(int[] l, int[] multiplier) {

		int[] result = new int[l.length + multiplier.length];

		int carry = 0;
		int[] tempResult = new int[result.length];
		for (int k = 0; k < multiplier.length; k++) {
			for (int i = 0; i < l.length; i++) {
				int tmp = (l[i] * multiplier[k]) + carry;
				tempResult[i] = (tmp) % 10;
				carry = tmp / 10;
			}
			tempResult[l.length] = carry;
			carry = 0;
			int carry2 = 0;
			for (int w = 0; w + k < tempResult.length; w++) {
				int tmp2 = result[k + w] + tempResult[w] + carry2;
				result[k + w] = tmp2 % 10;
				carry2 = tmp2 / 10;
			}
		}
		return result;
	}

	// a must be longer or equal to b
	public static int[] addBigNumbers(int[] a, int[] b) {
		int carry = 0;
		int temp = 0;

		int[] result = new int[a.length + b.length];
		for (int i = 0; i < a.length; i++) {
			if (i < b.length) {
				temp = a[i] + b[i] + carry;
				result[i] = temp % 10;
				carry = temp / 10;
			} else {
				temp = a[i] + carry;
				result[i] = temp % 10;
				carry = temp / 10;
			}
		}
		result[a.length] = carry;
		return result;
	}

	public static String toBinaryString(BitSet bs) {
		StringBuilder sb = new StringBuilder(bs.length());
		for (int i = bs.length() - 1; i >= 0; i--)
			sb.append(bs.get(i) ? 1 : 0);
		return sb.toString();
	}

	private static BitSet[] squareChunk(String square) {
		BitSet[] chunks = new BitSet[(square.length() + 1) / 2];
		if (square.length() == 1) {
			chunks[0] = convertToBitSet(Long.valueOf(square));// Short.valueOf(square);
			return chunks;
		}
		int index;
		if (square.length() % 2 != 0) {
			chunks[0] = convertToBitSet(Long.valueOf(square.substring(0, 1)));//Short.valueOf(square.substring(0, 1));
			index = 1;
			for (int i = 1; i < chunks.length; i++) {
				chunks[i] = convertToBitSet(Long.valueOf(square.substring(index, index+2)));//Short.valueOf(square.substring(index, index + 2));
				index = index + 2;
			}
		} else {
			index = 0;
			for (int i = 0; i < chunks.length; i++) {
				chunks[i] = convertToBitSet(Long.valueOf(square.substring(index, index+2))); //Short.valueOf(square.substring(index, index + 2));
				index = index + 2;
			}
		}
		return chunks;
	}

	public static BitSet logicalLeftShift(BitSet bs) {
		BitSet result = new BitSet(bs.length() + 1);
		for (int i = 0; i < bs.length(); i++) {
			result.set(bs.nextSetBit(i) + 1);
		}
		return result;
	}

	protected static BitSet multiplyBitSets(BitSet a, BitSet b) {

		BitSet result = new BitSet();
		for (int i = 0; i < b.length(); i++) {
			if (b.get(i)) {
				result = addBitSets(result, a);
			}
			a = logicalLeftShift(a);
		}
		return result;
	}

	// a must be bigger than b
	protected static BitSet subtractBitSet(BitSet a, BitSet b) {
		b.flip(0, a.length());
		b = addBitSets(b, convertToBitSet(1L));
		BitSet result = addBitSets(a, b);
		result = result.get(0, result.length() - 1);
		return result;
	}

	protected static BitSet addBitSets(BitSet a, BitSet b) {

		int i = 0, carry = 0;
		BitSet sum = new BitSet();

		while (a.length() >= i || b.length() >= i) {

			if ((a.get(i) && b.get(i) == false && carry == 0) || (a.get(i) == false && b.get(i) && carry == 0)
					|| (a.get(i) == false && b.get(i) == false && carry == 1)) {
				sum.set(i);
				carry = 0;
			} else if ((a.get(i) && b.get(i) && carry == 0) || (a.get(i) && carry == 1 && b.get(i) == false)
					|| (a.get(i) == false && b.get(i) && carry == 1)) {
				carry = 1;

			} else if ((a.get(i) && b.get(i) && carry == 1)) {
				sum.set(i);
			}
			i++;
		}

		return sum;
	}

	public static BitSet power(long aa, long b) {
		BitSet res = convertToBitSet(1L);
		BitSet a = convertToBitSet(aa);

		while (b > 0) {
			if ((b & 1) == 0) {
				a = multiplyBitSets(a, a);
				b >>>= 1;
			} else {
				res = multiplyBitSets(res, a);
				b--;
			}
		}
		return res;
	}

	
	public static int compare(BitSet a, BitSet b) {
		if (a.equals(b))
			return 0;
		BitSet xor = (BitSet) a.clone();
		xor.xor(b);
		int firstDiff = xor.length() - 1;
		if (firstDiff == -1)
			return 0;

		return b.get(firstDiff) ? 1 : -1;
	}

	// Conversions
	public static int[] longToIntTable(Long l) {

		String longAsString = l.toString();
		int[] digits = new int[longAsString.length()];
		for (int i = 0; i < longAsString.length(); i++) {
			digits[i] = (int) (l % 10);
			l = l / 10;
		}
		return digits;
	}

	public static BitSet convertToBitSet(long value) {
		BitSet bits = new BitSet();
		int index = 0;
		while (value != 0L) {
			if (value % 2L != 0) {
				bits.set(index);
			}
			++index;
			value = value >>> 1;
		}
		return bits;
	}

	public static long convertToLong(BitSet b) {
		return Long.valueOf(toBinaryString(b), 2);
	}

}
