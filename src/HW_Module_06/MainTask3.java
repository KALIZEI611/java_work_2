package HW_Module_06;

import java.util.Scanner;

public class MainTask3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== КОПИРОВАНИЕ ДИРЕКТОРИИ ===");
        System.out.print("Введите путь к исходной директории: ");
        String sourceDir = scanner.nextLine();

        System.out.print("Введите путь к новой директории: ");
        String targetDir = scanner.nextLine();

        DirectoryCopyTask copyTask = new DirectoryCopyTask(sourceDir, targetDir);
        DirectoryCopyTask.CopyStatistics statistics = copyTask.execute();

        System.out.println("\n=== СТАТИСТИКА В МЕТОДЕ MAIN ===");
        System.out.println("Исходная директория: " + statistics.getSourceDirectory());
        System.out.println("Целевая директория: " + statistics.getTargetDirectory());
        System.out.println("Скопировано файлов: " + statistics.getFilesCopied());
        System.out.println("Скопировано директорий: " + statistics.getDirectoriesCreated());
        System.out.println("Общий размер скопированных данных: " + statistics.getTotalBytesCopied() + " байт");
        System.out.println("Время выполнения: " + statistics.getExecutionTime() + " мс");
        System.out.println("Статус операции: " + (statistics.isSuccess() ? "УСПЕХ" : "ОШИБКА"));

        if (!statistics.getErrorMessages().isEmpty()) {
            System.out.println("\nОшибки при копировании:");
            for (String error : statistics.getErrorMessages()) {
                System.out.println("  - " + error);
            }
        }

        scanner.close();
    }
}
