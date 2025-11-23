package HW_Module_06;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ContentFilter implements Runnable {
    private final String inputFile;
    private final String outputFile;
    private final String bannedWordsFile;
    private final CountDownLatch searchCompletedLatch;
    private final CountDownLatch filterCompletedLatch;
    private final List<String> errorMessages;

    private final AtomicInteger wordsRemoved = new AtomicInteger(0);
    private boolean success = false;

    public ContentFilter(String inputFile, String outputFile, String bannedWordsFile,
                         CountDownLatch searchCompletedLatch, CountDownLatch filterCompletedLatch,
                         List<String> errorMessages) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.bannedWordsFile = bannedWordsFile;
        this.searchCompletedLatch = searchCompletedLatch;
        this.filterCompletedLatch = filterCompletedLatch;
        this.errorMessages = errorMessages;
    }

    @Override
    public void run() {
        System.out.println("Поток фильтрации ожидает завершения поиска...");

        try {
            searchCompletedLatch.await();

            System.out.println("Поток фильтрации начал работу...");

            Set<String> bannedWords = loadBannedWords(bannedWordsFile);
            System.out.println("Загружено запрещенных слов: " + bannedWords.size());

            filterContent(inputFile, outputFile, bannedWords);

            success = true;
            System.out.println("Фильтрация завершена. Результат в: " + outputFile);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            errorMessages.add("Фильтрация была прервана");
        } catch (Exception e) {
            errorMessages.add("Ошибка при фильтрации: " + e.getMessage());
            System.err.println("Ошибка при фильтрации: " + e.getMessage());
        } finally {
            filterCompletedLatch.countDown();
        }
    }

    private Set<String> loadBannedWords(String bannedWordsFile) throws IOException {
        Set<String> bannedWords = new HashSet<>();
        File file = new File(bannedWordsFile);

        if (!file.exists()) {
            createDefaultBannedWordsFile(bannedWordsFile);
            System.out.println("Создан файл с запрещенными словами по умолчанию");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(bannedWordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();
                if (!line.isEmpty() && !line.startsWith("#")) { // Игнорируем комментарии
                    bannedWords.add(line);
                }
            }
        }

        return bannedWords;
    }

    private void createDefaultBannedWordsFile(String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("# Файл с запрещенными словами");
            writer.println("# Каждое слово на отдельной строке");
            writer.println("плохое");
            writer.println("запрещенное");
            writer.println("нежелательное");
            writer.println("спам");
            writer.println("реклама");
        }
    }

    private void filterContent(String inputPath, String outputPath, Set<String> bannedWords) throws IOException {
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            throw new IOException("Входной файл не существует: " + inputPath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String filteredLine = filterLine(line, bannedWords);
                writer.println(filteredLine);

                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Фильтрация прервана");
                }
            }
        }
    }

    private String filterLine(String line, Set<String> bannedWords) {
        String[] words = line.split("\\s+");
        StringBuilder filteredLine = new StringBuilder();

        for (String word : words) {
            String cleanWord = word.toLowerCase().replaceAll("[^a-zA-Zа-яА-Я]", "");

            if (bannedWords.contains(cleanWord)) {
                filteredLine.append("[УДАЛЕНО] ");
                wordsRemoved.incrementAndGet();
            } else {
                filteredLine.append(word).append(" ");
            }
        }

        return filteredLine.toString().trim();
    }

    public int getWordsRemoved() {
        return wordsRemoved.get();
    }
    public boolean isSuccess() {
        return success;
    }
}