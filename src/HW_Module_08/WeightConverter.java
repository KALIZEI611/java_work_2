package HW_Module_08;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class WeightConverter {
    private static WeightConverter instance;
    private DecimalFormat formatter;

    private WeightConverter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        this.formatter = new DecimalFormat("#.##########", symbols);
    }

    public static WeightConverter getInstance() {
        if (instance == null) {
            instance = new WeightConverter();
        }
        return instance;
    }

    public double convert(double value, WeightUnit from, WeightUnit to) {
        if (value < 0) {
            throw new IllegalArgumentException("Значение не может быть отрицательным");
        }

        return from.convertTo(to, value);
    }

    public String convertFormatted(double value, WeightUnit from, WeightUnit to) {
        double result = convert(value, from, to);
        return formatter.format(result) + " " + to.getSymbol();
    }

    public double convertToKilograms(double value, WeightUnit from) {
        return convert(value, from, WeightUnit.KILOGRAM);
    }

    public double convertFromKilograms(double value, WeightUnit to) {
        return convert(value, WeightUnit.KILOGRAM, to);
    }

    public double[] convertToAll(double value, WeightUnit from) {
        double[] results = new double[WeightUnit.values().length];
        WeightUnit[] units = WeightUnit.values();

        for (int i = 0; i < units.length; i++) {
            results[i] = convert(value, from, units[i]);
        }

        return results;
    }

    public double add(double value1, WeightUnit unit1, double value2, WeightUnit unit2, WeightUnit resultUnit) {
        double value1InResult = convert(value1, unit1, resultUnit);
        double value2InResult = convert(value2, unit2, resultUnit);

        return value1InResult + value2InResult;
    }

    public double subtract(double value1, WeightUnit unit1, double value2, WeightUnit unit2, WeightUnit resultUnit) {
        double value1InResult = convert(value1, unit1, resultUnit);
        double value2InResult = convert(value2, unit2, resultUnit);

        if (value1InResult < value2InResult) {
            throw new IllegalArgumentException("Результат не может быть отрицательным");
        }

        return value1InResult - value2InResult;
    }

    public double multiply(double value, WeightUnit unit, double multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("Множитель не может быть отрицательным");
        }

        return value * multiplier;
    }

    public double divide(double value, WeightUnit unit, double divisor) {
        if (divisor <= 0) {
            throw new IllegalArgumentException("Делитель должен быть положительным");
        }

        return value / divisor;
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

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        this.formatter = new DecimalFormat(pattern.toString(), symbols);
    }
}