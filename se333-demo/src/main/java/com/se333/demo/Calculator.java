package com.se333.demo;

/**
 * Calculator class with basic and advanced arithmetic operations.
 * Contains intentional bugs for the testing agent to discover.
 */
public class Calculator {

    /**
     * Add two integers.
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtract b from a.
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Multiply two integers.
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Divide a by b.
     * BUG: does not handle division by zero — throws ArithmeticException.
     */
    public int divide(int a, int b) {
        return a / b;
    }

    /**
     * Return the absolute value.
     * BUG: fails for Integer.MIN_VALUE because Math.abs overflows.
     */
    public int absolute(int value) {
        return Math.abs(value);
    }

    /**
     * Compute factorial of n.
     * Assumes n >= 0.
     */
    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Check if a number is prime.
     * BUG: returns true for 1, which is not prime.
     */
    public boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compute the greatest common divisor using Euclid's algorithm.
     */
    public int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * Calculate the power of base^exponent.
     * Only supports non-negative exponents.
     */
    public long power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Exponent must be non-negative");
        }
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
