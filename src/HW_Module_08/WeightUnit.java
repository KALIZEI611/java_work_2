package HW_Module_08;


public enum WeightUnit {
    MILLIGRAM("Миллиграмм", "мг", 0.000001),
    GRAM("Грамм", "г", 0.001),
    KILOGRAM("Килограмм", "кг", 1.0),
    CENTNER("Центнер", "ц", 100.0),
    TON("Тонна", "т", 1000.0);

    private final String name;
    private final String symbol;
    private final double kilograms;

    WeightUnit(String name, String symbol, double kilograms) {
        this.name = name;
        this.symbol = symbol;
        this.kilograms = kilograms;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getKilograms() {
        return kilograms;
    }

    public double convertTo(WeightUnit target, double value) {
        double valueInKilograms = value * this.kilograms;
        return valueInKilograms / target.kilograms;
    }
}