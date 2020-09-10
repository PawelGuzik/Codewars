package roboScript2a;

import java.util.HashMap;
import java.util.Map;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoboScript {
	private static char currentDir;
	private static int currentXPos;
	private static int currentYPos;
	static int maxX;
	static int maxY;
	static int minX;
	static int minY;
	static String result;
	static Map<String, String> patterns;
	static RuntimeException ex = new RuntimeException();

	public RoboScript() {
		currentDir = 'R';
		currentXPos = 6000;
		currentYPos = 6000;
		maxX = 6000;
		minX = 6000;
		maxY = 6000;
		minY = 6000;
		result = "";
		tab1 = new boolean[12000][12000];
		tab1[6000][6000] = true;
		patterns = new HashMap<String, String>();

	}

	public static String execute(String code) {

		new RoboScript();
		StringBuilder strB = new StringBuilder(code);

		findPatternsDefinitions(strB);

		if (strB.length() == 0) {
			return "*";
		}

		removeBrackets(strB);

		String step = strB.substring(0, 1);
		String lastStep = "";
		Pattern stepPatt = Pattern.compile("[FLR]{1}[0-9]*|[FRL]{2,}\\d");
		Matcher stePMatcher = stepPatt.matcher(step);
		;

		for (int u = 1; u <= strB.length(); u++) {
			while ((stePMatcher.matches()) && (u <= strB.length() + 1)) {
				lastStep = step;
				if (u < strB.length() - 1) {
					step += strB.substring(u, u + 1);
					stePMatcher = stepPatt.matcher(step);
				} else if (u == strB.length() - 1) {
					step += strB.substring(u);
					stePMatcher = stepPatt.matcher(step);
				}
				u++;
			}
			u--;
			go(lastStep);
			if (!(u > strB.length() - 1)) {
				step = strB.substring(u, u + 1);
				stePMatcher = stepPatt.matcher(step);
			}
		}
		return printMap();
	}

	public static void findPatternsDefinitions(StringBuilder stbCode) {
		Pattern pattern = Pattern.compile("[p\\d+][\\(]?[PFLR\\d]+[\\)]?\\d*[^p]q");
		Matcher matcher = pattern.matcher(stbCode);

		while (matcher.find()) {

			Pattern patternNumber = Pattern.compile("p\\d+");
			Matcher matcherNumb;

			matcherNumb = patternNumber.matcher(stbCode);
			matcherNumb.find();

			String patN = stbCode.substring(matcherNumb.start(), matcherNumb.end());
			String patDef = stbCode.substring(matcherNumb.end(), matcher.end() - 1);

			if (patterns.putIfAbsent(patN, patDef.toString()) != null) {
				throw ex;
			}

			int start = stbCode.indexOf(patN);
			int end = start + patN.length() + patDef.length() + 1;

			stbCode = stbCode.replace(start, end, "");
			matcher = pattern.matcher(stbCode);

		}
		replacePatternsNamesWithPatternsDef(stbCode);
	}

	public static void replacePatternsNamesWithPatternsDef(StringBuilder code) {

		Pattern pattern = Pattern.compile("(P[\\d]+)");

		hasInfiniteRecursionInCode(code, pattern);

		solveNestedPatterns(null, pattern);

		replacePatternNameWithProperDefinition(code);

	}

	public static void replacePatternNameWithProperDefinition(StringBuilder code) {
		Pattern pattern = Pattern.compile("P\\d+");
		Matcher matcher = pattern.matcher(code);

		while (matcher.find()) {

			String ss = matcher.group() + "\\D+?";
			String val = patterns.get(matcher.group().toLowerCase());

			if (val != null) {
				if (ss.length() > matcher.group().length()) {
					ss = matcher.group();
				}
				code.replace(code.indexOf(ss), code.indexOf(ss) + ss.length(), val);
			} else {
				throw ex;
			}
			matcher = pattern.matcher(code);
		}
	}

	private static void solveNestedPatterns(Map.Entry<String, String> nested, Pattern p1) {

		nested = hasNestedPatternDef();

		for (Map.Entry<String, String> entry : patterns.entrySet()) {
			if (nested != null) {

				Pattern pattern = Pattern.compile(entry.getKey().toUpperCase());
				Matcher matcher = pattern.matcher(nested.getValue() + "");

				if (matcher.find()) {

					Matcher m1 = p1.matcher(nested.getValue());
					if (m1.find(0)) {

						String val = nested.getValue().replace(entry.getKey().toUpperCase(), entry.getValue());
						patterns.replace(nested.getKey(), nested.getValue(), val);
					}
				}
				nested = hasNestedPatternDef();
			}
		}
	}

	private static void hasInfiniteRecursion(String key, String value, String recLine, Pattern pattern) {

		recLine += key.toUpperCase();
		Matcher matcher = pattern.matcher(value + "");

		while (matcher.find()) {

			matcher.groupCount();
			matcher.group(0);

			for (int i = 0; i < matcher.groupCount(); i++) {

				String foundP = matcher.group(i);
				Pattern pattern1 = Pattern.compile(foundP);
				Matcher matcher1 = pattern1.matcher(recLine);

				if (matcher1.find()) {
					throw ex;
				} else {
					hasInfiniteRecursion(foundP, patterns.get(foundP.toLowerCase()), recLine, pattern);
				}
			}
		}
	}

	private static boolean hasInfiniteRecursionInCode(StringBuilder code, Pattern pattern) {

		Matcher matcher = pattern.matcher(code);
		while (matcher.find()) {
			String foundP = matcher.group().toLowerCase();
			hasInfiniteRecursion(foundP, patterns.get(foundP), foundP, pattern);
		}
		return false;
	}

	private static Map.Entry<String, String> hasNestedPatternDef() {
		for (Map.Entry<String, String> entry : patterns.entrySet()) {
			if (hasNestedPat(entry.getValue())) {
				return entry;
			}
		}
		return null;
	}

	private static boolean hasNestedPat(String code) {
		return code.contains("P");
	}

	private static String removeBrackets(StringBuilder stbCode) {

		while (stbCode.indexOf("(") > -1) {
			int lastOpenBracket = stbCode.lastIndexOf("(");
			int firstCloseBracket = stbCode.indexOf(")", lastOpenBracket);
			int k = 0;

			String repetitions = "";
			Pattern p1 = Pattern.compile("\\d.*");
			Matcher m1 = p1.matcher(stbCode.substring(firstCloseBracket + k + 1));

			while (((firstCloseBracket) <= stbCode.length() - 1) && m1.matches()) {

				repetitions += stbCode.substring(firstCloseBracket + k + 1, firstCloseBracket + k + 2);
				k++;
				m1 = p1.matcher(stbCode.substring(firstCloseBracket + k + 1));
			}

			int numberOfRep = Integer.parseInt(repetitions);
			String toRepeat = stbCode.substring(lastOpenBracket + 1, firstCloseBracket);
			stbCode.replace(lastOpenBracket, firstCloseBracket + repetitions.length() + 1,
					toRepeat.repeat(numberOfRep));
		}
		return stbCode.toString();
	}

	public static void go(String command) {

		int commandLength = command.length();
		char charCommand = command.charAt(0);
		if (commandLength == 1) {
			if (command.equalsIgnoreCase("F")) {
				move(1);
			} else {
				changeDirection(charCommand);
			}
		} else {
			int temp = Integer.parseInt(command.substring(1));

			if (charCommand == 'F') {
				move(temp);
			} else {
				temp = temp % 4;
				for (int i = 0; i < temp; i++) {
					changeDirection(charCommand);
				}
			}
		}
	}

	static boolean[][] tab1 = new boolean[12000][12000];

	private static void move(int distance) {

		if (currentDir == 'R') {
			for (int i = 0; i < distance; i++) {
				currentYPos++;

				tab1[currentXPos][currentYPos] = true;
			}
			if (currentYPos > maxY) {
				maxY = currentYPos;
			}
		} else if (currentDir == 'L') {
			for (int i = 0; i < distance; i++) {
				currentYPos--;
				tab1[currentXPos][currentYPos] = true;
			}
			if (currentYPos < minY) {
				minY = currentYPos;
			}
		} else if (currentDir == 'U') {
			for (int i = 0; i < distance; i++) {
				currentXPos++;
				tab1[currentXPos][currentYPos] = true;
			}
			if (currentXPos > maxX) {
				maxX = currentXPos;
			}
		} else if (currentDir == 'D') {
			for (int i = 0; i < distance; i++) {
				currentXPos--;
				tab1[currentXPos][currentYPos] = true;
			}
			if (currentXPos < minX) {
				minX = currentXPos;
			}
		}
	}

	public static String printMap() {

		if (maxX == 6000 && maxY == 6000 && minX == 6000 && minY == 6000) {
			return "*";
		}
		int size = ((maxX - minX) * (maxY - minY));
		StringBuffer strBRes = new StringBuffer(size);

		for (int k = maxX; k >= minX; k--) {
			int countSpaces = 0;
			int countStars = 0;
			for (int i = minY; i <= maxY; i++) {

				if (tab1[k][i]) {
					if (countSpaces == 0) {
						countStars++;
					} else {
						strBRes.append(" ".repeat(countSpaces));
						countSpaces = 0;
						countStars++;
					}
				} else {
					if (countStars == 0) {
						countSpaces++;
					} else {
						strBRes.append("*".repeat(countStars));
						countStars = 0;
						countSpaces++;
					}
				}
			}
			if (countSpaces > 0) {
				strBRes.append(" ".repeat(countSpaces));
			} else {
				strBRes.append("*".repeat(countStars));
			}
			if (!(maxX == minX || k == minX)) {
				strBRes.append("\r\n");
			}
		}
		return strBRes.toString();
	}

	private static void changeDirection(char rotation) {
		if (currentDir == 'R') {
			if (rotation == 'L') {
				currentDir = 'U';
			} else {
				currentDir = 'D';
			}
		} else if (currentDir == 'L') {
			if (rotation == 'L') {
				currentDir = 'D';
			} else {
				currentDir = 'U';
			}
		} else if (currentDir == 'U') {
			if (rotation == 'L') {
				currentDir = 'L';
			} else {
				currentDir = 'R';
			}
		} else {
			if (rotation == 'L') {
				currentDir = 'R';
			} else {
				currentDir = 'L';
			}
		}
	}
}
