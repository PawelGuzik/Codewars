package detective;

import java.util.LinkedList;

public class SecretDetective {
	LinkedList<Character> finalList = new LinkedList<Character>();

	public String recoverSecret(char[][] triplets) {
		fillUnique(triplets);
		boolean wasAllGood = false;
		while (!wasAllGood) {
			wasAllGood = true;

			for (int i = 0; i < triplets.length; i++) {
				for (int j = 0; j < triplets[i].length; j++) {
					char isAfter = 0;
					char isBefore = 0;
					if (j == 2) {
						isBefore = triplets[i][j - 1];
					} else if (j == 1) {
						isBefore = triplets[i][j - 1];
						isAfter = triplets[i][j + 1];
					} else if (j == 0) {
						isAfter = triplets[i][j + 1];
					}
					if (checkAndReplace(triplets, i, j, isAfter, isBefore)) {
						j--;
						wasAllGood = false;

					}
				}
			}
		}
		StringBuilder strB = new StringBuilder("");
		for (char c : finalList) {
			strB.append(c);
		}

		strB.toString();
		System.out.println(strB.toString());
		return strB.toString();
	}

	public void fillUnique(char[][] triplets) {
		for (int i = 0; i < triplets.length; i++) {
			for (int j = 0; j < triplets[i].length; j++) {
				if (finalList.contains(triplets[i][j])) {
					finalList.add(triplets[i][j]);
				}
			}

		}
	}

	public boolean checkAndReplace(char[][] triplets, int i, int j, char isAfter, char isBefore) {
		if (!finalList.contains(triplets[i][j])) {
			if (finalList.contains(isBefore)) {
				finalList.add(finalList.indexOf(isBefore) + 1, triplets[i][j]);
			} else if (finalList.contains(isAfter)) {
				finalList.add(finalList.indexOf(isAfter), triplets[i][j]);
			} else {
				finalList.add(triplets[i][j]);
			}
		} else if (isOnWrongPos(isBefore, triplets[i][j], isAfter)
				&& (finalList.contains(isAfter) || finalList.contains(isBefore))) {
			finalList.remove(finalList.indexOf(triplets[i][j]));
			return true;
		}
		return false;
	}

	public boolean isOnWrongPos(char isBefore, char c, char isAfter) {
		if ((finalList.indexOf(isBefore) < finalList.indexOf(c) && finalList.indexOf(isAfter) > finalList.indexOf(c))
				|| (isBefore == 0 && finalList.indexOf(isAfter) > finalList.indexOf(c))
				|| isAfter == 0 && finalList.indexOf(isBefore) < finalList.indexOf(c)) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		char[][] triplets = { { 't', 'u', 'p' }, { 'w', 'h', 'i' }, { 't', 's', 'u' }, { 'a', 't', 's' },
				{ 'h', 'a', 'p' }, { 't', 'i', 's' }, { 'w', 'h', 's' } };
		new SecretDetective().recoverSecret(triplets);

	}

}
