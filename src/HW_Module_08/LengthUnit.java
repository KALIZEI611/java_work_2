package HW_Module_08;
public enum LengthUnit {
    MILLIMETER("Миллиметр", "мм", 0.001),
    CENTIMETER("Сантиметр", "см", 0.01),
    DECIMETER("Дециметр", "дм", 0.1),
    METER("Метр", "м", 1.0),
    KILOMETER("Километр", "км", 1000.0);

    private final String name;
    private final String symbol;
    private final double meters;

    LengthUnit(String name, String symbol, double meters) {
        this.name = name;
        this.symbol = symbol;
        this.meters = meters;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getMeters() {
        return meters;
    }

    public double convertTo(LengthUnit target, double value) {
        double valueInMeters = value * this.meters;
        return valueInMeters / target.meters;
    }
}
