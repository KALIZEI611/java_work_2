package HW_Module_08;


import java.text.DecimalFormat;

public class LengthConverter {
    private static LengthConverter instance;
    private DecimalFormat formatter;

    private LengthConverter() {
        this.formatter = new DecimalFormat("#.##########");
    }

    public static LengthConverter getInstance() {
        if (instance == null) {
            instance = new LengthConverter();
        }
        return instance;
    }

    public double convert(double value, LengthUnit from, LengthUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Значение не может быть отрицательным");
        }

        return from.convertTo(to, value);
    }

    public String convertFormatted(double value, LengthUnit from, LengthUnit to) {
        double result = convert(value, from, to);
        return formatter.format(result) + " " + to.getSymbol();
    }

    public double convertToMeters(double value, LengthUnit from) {
        return convert(value, from, LengthUnit.METER);
    }

    public double convertFromMeters(double value, LengthUnit to) {
        return convert(value, LengthUnit.METER, to);
    }

    public double[] convertToAll(double value, LengthUnit from) {
        double[] results = new double[LengthUnit.values().length];
        LengthUnit[] units = LengthUnit.values();

        for (int i = 0; i < units.length; i++) {
            results[i] = convert(value, from, units[i]);
        }

        return results;
    }

    public double add(double value1, LengthUnit unit1, double value2, LengthUnit unit2, LengthUnit resultUnit) {
        double value1InResult = convert(value1, unit1, resultUnit);
        double value2InResult = convert(value2, unit2, resultUnit);

        return value1InResult + value2InResult;
    }

    public double subtract(double value1, LengthUnit unit1, double value2, LengthUnit unit2, LengthUnit resultUnit) {
        double value1InResult = convert(value1, unit1, resultUnit);
        double value2InResult = convert(value2, unit2, resultUnit);

        if (value1InResult < value2InResult) {
            throw new IllegalArgumentException("Результат не может быть отрицательным");
        }

        return value1InResult - value2InResult;
    }

    public void setPrecision(int decimalPlaces) {
        if (decimalPlaces < 0 || decimalPlaces > 10) {
            throw new IllegalArgumentException("Точность должна быть от 0 до 10 знаков");
        }

        StringBuilder pattern = new StringBuilder("#");
        if (decimalPlaces > 0) {
            pattern.append(".");
            for (int i = 0; i < decimalPlaces; i++) {
                pattern.append("#");
            }
        }

        this.formatter = new DecimalFormat(pattern.toString());
    }
}