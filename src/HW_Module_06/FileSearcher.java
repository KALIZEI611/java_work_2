package HW_Module_06;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FileSearcher implements Runnable {
    private final String searchDirectory;
    private final String searchWord;
    private final String outputFile;
    private final CountDownLatch completionLatch;
    private final List<String> errorMessages;

    private final AtomicInteger filesFound = new AtomicInteger(0);
    private final AtomicInteger mergedFileSize = new AtomicInteger(0);
    private boolean success = false;

    public FileSearcher(String searchDirectory, String searchWord, String outputFile,
                        CountDownLatch completionLatch, List<String> errorMessages) {
        this.searchDirectory = searchDirectory;
        this.searchWord = searchWord;
        this.outputFile = outputFile;
        this.completionLatch = completionLatch;
        this.errorMessages = errorMessages;
    }

    @Override
    public void run() {
        System.out.println("Поток поиска файлов запущен...");
        System.out.println("Поиск слова '" + searchWord + "' в директории: " + searchDirectory);

        try {
            List<File> matchingFiles = findFilesWithWord(searchDirectory, searchWord);

            if (matchingFiles.isEmpty()) {
                System.out.println("Файлы с искомым словом не найдены");
                success = true;
            } else {
                mergeFiles(matchingFiles, outputFile);
                success = true;
                System.out.println("Объединение файлов завершено. Результат в: " + outputFile);
            }

        } catch (Exception e) {
            errorMessages.add("Ошибка при поиске файлов: " + e.getMessage());
            System.err.println("Ошибка при поиске файлов: " + e.getMessage());
        } finally {
            completionLatch.countDown();
        }
    }

    private List<File> findFilesWithWord(String directoryPath, String word) throws IOException {
        List<File> result = new ArrayList<>();
        File directory = new File(directoryPath);

        if (!directory.exists() || !directory.isDirectory()) {
            throw new IOException("Директория не существует или не является директорией: " + directoryPath);
        }

        Files.walk(directory.toPath())
                .filter(Files::isRegularFile)
                .forEach(filePath -> {
                    try {
                        if (containsWord(filePath.toFile(), word)) {
                            result.add(filePath.toFile());
                            filesFound.incrementAndGet();
                            System.out.println("Найден файл: " + filePath);
                        }
                    } catch (IOException e) {
                        errorMessages.add("Ошибка при чтении файла " + filePath + ": " + e.getMessage());
                    }
                });

        return result;
    }

    private boolean containsWord(File file, String word) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(word.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void mergeFiles(List<File> files, String outputPath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputPath))) {
            writer.println("=== ОБЪЕДИНЕННОЕ СОДЕРЖИМОЕ ФАЙЛОВ ===");
            writer.println("Поисковое слово: " + searchWord);
            writer.println("Найдено файлов: " + files.size());
            writer.println("=" .repeat(50));
            writer.println();

            for (File file : files) {
                writer.println("ФАЙЛ: " + file.getName());
                writer.println("ПУТЬ: " + file.getAbsolutePath());
                writer.println("-".repeat(30));

                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.println(line);
                        mergedFileSize.addAndGet(line.length());
                    }
                }
                writer.println();
                writer.println();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Операция прервана");
                }
            }
        }

        File outputFile = new File(outputPath);
        if (outputFile.exists()) {
            mergedFileSize.set((int) outputFile.length());
        }
    }

    public int getFilesFound() {
        return filesFound.get();
    }
    public int getMergedFileSize() {
        return mergedFileSize.get();
    }
    public boolean isSuccess() {
        return success;
    }
}