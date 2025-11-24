package HW_Module_07;

import java.util.Objects;

public class Device {
    private final String name;
    private final int releaseYear;
    private final double price;
    private final Color color;
    private final DeviceType type;

    public Device(String name, int releaseYear, double price, Color color, DeviceType type) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.price = price;
        this.color = color;
        this.type = type;
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

    public Color getColor() {
        return color;
    }

    public DeviceType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s | %d год | %.2f руб. | %s | %s",
                name, releaseYear, price, color.getRussianName(), type.getRussianName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return releaseYear == device.releaseYear &&
                Double.compare(device.price, price) == 0 &&
                Objects.equals(name, device.name) &&
                color == device.color &&
                type == device.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, releaseYear, price, color, type);
    }
}