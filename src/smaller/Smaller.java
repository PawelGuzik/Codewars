package smaller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class Smaller {

	public static short countSmaller(short value, HashMap<Short, Short> unsorted) {
		short count = 0;
		if (unsorted == null || unsorted.size() == 0) {
			return 0;
		}
		for (Entry<Short, Short> entry : unsorted.entrySet()) {
			if (value > entry.getKey()) {
				count = (short) (count + entry.getValue());
			}
		}

		return count;
	}

	public static int[] smaller(int[] unsorted) {
		IntSummaryStatistics stat = Arrays.stream(unsorted).summaryStatistics();
		Integer max = stat.getMax();
		Integer min = stat.getMin();
		int gran = (max - min) / 16;
		if (gran == 0) {
			gran++;
		}

		int g1 = max - gran;
		int g2 = max - 2 * gran;
		int g3 = max - 3 * gran;
		int g4 = max - 4 * gran;
		int g5 = max - 5 * gran;
		int g6 = max - 6 * gran;
		int g7 = max - 7 * gran;
		int g8 = max - 8 * gran;
		int g9 = max - 9 * gran;
		int g10 = max - 10 * gran;
		int g11 = max - 11 * gran;
		int g12 = max - 12 * gran;
		int g13 = max - 13 * gran;
		int g14 = max - 14 * gran;
		int g15 = max - 15 * gran;

		
		Bucket[] buckets = new Bucket[17];
		for(int i =1; i<buckets.length; i++) {
			buckets[i] = new Bucket();
		}
	
		int[] result = new int[unsorted.length];

		for (int i = unsorted.length - 1; i >= 0; i--) {
			short current = (short) unsorted[i];
			short localSmaller;
			if (current > g8) {
				if (current > g7) {
					if (current > g6) {
						if (current > g5) {
							if (current > g4) {
								if (current > g3) {
									if (current > g2) {
										if (current > g1) {
											localSmaller = buckets[1].countSmaller(current);
											buckets[1].add(current);
											// bucket[1].add(current);
											result[i] = localSmaller + buckets[2].size() + buckets[3].size() + buckets[4].size()
													+ buckets[5].size() + buckets[6].size() + buckets[7].size() + buckets[8].size()
													+ buckets[9].size() + buckets[10].size() + buckets[11].size() + buckets[12].size()
													+ buckets[13].size() + buckets[14].size() + buckets[15].size() + buckets[16].size();
										} else {
											localSmaller = buckets[2].countSmaller(current);
											buckets[2].add(current);
											result[i] = localSmaller + buckets[3].size() + buckets[4].size() + buckets[5].size()
													+ buckets[6].size() + buckets[7].size() + buckets[8].size() + buckets[9].size()
													+ buckets[10].size() + buckets[11].size() + buckets[12].size() + buckets[13].size()
													+ buckets[14].size() + buckets[15].size() + buckets[16].size();
										}
									} else {
										localSmaller = buckets[3].countSmaller(current);
										buckets[3].add(current);
										result[i] = localSmaller + buckets[4].size() + buckets[5].size() + buckets[6].size() + buckets[7].size()
												+ buckets[8].size() + buckets[9].size() + buckets[10].size() + buckets[11].size() + buckets[12].size()
												+ buckets[13].size() + buckets[14].size() + buckets[15].size() + buckets[16].size();
									}
								} else {
									localSmaller = buckets[4].countSmaller(current);
									buckets[4].add(current);
									result[i] = localSmaller + buckets[5].size() + buckets[6].size() + buckets[7].size() + buckets[8].size()
											+ buckets[9].size() + buckets[10].size() + buckets[11].size() + buckets[12].size() + buckets[13].size()
											+ buckets[14].size() + buckets[15].size() + buckets[16].size();

								}
							} else {
								localSmaller = buckets[5].countSmaller(current);
								buckets[5].add(current);
								result[i] = localSmaller + buckets[6].size() + buckets[7].size() + buckets[8].size() + buckets[9].size()
										+ buckets[10].size() + buckets[11].size() + buckets[12].size() + buckets[13].size() + buckets[14].size()
										+ buckets[15].size() + buckets[16].size();
							}
						} else {
							localSmaller = buckets[6].countSmaller(current);
							buckets[6].add(current);
							result[i] = localSmaller + buckets[7].size() + buckets[8].size() + buckets[9].size() + buckets[10].size()
									+ buckets[11].size() + buckets[12].size() + buckets[13].size() + buckets[14].size() + buckets[15].size()
									+ buckets[16].size();
						}
					} else {
						localSmaller = buckets[7].countSmaller(current);
						buckets[7].add(current);
						result[i] = localSmaller + buckets[8].size() + buckets[9].size() + buckets[10].size() + buckets[11].size()
								+ buckets[12].size() + buckets[13].size() + buckets[14].size() + buckets[15].size() + buckets[16].size();
					}
				} else {
					localSmaller = buckets[8].countSmaller(current);
					buckets[8].add(current);
					result[i] = localSmaller + buckets[9].size() + buckets[10].size() + buckets[11].size() + buckets[12].size() + buckets[13].size()
							+ buckets[14].size() + buckets[15].size() + buckets[16].size();
				}
			} else if (current > g9) {
				localSmaller = buckets[9].countSmaller(current);
				buckets[9].add(current);
				result[i] = localSmaller + buckets[10].size() + buckets[11].size() + buckets[12].size() + buckets[13].size() + buckets[14].size()
						+ buckets[15].size() + buckets[16].size();
			} else if (current > g10) {
				localSmaller = buckets[10].countSmaller(current);
				buckets[10].add(current);
				result[i] = localSmaller + buckets[11].size() + buckets[12].size() + buckets[13].size() + buckets[14].size() + buckets[15].size()
						+ buckets[16].size();
			} else if (current > g11) {
				localSmaller = buckets[11].countSmaller(current);
				buckets[11].add(current);
				result[i] = localSmaller + buckets[12].size() + buckets[13].size() + buckets[14].size() + buckets[15].size() + buckets[16].size();
			} else if (current > g12) {
				localSmaller = buckets[12].countSmaller(current);
				buckets[12].add(current);
				result[i] = localSmaller + buckets[13].size() + buckets[14].size() + buckets[15].size() + buckets[16].size();
			} else if (current > g13) {
				localSmaller = buckets[13].countSmaller(current);
				buckets[13].add(current);
				result[i] = localSmaller + buckets[14].size() + buckets[15].size() + buckets[16].size();
			} else if (current > g14) {
				localSmaller = buckets[14].countSmaller(current);
				buckets[14].add(current);
				result[i] = localSmaller + buckets[15].size() + buckets[16].size();
			} else if (current > g15) {
				localSmaller = buckets[15].countSmaller(current);
				buckets[15].add(current);
				result[i] = localSmaller + buckets[16].size();
			} else {
				localSmaller = buckets[16].countSmaller(current);
				buckets[16].add(current);
				result[i] = localSmaller;
			}
		}
		return result;
	}

	private static class Bucket {
		
		short size = 0;
		int[] bucket2Positive = new int[5000];
		int[] bucket2Negative = new int[5000];

		public Bucket() {		
		
		}

		private void add(short key) {
			if (key >= 0) {
				bucket2Positive[key]++;
			} else {
				bucket2Negative[key * (-1)]++;
			}
			size++;
		}

		private short size() {
			return size;
		}

		private short countSmaller(short value) {
			short count = 0;
			if (bucket2Positive == null && bucket2Negative == null) {
				return 0;
			}
			if (bucket2Positive.length == 0 && bucket2Negative.length == 0) {
				return 0;
			}
			if (value >= 0) {
				for (int i = value - 1; i >= 0; i--) {
					if (bucket2Positive[i] > 0) {
						count = (short) (count + bucket2Positive[i]);
					}
				}
			} else {
				for (int i = value * (-1) - 1; i > 0; i--) {
					if (bucket2Negative[i] > 0) {
						count = (short) (count + bucket2Negative[i]);
					}
				}
				for (int i = 0; i < bucket2Positive.length; i++) {
					if (bucket2Positive[i] > 0) {
						count = (short) (count + bucket2Positive[i]);
					}
				}
			}
			return count;
		}
	}
}