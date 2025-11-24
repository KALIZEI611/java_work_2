package HW_Module_07;
import java.util.Objects;

public class Projector {
    private final String name;
    private final int releaseYear;
    private final double price;
    private final String manufacturer;

    public Projector(String name, int releaseYear, double price, String manufacturer) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getPrice() {
        return price;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return String.format("%s | %d год | %.2f руб. | %s",
                name, releaseYear, price, manufacturer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projector projector = (Projector) o;
        return releaseYear == projector.releaseYear &&
                Double.compare(projector.price, price) == 0 &&
                Objects.equals(name, projector.name) &&
                Objects.equals(manufacturer, projector.manufacturer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, releaseYear, price, manufacturer);
    }
}