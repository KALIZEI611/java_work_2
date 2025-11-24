package HW_Module_07;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DeviceAnalyzer {
    protected final List<Device> devices;

    public DeviceAnalyzer(List<Device> devices) {
        this.devices = devices;
    }

    public void showAllDevices() {
        System.out.println("\n=== ВСЕ УСТРОЙСТВА ===");
        if (devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }

        devices.forEach(device ->
                System.out.println("• " + device.toString())
        );
    }

    public void showDevicesByColor() {
        System.out.println("\n=== УСТРОЙСТВА ПО ЦВЕТУ ===");
        System.out.println("Доступные цвета:");
        for (Color color : Color.values()) {
            System.out.println("- " + color.getRussianName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите цвет: ");
        String colorInput = scanner.nextLine().trim();

        try {
            Color selectedColor = findColorByRussianName(colorInput);
            List<Device> filteredDevices = devices.stream()
                    .filter(device -> device.getColor() == selectedColor)
                    .collect(Collectors.toList());

            System.out.printf("\nУстройства цвета '%s':%n", selectedColor.getRussianName());
            if (filteredDevices.isEmpty()) {
                System.out.println("Устройств указанного цвета не найдено.");
            } else {
                filteredDevices.forEach(device ->
                        System.out.println("• " + device.toString())
                );
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Цвет не найден. Проверьте правильность ввода.");
        }
    }

    public void showDevicesByReleaseYear() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите год выпуска: ");

        if (scanner.hasNextInt()) {
            int year = scanner.nextInt();
            scanner.nextLine(); // очистка буфера

            List<Device> filteredDevices = devices.stream()
                    .filter(device -> device.getReleaseYear() == year)
                    .collect(Collectors.toList());

            System.out.printf("\n=== УСТРОЙСТВА %d ГОДА ВЫПУСКА ===%n", year);
            if (filteredDevices.isEmpty()) {
                System.out.println("Устройств указанного года выпуска не найдено.");
            } else {
                filteredDevices.forEach(device ->
                        System.out.println("• " + device.toString())
                );
            }
        } else {
            System.out.println("Неверный формат года.");
            scanner.nextLine(); // очистка неверного ввода
        }
    }

    public void showDevicesMoreExpensiveThan() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите минимальную цену: ");

        if (scanner.hasNextDouble()) {
            double minPrice = scanner.nextDouble();
            scanner.nextLine(); // очистка буфера

            List<Device> filteredDevices = devices.stream()
                    .filter(device -> device.getPrice() > minPrice)
                    .collect(Collectors.toList());

            System.out.printf("\n=== УСТРОЙСТВА ДОРОЖЕ %.2f РУБ. ===%n", minPrice);
            if (filteredDevices.isEmpty()) {
                System.out.println("Устройств дороже указанной цены не найдено.");
            } else {
                filteredDevices.forEach(device ->
                        System.out.println("• " + device.toString())
                );
            }
        } else {
            System.out.println("Неверный формат цены.");
            scanner.nextLine(); // очистка неверного ввода
        }
    }

    public void showDevicesByType() {
        System.out.println("\n=== УСТРОЙСТВА ПО ТИПУ ===");
        System.out.println("Доступные типы:");
        for (DeviceType type : DeviceType.values()) {
            System.out.println("- " + type.getRussianName());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите тип устройства: ");
        String typeInput = scanner.nextLine().trim();

        try {
            DeviceType selectedType = findDeviceTypeByRussianName(typeInput);
            List<Device> filteredDevices = devices.stream()
                    .filter(device -> device.getType() == selectedType)
                    .collect(Collectors.toList());

            System.out.printf("\nУстройства типа '%s':%n", selectedType.getRussianName());
            if (filteredDevices.isEmpty()) {
                System.out.println("Устройств указанного типа не найдено.");
            } else {
                filteredDevices.forEach(device ->
                        System.out.println("• " + device.toString())
                );
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Тип устройства не найден. Проверьте правильность ввода.");
        }
    }

    public void showDevicesByYearRange() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nВведите начальный год диапазона: ");
        int startYear = scanner.nextInt();
        System.out.print("Введите конечный год диапазона: ");
        int endYear = scanner.nextInt();
        scanner.nextLine(); // очистка буфера

        if (startYear > endYear) {
            System.out.println("Ошибка: начальный год не может быть больше конечного.");
            return;
        }

        List<Device> filteredDevices = devices.stream()
                .filter(device -> device.getReleaseYear() >= startYear &&
                        device.getReleaseYear() <= endYear)
                .collect(Collectors.toList());

        System.out.printf("\n=== УСТРОЙСТВА С %d ПО %d ГОД ===%n", startYear, endYear);
        if (filteredDevices.isEmpty()) {
            System.out.println("Устройств в указанном диапазоне лет не найдено.");
        } else {
            filteredDevices.forEach(device ->
                    System.out.println("• " + device.toString())
            );
        }
    }
    private Color findColorByRussianName(String russianName) {
        for (Color color : Color.values()) {
            if (color.getRussianName().equalsIgnoreCase(russianName)) {
                return color;
            }
        }
        throw new IllegalArgumentException("Color not found: " + russianName);
    }

    private DeviceType findDeviceTypeByRussianName(String russianName) {
        for (DeviceType type : DeviceType.values()) {
            if (type.getRussianName().equalsIgnoreCase(russianName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Device type not found: " + russianName);
    }

    public List<Device> getDevices() {
        return devices;
    }
}