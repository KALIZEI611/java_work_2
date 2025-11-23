package HW_Module_06;

import java.util.Scanner;

public class MainTask4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ПОИСК И ФИЛЬТРАЦИЯ ФАЙЛОВ ===");
        System.out.print("Введите путь к директории для поиска: ");
        String searchDir = scanner.nextLine();

        System.out.print("Введите слово для поиска: ");
        String searchWord = scanner.nextLine();

        System.out.print("Введите путь к файлу с запрещенными словами: ");
        String bannedWordsFile = scanner.nextLine();

        FileSearchAndFilterTask task = new FileSearchAndFilterTask(searchDir, searchWord, bannedWordsFile);
        FileSearchAndFilterTask.OperationStatistics statistics = task.execute();

        System.out.println("\n=== СТАТИСТИКА В МЕТОДЕ MAIN ===");
        System.out.println("Директория поиска: " + statistics.getSearchDirectory());
        System.out.println("Искомое слово: " + statistics.getSearchWord());
        System.out.println("Файл с запрещенными словами: " + statistics.getBannedWordsFile());
        System.out.println("Найдено файлов с искомым словом: " + statistics.getFilesFound());
        System.out.println("Общий размер объединенного файла: " + statistics.getMergedFileSize() + " байт");
        System.out.println("Удалено запрещенных слов: " + statistics.getWordsRemoved());
        System.out.println("Финальный файл: " + statistics.getFinalOutputFile());
        System.out.println("Время выполнения: " + statistics.getExecutionTime() + " мс");
        System.out.println("Статус операции: " + (statistics.isSuccess() ? "УСПЕХ" : "ОШИБКА"));

        if (!statistics.getErrorMessages().isEmpty()) {
            System.out.println("\nОшибки при выполнении:");
            for (String error : statistics.getErrorMessages()) {
                System.out.println("  - " + error);
            }
        }

        scanner.close();
    }
}