package HW_Module_07;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProjectorDataGenerator {

    private static final List<String> MANUFACTURERS = Arrays.asList(
            "Epson", "BenQ", "Sony", "LG", "ViewSonic",
            "Optoma", "NEC", "Panasonic", "Canon", "Acer"
    );

    private static final List<String> PROJECTOR_MODELS = Arrays.asList(
            "Home Cinema", "Business Pro", "Gaming Ultra", "Portable Mini",
            "4K Ultra HD", "Laser Projector", "Short Throw", "Office Master",
            "Cinema Pro", "Education Plus", "Wireless Max", "Bright Vision"
    );

    public static List<Projector> generateProjectors(int count) {
        Random random = new Random();
        int currentYear = java.time.Year.now().getValue();

        return Stream.generate(() -> createRandomProjector(random, currentYear))
                .limit(count)
                .collect(Collectors.toList());
    }

    private static Projector createRandomProjector(Random random, int currentYear) {
        String manufacturer = MANUFACTURERS.get(random.nextInt(MANUFACTURERS.size()));
        String model = PROJECTOR_MODELS.get(random.nextInt(PROJECTOR_MODELS.size()));
        String name = manufacturer + " " + model + " " + (1000 + random.nextInt(9000));

        int year = currentYear - random.nextInt(5); // проекторы за последние 5 лет
        double price = 20000 + random.nextDouble() * 300000; // цена от 20к до 320к

        return new Projector(name, year, Math.round(price * 100.0) / 100.0, manufacturer);
    }

    public static List<Projector> generateFixedTestProjectors() {
        int currentYear = java.time.Year.now().getValue();

        return Arrays.asList(
                new Projector("Epson Home Cinema 3200", currentYear, 89990.00, "Epson"),
                new Projector("BenQ Business Pro 450", currentYear - 1, 65990.00, "BenQ"),
                new Projector("Sony 4K Ultra HD 280", currentYear, 249990.00, "Sony"),
                new Projector("LG Laser Projector HU70", currentYear, 129990.00, "LG"),
                new Projector("ViewSonic Gaming Ultra X10", currentYear - 2, 78990.00, "ViewSonic"),
                new Projector("Optoma Short Throw 320", currentYear - 1, 55990.00, "Optoma"),
                new Projector("NEC Office Master 450", currentYear, 45990.00, "NEC"),
                new Projector("Panasonic Cinema Pro 680", currentYear - 3, 189990.00, "Panasonic"),
                new Projector("Canon Portable Mini LV-X", currentYear, 72990.00, "Canon"),
                new Projector("Acer Bright Vision H6810", currentYear - 1, 48990.00, "Acer"),
                new Projector("Epson Business Pro 450W", currentYear, 84990.00, "Epson"),
                new Projector("BenQ Gaming Ultra TK800", currentYear, 99990.00, "BenQ"),
                new Projector("Sony Laser Projector VPL", currentYear - 1, 349990.00, "Sony"),
                new Projector("LG 4K Ultra HD HU85", currentYear, 199990.00, "LG"),
                new Projector("Epson Portable Mini EF-12", currentYear, 79990.00, "Epson")
        );
    }

    public static List<String> getManufacturers() {
        return MANUFACTURERS;
    }
}
