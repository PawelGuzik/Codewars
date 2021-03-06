package interpreter2;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {
	Map<String, Double> id_Value;

	public Interpreter() {
		id_Value = new HashMap<String, Double>();
	}

	public Double input(String input) {

		Deque<String> tokens = tokenize(input);
		return readInput(tokens);
	}

	private Double readInput(Deque<String> tokens) throws IllegalArgumentException {
		if (tokens.size() == 1) {
			if (!isNumeric(tokens.peekFirst())) {
				if (id_Value.containsKey(tokens.peekFirst())) {
					return id_Value.get(tokens.peekFirst());
				} else {
					throw new IllegalArgumentException();
				}
			} else {
				return Double.valueOf(tokens.pollFirst());
			}
		} else if (tokens.contains("=")) {
			String key = tokens.pollFirst();
			tokens.pollFirst();
			Double value = evaluateParentheses(tokens);
			id_Value.put(key, value);
			return value;
		} else if (tokens.size() == 0) {
			return null;
		} else if (!tokens.contains("+") && !tokens.contains("-") && !tokens.contains("*") && !tokens.contains("/")
				&& !tokens.contains("%")) {
			throw new IllegalArgumentException();
		} else {
			return evaluateParentheses(tokens);
		}
	}

	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	public boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}

	private static Deque<String> tokenize(String input) {
		Deque<String> tokens = new LinkedList<>();
		Pattern pattern = Pattern.compile("=>|[-+*/%=\\(\\)]|[A-Za-z_][A-Za-z0-9_]*|[0-9]*(\\.?[0-9]+)");
		Matcher m = pattern.matcher(input);
		while (m.find()) {
			tokens.add(m.group());
		}
		return tokens;
	}

	private double evaluateParentheses(Deque<String> inputTokens) {
		// If the input has open parentheses then extract it and create new shortDeque
		// which can be evaluated as simple input (no parentheses) or repeat extracting
		// shortDeque
		if (inputTokens.contains("(")) {

			LinkedList<String> l = (LinkedList<String>) inputTokens;
			int closeInd = l.indexOf(")");
			List<String> subList = l.subList(0, closeInd);
			int startInd = subList.lastIndexOf("(");
			subList = subList.subList(startInd + 1, subList.size());
			Deque<String> shotrDeque = new LinkedList<String>(subList);

			for (int i = 0; i <= (closeInd - startInd); i++) {
				l.remove(startInd);
			}
			// Replace shortDeque with it's result
			double d = evaluate(shotrDeque, 1);
			l.add(startInd, Double.toString(d));
			// Repeat extracting parentheses
			if (l.contains("(")) {
				return evaluateParentheses(l);
			} else {
				return evaluate(l, 1);
			}
		} else {
			return evaluate(inputTokens, 1);
		}

	}

	// level represents complexity of the expression 0 - multiplication, division,
	// addition and subtraction may occur in input, 1 - only addition and
	// subtraction may occur in input

	private double evaluate(Deque<String> inputTokens, int level) {
		double result = 0d;
		double temp = 0d;
		char currentSign = '0';
		boolean negateNext = false;
		Deque<String> tempTokens = new LinkedList<>();
		Pattern numberPattern = Pattern.compile("[-]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");
		Pattern mulDivPattern = Pattern.compile("[-+*/%]+");
		// If only one element in input then return it as result
		if (inputTokens.size() == 1) {
			return Double.valueOf(inputTokens.pollFirst());
		}
		while (inputTokens.size() > 0) {
			String token = inputTokens.pollFirst();
			if (id_Value.containsKey(token)) {
				token = Double.toString(id_Value.get(token));
			}
			Matcher m = numberPattern.matcher(token);
			Matcher signMatcher = mulDivPattern.matcher(token);
			if (m.matches()) { // token is a number
				temp = Double.valueOf(token);
				if (negateNext) {
					temp = -temp;
				}
				if (currentSign == '*' || currentSign == '/' || currentSign == '%') {
					result = performSimpleOperation(result, temp, currentSign);
					currentSign = '0';
				} else if (currentSign == '0') {
					result = temp;
				} else if (currentSign == '+' || currentSign == '-') {
					if (level == 1) {
						tempTokens.addLast(Double.toString(result));
						result = temp;
						tempTokens.addLast(Character.toString(currentSign));
						currentSign = '0';
					} else if (level == 0) {
						result = performSimpleOperation(result, temp, currentSign);
						currentSign = '0';
					}
				}
			} else if (signMatcher.matches()) {
				if (currentSign == '-' && token.charAt(0) == '-') {
					currentSign = '+';
				} else if (currentSign != '0' && token.charAt(0) == '-') {
					negateNext = true;
				} else {
					currentSign = token.charAt(0);
				}
			}
			if (inputTokens.size() == 0) {
				tempTokens.addLast(Double.toString(result));
				return evaluate(tempTokens, 0);
			}
		}
		return result;
	}

	private double performSimpleOperation(double a, double b, char operationSign) {
		if (operationSign == '+') {
			return add(a, b);
		} else if (operationSign == '-') {
			return subtract(a, b);
		} else if (operationSign == '*') {
			return multiply(a, b);
		} else if (operationSign == '/') {
			return divide(a, b);
		} else if (operationSign == '%') {
			return modulo(a, b);
		} else {
			throw new UnsupportedOperationException(
					"This operation is not supported by method performSimpleOperation \n Details: \n a= " + a + "b= "
							+ b + "operationSign: " + operationSign);
		}

	}

	private double modulo(double a, double b) {
		return a % b;
	}

	private double add(double a, double b) {
		return a + b;
	}

	private double subtract(double a, double b) {
		return a - b;
	}

	private double multiply(double a, double b) {
		return a * b;
	}

	private double divide(double a, double b) {
		return a / b;
	}
	
	private class Function{
		String name;
		Deque<String> inputDefinition;
		Deque<String> functionDefinition;
		public Function(String name, Deque<String> inputDef, Deque<String> funDef) {
			this.name = name;
			inputDefinition = inputDef;
			functionDefinition = funDef;
		}
		private void validateFunctionDefinition( Deque<String> inputDef, Deque<String> funDef) {
			
		}
	}
}
