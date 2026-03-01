package com.se333.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for StringProcessor utility class.
 */
class StringProcessorTest {

    private StringProcessor sp;

    @BeforeEach
    void setUp() {
        sp = new StringProcessor();
    }

    // ── reverse ────────────────────────────────────

    @Test
    @DisplayName("reverse: normal string")
    void testReverse() {
        assertEquals("olleh", sp.reverse("hello"));
    }

    @Test
    @DisplayName("reverse: null input returns null")
    void testReverseNull() {
        assertNull(sp.reverse(null));
    }

    @Test
    @DisplayName("reverse: empty string")
    void testReverseEmpty() {
        assertEquals("", sp.reverse(""));
    }

    @Test
    @DisplayName("reverse: single character")
    void testReverseSingleChar() {
        assertEquals("a", sp.reverse("a"));
    }

    // ── isPalindrome ───────────────────────────────

    @Test
    @DisplayName("isPalindrome: racecar is a palindrome")
    void testPalindromeTrue() {
        assertTrue(sp.isPalindrome("racecar"));
    }

    @Test
    @DisplayName("isPalindrome: mixed case")
    void testPalindromeMixedCase() {
        assertTrue(sp.isPalindrome("RaceCar"));
    }

    @Test
    @DisplayName("isPalindrome: with spaces and punctuation")
    void testPalindromeWithSpaces() {
        assertTrue(sp.isPalindrome("A man a plan a canal Panama"));
    }

    @Test
    @DisplayName("isPalindrome: not a palindrome")
    void testPalindromeFalse() {
        assertFalse(sp.isPalindrome("hello"));
    }

    @Test
    @DisplayName("isPalindrome: null returns false")
    void testPalindromeNull() {
        assertFalse(sp.isPalindrome(null));
    }

    @Test
    @DisplayName("isPalindrome: empty returns false")
    void testPalindromeEmpty() {
        assertFalse(sp.isPalindrome(""));
    }

    // ── countVowels ────────────────────────────────

    @Test
    @DisplayName("countVowels: basic count")
    void testCountVowels() {
        assertEquals(2, sp.countVowels("hello"));
    }

    @Test
    @DisplayName("countVowels: all vowels")
    void testCountVowelsAllVowels() {
        assertEquals(5, sp.countVowels("aeiou"));
    }

    @Test
    @DisplayName("countVowels: no vowels")
    void testCountVowelsNone() {
        assertEquals(0, sp.countVowels("bcdfg"));
    }

    @Test
    @DisplayName("countVowels: null returns 0")
    void testCountVowelsNull() {
        assertEquals(0, sp.countVowels(null));
    }

    @Test
    @DisplayName("countVowels: uppercase vowels")
    void testCountVowelsUpperCase() {
        assertEquals(2, sp.countVowels("APPLE"));
    }

    // ── toTitleCase ────────────────────────────────

    @Test
    @DisplayName("toTitleCase: basic")
    void testTitleCase() {
        assertEquals("Hello World", sp.toTitleCase("hello world"));
    }

    @Test
    @DisplayName("toTitleCase: already title case")
    void testTitleCaseAlready() {
        assertEquals("Hello World", sp.toTitleCase("Hello World"));
    }

    @Test
    @DisplayName("toTitleCase: all caps")
    void testTitleCaseAllCaps() {
        assertEquals("Hello World", sp.toTitleCase("HELLO WORLD"));
    }

    @Test
    @DisplayName("toTitleCase: null returns null")
    void testTitleCaseNull() {
        assertNull(sp.toTitleCase(null));
    }

    @Test
    @DisplayName("toTitleCase: empty returns empty")
    void testTitleCaseEmpty() {
        assertEquals("", sp.toTitleCase(""));
    }

    // ── truncate ───────────────────────────────────

    @Test
    @DisplayName("truncate: string shorter than max")
    void testTruncateNoTruncation() {
        assertEquals("hi", sp.truncate("hi", 10));
    }

    @Test
    @DisplayName("truncate: string longer than max")
    void testTruncateLong() {
        assertEquals("Hell...", sp.truncate("Hello World", 7));
    }

    @Test
    @DisplayName("truncate: null input")
    void testTruncateNull() {
        assertNull(sp.truncate(null, 5));
    }

    @Test
    @DisplayName("truncate: negative maxLength throws")
    void testTruncateNegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> sp.truncate("test", -1));
    }

    @Test
    @DisplayName("truncate: maxLength <= 3 does not append ellipsis")
    void testTruncateShortMax() {
        assertEquals("He", sp.truncate("Hello", 2));
    }

    // ── countWordOccurrences ───────────────────────

    @Test
    @DisplayName("countWordOccurrences: basic count")
    void testCountWordOccurrences() {
        assertEquals(2, sp.countWordOccurrences("the cat and the dog", "the"));
    }

    @Test
    @DisplayName("countWordOccurrences: case insensitive")
    void testCountWordOccurrencesCaseInsensitive() {
        assertEquals(2, sp.countWordOccurrences("The cat and THE dog", "the"));
    }

    @Test
    @DisplayName("countWordOccurrences: word not found")
    void testCountWordOccurrencesNotFound() {
        assertEquals(0, sp.countWordOccurrences("hello world", "xyz"));
    }

    @Test
    @DisplayName("countWordOccurrences: null text returns 0")
    void testCountWordOccurrencesNullText() {
        assertEquals(0, sp.countWordOccurrences(null, "test"));
    }

    @Test
    @DisplayName("countWordOccurrences: null word returns 0")
    void testCountWordOccurrencesNullWord() {
        assertEquals(0, sp.countWordOccurrences("test", null));
    }

    @Test
    @DisplayName("countWordOccurrences: empty word returns 0")
    void testCountWordOccurrencesEmptyWord() {
        assertEquals(0, sp.countWordOccurrences("test text", ""));
    }

    // ── isValidEmail ───────────────────────────────

    @Test
    @DisplayName("isValidEmail: valid email")
    void testValidEmail() {
        assertTrue(sp.isValidEmail("user@example.com"));
    }

    @Test
    @DisplayName("isValidEmail: no @ symbol")
    void testInvalidEmailNoAt() {
        assertFalse(sp.isValidEmail("userexample.com"));
    }

    @Test
    @DisplayName("isValidEmail: multiple @ symbols")
    void testInvalidEmailMultipleAt() {
        assertFalse(sp.isValidEmail("user@@example.com"));
    }

    @Test
    @DisplayName("isValidEmail: no dot in domain")
    void testInvalidEmailNoDot() {
        assertFalse(sp.isValidEmail("user@examplecom"));
    }

    @Test
    @DisplayName("isValidEmail: null returns false")
    void testInvalidEmailNull() {
        assertFalse(sp.isValidEmail(null));
    }

    @Test
    @DisplayName("isValidEmail: empty returns false")
    void testInvalidEmailEmpty() {
        assertFalse(sp.isValidEmail(""));
    }

    @Test
    @DisplayName("isValidEmail: domain starts with dot")
    void testInvalidEmailDomainStartsDot() {
        assertFalse(sp.isValidEmail("user@.example.com"));
    }

    @Test
    @DisplayName("isValidEmail: domain ends with dot")
    void testInvalidEmailDomainEndsDot() {
        assertFalse(sp.isValidEmail("user@example."));
    }

    @Test
    @DisplayName("isValidEmail: nothing before @")
    void testInvalidEmailNothingBeforeAt() {
        assertFalse(sp.isValidEmail("@example.com"));
    }
}
