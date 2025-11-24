package HW_Module_06;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinder implements Runnable {
    private final String filePath;
    private final CountDownLatch fileFilledLatch;
    private final CountDownLatch operationsCompletedLatch;
    private final AtomicInteger primeCount;

    public PrimeFinder(String filePath, CountDownLatch fileFilledLatch,
                       CountDownLatch operationsCompletedLatch, AtomicInteger primeCount) {
        this.filePath = filePath;
        this.fileFilledLatch = fileFilledLatch;
        this.operationsCompletedLatch = operationsCompletedLatch;
        this.primeCount = primeCount;
    }

    @Override
    public void run() {
        System.out.println("Поток поиска простых чисел ожидает заполнения файла...");

        try {
            fileFilledLatch.await();

            System.out.println("Поток поиска простых чисел начал работу...");

            List<Integer> primes = new ArrayList<>();

            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNextInt()) {
                    int number = scanner.nextInt();

                    if (isPrime(number)) {
                        primes.add(number);

                        Thread.sleep(30);
                    }
                }
            }

            String outputFile = filePath + ".primes.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                for (int prime : primes) {
                    writer.println(prime);
                }
            }

            primeCount.set(primes.size());
            System.out.println("Найдено простых чисел: " + primes.size() + " (файл: " + outputFile + ")");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        } finally {
            operationsCompletedLatch.countDown();
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) return false;
        if (number == 2) return true;
        if (number % 2 == 0) return false;

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) return false;
        }
        return true;
    }
}