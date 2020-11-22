package interpreter2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class ExampleInterpreterTest {

  @Test
  public void basicTests() {
    Interpreter interpreter = new Interpreter();
    
   assertNull(interpreter.input("    "));
    // Basic arithmetic
   assertEquals(9, interpreter.input("9"), 0.0);
//   assertEquals(12, interpreter.input("1 2"), 0.0);
    assertEquals(2, interpreter.input("1+1"), 0.0);
    assertEquals(1, interpreter.input("2 - 1"), 0.0);
    assertEquals(6, interpreter.input("2 * 3"), 0.0);
    assertEquals(2, interpreter.input("8 / 4"), 0.0);
    assertEquals(3, interpreter.input("7 % 4"), 0.0) ;


    // Variables
    assertEquals(1, interpreter.input("x = 1"), 0.0);
    assertEquals(1, interpreter.input("x"), 0.0);
    assertEquals(4, interpreter.input("x + 3"), 0.0);
    assertFail("input: 'y'", () -> interpreter.input("y"));
    assertEquals(912, interpreter.input("912"), 0.0);
    
    interpreter.input("fn avg x y => (x + y) / 2");
    assertEquals(3, interpreter.input("avg 4 2"), 0.0);
    assertFail("input: 'avg 7'", () -> interpreter.input("avg 7"));
    assertFail("input: 'avg 7 2 4'", () -> interpreter.input("avg 7 2 4"));

    // Conflicts
    assertFail("input: 'fn x => 0'", () -> interpreter.input("fn x => 0"));
    assertFail("input: 'avg = 5'", () -> interpreter.input("avg = 5"));
  }

  private static void assertFail(String msg, Runnable runnable) {
    try {
      runnable.run();
      fail(msg);
    } catch (Exception e) {
      // Ok
    }
  }
}