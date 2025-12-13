package HW_Module_08;


import java.util.Map;
import java.util.Scanner;

public class StringApp {
    public static void main(String[] args) {
        StringAnalyzer analyzer = new StringAnalyzer();
        StringProcessor processor = new StringProcessor();
        StringValidator validator = new StringValidator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Анализатор строк ===");

        boolean running = true;
        while (running) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Проверить палиндром");
            System.out.println("2. Подсчитать гласные");
            System.out.println("3. Подсчитать согласные");
            System.out.println("4. Найти вхождения слова");
            System.out.println("5. Частота символов");
            System.out.println("6. Обратная строка");
            System.out.println("7. Выход");

            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите строку: ");
                    String palindromeInput = scanner.nextLine();
                    boolean isPalindrome = analyzer.isPalindrome(palindromeInput);
                    System.out.println("Это палиндром: " + isPalindrome);
                    break;

                case 2:
                    System.out.print("Введите строку: ");
                    String vowelsInput = scanner.nextLine();
                    int vowelsCount = analyzer.countVowels(vowelsInput);
                    System.out.println("Количество гласных: " + vowelsCount);
                    break;

                case 3:
                    System.out.print("Введите строку: ");
                    String consonantsInput = scanner.nextLine();
                    int consonantsCount = analyzer.countConsonants(consonantsInput);
                    System.out.println("Количество согласных: " + consonantsCount);
                    break;

                case 4:
                    System.out.print("Введите текст: ");
                    String text = scanner.nextLine();
                    System.out.print("Введите слово для поиска: ");
                    String word = scanner.nextLine();
                    int occurrences = analyzer.countWordOccurrences(text, word);
                    System.out.println("Количество вхождений: " + occurrences);
                    break;

                case 5:
                    System.out.print("Введите строку: ");
                    String freqInput = scanner.nextLine();
                    Map<Character, Integer> frequency = processor.getCharacterFrequency(freqInput);
                    System.out.println("Частота символов:");
                    for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    break;

                case 6:
                    System.out.print("Введите строку: ");
                    String reverseInput = scanner.nextLine();
                    String reversed = analyzer.reverseString(reverseInput);
                    System.out.println("Обратная строка: " + reversed);
                    break;

                case 7:
                    running = false;
                    break;

                default:
                    System.out.println("Неверный выбор");
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");

        System.out.println("\n=== Примеры работы ===");

        String testString = "А роза упала на лапу Азора";
        System.out.println("Тестовая строка: " + testString);
        System.out.println("Палиндром: " + analyzer.isPalindrome(testString));
        System.out.println("Гласные: " + analyzer.countVowels(testString));
        System.out.println("Согласные: " + analyzer.countConsonants(testString));
        System.out.println("Вхождения 'а': " + analyzer.countWordOccurrences(testString, "а"));
        System.out.println("Обратная строка: " + analyzer.reverseString(testString));
    }
}