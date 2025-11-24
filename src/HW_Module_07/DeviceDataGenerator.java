package HW_Module_07;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeviceDataGenerator {

    private static final List<String> SMARTPHONE_NAMES = Arrays.asList(
            "iPhone 15", "Samsung Galaxy S24", "Xiaomi 14", "Google Pixel 8",
            "OnePlus 12", "Huawei P60", "Realme GT 5", "Nothing Phone 2"
    );

    private static final List<String> LAPTOP_NAMES = Arrays.asList(
            "MacBook Pro", "Dell XPS 13", "Lenovo ThinkPad", "HP Spectre",
            "Asus ZenBook", "Acer Swift", "MSI Prestige", "Microsoft Surface"
    );

    private static final List<String> TABLET_NAMES = Arrays.asList(
            "iPad Pro", "Samsung Galaxy Tab", "Lenovo Tab", "Huawei MatePad",
            "Xiaomi Pad", "Microsoft Surface Pro"
    );

    private static final List<String> OTHER_DEVICE_NAMES = Arrays.asList(
            "Sony WH-1000XM5", "Apple Watch Series 9", "Samsung Galaxy Watch",
            "PlayStation 5", "Xbox Series X", "LG OLED TV", "Sony Bravia"
    );

    public static List<Device> generateDevices(int count) {
        Random random = new Random();
        return Stream.generate(() -> createRandomDevice(random))
                .limit(count)
                .collect(Collectors.toList());
    }

    private static Device createRandomDevice(Random random) {
        DeviceType type = DeviceType.values()[random.nextInt(DeviceType.values().length)];
        String name = generateDeviceName(type, random);
        int year = 2020 + random.nextInt(5); // 2020-2024
        double price = switch (type) {
            case SMARTPHONE -> 30000 + random.nextDouble() * 120000;
            case LAPTOP -> 50000 + random.nextDouble() * 200000;
            case TABLET -> 15000 + random.nextDouble() * 80000;
            case DESKTOP -> 40000 + random.nextDouble() * 150000;
            case SMARTWATCH -> 10000 + random.nextDouble() * 50000;
            case TV -> 30000 + random.nextDouble() * 300000;
            case CAMERA -> 20000 + random.nextDouble() * 150000;
            case HEADPHONES -> 5000 + random.nextDouble() * 40000;
            case SPEAKER -> 3000 + random.nextDouble() * 20000;
            case GAMING_CONSOLE -> 25000 + random.nextDouble() * 50000;
        };

        Color color = Color.values()[random.nextInt(Color.values().length)];

        return new Device(name, year, Math.round(price * 100.0) / 100.0, color, type);
    }

    private static String generateDeviceName(DeviceType type, Random random) {
        return switch (type) {
            case SMARTPHONE -> SMARTPHONE_NAMES.get(random.nextInt(SMARTPHONE_NAMES.size()));
            case LAPTOP -> LAPTOP_NAMES.get(random.nextInt(LAPTOP_NAMES.size()));
            case TABLET -> TABLET_NAMES.get(random.nextInt(TABLET_NAMES.size()));
            default -> OTHER_DEVICE_NAMES.get(random.nextInt(OTHER_DEVICE_NAMES.size()));
        };
    }
    public static List<Device> generateFixedTestDevices() {
        return Arrays.asList(
                new Device("iPhone 15", 2023, 89990.00, Color.BLACK, DeviceType.SMARTPHONE),
                new Device("Samsung Galaxy S24", 2024, 79990.00, Color.WHITE, DeviceType.SMARTPHONE),
                new Device("MacBook Pro", 2023, 199990.00, Color.SILVER, DeviceType.LAPTOP),
                new Device("iPad Pro", 2022, 89990.00, Color.SPACE_GRAY, DeviceType.TABLET),
                new Device("Dell XPS 13", 2024, 129990.00, Color.BLACK, DeviceType.LAPTOP),
                new Device("Samsung Galaxy Tab", 2023, 45990.00, Color.BLUE, DeviceType.TABLET),
                new Device("PlayStation 5", 2020, 49990.00, Color.WHITE, DeviceType.GAMING_CONSOLE),
                new Device("Apple Watch Series 9", 2023, 32990.00, Color.MIDNIGHT, DeviceType.SMARTWATCH),
                new Device("Sony WH-1000XM5", 2022, 29990.00, Color.BLACK, DeviceType.HEADPHONES),
                new Device("iPhone 14", 2022, 69990.00, Color.PURPLE, DeviceType.SMARTPHONE),
                new Device("LG OLED TV", 2023, 149990.00, Color.BLACK, DeviceType.TV),
                new Device("Xiaomi 14", 2024, 59990.00, Color.GREEN, DeviceType.SMARTPHONE),
                new Device("Microsoft Surface", 2023, 159990.00, Color.GRAY, DeviceType.LAPTOP),
                new Device("Samsung Galaxy Watch", 2023, 19990.00, Color.SILVER, DeviceType.SMARTWATCH)
        );
    }
}
