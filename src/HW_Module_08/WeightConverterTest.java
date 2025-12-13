package HW_Module_08;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

class WeightConverterTest {

    private WeightConverter converter;

    @BeforeEach
    void setUp() {
        converter = WeightConverter.getInstance();
        converter.setPrecision(10);
    }

    @Test
    void testSingletonInstance() {
        WeightConverter converter1 = WeightConverter.getInstance();
        WeightConverter converter2 = WeightConverter.getInstance();
        assertSame(converter1, converter2);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, MILLIGRAM, GRAM, 1",
            "1000, GRAM, KILOGRAM, 1",
            "100, KILOGRAM, CENTNER, 1",
            "10, CENTNER, TON, 1",
            "1000, KILOGRAM, TON, 1",
            "1, TON, KILOGRAM, 1000",
            "1, CENTNER, KILOGRAM, 100",
            "1, KILOGRAM, GRAM, 1000",
            "1, GRAM, MILLIGRAM, 1000",
            "2.5, KILOGRAM, GRAM, 2500",
            "1500, GRAM, KILOGRAM, 1.5",
            "5000, MILLIGRAM, GRAM, 5"
    })
    void testConvert(double value, WeightUnit from, WeightUnit to, double expected) {
        double result = converter.convert(value, from, to);
        assertEquals(expected, result, 0.00000001);
    }

    @Test
    void testConvertNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(-5, WeightUnit.KILOGRAM, WeightUnit.GRAM);
        });
    }

    @Test
    void testConvertFormatted() {
        String result = converter.convertFormatted(1, WeightUnit.KILOGRAM, WeightUnit.GRAM);
        // Разделяем результат на число и единицу измерения
        String[] parts = result.split(" ");
        String numberPart = parts[0];

        // Нормализуем число (заменяем запятую на точку если нужно)
        String normalizedNumber = numberPart.replace(',', '.');
        double numericValue = Double.parseDouble(normalizedNumber);

        // Проверяем числовое значение
        assertEquals(1000, numericValue, 0.0001);

        // Проверяем что результат содержит символ единицы измерения
        assertTrue(result.contains(WeightUnit.GRAM.getSymbol()));
    }

    @Test
    void testConvertToKilograms() {
        assertEquals(1, converter.convertToKilograms(1000000, WeightUnit.MILLIGRAM), 0.000001);
        assertEquals(1, converter.convertToKilograms(1000, WeightUnit.GRAM), 0.000001);
        assertEquals(1, converter.convertToKilograms(1, WeightUnit.KILOGRAM), 0.000001);
        assertEquals(1, converter.convertToKilograms(0.01, WeightUnit.CENTNER), 0.000001);
        assertEquals(1, converter.convertToKilograms(0.001, WeightUnit.TON), 0.000001);
    }

    @Test
    void testConvertFromKilograms() {
        assertEquals(1000000, converter.convertFromKilograms(1, WeightUnit.MILLIGRAM), 0.000001);
        assertEquals(1000, converter.convertFromKilograms(1, WeightUnit.GRAM), 0.000001);
        assertEquals(1, converter.convertFromKilograms(1, WeightUnit.KILOGRAM), 0.000001);
        assertEquals(0.01, converter.convertFromKilograms(1, WeightUnit.CENTNER), 0.000001);
        assertEquals(0.001, converter.convertFromKilograms(1, WeightUnit.TON), 0.000001);
    }

    @Test
    void testConvertToAll() {
        double value = 1;
        double[] results = converter.convertToAll(value, WeightUnit.KILOGRAM);

        assertEquals(5, results.length);
        assertEquals(1000000, results[0], 0.000001); // мг
        assertEquals(1000, results[1], 0.000001);    // г
        assertEquals(1, results[2], 0.000001);       // кг
        assertEquals(0.01, results[3], 0.000001);    // ц
        assertEquals(0.001, results[4], 0.000001);   // т
    }

    @ParameterizedTest
    @CsvSource({
            "1, KILOGRAM, 500, GRAM, KILOGRAM, 1.5",
            "2000, MILLIGRAM, 3, GRAM, GRAM, 5",
            "0.5, TON, 300, KILOGRAM, TON, 0.8"
    })
    void testAdd(double value1, WeightUnit unit1, double value2, WeightUnit unit2,
                 WeightUnit resultUnit, double expected) {
        double result = converter.add(value1, unit1, value2, unit2, resultUnit);
        assertEquals(expected, result, 0.000001);
    }

    @ParameterizedTest
    @CsvSource({
            "2, KILOGRAM, 500, GRAM, KILOGRAM, 1.5",
            "5, GRAM, 2000, MILLIGRAM, GRAM, 3",
            "1, TON, 300, KILOGRAM, TON, 0.7"
    })
    void testSubtract(double value1, WeightUnit unit1, double value2, WeightUnit unit2,
                      WeightUnit resultUnit, double expected) {
        double result = converter.subtract(value1, unit1, value2, unit2, resultUnit);
        assertEquals(expected, result, 0.000001);
    }

    @Test
    void testSubtractNegativeResult() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.subtract(1, WeightUnit.KILOGRAM, 1500, WeightUnit.GRAM, WeightUnit.KILOGRAM);
        });
    }

    @Test
    void testMultiply() {
        double result = converter.multiply(2.5, WeightUnit.KILOGRAM, 3);
        assertEquals(7.5, result, 0.000001);

        result = converter.multiply(100, WeightUnit.GRAM, 0.5);
        assertEquals(50, result, 0.000001);
    }

    @Test
    void testMultiplyNegativeMultiplier() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.multiply(5, WeightUnit.KILOGRAM, -2);
        });
    }

    @Test
    void testDivide() {
        double result = converter.divide(6, WeightUnit.KILOGRAM, 2);
        assertEquals(3, result, 0.000001);

        result = converter.divide(100, WeightUnit.GRAM, 4);
        assertEquals(25, result, 0.000001);
    }

    @Test
    void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.divide(10, WeightUnit.KILOGRAM, 0);
        });
    }

    @Test
    void testDivideByNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.divide(10, WeightUnit.KILOGRAM, -2);
        });
    }

    @Test
    void testSetPrecisionValid() {
        // Тестируем точность 3 знака
        converter.setPrecision(3);
        String result = converter.convertFormatted(1.23456789, WeightUnit.KILOGRAM, WeightUnit.KILOGRAM);
        String[] parts = result.split(" ");
        String numberPart = parts[0];
        String normalizedNumber = numberPart.replace(',', '.');
        double numericResult = Double.parseDouble(normalizedNumber);
        assertEquals(1.235, numericResult, 0.0001);

        // Тестируем точность 0 знаков
        converter.setPrecision(0);
        result = converter.convertFormatted(1.5, WeightUnit.KILOGRAM, WeightUnit.KILOGRAM);
        parts = result.split(" ");
        numberPart = parts[0];
        normalizedNumber = numberPart.replace(',', '.');
        numericResult = Double.parseDouble(normalizedNumber);
        assertEquals(2, numericResult, 0.0001);
    }

    @Test
    void testSetPrecisionInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.setPrecision(-1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            converter.setPrecision(11);
        });
    }

    @Test
    void testWeightUnitEnum() {
        assertEquals("Миллиграмм", WeightUnit.MILLIGRAM.getName());
        assertEquals("мг", WeightUnit.MILLIGRAM.getSymbol());
        assertEquals(0.000001, WeightUnit.MILLIGRAM.getKilograms(), 0.000001);

        assertEquals("Грамм", WeightUnit.GRAM.getName());
        assertEquals("г", WeightUnit.GRAM.getSymbol());
        assertEquals(0.001, WeightUnit.GRAM.getKilograms(), 0.000001);

        assertEquals("Килограмм", WeightUnit.KILOGRAM.getName());
        assertEquals("кг", WeightUnit.KILOGRAM.getSymbol());
        assertEquals(1.0, WeightUnit.KILOGRAM.getKilograms(), 0.000001);

        assertEquals("Центнер", WeightUnit.CENTNER.getName());
        assertEquals("ц", WeightUnit.CENTNER.getSymbol());
        assertEquals(100.0, WeightUnit.CENTNER.getKilograms(), 0.000001);

        assertEquals("Тонна", WeightUnit.TON.getName());
        assertEquals("т", WeightUnit.TON.getSymbol());
        assertEquals(1000.0, WeightUnit.TON.getKilograms(), 0.000001);
    }

    @Test
    void testWeightUnitConvertTo() {
        double result = WeightUnit.KILOGRAM.convertTo(WeightUnit.GRAM, 1);
        assertEquals(1000, result, 0.000001);

        result = WeightUnit.TON.convertTo(WeightUnit.KILOGRAM, 2.5);
        assertEquals(2500, result, 0.000001);

        result = WeightUnit.GRAM.convertTo(WeightUnit.MILLIGRAM, 10);
        assertEquals(10000, result, 0.000001);
    }

    @Test
    void testWeightFactoryCreateUnit() {
        assertEquals(WeightUnit.MILLIGRAM, WeightFactory.createUnit("mg"));
        assertEquals(WeightUnit.MILLIGRAM, WeightFactory.createUnit("мг"));
        assertEquals(WeightUnit.MILLIGRAM, WeightFactory.createUnit("milligram"));
        assertEquals(WeightUnit.MILLIGRAM, WeightFactory.createUnit("миллиграмм"));

        assertEquals(WeightUnit.GRAM, WeightFactory.createUnit("g"));
        assertEquals(WeightUnit.GRAM, WeightFactory.createUnit("г"));
        assertEquals(WeightUnit.GRAM, WeightFactory.createUnit("gram"));
        assertEquals(WeightUnit.GRAM, WeightFactory.createUnit("грамм"));

        assertEquals(WeightUnit.KILOGRAM, WeightFactory.createUnit("kg"));
        assertEquals(WeightUnit.KILOGRAM, WeightFactory.createUnit("кг"));
        assertEquals(WeightUnit.KILOGRAM, WeightFactory.createUnit("kilogram"));
        assertEquals(WeightUnit.KILOGRAM, WeightFactory.createUnit("килограмм"));

        assertEquals(WeightUnit.CENTNER, WeightFactory.createUnit("c"));
        assertEquals(WeightUnit.CENTNER, WeightFactory.createUnit("ц"));
        assertEquals(WeightUnit.CENTNER, WeightFactory.createUnit("centner"));
        assertEquals(WeightUnit.CENTNER, WeightFactory.createUnit("центнер"));

        assertEquals(WeightUnit.TON, WeightFactory.createUnit("t"));
        assertEquals(WeightUnit.TON, WeightFactory.createUnit("т"));
        assertEquals(WeightUnit.TON, WeightFactory.createUnit("ton"));
        assertEquals(WeightUnit.TON, WeightFactory.createUnit("тонна"));
    }

    @Test
    void testWeightFactoryCreateUnitInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            WeightFactory.createUnit("invalid");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            WeightFactory.createUnit("lb");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            WeightFactory.createUnit("ounce");
        });
    }

    @Test
    void testWeightFactoryGetAllUnits() {
        WeightUnit[] units = WeightFactory.getAllUnits();
        assertEquals(5, units.length);
        assertArrayEquals(WeightUnit.values(), units);
    }

    @Test
    void testWeightFactoryGetUnitInfo() {
        String info = WeightFactory.getUnitInfo(WeightUnit.KILOGRAM);
        assertTrue(info.contains("Килограмм"));
        assertTrue(info.contains("кг"));
    }

    @Test
    void testWeightFactoryGetUnitSymbols() {
        String[] symbols = WeightFactory.getUnitSymbols();
        assertEquals(5, symbols.length);
        assertEquals("мг", symbols[0]);
        assertEquals("г", symbols[1]);
        assertEquals("кг", symbols[2]);
        assertEquals("ц", symbols[3]);
        assertEquals("т", symbols[4]);
    }

    @Test
    void testZeroValue() {
        double result = converter.convert(0, WeightUnit.KILOGRAM, WeightUnit.GRAM);
        assertEquals(0, result, 0.000001);

        result = converter.add(0, WeightUnit.KILOGRAM, 0, WeightUnit.GRAM, WeightUnit.KILOGRAM);
        assertEquals(0, result, 0.000001);

        result = converter.subtract(0, WeightUnit.KILOGRAM, 0, WeightUnit.GRAM, WeightUnit.KILOGRAM);
        assertEquals(0, result, 0.000001);

        result = converter.multiply(0, WeightUnit.KILOGRAM, 5);
        assertEquals(0, result, 0.000001);

        result = converter.divide(0, WeightUnit.KILOGRAM, 5);
        assertEquals(0, result, 0.000001);
    }

    @Test
    void testLargeValue() {
        double result = converter.convert(1000000, WeightUnit.KILOGRAM, WeightUnit.TON);
        assertEquals(1000, result, 0.000001);

        result = converter.convert(0.000001, WeightUnit.TON, WeightUnit.MILLIGRAM);
        assertEquals(1000, result, 0.000001);
    }

    @Test
    void testSameUnitConversion() {
        double result = converter.convert(5, WeightUnit.KILOGRAM, WeightUnit.KILOGRAM);
        assertEquals(5, result, 0.000001);

        String formatted = converter.convertFormatted(5, WeightUnit.KILOGRAM, WeightUnit.KILOGRAM);
        String[] parts = formatted.split(" ");
        String numberPart = parts[0];
        String normalizedNumber = numberPart.replace(',', '.');
        double numericValue = Double.parseDouble(normalizedNumber);
        assertEquals(5, numericValue, 0.000001);
        assertTrue(formatted.contains("кг"));
    }

    @Test
    void testEdgeCases() {
        // Очень маленькое значение
        double result = converter.convert(0.000001, WeightUnit.KILOGRAM, WeightUnit.MILLIGRAM);
        assertEquals(1, result, 0.000001);

        // Очень большое значение
        result = converter.convert(999999999, WeightUnit.KILOGRAM, WeightUnit.TON);
        assertEquals(999999.999, result, 0.000001);
    }

    @Test
    void testComplexConversionChain() {
        // Конвертация через несколько единиц
        double milligrams = 1500000; // 1.5 кг в миллиграммах
        double grams = converter.convert(milligrams, WeightUnit.MILLIGRAM, WeightUnit.GRAM);
        double kilograms = converter.convert(grams, WeightUnit.GRAM, WeightUnit.KILOGRAM);
        double centners = converter.convert(kilograms, WeightUnit.KILOGRAM, WeightUnit.CENTNER);
        double tons = converter.convert(centners, WeightUnit.CENTNER, WeightUnit.TON);

        assertEquals(1500, grams, 0.000001);
        assertEquals(1.5, kilograms, 0.000001);
        assertEquals(0.015, centners, 0.000001);
        assertEquals(0.0015, tons, 0.000001);
    }
}