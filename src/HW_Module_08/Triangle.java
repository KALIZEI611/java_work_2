package HW_Module_08;

public class Triangle extends Shape {
    private double base;
    private double height;
    private double sideA;
    private double sideB;
    private double sideC;

    public Triangle(double base, double height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Основание и высота должны быть положительными числами");
        }
        this.base = base;
        this.height = height;
    }

    public Triangle(double sideA, double sideB, double sideC) {
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IllegalArgumentException("Стороны должны быть положительными числами");
        }
        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new IllegalArgumentException("Треугольник с такими сторонами не существует");
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    @Override
    public double calculateArea() {
        if (base > 0 && height > 0) {
            return 0.5 * base * height;
        } else {
            double s = (sideA + sideB + sideC) / 2;
            return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
        }
    }

    public double getBase() { 
        return base; 
    }
    public double getHeight() { 
        return height; 
    }
    public double getSideA() { 
        return sideA; 
    }
    public double getSideB() { 
        return sideB; 
    }
    public double getSideC() { 
        return sideC; 
    }
}