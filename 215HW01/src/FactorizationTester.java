import junit.framework.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

/**
 * A JUnit test case class.
 * Every method starting with the word "test" will be called when running
 * the test with JUnit.
 */
 
public class FactorizationTester extends TestCase {
    /**
   * Stream to hold the result of each test.
   */
  private ByteArrayOutputStream result;

  /**
   * Wrapper to provide printing functionality.
   */
  private PrintStream outputStream;

  /**
   * Setup the output streams before each test.
   */   
  protected void setUp () {
    result = new ByteArrayOutputStream();
    outputStream = new PrintStream(result);
  }    
  
  /**
   * Print a nice message about each test.
   */
  private void printInfo(String description, String expected, String actual) {
    System.out.format ("\n%s\n", description);
    System.out.format ("output:\t%s\n", actual);
    System.out.format ("correct:\t%s\n", expected);
  }

  /**
   * These are the various test methods.  The first 10 test factorizations
   * using the object constructed to factorize numbers up to 100; the latter
   * 7 use the object to factorize numbers up to 100000.
   */
  public void test100MaxFactorize1() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (1);

    // Print Results
    String expected = new String("Prime factorization of 1 is: 1");
    printInfo("Factorizing 1 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }

  public void test100MaxFactorize7() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (7);

    // Print Results
    String expected = new String("Prime factorization of 7 is: 7");
    printInfo("Factorizing 7 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }

  public void test100MaxFactorize5() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (5);

    // Print Results
    String expected = new String("Prime factorization of 5 is: 5");
    printInfo("Factorizing 5 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
    
  public void test100MaxFactorize30() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (30);

    // Print Results
    String expected = new String("Prime factorization of 30 is: 2 x 3 x 5");
    printInfo("Factorizing 30 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }

  public void test100MaxFactorize81() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (81);

    // Print Results
    String expected = new String("Prime factorization of 81 is: 3 x 3 x 3 x 3");
    printInfo("Factorizing 81 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100MaxFactorize71() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (71);

    // Print Results
    String expected = new String("Prime factorization of 71 is: 71");
    printInfo("Factorizing 71 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100MaxFactorize100() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (100);

    // Print Results
    String expected = new String("Prime factorization of 100 is: 2 x 2 x 5 x 5");
    printInfo("Factorizing 100 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100MaxFactorize101() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (101);

    // Print Results
    String expected = new String("101 is too large to factorize");
    printInfo("Factorizing 101 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100MaxFactorize0() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (0);

    // Print Results
    String expected = new String("Can't factorize a number less than 1");
    printInfo("Factorizing 0 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100MaxFactorize97() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100 = new PrimeFactorizer(100, outputStream);
    myFactorizerMax100.printPrimeFactorization (97);

    // Print Results
    String expected = new String("Prime factorization of 97 is: 97");
    printInfo("Factorizing 97 using max 100", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }

  public void test100000000MaxFactorize34534() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100000000 = new PrimeFactorizer(100000000, outputStream);
    myFactorizerMax100000000.printPrimeFactorization (34534);

    // Print Results
    String expected = new String("Prime factorization of 34534 is: 2 x 31 x 557");
    printInfo("Factorizing 34534 using max 100000000", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }

  public void test100000000MaxFactorize4339() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100000000 = new PrimeFactorizer(100000000, outputStream);
    myFactorizerMax100000000.printPrimeFactorization (4339);

    // Print Results
    String expected = new String("Prime factorization of 4339 is: 4339");
    printInfo("Factorizing 4339 using max 100000000", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }

  public void test100000000MaxFactorize65536() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100000000 = new PrimeFactorizer(100000000, outputStream);
    myFactorizerMax100000000.printPrimeFactorization (65536);

    // Print Results
    String expected = new String("Prime factorization of 65536 is: 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2");
    printInfo("Factorizing 65536 using max 100000000", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100000000MaxFactorize99797() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100000000 = new PrimeFactorizer(100000000, outputStream);
    myFactorizerMax100000000.printPrimeFactorization (99797);

    // Print Results
    String expected = new String("Prime factorization of 99797 is: 23 x 4339");
    printInfo("Factorizing 99797 using max 100000000", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100000000MaxFactorize307() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100000000 = new PrimeFactorizer(100000000, outputStream);
    myFactorizerMax100000000.printPrimeFactorization (307);

    // Print Results
    String expected = new String("Prime factorization of 307 is: 307");
    printInfo("Factorizing 307 using max 100000000", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }

  public void test100000000000MaxFactorize38845248344() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100000000000 = new PrimeFactorizer(100000000000L, outputStream);
    myFactorizerMax100000000000.printPrimeFactorization (38845248344L);

    // Print Results
    String expected = new String("Prime factorization of 38845248344 is: 2 x 2 x 2 x 7 x 693665149");
    printInfo("Factorizing 38845248344 using max 100000000000", expected, result.toString());
    
    // Check if test passed
    assertEquals (expected, result.toString());
    
    result.reset ();
    myFactorizerMax100000000000.printPrimeFactorization (24210833220L);
    expected = new String("Prime factorization of 24210833220 is: 2 x 2 x 3 x 3 x 5 x 7 x 17 x 19 x 19 x 31 x 101");
    printInfo("Factorizing 24210833220 using max 100000000000", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  public void test100000000000MaxFactorize5387457483444() {
    // Factorize the number
    PrimeFactorizer myFactorizerMax100000000000 = new PrimeFactorizer(100000000000L, outputStream);
    myFactorizerMax100000000000.printPrimeFactorization (5387457483444L);

    // Print Results
    String expected = new String("5387457483444 is too large to factorize");
    printInfo("Factorizing 5387457483444 using max 100000000000", expected, result.toString());

    // Check if test passed
    assertEquals (expected, result.toString());
  }
  
  
  public void testSpeed() {
    
    // here we factorize 10,000 large numbers; make sure that the constructor correctly sets
    // up the array of primes just once... if it does not, it'll take too long to run
    PrimeFactorizer myFactorizerMax100000000000 = new PrimeFactorizer(100000000000L, outputStream);
    for (long i = 38845248344L; i < 38845248344L + 50000; i++) {//50000
      myFactorizerMax100000000000.printPrimeFactorization (i);
      if (i == 38845248344L) {
        String expected = new String("Prime factorization of 38845248344 is: 2 x 2 x 2 x 7 x 693665149"); 
        assertEquals (expected, result.toString());
      }
    }
    
    // if we make it here, we pass the test
    assertTrue (true);
  }
}