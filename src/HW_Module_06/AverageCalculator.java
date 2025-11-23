package HW_Module_06;

import java.util.concurrent.CountDownLatch;

public class AverageCalculator implements Runnable {
    private final int[] array;
    private final CountDownLatch startLatch;
    private final CountDownLatch calculationLatch;
    private final ArrayCalculationTask task;

    public AverageCalculator(int[] array, CountDownLatch startLatch, CountDownLatch calculationLatch, ArrayCalculationTask task) {
        this.array = array;
        this.startLatch = startLatch;
        this.calculationLatch = calculationLatch;
        this.task = task;
    }

    @Override
    public void run() {
        System.out.println("Поток вычисления среднего ожидает заполнения массива...");

        try {
            startLatch.await();

            System.out.println("Поток вычисления среднего начал работу...");
            int sum = 0;
            for (int num : array) {
                sum += num;
                Thread.sleep(50);
            }

            double average = (double) sum / array.length;
            task.setAverageResult(average);
            System.out.println("Среднее арифметическое вычислено: " + average);
            calculationLatch.countDown();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
