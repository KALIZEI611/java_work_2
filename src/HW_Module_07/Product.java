package HW_Module_07;

public class Product {
    private final String name;
    private final String category;
    private final double price;

    public Product(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Product(String name, String category) {
        this(name, category, 0.0);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        if (price > 0) {
            return String.format("%s (%s) - %.2f руб.", name, category, price);
        }
        return String.format("%s (%s)", name, category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) && category.equals(product.category);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, category);
    }
}