package HW_Module_03;

import java.util.*;
import java.util.stream.Collectors;

public class LanguageDictionary {
    private Map<String, DictionaryWord> dictionary;
    private String sourceLanguage;
    private String targetLanguage;

    public LanguageDictionary(String sourceLanguage, String targetLanguage) {
        this.dictionary = new HashMap<>();
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
        initializeSampleData();
    }

    // Инициализация начальными данными
    private void initializeSampleData() {
        addWord("hello", new HashSet<>(Arrays.asList("hola", "buenos dias")));
        addWord("goodbye", new HashSet<>(Arrays.asList("adios", "hasta luego")));
        addWord("thank you", new HashSet<>(Arrays.asList("gracias")));
        addWord("please", new HashSet<>(Arrays.asList("por favor")));
        addWord("water", new HashSet<>(Arrays.asList("agua")));
        addWord("food", new HashSet<>(Arrays.asList("comida", "alimento")));
        addWord("house", new HashSet<>(Arrays.asList("casa", "hogar")));
        addWord("car", new HashSet<>(Arrays.asList("coche", "automovil", "carro")));
        addWord("book", new HashSet<>(Arrays.asList("libro")));
        addWord("computer", new HashSet<>(Arrays.asList("ordenador", "computadora")));

        // Имитируем некоторое количество обращений для демонстрации
        simulateAccesses();
    }

    // Имитация обращений к словам для демонстрации популярности
    private void simulateAccesses() {
        Random random = new Random();
        List<String> words = new ArrayList<>(dictionary.keySet());

        for (int i = 0; i < 50; i++) {
            String randomWord = words.get(random.nextInt(words.size()));
            dictionary.get(randomWord).incrementAccessCount();
        }
    }

    // Добавление слова
    public boolean addWord(String word, Set<String> translations) {
        if (dictionary.containsKey(word.toLowerCase())) {
            return false; // Слово уже существует
        }
        dictionary.put(word.toLowerCase(), new DictionaryWord(word, translations));
        return true;
    }

    // Добавление слова с одним переводом
    public boolean addWord(String word, String translation) {
        return addWord(word, new HashSet<>(Arrays.asList(translation)));
    }

    // Замена слова (удаление старого и добавление нового)
    public boolean replaceWord(String oldWord, String newWord, Set<String> translations) {
        if (!dictionary.containsKey(oldWord.toLowerCase())) {
            return false;
        }
        removeWord(oldWord);
        return addWord(newWord, translations);
    }

    // Удаление слова
    public boolean removeWord(String word) {
        return dictionary.remove(word.toLowerCase()) != null;
    }

    // Отображение слова и его переводов
    public void displayWord(String word) {
        DictionaryWord dictWord = dictionary.get(word.toLowerCase());
        if (dictWord != null) {
            dictWord.incrementAccessCount();
            System.out.println(dictWord);
        } else {
            System.out.println("Слово '" + word + "' не найдено в словаре.");
        }
    }

    // Добавление перевода к существующему слову
    public boolean addTranslation(String word, String translation) {
        DictionaryWord dictWord = dictionary.get(word.toLowerCase());
        if (dictWord != null) {
            dictWord.addTranslation(translation);
            return true;
        }
        return false;
    }

    // Добавление нескольких переводов
    public boolean addTranslations(String word, Set<String> translations) {
        DictionaryWord dictWord = dictionary.get(word.toLowerCase());
        if (dictWord != null) {
            dictWord.addTranslations(translations);
            return true;
        }
        return false;
    }

    // Замена всех переводов слова
    public boolean replaceTranslations(String word, Set<String> newTranslations) {
        DictionaryWord dictWord = dictionary.get(word.toLowerCase());
        if (dictWord != null) {
            dictWord.replaceTranslations(newTranslations);
            return true;
        }
        return false;
    }

    // Удаление перевода
    public boolean removeTranslation(String word, String translation) {
        DictionaryWord dictWord = dictionary.get(word.toLowerCase());
        if (dictWord != null) {
            return dictWord.removeTranslation(translation);
        }
        return false;
    }

    // Получение топ-10 самых популярных слов
    public List<DictionaryWord> getTop10Popular() {
        return dictionary.values().stream()
                .sorted((w1, w2) -> Integer.compare(w2.getAccessCount(), w1.getAccessCount()))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Получение топ-10 самых непопулярных слов
    public List<DictionaryWord> getTop10Unpopular() {
        return dictionary.values().stream()
                .sorted(Comparator.comparingInt(DictionaryWord::getAccessCount))
                .limit(10)
                .collect(Collectors.toList());
    }

    // Отображение топ-10 самых популярных слов
    public void displayTop10Popular() {
        System.out.println("\n=== ТОП-10 САМЫХ ПОПУЛЯРНЫХ СЛОВ ===");

        List<DictionaryWord> popularWords = getTop10Popular();

        if (popularWords.isEmpty()) {
            System.out.println("Словарь пуст.");
            return;
        }

        for (int i = 0; i < popularWords.size(); i++) {
            DictionaryWord word = popularWords.get(i);
            System.out.printf("%d. %s - %d обращений\n",
                    i + 1, word.getWord(), word.getAccessCount());
        }
    }

    // Отображение топ-10 самых непопулярных слов
    public void displayTop10Unpopular() {
        System.out.println("\n=== ТОП-10 САМЫХ НЕПОПУЛЯРНЫХ СЛОВ ===");

        List<DictionaryWord> unpopularWords = getTop10Unpopular();

        if (unpopularWords.isEmpty()) {
            System.out.println("Словарь пуст.");
            return;
        }

        for (int i = 0; i < unpopularWords.size(); i++) {
            DictionaryWord word = unpopularWords.get(i);
            System.out.printf("%d. %s - %d обращений\n",
                    i + 1, word.getWord(), word.getAccessCount());
        }
    }

    // Поиск слов по частичному совпадению
    public List<DictionaryWord> searchWords(String partialWord) {
        return dictionary.values().stream()
                .filter(word -> word.getWord().contains(partialWord.toLowerCase()))
                .sorted(Comparator.comparing(DictionaryWord::getWord))
                .collect(Collectors.toList());
    }

    // Отображение результатов поиска
    public void displaySearchResults(String partialWord) {
        System.out.println("\n=== РЕЗУЛЬТАТЫ ПОИСКА '" + partialWord + "' ===");

        List<DictionaryWord> foundWords = searchWords(partialWord);

        if (foundWords.isEmpty()) {
            System.out.println("Слова не найдены.");
        } else {
            foundWords.forEach(word -> {
                word.incrementAccessCount(); // Учитываем поиск как обращение
                System.out.printf("- %s (%d переводов, %d обращений)\n",
                        word.getWord(), word.getTranslationCount(), word.getAccessCount());
            });
        }
    }

    // Отображение статистики словаря
    public void displayStatistics() {
        System.out.println("\n=== СТАТИСТИКА СЛОВАРЯ ===");
        System.out.println("Направление: " + sourceLanguage + " -> " + targetLanguage);
        System.out.println("Общее количество слов: " + dictionary.size());

        int totalTranslations = dictionary.values().stream()
                .mapToInt(DictionaryWord::getTranslationCount)
                .sum();
        System.out.println("Общее количество переводов: " + totalTranslations);

        double averageTranslations = dictionary.isEmpty() ? 0 :
                (double) totalTranslations / dictionary.size();
        System.out.printf("Среднее количество переводов на слово: %.2f\n", averageTranslations);

        // Самое популярное слово
        Optional<DictionaryWord> mostPopular = dictionary.values().stream()
                .max(Comparator.comparingInt(DictionaryWord::getAccessCount));
        if (mostPopular.isPresent()) {
            DictionaryWord word = mostPopular.get();
            System.out.printf("Самое популярное слово: '%s' (%d обращений)\n",
                    word.getWord(), word.getAccessCount());
        }

        // Слово с наибольшим количеством переводов
        Optional<DictionaryWord> mostTranslations = dictionary.values().stream()
                .max(Comparator.comparingInt(DictionaryWord::getTranslationCount));
        if (mostTranslations.isPresent()) {
            DictionaryWord word = mostTranslations.get();
            System.out.printf("Слово с наибольшим количеством переводов: '%s' (%d переводов)\n",
                    word.getWord(), word.getTranslationCount());
        }
    }

    // Отображение всех слов в словаре
    public void displayAllWords() {
        System.out.println("\n=== ВСЕ СЛОВА В СЛОВАРЕ ===");

        if (dictionary.isEmpty()) {
            System.out.println("Словарь пуст.");
            return;
        }

        // Используем TreeMap для сортировки слов по алфавиту
        Map<String, DictionaryWord> sortedDictionary = new TreeMap<>(dictionary);

        sortedDictionary.forEach((word, dictWord) -> {
            System.out.printf("%s (%d переводов, %d обращений)\n",
                    word, dictWord.getTranslationCount(), dictWord.getAccessCount());
        });
    }

    // Геттеры
    public String getSourceLanguage() { return sourceLanguage; }
    public String getTargetLanguage() { return targetLanguage; }
    public int getDictionarySize() { return dictionary.size(); }

    // Получение слова по ключу (для тестирования)
    public DictionaryWord getWord(String word) {
        return dictionary.get(word.toLowerCase());
    }

    // Проверка существования слова
    public boolean containsWord(String word) {
        return dictionary.containsKey(word.toLowerCase());
    }
}
