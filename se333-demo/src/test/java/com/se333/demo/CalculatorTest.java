package com.se333.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for Calculator class.
 * Started with basic cases — the testing agent should expand this
 * to cover edge cases and branches.
 */
class CalculatorTest {

    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    // ── Addition ────────────────────────────────────

    @Test
    @DisplayName("add: positive numbers")
    void testAddPositive() {
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    @DisplayName("add: negative numbers")
    void testAddNegative() {
        assertEquals(-5, calc.add(-2, -3));
    }

    @Test
    @DisplayName("add: zero")
    void testAddZero() {
        assertEquals(7, calc.add(7, 0));
    }

    // ── Subtraction ────────────────────────────────

    @Test
    @DisplayName("subtract: basic")
    void testSubtract() {
        assertEquals(4, calc.subtract(10, 6));
    }

    @Test
    @DisplayName("subtract: result is negative")
    void testSubtractNegativeResult() {
        assertEquals(-3, calc.subtract(2, 5));
    }

    // ── Multiplication ─────────────────────────────

    @Test
    @DisplayName("multiply: positive")
    void testMultiply() {
        assertEquals(24, calc.multiply(4, 6));
    }

    @Test
    @DisplayName("multiply: by zero")
    void testMultiplyByZero() {
        assertEquals(0, calc.multiply(5, 0));
    }

    @Test
    @DisplayName("multiply: negative values")
    void testMultiplyNegative() {
        assertEquals(6, calc.multiply(-2, -3));
    }

    // ── Division ───────────────────────────────────

    @Test
    @DisplayName("divide: basic division")
    void testDivide() {
        assertEquals(5, calc.divide(10, 2));
    }

    @Test
    @DisplayName("divide: by zero should throw")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(10, 0));
    }

    @Test
    @DisplayName("divide: integer truncation")
    void testDivideIntegerTruncation() {
        assertEquals(3, calc.divide(7, 2));
    }

    // ── Absolute ───────────────────────────────────

    @Test
    @DisplayName("absolute: positive stays positive")
    void testAbsolutePositive() {
        assertEquals(5, calc.absolute(5));
    }

    @Test
    @DisplayName("absolute: negative becomes positive")
    void testAbsoluteNegative() {
        assertEquals(5, calc.absolute(-5));
    }

    @Test
    @DisplayName("absolute: zero")
    void testAbsoluteZero() {
        assertEquals(0, calc.absolute(0));
    }

    // ── Factorial ──────────────────────────────────

    @Test
    @DisplayName("factorial: 0! = 1")
    void testFactorialZero() {
        assertEquals(1, calc.factorial(0));
    }

    @Test
    @DisplayName("factorial: 5! = 120")
    void testFactorialFive() {
        assertEquals(120, calc.factorial(5));
    }

    @Test
    @DisplayName("factorial: negative throws exception")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1));
    }

    @Test
    @DisplayName("factorial: 1! = 1")
    void testFactorialOne() {
        assertEquals(1, calc.factorial(1));
    }

    // ── isPrime ────────────────────────────────────

    @Test
    @DisplayName("isPrime: 2 is prime")
    void testIsPrimeTwo() {
        assertTrue(calc.isPrime(2));
    }

    @Test
    @DisplayName("isPrime: 7 is prime")
    void testIsPrimeSeven() {
        assertTrue(calc.isPrime(7));
    }

    @Test
    @DisplayName("isPrime: 4 is not prime")
    void testIsPrimeFour() {
        assertFalse(calc.isPrime(4));
    }

    @Test
    @DisplayName("isPrime: 1 is not prime")
    void testIsPrimeOne() {
        assertFalse(calc.isPrime(1));
    }

    @Test
    @DisplayName("isPrime: negative is not prime")
    void testIsPrimeNegative() {
        assertFalse(calc.isPrime(-5));
    }

    // ── GCD ────────────────────────────────────────

    @Test
    @DisplayName("gcd: basic")
    void testGcd() {
        assertEquals(6, calc.gcd(12, 18));
    }

    @Test
    @DisplayName("gcd: coprime numbers")
    void testGcdCoprime() {
        assertEquals(1, calc.gcd(7, 13));
    }

    @Test
    @DisplayName("gcd: one is zero")
    void testGcdWithZero() {
        assertEquals(5, calc.gcd(5, 0));
    }

    @Test
    @DisplayName("gcd: negative values")
    void testGcdNegative() {
        assertEquals(6, calc.gcd(-12, 18));
    }

    // ── Power ──────────────────────────────────────

    @Test
    @DisplayName("power: 2^3 = 8")
    void testPower() {
        assertEquals(8, calc.power(2, 3));
    }

    @Test
    @DisplayName("power: anything^0 = 1")
    void testPowerZeroExponent() {
        assertEquals(1, calc.power(99, 0));
    }

    @Test
    @DisplayName("power: negative exponent throws")
    void testPowerNegativeExponent() {
        assertThrows(IllegalArgumentException.class, () -> calc.power(2, -1));
    }

    @Test
    @DisplayName("power: 0^5 = 0")
    void testPowerZeroBase() {
        assertEquals(0, calc.power(0, 5));
    }
}
