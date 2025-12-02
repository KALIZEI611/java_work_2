package HW_Module_08;

public class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException("Длина и ширина должны быть положительными числами");
        }
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea() {
        return length * width;
    }

    public double getLength() { 
        return length; 
    }
    public double getWidth() { 
        return width; 
    }
}