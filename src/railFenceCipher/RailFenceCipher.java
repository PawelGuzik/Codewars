package railFenceCipher;

import java.util.ArrayList;
import java.util.List;

public class RailFenceCipher {
	static String encode(String s, int n) {
		if(s==null || s.length()==0) { return "";}
		List<List<Character>> finalList = new ArrayList<List<Character>>();
		for (int i = 0; i < n; i++) {
			List<Character> line = new ArrayList<>();
			finalList.add(line);
		}
		int x = 0;
		int i = 0;
		int inc = 0;
		while (x < s.length()) {
			finalList.get(i).add(s.charAt(x));

			if (i == n - 1) {inc = -1;}
			if (i == 0) {inc = 1;}
			i = i + inc;
			x++;
		}
		StringBuilder stb = new StringBuilder();
		for (List<Character> list : finalList) {
			for (Character list2 : list) {
				stb.append(list2);
			}
		}
		return stb.toString();
	}

	static String decode(String s, int n) {
		if(s==null || s.length()==0) { return "";}
		
		int lineLength = s.length();
		int x = 0;
		int k = 0;
		int inc = 0;
		int[] lengths = new int[n];
		
		while (x < lineLength) {
			lengths[k]++;

			if (k == n - 1) { inc = -1;}
			if (k == 0) { inc = 1;}
			
			k = k + inc;
			x++;
		}

		List<String> finalList = new ArrayList<>();
		for (int i = 0; i < n; i++) {

			String line = s.substring(0, lengths[i]);
			s = s.substring(lengths[i]);
			finalList.add(line);
		}
		x=0;
		k=0;
		inc=0;
		StringBuilder strb = new StringBuilder();
		while(x<lineLength) {
			String line = finalList.get(k);
			strb.append(line.charAt(0));
			finalList.set(k, line.substring(1));
			if (k == n - 1) {inc = -1;}
			if (k == 0) {inc = 1;}
			k = k + inc;
			x++;
		}
		return strb.toString();
	}
}