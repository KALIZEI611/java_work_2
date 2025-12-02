package HW_Module_08;

public class Rhombus extends Shape {
    private double diagonal1;
    private double diagonal2;
    private double side;
    private double height;

    public Rhombus(double diagonal1, double diagonal2) {
        if (diagonal1 <= 0 || diagonal2 <= 0) {
            throw new IllegalArgumentException("Диагонали должны быть положительными числами");
        }
        this.diagonal1 = diagonal1;
        this.diagonal2 = diagonal2;
    }

    public Rhombus(double side, double height, boolean useSideHeight) {
        if (side <= 0 || height <= 0) {
            throw new IllegalArgumentException("Сторона и высота должны быть положительными числами");
        }
        this.side = side;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        if (diagonal1 > 0 && diagonal2 > 0) {
            return (diagonal1 * diagonal2) / 2;
        } else {
            return side * height;
        }
    }

    public double getDiagonal1() { 
        return diagonal1; 
    }
    public double getDiagonal2() { 
        return diagonal2; 
    }
    public double getSide() { 
        return side; 
    }
    public double getHeight() { 
        return height; 
    }
}