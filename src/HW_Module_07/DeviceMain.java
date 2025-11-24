package HW_Module_07;
import java.util.List;
import java.util.Scanner;

public class DeviceMain {
    public static void main(String[] args) {
        List<Device> devices = DeviceDataGenerator.generateFixedTestDevices();

        DeviceAnalyzer analyzer = new DeviceAnalyzer(devices);

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
                        analyzer.showAllDevices();
                        break;
                    case 2:
                        analyzer.showDevicesByColor();
                        break;
                    case 3:
                        analyzer.showDevicesByReleaseYear();
                        break;
                    case 4:
                        analyzer.showDevicesMoreExpensiveThan();
                        break;
                    case 5:
                        analyzer.showDevicesByType();
                        break;
                    case 6:
                        analyzer.showDevicesByYearRange();
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
                System.out.println("\n" + "=".repeat(60));
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n=== АНАЛИЗ УСТРОЙСТВ ===");
        System.out.println("1. Показать все устройства");
        System.out.println("2. Показать устройства заданного цвета");
        System.out.println("3. Показать устройства заданного года выпуска");
        System.out.println("4. Показать устройства дороже заданной цены");
        System.out.println("5. Показать устройства заданного типа");
        System.out.println("6. Показать устройства по диапазону лет выпуска");
        System.out.println("0. Выход");
    }
}