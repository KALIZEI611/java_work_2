package HW_Module_07;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProjectorAnalyzer {
    protected final List<Projector> projectors;

    public ProjectorAnalyzer(List<Projector> projectors) {
        this.projectors = projectors;
    }

    public void showAllProjectors() {
        System.out.println("\n=== ВСЕ ПРОЕКТОРЫ ===");
        if (projectors.isEmpty()) {
            System.out.println("Список проекторов пуст.");
            return;
        }

        projectors.forEach(projector ->
                System.out.println("• " + projector.toString())
        );
        System.out.println("Всего проекторов: " + projectors.size());
    }

    public void showProjectorsByManufacturer() {
        System.out.println("\n=== ПРОЕКТОРЫ ПО ПРОИЗВОДИТЕЛЮ ===");
        System.out.println("Доступные производители:");
        ProjectorDataGenerator.getManufacturers().forEach(manufacturer ->
                System.out.println("- " + manufacturer)
        );

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите производителя: ");
        String manufacturer = scanner.nextLine().trim();

        List<Projector> filteredProjectors = projectors.stream()
                .filter(projector -> projector.getManufacturer().equalsIgnoreCase(manufacturer))
                .collect(Collectors.toList());

        System.out.printf("\nПроекторы производителя '%s':%n", manufacturer);
        if (filteredProjectors.isEmpty()) {
            System.out.println("Проекторов указанного производителя не найдено.");
        } else {
            filteredProjectors.forEach(projector ->
                    System.out.println("• " + projector.toString())
            );
            System.out.printf("Найдено: %d проекторов%n", filteredProjectors.size());
        }
    }

    public void showCurrentYearProjectors() {
        int currentYear = java.time.Year.now().getValue();

        List<Projector> currentYearProjectors = projectors.stream()
                .filter(projector -> projector.getReleaseYear() == currentYear)
                .collect(Collectors.toList());

        System.out.printf("\n=== ПРОЕКТОРЫ %d ГОДА ВЫПУСКА ===%n", currentYear);
        if (currentYearProjectors.isEmpty()) {
            System.out.println("Проекторов текущего года не найдено.");
        } else {
            currentYearProjectors.forEach(projector ->
                    System.out.println("• " + projector.toString())
            );
            System.out.printf("Найдено: %d проекторов%n", currentYearProjectors.size());
        }
    }
    public void showProjectorsMoreExpensiveThan() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите минимальную цену: ");

        if (scanner.hasNextDouble()) {
            double minPrice = scanner.nextDouble();
            scanner.nextLine(); // очистка буфера

            List<Projector> filteredProjectors = projectors.stream()
                    .filter(projector -> projector.getPrice() > minPrice)
                    .collect(Collectors.toList());

            System.out.printf("\n=== ПРОЕКТОРЫ ДОРОЖЕ %.2f РУБ. ===%n", minPrice);
            if (filteredProjectors.isEmpty()) {
                System.out.println("Проекторов дороже указанной цены не найдено.");
            } else {
                filteredProjectors.forEach(projector ->
                        System.out.println("• " + projector.toString())
                );
                System.out.printf("Найдено: %d проекторов%n", filteredProjectors.size());
            }
        } else {
            System.out.println("Неверный формат цены.");
            scanner.nextLine(); // очистка неверного ввода
        }
    }

    public void showProjectorsSortedByPriceAscending() {
        System.out.println("\n=== ПРОЕКТОРЫ ПО ВОЗРАСТАНИЮ ЦЕНЫ ===");

        List<Projector> sortedProjectors = projectors.stream()
                .sorted(Comparator.comparingDouble(Projector::getPrice))
                .collect(Collectors.toList());

        if (sortedProjectors.isEmpty()) {
            System.out.println("Список проекторов пуст.");
        } else {
            sortedProjectors.forEach(projector ->
                    System.out.printf("• %.2f руб. | %s%n", projector.getPrice(), projector.toString())
            );
        }
    }

    public void showProjectorsSortedByPriceDescending() {
        System.out.println("\n=== ПРОЕКТОРЫ ПО УБЫВАНИЮ ЦЕНЫ ===");

        List<Projector> sortedProjectors = projectors.stream()
                .sorted(Comparator.comparingDouble(Projector::getPrice).reversed())
                .collect(Collectors.toList());

        if (sortedProjectors.isEmpty()) {
            System.out.println("Список проекторов пуст.");
        } else {
            sortedProjectors.forEach(projector ->
                    System.out.printf("• %.2f руб. | %s%n", projector.getPrice(), projector.toString())
            );
        }
    }

    public void showProjectorsSortedByYearAscending() {
        System.out.println("\n=== ПРОЕКТОРЫ ПО ВОЗРАСТАНИЮ ГОДА ВЫПУСКА ===");

        List<Projector> sortedProjectors = projectors.stream()
                .sorted(Comparator.comparingInt(Projector::getReleaseYear))
                .collect(Collectors.toList());

        if (sortedProjectors.isEmpty()) {
            System.out.println("Список проекторов пуст.");
        } else {
            sortedProjectors.forEach(projector ->
                    System.out.printf("• %d год | %s%n", projector.getReleaseYear(), projector.toString())
            );
        }
    }

    public void showProjectorsSortedByYearDescending() {
        System.out.println("\n=== ПРОЕКТОРЫ ПО УБЫВАНИЮ ГОДА ВЫПУСКА ===");

        List<Projector> sortedProjectors = projectors.stream()
                .sorted(Comparator.comparingInt(Projector::getReleaseYear).reversed())
                .collect(Collectors.toList());

        if (sortedProjectors.isEmpty()) {
            System.out.println("Список проекторов пуст.");
        } else {
            sortedProjectors.forEach(projector ->
                    System.out.printf("• %d год | %s%n", projector.getReleaseYear(), projector.toString())
            );
        }
    }
    public List<Projector> getProjectors() {
        return projectors;
    }
}
