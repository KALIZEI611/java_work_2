package HW_Module_08;

import java.util.Scanner;

public class GeometryApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Калькулятор площадей геометрических фигур ===");

        while (true) {
            printMenu();
            int choice = scanner.nextInt();

            if (choice == 0) {
                System.out.println("Выход из программы.");
                break;
            }

            try {
                processChoice(choice, scanner);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("Выберите фигуру:");
        System.out.println("1. Треугольник (через основание и высоту)");
        System.out.println("2. Треугольник (через три стороны)");
        System.out.println("3. Прямоугольник");
        System.out.println("4. Квадрат");
        System.out.println("5. Ромб (через диагонали)");
        System.out.println("6. Ромб (через сторону и высоту)");
        System.out.println("0. Выход");
        System.out.print("Ваш выбор: ");
    }

    private static void processChoice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                calculateTriangleBaseHeight(scanner);
                break;
            case 2:
                calculateTriangleThreeSides(scanner);
                break;
            case 3:
                calculateRectangle(scanner);
                break;
            case 4:
                calculateSquare(scanner);
                break;
            case 5:
                calculateRhombusDiagonals(scanner);
                break;
            case 6:
                calculateRhombusSideHeight(scanner);
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private static void calculateTriangleBaseHeight(Scanner scanner) {
        System.out.print("Введите основание треугольника: ");
        double base = scanner.nextDouble();
        System.out.print("Введите высоту треугольника: ");
        double height = scanner.nextDouble();

        Triangle triangle = ShapeFactory.createTriangleByBaseHeight(base, height);
        System.out.printf("Площадь треугольника: %.2f\n", triangle.calculateArea());
    }

    private static void calculateTriangleThreeSides(Scanner scanner) {
        System.out.print("Введите сторону A: ");
        double sideA = scanner.nextDouble();
        System.out.print("Введите сторону B: ");
        double sideB = scanner.nextDouble();
        System.out.print("Введите сторону C: ");
        double sideC = scanner.nextDouble();

        Triangle triangle = ShapeFactory.createTriangleBySides(sideA, sideB, sideC);
        System.out.printf("Площадь треугольника: %.2f\n", triangle.calculateArea());
    }

    private static void calculateRectangle(Scanner scanner) {
        System.out.print("Введите длину прямоугольника: ");
        double length = scanner.nextDouble();
        System.out.print("Введите ширину прямоугольника: ");
        double width = scanner.nextDouble();

        Rectangle rectangle = ShapeFactory.createRectangle(length, width);
        System.out.printf("Площадь прямоугольника: %.2f\n", rectangle.calculateArea());
    }

    private static void calculateSquare(Scanner scanner) {
        System.out.print("Введите сторону квадрата: ");
        double side = scanner.nextDouble();

        Square square = ShapeFactory.createSquare(side);
        System.out.printf("Площадь квадрата: %.2f\n", square.calculateArea());
    }

    private static void calculateRhombusDiagonals(Scanner scanner) {
        System.out.print("Введите первую диагональ ромба: ");
        double diag1 = scanner.nextDouble();
        System.out.print("Введите вторую диагональ ромба: ");
        double diag2 = scanner.nextDouble();

        Rhombus rhombus = ShapeFactory.createRhombusByDiagonals(diag1, diag2);
        System.out.printf("Площадь ромба: %.2f\n", rhombus.calculateArea());
    }

    private static void calculateRhombusSideHeight(Scanner scanner) {
        System.out.print("Введите сторону ромба: ");
        double side = scanner.nextDouble();
        System.out.print("Введите высоту ромба: ");
        double height = scanner.nextDouble();

        Rhombus rhombus = ShapeFactory.createRhombusBySideHeight(side, height);
        System.out.printf("Площадь ромба: %.2f\n", rhombus.calculateArea());
    }
}