package HW_Module_08;

import java.util.Scanner;

public class LengthApp {
    public static void main(String[] args) {
        LengthConverter converter = LengthConverter.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Конвертер длины ===");

        boolean running = true;
        while (running) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Конвертировать значение");
            System.out.println("2. Конвертировать во все единицы");
            System.out.println("3. Сложение длин");
            System.out.println("4. Вычитание длин");
            System.out.println("5. Изменить точность");
            System.out.println("6. Показать информацию о единицах");
            System.out.println("7. Выход");

            System.out.print("Ваш выбор: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    performConversion(converter, scanner);
                    break;

                case 2:
                    convertToAllUnits(converter, scanner);
                    break;

                case 3:
                    performAddition(converter, scanner);
                    break;

                case 4:
                    performSubtraction(converter, scanner);
                    break;

                case 5:
                    setPrecision(converter, scanner);
                    break;

                case 6:
                    showUnitInfo();
                    break;

                case 7:
                    running = false;
                    break;

                default:
                    System.out.println("Неверный выбор");
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");

        System.out.println("\n=== Примеры работы ===");

        LengthUnit meter = LengthFactory.createUnit("м");
        LengthUnit centimeter = LengthFactory.createUnit("см");

        double result = converter.convert(1.5, meter, centimeter);
        System.out.println("1.5 м = " + result + " см");

        double[] allResults = converter.convertToAll(100, centimeter);
        LengthUnit[] units = LengthUnit.values();
        for (int i = 0; i < units.length; i++) {
            System.out.println(units[i].getSymbol() + ": " + allResults[i]);
        }
    }

    private static void performConversion(LengthConverter converter, Scanner scanner) {
        System.out.print("Введите значение: ");
        double value = scanner.nextDouble();

        System.out.print("Введите исходную единицу (мм, см, дм, м, км): ");
        String fromSymbol = scanner.next();

        System.out.print("Введите целевую единицу (мм, см, дм, м, км): ");
        String toSymbol = scanner.next();

        try {
            LengthUnit from = LengthFactory.createUnit(fromSymbol);
            LengthUnit to = LengthFactory.createUnit(toSymbol);

            String result = converter.convertFormatted(value, from, to);
            System.out.printf("%.2f %s = %s%n", value, from.getSymbol(), result);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void convertToAllUnits(LengthConverter converter, Scanner scanner) {
        System.out.print("Введите значение: ");
        double value = scanner.nextDouble();

        System.out.print("Введите единицу измерения (мм, см, дм, м, км): ");
        String unitSymbol = scanner.next();

        try {
            LengthUnit from = LengthFactory.createUnit(unitSymbol);
            double[] results = converter.convertToAll(value, from);
            LengthUnit[] units = LengthUnit.values();

            System.out.println("\nРезультаты конвертации:");
            for (int i = 0; i < units.length; i++) {
                System.out.printf("%s: %.10f%n", units[i].getSymbol(), results[i]);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void performAddition(LengthConverter converter, Scanner scanner) {
        System.out.print("Введите первое значение: ");
        double value1 = scanner.nextDouble();
        System.out.print("Введите единицу первого значения: ");
        String unit1Symbol = scanner.next();

        System.out.print("Введите второе значение: ");
        double value2 = scanner.nextDouble();
        System.out.print("Введите единицу второго значения: ");
        String unit2Symbol = scanner.next();

        System.out.print("Введите единицу результата: ");
        String resultUnitSymbol = scanner.next();

        try {
            LengthUnit unit1 = LengthFactory.createUnit(unit1Symbol);
            LengthUnit unit2 = LengthFactory.createUnit(unit2Symbol);
            LengthUnit resultUnit = LengthFactory.createUnit(resultUnitSymbol);

            double result = converter.add(value1, unit1, value2, unit2, resultUnit);
            System.out.printf("%.2f %s + %.2f %s = %.2f %s%n",
                    value1, unit1.getSymbol(), value2, unit2.getSymbol(), result, resultUnit.getSymbol());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void performSubtraction(LengthConverter converter, Scanner scanner) {
        System.out.print("Введите уменьшаемое: ");
        double value1 = scanner.nextDouble();
        System.out.print("Введите единицу уменьшаемого: ");
        String unit1Symbol = scanner.next();

        System.out.print("Введите вычитаемое: ");
        double value2 = scanner.nextDouble();
        System.out.print("Введите единицу вычитаемого: ");
        String unit2Symbol = scanner.next();

        System.out.print("Введите единицу результата: ");
        String resultUnitSymbol = scanner.next();

        try {
            LengthUnit unit1 = LengthFactory.createUnit(unit1Symbol);
            LengthUnit unit2 = LengthFactory.createUnit(unit2Symbol);
            LengthUnit resultUnit = LengthFactory.createUnit(resultUnitSymbol);

            double result = converter.subtract(value1, unit1, value2, unit2, resultUnit);
            System.out.printf("%.2f %s - %.2f %s = %.2f %s%n",
                    value1, unit1.getSymbol(), value2, unit2.getSymbol(), result, resultUnit.getSymbol());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void setPrecision(LengthConverter converter, Scanner scanner) {
        System.out.print("Введите количество знаков после запятой (0-10): ");
        int precision = scanner.nextInt();

        try {
            converter.setPrecision(precision);
            System.out.println("Точность установлена: " + precision + " знаков");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showUnitInfo() {
        System.out.println("\nЕдиницы измерения длины:");
        for (LengthUnit unit : LengthFactory.getAllUnits()) {
            System.out.println(LengthFactory.getUnitInfo(unit));
        }
    }
}