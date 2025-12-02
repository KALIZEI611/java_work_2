package HW_Module_08;

import java.util.Scanner;

public class WeightApp {
    public static void main(String[] args) {
        WeightConverter converter = WeightConverter.getInstance();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Конвертер веса ===");

        boolean running = true;
        while (running) {
            System.out.println("\nВыберите операцию:");
            System.out.println("1. Конвертировать значение");
            System.out.println("2. Конвертировать во все единицы");
            System.out.println("3. Сложение весов");
            System.out.println("4. Вычитание весов");
            System.out.println("5. Умножение веса");
            System.out.println("6. Деление веса");
            System.out.println("7. Изменить точность");
            System.out.println("8. Показать информацию о единицах");
            System.out.println("9. Выход");

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
                    performMultiplication(converter, scanner);
                    break;

                case 6:
                    performDivision(converter, scanner);
                    break;

                case 7:
                    setPrecision(converter, scanner);
                    break;

                case 8:
                    showUnitInfo();
                    break;

                case 9:
                    running = false;
                    break;

                default:
                    System.out.println("Неверный выбор");
            }
        }

        scanner.close();
        System.out.println("Программа завершена.");

        System.out.println("\n=== Примеры работы ===");

        WeightUnit kilogram = WeightFactory.createUnit("кг");
        WeightUnit gram = WeightFactory.createUnit("г");

        double result = converter.convert(2.5, kilogram, gram);
        System.out.println("2.5 кг = " + result + " г");

        double[] allResults = converter.convertToAll(1000, gram);
        WeightUnit[] units = WeightUnit.values();
        for (int i = 0; i < units.length; i++) {
            System.out.println(units[i].getSymbol() + ": " + allResults[i]);
        }
    }

    private static void performConversion(WeightConverter converter, Scanner scanner) {
        System.out.print("Введите значение: ");
        double value = scanner.nextDouble();

        System.out.print("Введите исходную единицу (мг, г, кг, ц, т): ");
        String fromSymbol = scanner.next();

        System.out.print("Введите целевую единицу (мг, г, кг, ц, т): ");
        String toSymbol = scanner.next();

        try {
            WeightUnit from = WeightFactory.createUnit(fromSymbol);
            WeightUnit to = WeightFactory.createUnit(toSymbol);

            String result = converter.convertFormatted(value, from, to);
            System.out.printf("%.2f %s = %s%n", value, from.getSymbol(), result);

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void convertToAllUnits(WeightConverter converter, Scanner scanner) {
        System.out.print("Введите значение: ");
        double value = scanner.nextDouble();

        System.out.print("Введите единицу измерения (мг, г, кг, ц, т): ");
        String unitSymbol = scanner.next();

        try {
            WeightUnit from = WeightFactory.createUnit(unitSymbol);
            double[] results = converter.convertToAll(value, from);
            WeightUnit[] units = WeightUnit.values();

            System.out.println("\nРезультаты конвертации:");
            for (int i = 0; i < units.length; i++) {
                System.out.printf("%s: %.10f%n", units[i].getSymbol(), results[i]);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void performAddition(WeightConverter converter, Scanner scanner) {
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
            WeightUnit unit1 = WeightFactory.createUnit(unit1Symbol);
            WeightUnit unit2 = WeightFactory.createUnit(unit2Symbol);
            WeightUnit resultUnit = WeightFactory.createUnit(resultUnitSymbol);

            double result = converter.add(value1, unit1, value2, unit2, resultUnit);
            System.out.printf("%.2f %s + %.2f %s = %.2f %s%n",
                    value1, unit1.getSymbol(), value2, unit2.getSymbol(), result, resultUnit.getSymbol());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void performSubtraction(WeightConverter converter, Scanner scanner) {
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
            WeightUnit unit1 = WeightFactory.createUnit(unit1Symbol);
            WeightUnit unit2 = WeightFactory.createUnit(unit2Symbol);
            WeightUnit resultUnit = WeightFactory.createUnit(resultUnitSymbol);

            double result = converter.subtract(value1, unit1, value2, unit2, resultUnit);
            System.out.printf("%.2f %s - %.2f %s = %.2f %s%n",
                    value1, unit1.getSymbol(), value2, unit2.getSymbol(), result, resultUnit.getSymbol());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void performMultiplication(WeightConverter converter, Scanner scanner) {
        System.out.print("Введите значение: ");
        double value = scanner.nextDouble();
        System.out.print("Введите единицу значения: ");
        String unitSymbol = scanner.next();

        System.out.print("Введите множитель: ");
        double multiplier = scanner.nextDouble();

        try {
            WeightUnit unit = WeightFactory.createUnit(unitSymbol);
            double result = converter.multiply(value, unit, multiplier);
            System.out.printf("%.2f %s * %.2f = %.2f %s%n",
                    value, unit.getSymbol(), multiplier, result, unit.getSymbol());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void performDivision(WeightConverter converter, Scanner scanner) {
        System.out.print("Введите значение: ");
        double value = scanner.nextDouble();
        System.out.print("Введите единицу значения: ");
        String unitSymbol = scanner.next();

        System.out.print("Введите делитель: ");
        double divisor = scanner.nextDouble();

        try {
            WeightUnit unit = WeightFactory.createUnit(unitSymbol);
            double result = converter.divide(value, unit, divisor);
            System.out.printf("%.2f %s / %.2f = %.2f %s%n",
                    value, unit.getSymbol(), divisor, result, unit.getSymbol());

        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void setPrecision(WeightConverter converter, Scanner scanner) {
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
        System.out.println("\nЕдиницы измерения веса:");
        for (WeightUnit unit : WeightFactory.getAllUnits()) {
            System.out.println(WeightFactory.getUnitInfo(unit));
        }
    }
}