package squareIntoSquares;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Decompose {
	static Map<Long, Long> finalMap = new TreeMap<>();

	public static String decompose(long n) {
		finalMap.clear();

		long nSquare = n * n;
		long nSmallSquare = (n - 1) * (n - 1);

		finalMap.put(n - 1, nSquare - nSmallSquare);
		
		collectSquares(nSquare - nSmallSquare);
		
		StringBuilder strBuilder = new StringBuilder();
		finalMap.forEach((key, value) -> {
			strBuilder.append(key != 0 ? key + " " : "");
		});
		String result = strBuilder.toString().trim();
		System.out.println(result);
		return result.length() == 0 ? null : result;

	}

	public static void collectSquares(long rest) {
		while (rest != 2) {
			long sqrt = (int) Math.sqrt((double) rest);
			rest = (long) (rest - Math.pow((double) sqrt, 2));

			if (finalMap.putIfAbsent(sqrt, rest) != null) {
				rest = findNextToDecompose();
			}

			if (rest == 0) {
				return;
			}

			if (rest == 2) {
				long sum = rest + sqrt * sqrt;

				if (Math.pow(sqrt, 2) > sum / 2) {

					finalMap.remove(sqrt);
					sqrt--;
					rest = sum - sqrt * sqrt;
					finalMap.put(sqrt, rest);

				} else {
					finalMap.remove(sqrt);
					rest = findNextToDecompose();
				}
			}
		}

	}

	public static Long findNextToDecompose() {
		List<Long> keysToRemove = new ArrayList<>();

		for (Map.Entry<Long, Long> entry : finalMap.entrySet()) {
			if (Math.pow(entry.getKey() - 1, 2) > (Math.pow(entry.getKey(), 2) + entry.getValue()) / 2) {
				keysToRemove.add(entry.getKey());
				break;
			} else {
				keysToRemove.add(entry.getKey());
			}
		}

		Long keyReplace = keysToRemove.get(keysToRemove.size() - 1);
		Long keyReplacePow2 = (long) Math.pow(keyReplace, 2);
		Long keyReplacePow2Small = (long) Math.pow(keyReplace - 1, 2);
		Long valReplace = (long) (finalMap.get(keyReplace) + keyReplacePow2 - keyReplacePow2Small);

		keysToRemove.forEach(key -> {
			finalMap.remove(key);
		});

		finalMap.put(keyReplace - 1, valReplace);

		if (finalMap.size() == 1 && keyReplacePow2Small <= (valReplace + keyReplacePow2Small) / 2) {
			finalMap.clear();
			valReplace = 0L;
		}
		return valReplace;
	}

	public static void main(String[] args) {
		decompose(8);

	}

}
