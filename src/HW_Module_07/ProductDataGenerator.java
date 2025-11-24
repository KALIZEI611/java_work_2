package HW_Module_07;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProductDataGenerator {

    private static final List<String> PRODUCT_NAMES = Arrays.asList(
            "Молоко", "Хлеб", "Сыр", "Масло", "Йогурт", "Кефир", "Сметана",
            "Колбаса", "Сок", "Вода", "Чай", "Кофе", "Сахар", "Соль",
            "Молоко пастеризованное", "Молоко ультрапастеризованное",
            "Яблоки", "Бананы", "Апельсины", "Помидоры", "Огурцы"
    );

    private static final List<String> CATEGORIES = Arrays.asList(
            "Молоко", "Хлебобулочные", "Сыры", "Мясо", "Напитки",
            "Бакалея", "Фрукты", "Овощи"
    );

    public static List<Product> generateProducts(int count) {
        Random random = new Random();
        return Stream.generate(() -> {
                    String name = PRODUCT_NAMES.get(random.nextInt(PRODUCT_NAMES.size()));
                    String category = CATEGORIES.get(random.nextInt(CATEGORIES.size()));
                    double price = 50 + random.nextDouble() * 500;
                    return new Product(name, category, Math.round(price * 100.0) / 100.0);
                })
                .limit(count)
                .collect(Collectors.toList());
    }

    public static List<Product> generateFixedTestProducts() {
        return Arrays.asList(
                new Product("Молоко", "Молоко", 85.50),
                new Product("Хлеб", "Хлебобулочные", 45.00),
                new Product("Сыр", "Сыры", 320.00),
                new Product("Молоко", "Молоко", 90.00),
                new Product("Масло", "Молоко", 150.00),
                new Product("Йогурт", "Молоко", 65.00),
                new Product("Сок", "Напитки", 120.00),
                new Product("Молоко", "Молоко", 85.50),
                new Product("Чай", "Бакалея", 200.00),
                new Product("Кофе", "Бакалея", 450.00),
                new Product("Сок", "Напитки", 110.00),
                new Product("Хлеб", "Хлебобулочные", 40.00),
                new Product("Сыр", "Сыры", 350.00),
                new Product("Йогурт", "Молоко", 70.00)
        );
    }
}