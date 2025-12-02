package HW_Module_08;


import java.util.Scanner;

public class CurrencyApp {
    public static void main(String[] args) {
        CurrencyConverter converter = CurrencyConverter.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Конвертер валют ===");

        boolean running = true;
        while (running) {
            System.out.println("\nДоступные валюты:");
            for (Currency currency : CurrencyFactory.getAllCurrencies()) {
                System.out.println(CurrencyFactory.getCurrencyInfo(currency));
            }

            System.out.print("\nВведите сумму: ");
            double amount = scanner.nextDouble();

            System.out.print("Введите исходную валюту (USD, EUR, GBP, JPY): ");
            String fromCode = scanner.next();

            System.out.print("Введите целевую валюту (USD, EUR, GBP, JPY): ");
            String toCode = scanner.next();

            try {
                Currency from = CurrencyFactory.createCurrency(fromCode);
                Currency to = CurrencyFactory.createCurrency(toCode);

                double result = converter.convert(amount, from, to);
                double commission = converter.calculateCommission(result);

                System.out.printf("\nРезультат конвертации: %.2f %s -> %.2f %s%n",
                        amount, from.getName(), result, to.getName());
                System.out.printf("Комиссия: %.2f %s (%.1f%%)%n",
                        commission, to.getName(), converter.getCommissionRate() * 100);

            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            System.out.print("\nПродолжить? (да/нет): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("да")) {
                running = false;
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");
    }
}
