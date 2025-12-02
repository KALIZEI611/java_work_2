package HW_Module_08;


public class LengthFactory {

    public static LengthUnit createUnit(String unitCode) {
        switch (unitCode.toLowerCase()) {
            case "mm":
            case "мм":
            case "millimeter":
            case "миллиметр":
                return LengthUnit.MILLIMETER;

            case "cm":
            case "см":
            case "centimeter":
            case "сантиметр":
                return LengthUnit.CENTIMETER;

            case "dm":
            case "дм":
            case "decimeter":
            case "дециметр":
                return LengthUnit.DECIMETER;

            case "m":
            case "м":
            case "meter":
            case "метр":
                return LengthUnit.METER;

            case "km":
            case "км":
            case "kilometer":
            case "километр":
                return LengthUnit.KILOMETER;

            default:
                throw new IllegalArgumentException("Неизвестная единица измерения: " + unitCode);
        }
    }

    public static LengthUnit[] getAllUnits() {
        return LengthUnit.values();
    }

    public static String getUnitInfo(LengthUnit unit) {
        return unit.getName() + " (" + unit.getSymbol() + ") - " + unit.getMeters() + " метров";
    }

    public static String[] getUnitSymbols() {
        LengthUnit[] units = LengthUnit.values();
        String[] symbols = new String[units.length];

        for (int i = 0; i < units.length; i++) {
            symbols[i] = units[i].getSymbol();
        }

        return symbols;
    }
}