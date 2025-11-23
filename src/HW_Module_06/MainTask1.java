package HW_Module_06;

import java.util.Arrays;

public class MainTask1 {
    public static void main(String[] args) {
        System.out.println("=== ЗАПУСК ПРИЛОЖЕНИЯ ===");
        System.out.println("Размер массива: " + ArrayCalculationTask.ARRAY_SIZE);
        ArrayCalculationTask task = new ArrayCalculationTask();
        task.execute();
        ArrayCalculationTask.Results results = task.getResults();
        System.out.println("\n=== РЕЗУЛЬТАТЫ В МЕТОДЕ MAIN ===");
        System.out.println("Сгенерированный массив: " + Arrays.toString(results.getArray()));
        System.out.println("Сумма элементов массива: " + results.getSum());
        System.out.println("Среднеарифметическое значение: " + results.getAverage());
    }
}
