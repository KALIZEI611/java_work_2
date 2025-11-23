package HW_Module_06;


import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class ArrayFiller implements Runnable {
    private final int[] array;
    private final CountDownLatch startLatch;

    public ArrayFiller(int[] array, CountDownLatch startLatch) {
        this.array = array;
        this.startLatch = startLatch;
    }

    @Override
    public void run() {
        System.out.println("Поток заполнения массива запущен...");
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(100) + 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("Массив заполнен: " + java.util.Arrays.toString(array));
        startLatch.countDown();
    }
}
