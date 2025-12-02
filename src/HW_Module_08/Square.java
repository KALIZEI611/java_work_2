package HW_Module_08;

public class Square extends Shape {
    private double side;

    public Square(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Сторона должна быть положительным числом");
        }
        this.side = side;
    }

    @Override
    public double calculateArea() {
        return side * side;
    }

    public double getSide() { 
        return side; 
    }
}