package HW_Module_06;

import java.util.concurrent.CountDownLatch;

public class ArrayCalculationTask {
    public static final int ARRAY_SIZE = 10;
    private final int[] array = new int[ARRAY_SIZE];

    private final CountDownLatch startLatch = new CountDownLatch(1);
    private final CountDownLatch calculationLatch = new CountDownLatch(2);

    private volatile int sumResult = 0;
    private volatile double averageResult = 0.0;

    public void execute() {
        Thread fillerThread = new Thread(new ArrayFiller(array, startLatch));
        Thread sumThread = new Thread(new SumCalculator(array, startLatch, calculationLatch, this));
        Thread averageThread = new Thread(new AverageCalculator(array, startLatch, calculationLatch, this));
        fillerThread.start();
        sumThread.start();
        averageThread.start();

        try {
            calculationLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Основной поток был прерван");
        }
    }

    public Results getResults() {
        return new Results(array, sumResult, averageResult);
    }
    public void setSumResult(int sum) {
        this.sumResult = sum;
    }
    public void setAverageResult(double average) {
        this.averageResult = average;
    }
    public static class Results {
        private final int[] array;
        private final int sum;
        private final double average;

        public Results(int[] array, int sum, double average) {
            this.array = array.clone();
            this.sum = sum;
            this.average = average;
        }

        public int[] getArray() {
            return array.clone();
        }

        public int getSum() {
            return sum;
        }

        public double getAverage() {
            return average;
        }
    }
}
