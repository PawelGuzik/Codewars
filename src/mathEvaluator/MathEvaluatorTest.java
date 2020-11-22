package mathEvaluator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MathEvaluatorTest {
  @Test public void testAddition() {
    assertEquals(new MathEvaluator().calculate("1+1"), 2d, 0.01);
  }
  
  @Test public void testSubtraction() {
    assertEquals(new MathEvaluator().calculate("1 - 1"), 0d, 0.01);
  }

  @Test public void testMultiplication() {
    assertEquals(new MathEvaluator().calculate("1* 1"), 1d, 0.01);
  }

  @Test public void testDivision() {
    assertEquals(new MathEvaluator().calculate("1 /1"), 1d, 0.01);
  }

  @Test public void testNegative() {
    assertEquals(new MathEvaluator().calculate("-123"), -123d, 0.01);
  }

  @Test public void testLiteral() {
    assertEquals(new MathEvaluator().calculate("123"), 123d, 0.01);
  }

  @Test public void testExpression() {
    assertEquals(new MathEvaluator().calculate("2 /2/2+ 3 + 3 * 4.75- -6"), 23.75, 0.01);
  }
  
  @Test public void testExpression2() {
	    assertEquals(new MathEvaluator().calculate("2 * 2 * 4 + 5 + 6 + 7 * 8 * 9 - -6 - -13"), 550, 0.01);
	  }

  @Test public void testSimple() {
    assertEquals(new MathEvaluator().calculate("12* 123"), 1476d, 0.01);
  }

  @Test public void testComplex() {
    assertEquals(new MathEvaluator().calculate("((2 + 2) * 3) * 4.33 - -6"), 57.96, 0.01);
  }
  
  @Test public void testMultipleNegative() {
	    assertEquals(new MathEvaluator().calculate("12 * - 1"), -12.0, 0.01);
	  }
  
  
  @Test public void testMultipleNegativeBeforeParentheses() {
	    assertEquals(new MathEvaluator().calculate("( 1 - 2 ) + - ( - ( - ( - 4 ) ) )"), 3, 0.01);
	  }
}