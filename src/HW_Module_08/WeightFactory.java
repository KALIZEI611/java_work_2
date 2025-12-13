package HW_Module_08;
public class WeightFactory {

    public static WeightUnit createUnit(String unitCode) {
        switch (unitCode.toLowerCase()) {
            case "mg":
            case "мг":
            case "milligram":
            case "миллиграмм":
                return WeightUnit.MILLIGRAM;

            case "g":
            case "г":
            case "gram":
            case "грамм":
                return WeightUnit.GRAM;

            case "kg":
            case "кг":
            case "kilogram":
            case "килограмм":
                return WeightUnit.KILOGRAM;

            case "c":
            case "ц":
            case "centner":
            case "центнер":
                return WeightUnit.CENTNER;

            case "t":
            case "т":
            case "ton":
            case "тонна":
                return WeightUnit.TON;

            default:
                throw new IllegalArgumentException("Неизвестная единица измерения: " + unitCode);
        }
    }

    public static WeightUnit[] getAllUnits() {
        return WeightUnit.values();
    }

    public static String getUnitInfo(WeightUnit unit) {
        return unit.getName() + " (" + unit.getSymbol() + ") - " + unit.getKilograms() + " кг";
    }

    public static String[] getUnitSymbols() {
        WeightUnit[] units = WeightUnit.values();
        String[] symbols = new String[units.length];

        for (int i = 0; i < units.length; i++) {
            symbols[i] = units[i].getSymbol();
        }

        return symbols;
    }
}
