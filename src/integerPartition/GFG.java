package integerPartition;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class GFG {
	static Set<Integer> set = new HashSet<>();

	
	static void computeProd(int p[], int n) {
		int prod = 1;
		for (int i = 0; i < n; i++) {
			prod = prod * p[i];
		}
		set.add(prod);
		
	}

	
	static void printAllUniqueParts(int n) {
		int[] p = new int[(int) n]; 
		int k = 0; 
		p[k] = n; 

		
		while (true) {
			
			computeProd(p, k + 1);
	
			int rem_val = 0;
			while (k >= 0 && p[k] == 1) {
				rem_val += p[k];
				k--;
			}
		
			if (k < 0)
				return;
			
			p[k]--;
			rem_val++;
			
			while (rem_val > p[k]) {
				p[k + 1] = p[k];
				rem_val = rem_val - p[k];
				k++;
			}
		
			p[k + 1] = rem_val;
			k++;
		}
	}


	public static void main(String[] args) {

		
		System.out.println(part(11));
		System.out.println(part(12));
		System.out.println(part(10));
		System.out.println(part(2));
	}

	public static String part(int n) {
		printAllUniqueParts(n);
		List<Integer> myList = new ArrayList<Integer>(set);
		myList.sort(Comparator.naturalOrder());
		int range = (int) myList.get(myList.size() - 1) - myList.get(0);
		long sum = 0;
		for (int integer : myList) {
			sum = sum + integer;
		}
		double average = (double) sum / myList.size();
		float median = myList.size() % 2 == 0
				? (float) (myList.get((myList.size() / 2) - 1) + myList.get((myList.size() / 2))) / 2 :(float) myList.get((myList.size() / 2));
				DecimalFormat df = new DecimalFormat("###.00");
						
						System.out.println(myList);
						set.clear();
		return "Range: " + range + " Average: " + df.format(average) + " Median: " + df.format(median) ;
		
	}
} 
