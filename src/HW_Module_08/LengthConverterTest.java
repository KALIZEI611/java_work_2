package HW_Module_08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
class LengthConverterTest {

    private LengthConverter converter;

    @BeforeEach
    void setUp() {
        converter = LengthConverter.getInstance();
        converter.setPrecision(10);
    }

    @Test
    void testSingletonInstance() {
        LengthConverter converter1 = LengthConverter.getInstance();
        LengthConverter converter2 = LengthConverter.getInstance();
        assertSame(converter1, converter2);
    }

    @ParameterizedTest
    @CsvSource({
            "1000, MILLIMETER, METER, 1",
            "100, CENTIMETER, METER, 1",
            "10, DECIMETER, METER, 1",
            "1, METER, METER, 1",
            "0.001, KILOMETER, METER, 1",
            "1, METER, MILLIMETER, 1000",
            "1, METER, CENTIMETER, 100",
            "1, METER, DECIMETER, 10",
            "1, METER, KILOMETER, 0.001",
            "2.5, METER, CENTIMETER, 250",
            "150, CENTIMETER, METER, 1.5",
            "5000, MILLIMETER, CENTIMETER, 500"
    })
    void testConvert(double value, LengthUnit from, LengthUnit to, double expected) {
        double result = converter.convert(value, from, to);
        assertEquals(expected, result, 0.00000001);
    }

    @Test
    void testConvertNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(-5, LengthUnit.METER, LengthUnit.CENTIMETER);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "1, METER, CENTIMETER, '100'",
            "2.5, METER, MILLIMETER, '2500'",
            "0.5, KILOMETER, METER, '500'"
    })
    void testConvertFormatted(double value, LengthUnit from, LengthUnit to, String expectedValue) {
        String result = converter.convertFormatted(value, from, to);
        String[] parts = result.split(" ");
        String numberPart = parts[0];

        String normalizedNumber = numberPart.replace(',', '.');
        double numericValue = Double.parseDouble(normalizedNumber);

        double expected = Double.parseDouble(expectedValue);
        assertEquals(expected, numericValue, 0.0001);

        assertTrue(result.contains(to.getSymbol()));
    }

    @Test
    void testConvertToMeters() {
        assertEquals(1, converter.convertToMeters(1000, LengthUnit.MILLIMETER), 0.000001);
        assertEquals(1, converter.convertToMeters(100, LengthUnit.CENTIMETER), 0.000001);
        assertEquals(1, converter.convertToMeters(10, LengthUnit.DECIMETER), 0.000001);
        assertEquals(1, converter.convertToMeters(1, LengthUnit.METER), 0.000001);
        assertEquals(1, converter.convertToMeters(0.001, LengthUnit.KILOMETER), 0.000001);
    }

    @Test
    void testConvertFromMeters() {
        assertEquals(1000, converter.convertFromMeters(1, LengthUnit.MILLIMETER), 0.000001);
        assertEquals(100, converter.convertFromMeters(1, LengthUnit.CENTIMETER), 0.000001);
        assertEquals(10, converter.convertFromMeters(1, LengthUnit.DECIMETER), 0.000001);
        assertEquals(1, converter.convertFromMeters(1, LengthUnit.METER), 0.000001);
        assertEquals(0.001, converter.convertFromMeters(1, LengthUnit.KILOMETER), 0.000001);
    }

    @Test
    void testConvertToAll() {
        double value = 1;
        double[] results = converter.convertToAll(value, LengthUnit.METER);

        assertEquals(5, results.length);
        assertEquals(1000, results[0], 0.000001); // мм
        assertEquals(100, results[1], 0.000001);  // см
        assertEquals(10, results[2], 0.000001);   // дм
        assertEquals(1, results[3], 0.000001);    // м
        assertEquals(0.001, results[4], 0.000001); // км
    }

    @ParameterizedTest
    @CsvSource({
            "1, METER, 100, CENTIMETER, METER, 2",
            "500, MILLIMETER, 0.5, METER, CENTIMETER, 100",
            "2, KILOMETER, 1500, METER, KILOMETER, 3.5"
    })
    void testAdd(double value1, LengthUnit unit1, double value2, LengthUnit unit2,
                 LengthUnit resultUnit, double expected) {
        double result = converter.add(value1, unit1, value2, unit2, resultUnit);
        assertEquals(expected, result, 0.000001);
    }

    @ParameterizedTest
    @CsvSource({
            "2, METER, 100, CENTIMETER, METER, 1",
            "1500, MILLIMETER, 0.5, METER, CENTIMETER, 100",
            "3.5, KILOMETER, 1500, METER, KILOMETER, 2"
    })
    void testSubtract(double value1, LengthUnit unit1, double value2, LengthUnit unit2,
                      LengthUnit resultUnit, double expected) {
        double result = converter.subtract(value1, unit1, value2, unit2, resultUnit);
        assertEquals(expected, result, 0.000001);
    }

    @Test
    void testSubtractNegativeResult() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.subtract(1, LengthUnit.METER, 200, LengthUnit.CENTIMETER, LengthUnit.METER);
        });
    }

    @Test
    void testSetPrecisionValid() {
        converter.setPrecision(3);
        String result = converter.convertFormatted(1.23456789, LengthUnit.METER, LengthUnit.METER);
        String[] parts = result.split(" ");
        String numberPart = parts[0];

        String normalizedNumber = numberPart.replace(',', '.');
        double numericResult = Double.parseDouble(normalizedNumber);

        assertEquals(1.235, numericResult, 0.0001);

        converter.setPrecision(0);
        result = converter.convertFormatted(1.5, LengthUnit.METER, LengthUnit.METER);
        parts = result.split(" ");
        numberPart = parts[0];
        normalizedNumber = numberPart.replace(',', '.');
        numericResult = Double.parseDouble(normalizedNumber);
        assertEquals(2, numericResult, 0.0001);

        converter.setPrecision(5);
        result = converter.convertFormatted(1.123456789, LengthUnit.METER, LengthUnit.METER);
        parts = result.split(" ");
        numberPart = parts[0];
        normalizedNumber = numberPart.replace(',', '.');
        numericResult = Double.parseDouble(normalizedNumber);
        assertEquals(1.12346, numericResult, 0.00001);
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
    void testLengthUnitEnum() {
        assertEquals("Миллиметр", LengthUnit.MILLIMETER.getName());
        assertEquals("мм", LengthUnit.MILLIMETER.getSymbol());
        assertEquals(0.001, LengthUnit.MILLIMETER.getMeters(), 0.000001);

        assertEquals("Сантиметр", LengthUnit.CENTIMETER.getName());
        assertEquals("см", LengthUnit.CENTIMETER.getSymbol());
        assertEquals(0.01, LengthUnit.CENTIMETER.getMeters(), 0.000001);

        assertEquals("Дециметр", LengthUnit.DECIMETER.getName());
        assertEquals("дм", LengthUnit.DECIMETER.getSymbol());
        assertEquals(0.1, LengthUnit.DECIMETER.getMeters(), 0.000001);

        assertEquals("Метр", LengthUnit.METER.getName());
        assertEquals("м", LengthUnit.METER.getSymbol());
        assertEquals(1.0, LengthUnit.METER.getMeters(), 0.000001);

        assertEquals("Километр", LengthUnit.KILOMETER.getName());
        assertEquals("км", LengthUnit.KILOMETER.getSymbol());
        assertEquals(1000.0, LengthUnit.KILOMETER.getMeters(), 0.000001);
    }

    @Test
    void testLengthUnitConvertTo() {
        double result = LengthUnit.METER.convertTo(LengthUnit.CENTIMETER, 1);
        assertEquals(100, result, 0.000001);

        result = LengthUnit.KILOMETER.convertTo(LengthUnit.METER, 2.5);
        assertEquals(2500, result, 0.000001);

        result = LengthUnit.CENTIMETER.convertTo(LengthUnit.MILLIMETER, 10);
        assertEquals(100, result, 0.000001);
    }

    @Test
    void testLengthFactoryCreateUnit() {
        assertEquals(LengthUnit.MILLIMETER, LengthFactory.createUnit("mm"));
        assertEquals(LengthUnit.MILLIMETER, LengthFactory.createUnit("мм"));
        assertEquals(LengthUnit.MILLIMETER, LengthFactory.createUnit("millimeter"));
        assertEquals(LengthUnit.MILLIMETER, LengthFactory.createUnit("миллиметр"));

        assertEquals(LengthUnit.CENTIMETER, LengthFactory.createUnit("cm"));
        assertEquals(LengthUnit.CENTIMETER, LengthFactory.createUnit("см"));
        assertEquals(LengthUnit.CENTIMETER, LengthFactory.createUnit("centimeter"));
        assertEquals(LengthUnit.CENTIMETER, LengthFactory.createUnit("сантиметр"));

        assertEquals(LengthUnit.DECIMETER, LengthFactory.createUnit("dm"));
        assertEquals(LengthUnit.DECIMETER, LengthFactory.createUnit("дм"));
        assertEquals(LengthUnit.DECIMETER, LengthFactory.createUnit("decimeter"));
        assertEquals(LengthUnit.DECIMETER, LengthFactory.createUnit("дециметр"));

        assertEquals(LengthUnit.METER, LengthFactory.createUnit("m"));
        assertEquals(LengthUnit.METER, LengthFactory.createUnit("м"));
        assertEquals(LengthUnit.METER, LengthFactory.createUnit("meter"));
        assertEquals(LengthUnit.METER, LengthFactory.createUnit("метр"));

        assertEquals(LengthUnit.KILOMETER, LengthFactory.createUnit("km"));
        assertEquals(LengthUnit.KILOMETER, LengthFactory.createUnit("км"));
        assertEquals(LengthUnit.KILOMETER, LengthFactory.createUnit("kilometer"));
        assertEquals(LengthUnit.KILOMETER, LengthFactory.createUnit("километр"));
    }

    @Test
    void testLengthFactoryCreateUnitInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            LengthFactory.createUnit("invalid");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            LengthFactory.createUnit("ft");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            LengthFactory.createUnit("inch");
        });
    }

    @Test
    void testLengthFactoryGetAllUnits() {
        LengthUnit[] units = LengthFactory.getAllUnits();
        assertEquals(5, units.length);
        assertArrayEquals(LengthUnit.values(), units);
    }

    @Test
    void testLengthFactoryGetUnitInfo() {
        String info = LengthFactory.getUnitInfo(LengthUnit.METER);
        assertTrue(info.contains("Метр"));
        assertTrue(info.contains("м"));
    }

    @Test
    void testLengthFactoryGetUnitSymbols() {
        String[] symbols = LengthFactory.getUnitSymbols();
        assertEquals(5, symbols.length);
        assertEquals("мм", symbols[0]);
        assertEquals("см", symbols[1]);
        assertEquals("дм", symbols[2]);
        assertEquals("м", symbols[3]);
        assertEquals("км", symbols[4]);
    }

    @Test
    void testZeroValue() {
        double result = converter.convert(0, LengthUnit.METER, LengthUnit.CENTIMETER);
        assertEquals(0, result, 0.000001);

        result = converter.add(0, LengthUnit.METER, 0, LengthUnit.CENTIMETER, LengthUnit.METER);
        assertEquals(0, result, 0.000001);

        result = converter.subtract(0, LengthUnit.METER, 0, LengthUnit.CENTIMETER, LengthUnit.METER);
        assertEquals(0, result, 0.000001);
    }

    @Test
    void testLargeValue() {
        double result = converter.convert(1000000, LengthUnit.METER, LengthUnit.KILOMETER);
        assertEquals(1000, result, 0.000001);

        result = converter.convert(0.000001, LengthUnit.KILOMETER, LengthUnit.MILLIMETER);
        assertEquals(1, result, 0.000001);
    }

    @Test
    void testSameUnitConversion() {
        double result = converter.convert(5, LengthUnit.METER, LengthUnit.METER);
        assertEquals(5, result, 0.000001);

        String formatted = converter.convertFormatted(5, LengthUnit.METER, LengthUnit.METER);
        String[] parts = formatted.split(" ");
        String numberPart = parts[0];
        String normalizedNumber = numberPart.replace(',', '.');
        double numericValue = Double.parseDouble(normalizedNumber);
        assertEquals(5, numericValue, 0.000001);
        assertTrue(formatted.contains("м"));
    }

    @Test
    void testEdgeCases() {
        double result = converter.convert(0.000001, LengthUnit.METER, LengthUnit.MILLIMETER);
        assertEquals(0.001, result, 0.000001);

        result = converter.convert(999999999, LengthUnit.METER, LengthUnit.KILOMETER);
        assertEquals(999999.999, result, 0.000001);
    }
}