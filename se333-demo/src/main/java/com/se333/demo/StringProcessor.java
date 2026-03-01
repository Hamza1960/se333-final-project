package com.se333.demo;

/**
 * Utility class for common string operations.
 * Provides validation, transformation, and analysis methods.
 */
public class StringProcessor {

    /**
     * Reverse a string.
     *
     * @param input the string to reverse
     * @return reversed string, or null if input is null
     */
    public String reverse(String input) {
        if (input == null) {
            return null;
        }
        return new StringBuilder(input).reverse().toString();
    }

    /**
     * Check if a string is a palindrome (case-insensitive).
     */
    public boolean isPalindrome(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        String cleaned = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    /**
     * Count the number of vowels in a string.
     */
    public int countVowels(String input) {
        if (input == null) {
            return 0;
        }
        int count = 0;
        for (char c : input.toLowerCase().toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                count++;
            }
        }
        return count;
    }

    /**
     * Convert a string to title case.
     * Example: "hello world" -> "Hello World"
     */
    public String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
                result.append(c);
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }

    /**
     * Truncate a string to the specified length, appending "..." if truncated.
     */
    public String truncate(String input, int maxLength) {
        if (input == null) {
            return null;
        }
        if (maxLength < 0) {
            throw new IllegalArgumentException("maxLength cannot be negative");
        }
        if (input.length() <= maxLength) {
            return input;
        }
        if (maxLength <= 3) {
            return input.substring(0, maxLength);
        }
        return input.substring(0, maxLength - 3) + "...";
    }

    /**
     * Count occurrences of a word in the given text (case-insensitive).
     */
    public int countWordOccurrences(String text, String word) {
        if (text == null || word == null || word.isEmpty()) {
            return 0;
        }
        String lowerText = text.toLowerCase();
        String lowerWord = word.toLowerCase();
        int count = 0;
        int index = 0;
        while ((index = lowerText.indexOf(lowerWord, index)) != -1) {
            count++;
            index += lowerWord.length();
        }
        return count;
    }

    /**
     * Check if the input string is a valid email address (simple check).
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        // Simple validation: must have exactly one @, and at least one dot after @
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 || atIndex != email.lastIndexOf('@')) {
            return false;
        }
        String domain = email.substring(atIndex + 1);
        return domain.contains(".") && !domain.startsWith(".") && !domain.endsWith(".");
    }
}
