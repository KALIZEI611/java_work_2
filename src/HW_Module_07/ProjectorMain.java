package HW_Module_07;
import java.util.List;
import java.util.Scanner;

public class ProjectorMain {
    public static void main(String[] args) {
        List<Projector> projectors = ProjectorDataGenerator.generateFixedTestProjectors();
        ProjectorAnalyzer analyzer = new ProjectorAnalyzer(projectors);

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
                        analyzer.showAllProjectors();
                        break;
                    case 2:
                        analyzer.showProjectorsByManufacturer();
                        break;
                    case 3:
                        analyzer.showCurrentYearProjectors();
                        break;
                    case 4:
                        analyzer.showProjectorsMoreExpensiveThan();
                        break;
                    case 5:
                        analyzer.showProjectorsSortedByPriceAscending();
                        break;
                    case 6:
                        analyzer.showProjectorsSortedByPriceDescending();
                        break;
                    case 7:
                        analyzer.showProjectorsSortedByYearAscending();
                        break;
                    case 8:
                        analyzer.showProjectorsSortedByYearDescending();
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
                scanner.nextLine();
            }

            if (!exit) {
                System.out.println("\n" + "=".repeat(70));
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== АНАЛИЗ ПРОЕКТОРОВ ===");
        System.out.println("1. Показать все проекторы");
        System.out.println("2. Показать проекторы одного производителя");
        System.out.println("3. Показать проекторы текущего года");
        System.out.println("4. Показать проекторы дороже заданной цены");
        System.out.println("5. Показать проекторы (сортировка по цене ↑)");
        System.out.println("6. Показать проекторы (сортировка по цене ↓)");
        System.out.println("7. Показать проекторы (сортировка по году ↑)");
        System.out.println("8. Показать проекторы (сортировка по году ↓)");
        System.out.println("0. Выход");
    }
}
