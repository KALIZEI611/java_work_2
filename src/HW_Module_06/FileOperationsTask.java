package HW_Module_06;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class FileOperationsTask {
    private final String filePath;
    private final CountDownLatch fileFilledLatch = new CountDownLatch(1);
    private final CountDownLatch operationsCompletedLatch = new CountDownLatch(2);

    private final AtomicInteger primeCount = new AtomicInteger(0);
    private final AtomicInteger factorialCount = new AtomicInteger(0);
    private final AtomicInteger totalNumbers = new AtomicInteger(0);
    private long executionTime;

    public FileOperationsTask(String filePath) {
        this.filePath = filePath;
    }

    public Statistics execute() {
        long startTime = System.currentTimeMillis();

        Thread fileFillerThread = new Thread(new FileFiller(filePath, fileFilledLatch, totalNumbers));
        Thread primeFinderThread = new Thread(new PrimeFinder(filePath, fileFilledLatch, operationsCompletedLatch, primeCount));
        Thread factorialCalculatorThread = new Thread(new FactorialCalculator(filePath, fileFilledLatch, operationsCompletedLatch, factorialCount));

        fileFillerThread.start();
        primeFinderThread.start();
        factorialCalculatorThread.start();

        try {
            operationsCompletedLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Основной поток был прерван");
        }

        executionTime = System.currentTimeMillis() - startTime;

        return new Statistics(
                totalNumbers.get(),
                primeCount.get(),
                factorialCount.get(),
                filePath + ".primes.txt",
                filePath + ".factorials.txt",
                executionTime
        );
    }

    public static class Statistics {
        private final int totalNumbers;
        private final int primeCount;
        private final int factorialCount;
        private final String primeFile;
        private final String factorialFile;
        private final long executionTime;

        public Statistics(int totalNumbers, int primeCount, int factorialCount,
                          String primeFile, String factorialFile, long executionTime) {
            this.totalNumbers = totalNumbers;
            this.primeCount = primeCount;
            this.factorialCount = factorialCount;
            this.primeFile = primeFile;
            this.factorialFile = factorialFile;
            this.executionTime = executionTime;
        }

        public int getTotalNumbers() { return totalNumbers; }
        public int getPrimeCount() { return primeCount; }
        public int getFactorialCount() { return factorialCount; }
        public String getPrimeFile() { return primeFile; }
        public String getFactorialFile() { return factorialFile; }
        public long getExecutionTime() { return executionTime; }
    }
}
