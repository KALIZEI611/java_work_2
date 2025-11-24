package HW_Module_06;

import java.io.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FileFiller implements Runnable {
    private final String filePath;
    private final CountDownLatch fileFilledLatch;
    private final AtomicInteger totalNumbers;
    private static final int NUMBERS_COUNT = 20;
    private static final int MAX_NUMBER = 50;

    public FileFiller(String filePath, CountDownLatch fileFilledLatch, AtomicInteger totalNumbers) {
        this.filePath = filePath;
        this.fileFilledLatch = fileFilledLatch;
        this.totalNumbers = totalNumbers;
    }

    @Override
    public void run() {
        System.out.println("Поток заполнения файла запущен...");

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            Random random = new Random();

            for (int i = 0; i < NUMBERS_COUNT; i++) {
                int number = random.nextInt(MAX_NUMBER) + 1;
                writer.println(number);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            totalNumbers.set(NUMBERS_COUNT);
            System.out.println("Файл заполнен: " + filePath + " (числа: " + NUMBERS_COUNT + ")");

        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
        } finally {
            fileFilledLatch.countDown();
        }
    }
}