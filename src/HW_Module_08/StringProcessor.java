package HW_Module_08;


import java.util.HashMap;
import java.util.Map;

public class StringProcessor {

    public Map<Character, Integer> getCharacterFrequency(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                frequency.put(c, frequency.getOrDefault(c, 0) + 1);
            }
        }
        return frequency;
    }

    public String removeSpaces(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        return input.replaceAll("\\s+", "");
    }

    public String toUpperCase(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        return input.toUpperCase();
    }

    public String toLowerCase(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        return input.toLowerCase();
    }

    public String replaceWord(String text, String oldWord, String newWord) {
        if (text == null || oldWord == null || newWord == null) {
            throw new IllegalArgumentException("Параметры не могут быть null");
        }

        return text.replaceAll("(?i)\\b" + oldWord + "\\b", newWord);
    }

    public boolean containsWord(String text, String word) {
        if (text == null || word == null) {
            throw new IllegalArgumentException("Параметры не могут быть null");
        }

        return text.toLowerCase().contains(word.toLowerCase());
    }
}