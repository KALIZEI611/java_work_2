package HW_Module_03;

import java.util.*;

// Класс для представления слова и его переводов
class DictionaryWord {
    private String word;
    private Set<String> translations;
    private int accessCount;

    public DictionaryWord(String word) {
        this.word = word.toLowerCase();
        this.translations = new TreeSet<>(); // TreeSet для сортированных переводов
        this.accessCount = 0;
    }

    public DictionaryWord(String word, Set<String> translations) {
        this.word = word.toLowerCase();
        this.translations = new TreeSet<>(translations);
        this.accessCount = 0;
    }

    public void addTranslation(String translation) {
        translations.add(translation.toLowerCase());
    }

    public void addTranslations(Set<String> newTranslations) {
        translations.addAll(newTranslations);
    }

    public boolean removeTranslation(String translation) {
        return translations.remove(translation.toLowerCase());
    }

    public void replaceTranslations(Set<String> newTranslations) {
        translations.clear();
        translations.addAll(newTranslations);
    }

    public void incrementAccessCount() {
        accessCount++;
    }

    // Геттеры
    public String getWord() {
        return word;
    }

    public Set<String> getTranslations() {
        return new TreeSet<>(translations);
    }

    public int getAccessCount() {
        return accessCount;
    }

    public int getTranslationCount() {
        return translations.size();
    }

    @Override
    public String toString() {
        return String.format("Слово: '%s' (обращений: %d)\nПереводы: %s",
                word, accessCount, String.join(", ", translations));
    }
}