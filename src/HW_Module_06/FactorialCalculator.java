package HW_Module_06;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FactorialCalculator implements Runnable {
    private final String filePath;
    private final CountDownLatch fileFilledLatch;
    private final CountDownLatch operationsCompletedLatch;
    private final AtomicInteger factorialCount;

    public FactorialCalculator(String filePath, CountDownLatch fileFilledLatch,
                               CountDownLatch operationsCompletedLatch, AtomicInteger factorialCount) {
        this.filePath = filePath;
        this.fileFilledLatch = fileFilledLatch;
        this.operationsCompletedLatch = operationsCompletedLatch;
        this.factorialCount = factorialCount;
    }

    @Override
    public void run() {
        System.out.println("Поток вычисления факториалов ожидает заполнения файла...");

        try {
            fileFilledLatch.await();

            System.out.println("Поток вычисления факториалов начал работу...");

            List<String> factorialResults = new ArrayList<>();

            try (Scanner scanner = new Scanner(new File(filePath))) {
                while (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    BigInteger factorial = calculateFactorial(number);
                    factorialResults.add(number + "! = " + factorial);

                    Thread.sleep(40);
                }
            }

            String outputFile = filePath + ".factorials.txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile))) {
                for (String result : factorialResults) {
                    writer.println(result);
                }
            }

            factorialCount.set(factorialResults.size());
            System.out.println("Вычислено факториалов: " + factorialResults.size() + " (файл: " + outputFile + ")");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        } finally {
            operationsCompletedLatch.countDown();
        }
    }

    private BigInteger calculateFactorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
}