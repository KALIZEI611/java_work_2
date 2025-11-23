package HW_Module_06;

import java.util.Scanner;

public class MainTask2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ФАЙЛОВЫЕ ОПЕРАЦИИ С МНОГОПОТОЧНОСТЬЮ ===");
        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();
        FileOperationsTask task = new FileOperationsTask(filePath);
        FileOperationsTask.Statistics statistics = task.execute();
        System.out.println("\n=== СТАТИСТИКА В МЕТОДЕ MAIN ===");
        System.out.println("Общее количество чисел в файле: " + statistics.getTotalNumbers());
        System.out.println("Количество найденных простых чисел: " + statistics.getPrimeCount());
        System.out.println("Количество вычисленных факториалов: " + statistics.getFactorialCount());
        System.out.println("Файл с простыми числами: " + statistics.getPrimeFile());
        System.out.println("Файл с факториалами: " + statistics.getFactorialFile());
        System.out.println("Время выполнения (мс): " + statistics.getExecutionTime());
        scanner.close();
    }
}
