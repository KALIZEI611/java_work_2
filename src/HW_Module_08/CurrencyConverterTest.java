package HW_Module_08;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {

    private CurrencyConverter converter;

    @BeforeEach
    void setUp() {
        converter = CurrencyConverter.getInstance();
        converter.setCommissionRate(0.01);
    }

    @Test
    void testSingletonInstance() {
        CurrencyConverter converter1 = CurrencyConverter.getInstance();
        CurrencyConverter converter2 = CurrencyConverter.getInstance();
        assertSame(converter1, converter2);
    }

    @Test
    void testUSDToEUR() {
        double result = converter.convert(100, Currency.USD, Currency.EUR);
        double expected = 100 * Currency.EUR.getRateToUSD() - (100 * Currency.EUR.getRateToUSD() * 0.01);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testEURToGBP() {
        double result = converter.convert(100, Currency.EUR, Currency.GBP);
        double usdAmount = 100 / Currency.EUR.getRateToUSD();
        double gbpAmount = usdAmount * Currency.GBP.getRateToUSD();
        double expected = gbpAmount - (gbpAmount * 0.01);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testGBPToJPY() {
        double result = converter.convert(50, Currency.GBP, Currency.JPY);
        double usdAmount = 50 / Currency.GBP.getRateToUSD();
        double jpyAmount = usdAmount * Currency.JPY.getRateToUSD();
        double expected = jpyAmount - (jpyAmount * 0.01);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testJPYToUSD() {
        double result = converter.convert(10000, Currency.JPY, Currency.USD);
        double usdAmount = 10000 / Currency.JPY.getRateToUSD();
        double expected = usdAmount - (usdAmount * 0.01);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testSameCurrencyConversion() {
        double result = converter.convert(100, Currency.USD, Currency.USD);
        double expected = 100 - (100 * 0.01);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(-100, Currency.USD, Currency.EUR);
        });
    }

    @Test
    void testZeroAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(0, Currency.USD, Currency.EUR);
        });
    }

    @Test
    void testSetCommissionRateValid() {
        converter.setCommissionRate(0.05);
        assertEquals(0.05, converter.getCommissionRate(), 0.001);
    }

    @Test
    void testSetCommissionRateInvalidNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.setCommissionRate(-0.01);
        });
    }

    @Test
    void testSetCommissionRateInvalidTooHigh() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.setCommissionRate(0.11);
        });
    }

    @Test
    void testConvertWithCustomRate() {
        double customRate = 0.95;
        double result = converter.convertWithRate(100, Currency.USD, Currency.EUR, customRate);
        double expected = 100 * customRate - (100 * customRate * 0.01);
        assertEquals(expected, result, 0.001);
    }

    @Test
    void testConvertWithCustomRateNegativeRate() {
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convertWithRate(100, Currency.USD, Currency.EUR, -0.5);
        });
    }

    @Test
    void testCalculateCommission() {
        converter.setCommissionRate(0.02);
        double commission = converter.calculateCommission(100);
        assertEquals(2.0, commission, 0.001);
    }

    @Test
    void testCurrencyFactoryValid() {
        Currency usd = CurrencyFactory.createCurrency("USD");
        assertEquals(Currency.USD, usd);

        Currency eur = CurrencyFactory.createCurrency("EUR");
        assertEquals(Currency.EUR, eur);

        Currency gbp = CurrencyFactory.createCurrency("GBP");
        assertEquals(Currency.GBP, gbp);

        Currency jpy = CurrencyFactory.createCurrency("JPY");
        assertEquals(Currency.JPY, jpy);
    }

    @Test
    void testCurrencyFactoryInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            CurrencyFactory.createCurrency("RUB");
        });
    }

    @Test
    void testGetAllCurrencies() {
        Currency[] currencies = CurrencyFactory.getAllCurrencies();
        assertEquals(4, currencies.length);
        assertArrayEquals(Currency.values(), currencies);
    }

    @Test
    void testCurrencyEnumMethods() {
        assertEquals("Доллар США", Currency.USD.getName());
        assertEquals(1.0, Currency.USD.getRateToUSD(), 0.001);
        assertEquals(0.92, Currency.EUR.getRateToUSD(), 0.001);
        assertEquals(0.79, Currency.GBP.getRateToUSD(), 0.001);
        assertEquals(148.5, Currency.JPY.getRateToUSD(), 0.001);
    }
}
