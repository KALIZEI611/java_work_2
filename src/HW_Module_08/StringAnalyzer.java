package HW_Module_08;

public class StringAnalyzer {

    public boolean isPalindrome(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        String cleaned = input.replaceAll("[^а-яА-Яa-zA-Z]", "").toLowerCase();
        if (cleaned.isEmpty()) {
            return false;
        }

        int left = 0;
        int right = cleaned.length() - 1;

        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public int countVowels(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        int count = 0;
        String vowels = "aeiouаеёиоуыэюяAEIOUАЕЁИОУЫЭЮЯ";

        for (char c : input.toCharArray()) {
            if (vowels.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    public int countConsonants(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        int count = 0;
        String consonants = "bcdfghjklmnpqrstvwxyzбвгджзйклмнпрстфхцчшщBCDFGHJKLMNPQRSTVWXYZБВГДЖЗЙКЛМНПРСТФХЦЧШЩ";

        for (char c : input.toCharArray()) {
            if (consonants.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }

    public int countWordOccurrences(String text, String word) {
        if (text == null || word == null) {
            throw new IllegalArgumentException("Параметры не могут быть null");
        }
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Слово не может быть пустым");
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

    public int countTotalLetters(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        return countVowels(input) + countConsonants(input);
    }

    public String reverseString(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Строка не может быть null");
        }

        return new StringBuilder(input).reverse().toString();
    }
}