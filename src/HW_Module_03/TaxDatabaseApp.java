package HW_Module_03;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaxDatabaseApp {
    private TaxInspectorateDatabase database;
    private Scanner scanner;

    public TaxDatabaseApp() {
        this.database = new TaxInspectorateDatabase();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println("=== БАЗА ДАННЫХ НАЛОГОВОЙ ИНСПЕКЦИИ ===");

        while (true) {
            displayMenu();
            int choice = getIntInput("Выберите действие: ");

            switch (choice) {
                case 1 -> database.printFullDatabase();
                case 2 -> printDataByPersonalId();
                case 3 -> printDataByFineType();
                case 4 -> printDataByCity();
                case 5 -> addNewPerson();
                case 6 -> addNewFine();
                case 7 -> removeFine();
                case 8 -> replacePersonInfo();
                case 9 -> showStatistics();
                case 0 -> {
                    System.out.println("Выход из программы...");
                    return;
                }
                default -> System.out.println("Неверный выбор. Попробуйте снова.");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Полная распечатка базы данных");
        System.out.println("2. Распечатка данных по конкретному коду");
        System.out.println("3. Распечатка данных по типу штрафа");
        System.out.println("4. Распечатка данных по городу");
        System.out.println("5. Добавление нового человека");
        System.out.println("6. Добавление штрафа существующему человеку");
        System.out.println("7. Удаление штрафа");
        System.out.println("8. Замена информации о человеке");
        System.out.println("9. Показать статистику");
        System.out.println("0. Выход");
    }

    private void printDataByPersonalId() {
        System.out.println("\n--- ПОИСК ПО ПЕРСОНАЛЬНОМУ КОДУ ---");
        String personalId = getStringInput("Введите персональный код: ");
        database.printDataByPersonalId(personalId);
    }

    private void printDataByFineType() {
        System.out.println("\n--- ПОИСК ПО ТИПУ ШТРАФА ---");
        System.out.println("Доступные типы штрафов: " + String.join(", ", database.getAllFineTypes()));
        String fineType = getStringInput("Введите тип штрафа: ");
        database.printDataByFineType(fineType);
    }

    private void printDataByCity() {
        System.out.println("\n--- ПОИСК ПО ГОРОДУ ---");
        System.out.println("Доступные города: " + String.join(", ", database.getAllCities()));
        String city = getStringInput("Введите город: ");
        database.printDataByCity(city);
    }

    private void addNewPerson() {
        System.out.println("\n--- ДОБАВЛЕНИЕ НОВОГО ЧЕЛОВЕКА ---");
        String personalId = getStringInput("Персональный код: ");
        String firstName = getStringInput("Имя: ");
        String lastName = getStringInput("Фамилия: ");
        String address = getStringInput("Адрес: ");
        String phone = getStringInput("Телефон: ");

        database.addPerson(personalId, firstName, lastName, address, phone);
    }

    private void addNewFine() {
        System.out.println("\n--- ДОБАВЛЕНИЕ ШТРАФА ---");
        String personalId = getStringInput("Персональный код человека: ");
        String fineId = getStringInput("ID штрафа: ");
        String type = getStringInput("Тип штрафа: ");
        double amount = getDoubleInput("Сумма штрафа: ");
        String city = getStringInput("Город: ");
        String description = getStringInput("Описание: ");

        Fine fine = new Fine(fineId, type, amount, city, description);
        database.addFineToPerson(personalId, fine);
    }

    private void removeFine() {
        System.out.println("\n--- УДАЛЕНИЕ ШТРАФА ---");
        String personalId = getStringInput("Персональный код человека: ");
        String fineId = getStringInput("ID штрафа для удаления: ");

        database.removeFine(personalId, fineId);
    }

    private void replacePersonInfo() {
        System.out.println("\n--- ЗАМЕНА ИНФОРМАЦИИ О ЧЕЛОВЕКЕ ---");
        String personalId = getStringInput("Персональный код: ");
        String newFirstName = getStringInput("Новое имя: ");
        String newLastName = getStringInput("Новая фамилия: ");
        String newAddress = getStringInput("Новый адрес: ");
        String newPhone = getStringInput("Новый телефон: ");

        List<Fine> newFines = new ArrayList<>();
        database.replacePersonInfo(personalId, newFirstName, newLastName, newAddress, newPhone, newFines);
    }

    private void showStatistics() {
        database.printFullDatabase();
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите число.");
            }
        }
    }

    public static void main(String[] args) {
        TaxDatabaseApp app = new TaxDatabaseApp();
        app.run();
    }
}
