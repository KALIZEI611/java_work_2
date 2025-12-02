package HW_Module_08;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StringAnalyzerTest {

    private StringAnalyzer analyzer;
    private StringProcessor processor;
    private StringValidator validator;

    @BeforeEach
    void setUp() {
        analyzer = new StringAnalyzer();
        processor = new StringProcessor();
        validator = new StringValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"racecar", "А роза упала на лапу Азора", "Madam", "топот", "12321", "a", "   "})
    void testIsPalindromeValid(String input) {
        assertTrue(analyzer.isPalindrome(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "world", "java", "test", "not a palindrome"})
    void testIsPalindromeInvalid(String input) {
        assertFalse(analyzer.isPalindrome(input));
    }

    @Test
    void testIsPalindromeNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.isPalindrome(null);
        });
    }

    @Test
    void testIsPalindromeEmpty() {
        assertFalse(analyzer.isPalindrome(""));
    }

    @Test
    void testIsPalindromeOnlySpaces() {
        assertFalse(analyzer.isPalindrome("   "));
    }

    @ParameterizedTest
    @CsvSource({
            "'hello', 2",
            "'world', 1",
            "'aeiou', 5",
            "'AEIOU', 5",
            "'аеёиоуыэюя', 10",
            "'АЕЁИОУЫЭЮЯ', 10",
            "'test string', 2",
            "'12345', 0",
            "'', 0"
    })
    void testCountVowels(String input, int expected) {
        assertEquals(expected, analyzer.countVowels(input));
    }

    @Test
    void testCountVowelsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.countVowels(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "'hello', 3",
            "'world', 4",
            "'bcdfg', 5",
            "'test string', 8",
            "'aeiou', 0",
            "'12345', 0",
            "'', 0"
    })
    void testCountConsonants(String input, int expected) {
        assertEquals(expected, analyzer.countConsonants(input));
    }

    @Test
    void testCountConsonantsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.countConsonants(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "'hello world hello', 'hello', 2",
            "'test test test', 'test', 3",
            "'word word', 'word', 2",
            "'single', 'single', 1",
            "'no match', 'word', 0",
            "'Test test TEST', 'test', 3",
            "'а роза упала на лапу', 'а', 3",
            "'', 'word', 0"
    })
    void testCountWordOccurrences(String text, String word, int expected) {
        assertEquals(expected, analyzer.countWordOccurrences(text, word));
    }

    @Test
    void testCountWordOccurrencesNullText() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.countWordOccurrences(null, "word");
        });
    }

    @Test
    void testCountWordOccurrencesNullWord() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.countWordOccurrences("text", null);
        });
    }

    @Test
    void testCountWordOccurrencesEmptyWord() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.countWordOccurrences("text", "");
        });
    }

    @Test
    void testCountTotalLetters() {
        assertEquals(5, analyzer.countTotalLetters("hello"));
        assertEquals(0, analyzer.countTotalLetters("12345"));
        assertEquals(10, analyzer.countTotalLetters("hello world"));
    }

    @Test
    void testCountTotalLettersNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.countTotalLetters(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "'hello', 'olleh'",
            "'world', 'dlrow'",
            "'123', '321'",
            "'a', 'a'",
            "'', ''",
            "'test string', 'gnirts tset'"
    })
    void testReverseString(String input, String expected) {
        assertEquals(expected, analyzer.reverseString(input));
    }

    @Test
    void testReverseStringNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            analyzer.reverseString(null);
        });
    }

    @Test
    void testGetCharacterFrequency() {
        Map<Character, Integer> frequency = processor.getCharacterFrequency("hello");
        assertEquals(1, frequency.get('h'));
        assertEquals(1, frequency.get('e'));
        assertEquals(2, frequency.get('l'));
        assertEquals(1, frequency.get('o'));
    }

    @Test
    void testGetCharacterFrequencyOnlyLetters() {
        Map<Character, Integer> frequency = processor.getCharacterFrequency("hello 123");
        assertFalse(frequency.containsKey('1'));
        assertFalse(frequency.containsKey('2'));
        assertFalse(frequency.containsKey('3'));
        assertFalse(frequency.containsKey(' '));
    }

    @Test
    void testGetCharacterFrequencyNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.getCharacterFrequency(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "'hello world', 'helloworld'",
            "'  test  ', 'test'",
            "'', ''",
            "'multiple   spaces', 'multiplespaces'"
    })
    void testRemoveSpaces(String input, String expected) {
        assertEquals(expected, processor.removeSpaces(input));
    }

    @Test
    void testRemoveSpacesNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.removeSpaces(null);
        });
    }

    @Test
    void testToUpperCase() {
        assertEquals("HELLO", processor.toUpperCase("hello"));
        assertEquals("TEST 123", processor.toUpperCase("test 123"));
    }

    @Test
    void testToUpperCaseNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.toUpperCase(null);
        });
    }

    @Test
    void testToLowerCase() {
        assertEquals("hello", processor.toLowerCase("HELLO"));
        assertEquals("test 123", processor.toLowerCase("TEST 123"));
    }

    @Test
    void testToLowerCaseNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.toLowerCase(null);
        });
    }

    @Test
    void testReplaceWord() {
        assertEquals("world world world",
                processor.replaceWord("hello hello hello", "hello", "world"));
        assertEquals("Test World test",
                processor.replaceWord("Test Hello test", "hello", "World"));
    }

    @Test
    void testReplaceWordNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.replaceWord(null, "old", "new");
        });
    }

    @Test
    void testContainsWord() {
        assertTrue(processor.containsWord("hello world", "hello"));
        assertTrue(processor.containsWord("HELLO WORLD", "hello"));
        assertFalse(processor.containsWord("hello world", "test"));
    }

    @Test
    void testContainsWordNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.containsWord(null, "word");
        });
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "  ", "\t", "\n"})
    void testIsEmpty(String input) {
        assertTrue(validator.isEmpty(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "WORLD", "тест", "HelloWorld"})
    void testIsOnlyLettersValid(String input) {
        assertTrue(validator.isOnlyLetters(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello123", "test!", "word test", "123"})
    void testIsOnlyLettersInvalid(String input) {
        assertFalse(validator.isOnlyLetters(input));
    }

    @Test
    void testIsOnlyLettersNull() {
        assertFalse(validator.isOnlyLetters(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123", "123456", "0"})
    void testIsOnlyDigitsValid(String input) {
        assertTrue(validator.isOnlyDigits(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"123abc", "12.34", "test", " "})
    void testIsOnlyDigitsInvalid(String input) {
        assertFalse(validator.isOnlyDigits(input));
    }

    @Test
    void testIsOnlyDigitsNull() {
        assertFalse(validator.isOnlyDigits(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello123", "Test123", "abc123ABC"})
    void testIsAlphaNumericValid(String input) {
        assertTrue(validator.isAlphaNumeric(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello!", "test test", "123-456", " "})
    void testIsAlphaNumericInvalid(String input) {
        assertFalse(validator.isAlphaNumeric(input));
    }

    @Test
    void testIsAlphaNumericNull() {
        assertFalse(validator.isAlphaNumeric(null));
    }

    @ParameterizedTest
    @CsvSource({
            "'hello', 5",
            "'', 0",
            "'test string', 11",
            "'  ', 2"
    })
    void testGetLength(String input, int expected) {
        assertEquals(expected, validator.getLength(input));
    }

    @Test
    void testGetLengthNull() {
        assertEquals(0, validator.getLength(null));
    }

    @ParameterizedTest
    @CsvSource({
            "'  hello  ', 'hello'",
            "'test', 'test'",
            "'', ''",
            "'   ', ''"
    })
    void testTrimSpaces(String input, String expected) {
        assertEquals(expected, validator.trimSpaces(input));
    }

    @Test
    void testTrimSpacesNull() {
        assertEquals("", validator.trimSpaces(null));
    }
}