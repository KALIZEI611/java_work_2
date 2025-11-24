package HW_Module_07;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdvancedProductAnalyzer extends ProductAnalyzer {

    public AdvancedProductAnalyzer(List<Product> products) {
        super(products);
    }

    public void showMostExpensiveProducts() {
        System.out.println("\n=== САМЫЕ ДОРОГИЕ ПРОДУКТЫ ===");

        Optional<Double> maxPrice = getProducts().stream()
                .map(Product::getPrice)
                .max(Double::compareTo);

        if (maxPrice.isPresent()) {
            List<Product> expensiveProducts = getProducts().stream()
                    .filter(product -> product.getPrice() == maxPrice.get())
                    .collect(Collectors.toList());

            expensiveProducts.forEach(System.out::println);
        } else {
            System.out.println("Продукты не найдены.");
        }
    }

    public void showProductsByCategory() {
        System.out.println("\n=== ПРОДУКТЫ ПО КАТЕГОРИЯМ ===");

        Map<String, List<Product>> productsByCategory = getProducts().stream()
                .collect(Collectors.groupingBy(Product::getCategory));

        productsByCategory.forEach((category, productList) -> {
            System.out.println("\n" + category + ":");
            productList.forEach(product ->
                    System.out.println("  - " + product.getName() + " (" + product.getPrice() + " руб.)"));
        });
    }
    public void showAveragePriceByCategory() {
        System.out.println("\n=== СРЕДНЯЯ ЦЕНА ПО КАТЕГОРИЯМ ===");

        Map<String, Double> averagePriceByCategory = getProducts().stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.averagingDouble(Product::getPrice)
                ));

        averagePriceByCategory.forEach((category, avgPrice) ->
                System.out.printf("%s: %.2f руб.%n", category, avgPrice));
    }
}
