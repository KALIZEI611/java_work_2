package HW_Module_08;


public class CurrencyConverter {
    private static CurrencyConverter instance;
    private double commissionRate;

    private CurrencyConverter() {
        this.commissionRate = 0.01;
    }

    public static CurrencyConverter getInstance() {
        if (instance == null) {
            instance = new CurrencyConverter();
        }
        return instance;
    }

    public double convert(double amount, Currency from, Currency to) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }

        double convertedAmount = from.convertTo(to, amount);
        double commission = convertedAmount * commissionRate;

        return convertedAmount - commission;
    }

    public double convertWithRate(double amount, Currency from, Currency to, double customRate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }
        if (customRate <= 0) {
            throw new IllegalArgumentException("Курс должен быть положительным");
        }

        double amountInUSD = amount / from.getRateToUSD();
        double convertedAmount = amountInUSD * customRate;
        double commission = convertedAmount * commissionRate;

        return convertedAmount - commission;
    }

    public void setCommissionRate(double rate) {
        if (rate < 0 || rate > 0.1) {
            throw new IllegalArgumentException("Комиссия должна быть от 0 до 10%");
        }
        this.commissionRate = rate;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public double calculateCommission(double amount) {
        return amount * commissionRate;
    }
}
