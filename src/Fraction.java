/**
 * A class representing a fraction. Consists of a numerator
 * and denominator. Contains methods which provide add, divide,
 * subtract, multiplication functionality on the current Fraction.
 * @Author: Falko Noe
 * @Version: 1.0
 */

public class Fraction {

  private int num;
  private int den;

  /**
   * Constructor for the Fraction. Returns an instance of
   * Fraction with the specified denominator and numerator.
   * @param num: The int representation of the numerator
   * @param den: The int representation of the denominator
   */
  Fraction(int num, int den) {
    this.num = num;
    this.den = den;
  }

  /**
   * Constructor for the Fraction.
   * @param num: The int representation of the numerator
   */
  Fraction(int num) {
    this.num = num;
    this.den = 1;
  }

  /**
   * Forms the product of two Fractions.
   * @param x: The Fraction against which to multiply.
   * @return: A new fraction that is the product of this fraction
   * and the input. Does not return the reduced product.
   */
  Fraction mult(Fraction x) {
    return reduce(new Fraction(this.num * x.num, this.den * x.den));
  }

  /**
   * Forms the quotient of dividing the current Fraction by
   * the input. Does not reduce.
   * @param x: The Fraction against which to divide.
   * @return: A new Fraction that is the division of the Fraction
   * by the input.
   */
  Fraction div(Fraction x) {
    if (x.num < 0) {
      return reduce(new Fraction(this.num * x.den * -1, this.den * x.num * -1));
    } else {
      return reduce(new Fraction(this.num * x.den, this.den * x.num));
    }
  }

  /**
   * Subtracts the current Fraction by the input and returns
   * a new Fraction corresponding to the result.
   * @param x: The input Fraction that will subtract from the input.
   * @return: The Fraction result of subtracting the current
   * Fraction by the input.
   */
  Fraction sub(Fraction x) {
    return reduce(new Fraction(this.num * x.den - x.num * this.den,
            this.den * x.den));
  }

  /**
   * Performs the reduction of the input Fraction. Will either
   * return the original Fraction or a reduced form.
   * @param x: The Fraction we want to reduce
   * @return: The reduced Fraction or the original
   * Fraction if it could not be reduced.
   */
  static Fraction reduce(Fraction x) {
    if (x.num == 0) {
      return new Fraction(x.num);
    }
    // GCD of any number and 1 is 1
    if (x.num == 1 || x.den == 1) {
      return x;
    }
    int numerator = x.num;
    int denominator = x.den;
    // Positive integers only to simplify calcGCD logic
    if (numerator < 0) {
      numerator = 0 - numerator;
    }
    if (denominator < 0) {
      denominator = 0 - denominator;
    }
    int gcd = calcGcd(numerator, denominator);
    return new Fraction(x.num/gcd, x.den/gcd);
  }

  /**
   * Recursive method that calculates the GCD of the two
   * input integers.
   * @param a: The numerator, but could be any integer
   * @param b: The denominator, but could be any integer
   * @return The GCD
   */
  private static int calcGcd(int a, int b) {
    if (a == b) {
      return a;
    } else if (b > a) {
      return calcGcd(a, b - a);
    } else {
      return calcGcd(a - b, a);
    }
  }

  /**
   * Adds the current Fraction with the input and returns
   * a Fraction that is the result of the operation.
   * @param x: The input Fraction that will be added to the input.
   * @return: The Fraction result of adding the current
   * Fraction by the input.
   */
  Fraction add(Fraction x) {
    return new Fraction(this.num * x.den + x.num * this.den,
            this.den * x.den);
  }

  /**
   * A method which returns a boolean value based on
   * whether the Fraction is equal to 0.
   * @return: Returns true if the Fraction is equal to 0,
   * false otherwise.
   */
  boolean equalsZero() {
    return this.num == 0;
  }

  /**
   * Converts the current Fraction to the result of dividing
   * the numerator
   * by the denominator.
   * @return: The integer result of dividing the numerator
   * by the denominator.
   */
  int toInt() {
    return this.num / this.den;
  }

  /**
   * Overrides the toString method of Object.
   * @return: The string representation of the Fraction.
   */
  @Override
  public String toString() {
    if (this.num == 0 || this.den == 1) {
      return this.num + "";
    } else {
      return this.num + "/" + this.den;
    }
  }
}
