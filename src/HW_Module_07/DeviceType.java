package HW_Module_07;

public enum DeviceType {
    SMARTPHONE("Смартфон"),
    TABLET("Планшет"),
    LAPTOP("Ноутбук"),
    DESKTOP("Компьютер"),
    SMARTWATCH("Умные часы"),
    TV("Телевизор"),
    CAMERA("Камера"),
    HEADPHONES("Наушники"),
    SPEAKER("Колонка"),
    GAMING_CONSOLE("Игровая консоль");

    private final String russianName;

    DeviceType(String russianName) {
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