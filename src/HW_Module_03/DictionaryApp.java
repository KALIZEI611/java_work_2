package HW_Module_03;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DictionaryApp {
    private LanguageDictionary dictionary;
    private Scanner scanner;

    public DictionaryApp() {
        this.dictionary = new LanguageDictionary("Английский", "Испанский");
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=== ПРОГРАММА ДЛЯ РАБОТЫ СО СЛОВАРЕМ ===");
        System.out.println("Направление: " + dictionary.getSourceLanguage() + " -> " + dictionary.getTargetLanguage());

        while (true) {
            displayMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> addWord();
                case 2 -> displayWord();
                case 3 -> addTranslation();
                case 4 -> replaceTranslation();
                case 5 -> removeTranslation();
                case 6 -> replaceWord();
                case 7 -> removeWord();
                case 8 -> dictionary.displayTop10Popular();
                case 9 -> dictionary.displayTop10Unpopular();
                case 10 -> searchWord();
                case 11 -> dictionary.displayStatistics();
                case 12 -> dictionary.displayAllWords();
                case 0 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Добавить слово");
        System.out.println("2. Показать слово и переводы");
        System.out.println("3. Добавить перевод");
        System.out.println("4. Заменить переводы слова");
        System.out.println("5. Удалить перевод");
        System.out.println("6. Заменить слово");
        System.out.println("7. Удалить слово");
        System.out.println("8. Топ-10 популярных слов");
        System.out.println("9. Топ-10 непопулярных слов");
        System.out.println("10. Поиск слова");
        System.out.println("11. Статистика словаря");
        System.out.println("12. Показать все слова");
        System.out.println("0. Выход");
    }

    private void addWord() {
        System.out.println("\n--- ДОБАВЛЕНИЕ СЛОВА ---");
        String word = getStringInput("Введите слово: ");

        if (dictionary.containsWord(word)) {
            System.out.println("Ошибка: слово уже существует в словаре.");
            return;
        }

        Set<String> translations = getTranslationsInput();
        if (translations.isEmpty()) {
            System.out.println("Ошибка: необходимо добавить хотя бы один перевод.");
            return;
        }

        if (dictionary.addWord(word, translations)) {
            System.out.println("Слово успешно добавлено.");
        } else {
            System.out.println("Ошибка при добавлении слова.");
        }
    }

    private void displayWord() {
        System.out.println("\n--- ПОКАЗ СЛОВА ---");
        String word = getStringInput("Введите слово: ");
        dictionary.displayWord(word);
    }

    private void addTranslation() {
        System.out.println("\n--- ДОБАВЛЕНИЕ ПЕРЕВОДА ---");
        String word = getStringInput("Введите слово: ");

        if (!dictionary.containsWord(word)) {
            System.out.println("Ошибка: слово не найдено в словаре.");
            return;
        }

        String translation = getStringInput("Введите перевод: ");
        if (translation.isEmpty()) {
            System.out.println("Ошибка: перевод не может быть пустым.");
            return;
        }

        if (dictionary.addTranslation(word, translation)) {
            System.out.println("Перевод успешно добавлен.");
        } else {
            System.out.println("Ошибка при добавлении перевода.");
        }
    }

    private void replaceTranslation() {
        System.out.println("\n--- ЗАМЕНА ПЕРЕВОДОВ ---");
        String word = getStringInput("Введите слово: ");

        if (!dictionary.containsWord(word)) {
            System.out.println("Ошибка: слово не найдено в словаре.");
            return;
        }

        Set<String> translations = getTranslationsInput();
        if (translations.isEmpty()) {
            System.out.println("Ошибка: необходимо добавить хотя бы один перевод.");
            return;
        }

        if (dictionary.replaceTranslations(word, translations)) {
            System.out.println("Переводы успешно заменены.");
        } else {
            System.out.println("Ошибка при замене переводов.");
        }
    }

    private void removeTranslation() {
        System.out.println("\n--- УДАЛЕНИЕ ПЕРЕВОДА ---");
        String word = getStringInput("Введите слово: ");
        String translation = getStringInput("Введите перевод для удаления: ");

        if (dictionary.removeTranslation(word, translation)) {
            System.out.println("Перевод успешно удален.");
        } else {
            System.out.println("Ошибка: слово или перевод не найдены.");
        }
    }

    private void replaceWord() {
        System.out.println("\n--- ЗАМЕНА СЛОВА ---");
        String oldWord = getStringInput("Введите слово для замены: ");

        if (!dictionary.containsWord(oldWord)) {
            System.out.println("Ошибка: исходное слово не найдено в словаре.");
            return;
        }

        String newWord = getStringInput("Введите новое слово: ");
        if (dictionary.containsWord(newWord)) {
            System.out.println("Ошибка: новое слово уже существует в словаре.");
            return;
        }

        Set<String> translations = getTranslationsInput();
        if (translations.isEmpty()) {
            System.out.println("Ошибка: необходимо добавить хотя бы один перевод.");
            return;
        }

        if (dictionary.replaceWord(oldWord, newWord, translations)) {
            System.out.println("Слово успешно заменено.");
        } else {
            System.out.println("Ошибка при замене слова.");
        }
    }

    private void removeWord() {
        System.out.println("\n--- УДАЛЕНИЕ СЛОВА ---");
        String word = getStringInput("Введите слово для удаления: ");

        if (dictionary.removeWord(word)) {
            System.out.println("Слово успешно удалено.");
        } else {
            System.out.println("Ошибка: слово не найдено в словаре.");
        }
    }

    private void searchWord() {
        System.out.println("\n--- ПОИСК СЛОВА ---");
        String partialWord = getStringInput("Введите часть слова для поиска: ");
        dictionary.displaySearchResults(partialWord);
    }

    private Set<String> getTranslationsInput() {
        Set<String> translations = new HashSet<>();
        System.out.println("Введите переводы (пустая строка для завершения):");

        int count = 0;
        while (true) {
            System.out.print("Перевод " + (count + 1) + ": ");
            String translation = scanner.nextLine().trim();
            if (translation.isEmpty()) {
                if (count == 0) {
                    System.out.println("Необходимо ввести хотя бы один перевод.");
                    continue;
                }
                break;
            }
            translations.add(translation);
            count++;
        }

        return translations;
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    public static void main(String[] args) {
        DictionaryApp app = new DictionaryApp();
        app.run();
    }
}