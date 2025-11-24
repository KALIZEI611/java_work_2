package HW_Module_07;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProductAnalyzer {
    private final List<Product> products;

    public ProductAnalyzer(List<Product> products) {
        this.products = products;
    }

    public void showAllProducts() {
        System.out.println("\n=== ВСЕ ПРОДУКТЫ ===");
        if (products.isEmpty()) {
            System.out.println("Список продуктов пуст.");
            return;
        }

        products.stream()
                .forEach(System.out::println);
    }

    public void showProductsWithShortNames() {
        System.out.println("\n=== ПРОДУКТЫ С НАЗВАНИЕМ < 5 СИМВОЛОВ ===");
        List<Product> shortNamedProducts = products.stream()
                .filter(product -> product.getName().length() < 5)
                .collect(Collectors.toList());

        if (shortNamedProducts.isEmpty()) {
            System.out.println("Продуктов с названием меньше 5 символов не найдено.");
        } else {
            shortNamedProducts.forEach(System.out::println);
        }
    }

    public void countProductOccurrences() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите название продукта для поиска: ");
        String productName = scanner.nextLine().trim();

        long count = products.stream()
                .filter(product -> product.getName().equalsIgnoreCase(productName))
                .count();

        System.out.printf("Продукт '%s' встречается %d раз(а)%n", productName, count);
    }

    public void showProductsStartingWithLetter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите букву для поиска продуктов: ");
        String letterInput = scanner.nextLine().trim();

        if (letterInput.isEmpty()) {
            System.out.println("Буква не введена.");
            return;
        }

        char letter = letterInput.charAt(0);
        List<Product> filteredProducts = products.stream()
                .filter(product ->
                        !product.getName().isEmpty() &&
                                Character.toLowerCase(product.getName().charAt(0)) == Character.toLowerCase(letter))
                .collect(Collectors.toList());

        System.out.printf("\n=== ПРОДУКТЫ, НАЧИНАЮЩИЕСЯ НА '%s' ===%n", letter);
        if (filteredProducts.isEmpty()) {
            System.out.println("Продуктов, начинающихся на указанную букву, не найдено.");
        } else {
            filteredProducts.forEach(System.out::println);
        }
    }

    public void showMilkProducts() {
        System.out.println("\n=== ПРОДУКТЫ КАТЕГОРИИ 'МОЛОКО' ===");
        List<Product> milkProducts = products.stream()
                .filter(product -> "Молоко".equalsIgnoreCase(product.getCategory()))
                .collect(Collectors.toList());

        if (milkProducts.isEmpty()) {
            System.out.println("Продуктов категории 'Молоко' не найдено.");
        } else {
            milkProducts.forEach(System.out::println);
        }
    }

    public void showProductStatistics() {
        System.out.println("\n=== СТАТИСТИКА ПРОДУКТОВ ===");

        long uniqueProductsCount = products.stream()
                .map(Product::getName)
                .distinct()
                .count();
        System.out.println("Уникальных названий продуктов: " + uniqueProductsCount);

        Map<String, Long> productFrequency = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getName,
                        Collectors.counting()
                ));

        productFrequency.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(entry ->
                        System.out.printf("Самый частый продукт: '%s' (%d раз)%n",
                                entry.getKey(), entry.getValue()));

        Map<String, Long> categoryCount = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.counting()
                ));

        System.out.println("Количество продуктов по категориям:");
        categoryCount.forEach((category, count) ->
                System.out.printf("  %s: %d%n", category, count));
    }

    public List<Product> getProducts() {
        return products;
    }
}
