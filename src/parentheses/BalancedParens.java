package parentheses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BalancedParens {
	static List<String> parList = new ArrayList<String>(); 

	public static void main(String[] args) {
		balancedParens(0);
		Map<String, Integer> map1 = new HashMap<>();
		map1.put("q", 1);
		Map<String, Integer> map2 = new HashMap<>();
		map2.putAll(map1);
		
		map2.put("q", 2);
		
		map1.put("q", 10);
	}

	public static List<String> balancedParens(int n) {
		parList.clear();
		Map<String, Integer> avaliblePar = new HashMap<>();
		String myString = "";
		fillHashMap(avaliblePar, n);
		addNext(avaliblePar, myString);
		// your code here
		return parList;
	}

	public static void addNext(Map<String, Integer> myMap, String myString) {
		
		if (!isEmpty(myMap)) {
			if (containOpenParent(myMap)) {
				String myNewString = myString + "(";
				Map<String, Integer> myNewMap = new HashMap<String, Integer>();
				myNewMap.putAll(myMap);
						myNewMap.put("(", myMap.get("(") - 1);
				addNext(myNewMap, myNewString);
			}
			if (containCloseParent(myMap)&& canAddCloseParent(myString)) {
				String myNewString = myString + ")";
				Map<String, Integer> myNewMap = new HashMap<String, Integer>();
				myNewMap.putAll(myMap);
					myNewMap.put(")", myMap.get(")") - 1);
				addNext(myNewMap, myNewString);
			}
		}else {
			parList.add(myString);
		}
		
	}

	public static boolean canAddCloseParent(String currentString) {
		int openPar = 0;
		int closePar = 0;
		for (int i = 0; i < currentString.length(); i++) {
			if (currentString.charAt(i) == '(') {
				openPar++;
			} else {
				closePar++;
			}
		}
		return openPar > closePar;
	}

	public static boolean isEmpty(Map<String, Integer> parentheses) {
		return parentheses.get("(") == 0 && parentheses.get(")") == 0;
	}

	public static boolean containOpenParent(Map<String, Integer> myMap) {
		return myMap.get("(") > 0;
	}

	public static boolean containCloseParent(Map<String, Integer> myMap) {
		return myMap.get(")") > 0;
	}

	public static void fillHashMap(Map<String, Integer> myMap, int number) {
		myMap.put("(", number);
		myMap.put(")", number);

	}
}
