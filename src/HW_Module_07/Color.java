package HW_Module_07;
public enum Color {
    BLACK("Черный"),
    WHITE("Белый"),
    SILVER("Серебристый"),
    GOLD("Золотой"),
    BLUE("Синий"),
    RED("Красный"),
    GREEN("Зеленый"),
    PURPLE("Фиолетовый"),
    PINK("Розовый"),
    GRAY("Серый"),
    SPACE_GRAY("Серый космос"),
    MIDNIGHT("Ночной синий");

    private final String russianName;

    Color(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }

    @Override
    public String toString() {
        return russianName;
    }
}