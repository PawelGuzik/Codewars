package insaneColouredTriangles;

public class Kata {

	private static int maxTriangle;
	private static int maxTriangleSizePower;

	public static char triangle(final String row) {
		return triangle100(row.toCharArray());
	}

	private static char triangle100(char[] chars) {
		int maxLength = chars.length;
		maxTriangleSizePower = (int) (Math.log10(maxLength - 1) / Math.log10(3)); // max power of 3 which is smaller
																					// than chars length
		maxTriangle = (int) (Math.pow(3, maxTriangleSizePower) + 1); // triangle which can be solved just by looking at
																		// the first and last letter

		if (maxLength == 1) {
			return chars[0];
		}

		int limit = maxLength - maxTriangle;
		char[] newArray = new char[limit + 1];

		for (int i = 0; i <= limit; i++) {
			char next = chars[maxTriangle + i - 1];
			if (chars[i] == 'B') {
				if (next == 'B') {
					newArray[i] = 'B';
				} else if (next == 'R') {
					newArray[i] = 'G';
				} else if (next == 'G') {
					newArray[i] = 'R';
				}
			} else if (chars[i] == 'R') {
				if (next == 'B') {
					newArray[i] = 'G';
				} else if (next == 'R') {
					newArray[i] = 'R';
				} else if (next == 'G') {
					newArray[i] = 'B';
				}
			} else {
				if (next == 'B') {
					newArray[i] = 'R';
				} else if (next == 'R') {
					newArray[i] = 'B';
				} else {
					newArray[i] = 'G';
				}
			}
		}

		if (maxTriangle == maxLength) {
			return newArray[0];
		}
		if (newArray.length > 50) {
			return triangle100(newArray);
		} else {
			return triangleRecursive2(1, newArray);
		}
	}

	private static char triangleRecursive2(int level, char[] shortTriangle) {
		int maxLength = shortTriangle.length;
		if (maxLength == 1) {
			return shortTriangle[0];
		}
		while (level < maxLength) {
			int limit = maxLength - level;
			for (int i = 0; i < limit; i++) {
				char next = shortTriangle[i + 1];
				if (shortTriangle[i] == 'B') {
					if (next == 'B') {
						shortTriangle[i] = 'B';
					} else if (next == 'R') {
						shortTriangle[i] = 'G';
					} else if (next == 'G') {
						shortTriangle[i] = 'R';
					}
				} else if (shortTriangle[i] == 'R') {
					if (next == 'B') {
						shortTriangle[i] = 'G';
					} else if (next == 'R') {
						shortTriangle[i] = 'R';
					} else if (next == 'G') {
						shortTriangle[i] = 'B';
					}
				} else {
					if (next == 'B') {
						shortTriangle[i] = 'R';
					} else if (next == 'R') {
						shortTriangle[i] = 'B';
					} else {
						shortTriangle[i] = 'G';
					}
				}
			}
			level++;
		}
		return shortTriangle[0];
	}

}