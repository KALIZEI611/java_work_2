package HW_Module_03;

import java.util.Date;

public class Fine {
    private String id;
    private String type;
    private double amount;
    private String city;
    private Date date;
    private String description;

    public Fine(String id, String type, double amount, String city, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.city = city;
        this.date = new Date();
        this.description = description;
    }

    public Fine(String id, String type, double amount, String city, Date date, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.city = city;
        this.date = date;
        this.description = description;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getCity() { return city; }
    public Date getDate() { return date; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("Штраф ID: %s | Тип: %s | Сумма: %.2f | Город: %s | Дата: %s | Описание: %s",
                id, type, amount, city, date, description);
    }
}

