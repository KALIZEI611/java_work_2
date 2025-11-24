package HW_Module_07;

import java.util.List;
import java.util.Scanner;

public class MainTask2 {
    public static void main(String[] args) {
        List<Product> products = ProductDataGenerator.generateFixedTestProducts();
        ProductAnalyzer analyzer = new ProductAnalyzer(products);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printMenu();
            System.out.print("Выберите действие: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        analyzer.showAllProducts();
                        break;
                    case 2:
                        analyzer.showProductsWithShortNames();
                        break;
                    case 3:
                        analyzer.countProductOccurrences();
                        break;
                    case 4:
                        analyzer.showProductsStartingWithLetter();
                        break;
                    case 5:
                        analyzer.showMilkProducts();
                        break;
                    case 6:
                        analyzer.showProductStatistics();
                        break;
                    case 0:
                        exit = true;
                        System.out.println("Выход из программы.");
                        break;
                    default:
                        System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } else {
                System.out.println("Неверный ввод. Введите число.");
                scanner.nextLine(); // очистка неверного ввода
            }

            if (!exit) {
                System.out.println("\n" + "=".repeat(50));
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== АНАЛИЗ ПРОДУКТОВ ===");
        System.out.println("1. Показать все продукты");
        System.out.println("2. Показать продукты с названием < 5 символов");
        System.out.println("3. Посчитать количество определенного продукта");
        System.out.println("4. Показать продукты, начинающиеся на букву");
        System.out.println("5. Показать продукты категории 'Молоко'");
        System.out.println("6. Показать статистику");
        System.out.println("0. Выход");
    }
}
