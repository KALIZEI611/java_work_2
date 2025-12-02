package HW_Module_08;

public enum Currency {
    USD("Доллар США", 1.0),
    EUR("Евро", 0.92),
    GBP("Фунт стерлингов", 0.79),
    JPY("Японская йена", 148.5);

    private final String name;
    private final double rateToUSD;

    Currency(String name, double rateToUSD) {
        this.name = name;
        this.rateToUSD = rateToUSD;
    }

    public String getName() {
        return name;
    }

    public double getRateToUSD() {
        return rateToUSD;
    }

    public double convertTo(Currency target, double amount) {
        double amountInUSD = amount / this.rateToUSD;
        return amountInUSD * target.rateToUSD;
    }
}
