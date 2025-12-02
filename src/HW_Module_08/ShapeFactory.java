package HW_Module_08;

public class ShapeFactory {

    public static Triangle createTriangleByBaseHeight(double base, double height) {
        return new Triangle(base, height);
    }

    public static Triangle createTriangleBySides(double sideA, double sideB, double sideC) {
        return new Triangle(sideA, sideB, sideC);
    }

    public static Rectangle createRectangle(double length, double width) {
        return new Rectangle(length, width);
    }

    public static Square createSquare(double side) {
        return new Square(side);
    }

    public static Rhombus createRhombusByDiagonals(double diagonal1, double diagonal2) {
        return new Rhombus(diagonal1, diagonal2);
    }

    public static Rhombus createRhombusBySideHeight(double side, double height) {
        return new Rhombus(side, height, true);
    }
}