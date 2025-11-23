package HW_Module_03;

import java.util.*;
import java.util.stream.Collectors;

public class TaxInspectorateDatabase {
    private Map<String, Person> database;

    public TaxInspectorateDatabase() {
        this.database = new HashMap<>();
        initializeSampleData();
    }

    private void initializeSampleData() {
        addPerson("1234567890", "Иван", "Иванов", "ул. Ленина 10, Москва", "+7-999-123-45-67");
        addPerson("2345678901", "Петр", "Петров", "ул. Пушкина 25, Санкт-Петербург", "+7-999-234-56-78");
        addPerson("3456789012", "Мария", "Сидорова", "пр. Мира 15, Казань", "+7-999-345-67-89");

        addFineToPerson("1234567890", new Fine("F001", "Налог на имущество", 5000.0, "Москва", "Неуплата налога на квартиру"));
        addFineToPerson("1234567890", new Fine("F002", "Дорожный штраф", 1500.0, "Москва", "Превышение скорости"));
        addFineToPerson("2345678901", new Fine("F003", "НДФЛ", 7500.0, "Санкт-Петербург", "Неуплата подоходного налога"));
        addFineToPerson("3456789012", new Fine("F004", "Земельный налог", 3200.0, "Казань", "Неуплата налога на землю"));
        addFineToPerson("3456789012", new Fine("F005", "Дорожный штраф", 2000.0, "Москва", "Парковка в неположенном месте"));
        addFineToPerson("3456789012", new Fine("F006", "Налог на транспорт", 4500.0, "Казань", "Неуплата транспортного налога"));
    }

    public void printFullDatabase() {
        System.out.println("\n=== ПОЛНАЯ БАЗА ДАННЫХ НАЛОГОВОЙ ИНСПЕКЦИИ ===");

        if (database.isEmpty()) {
            System.out.println("База данных пуста.");
            return;
        }

        // Используем TreeMap для сортировки по ID
        Map<String, Person> sortedDatabase = new TreeMap<>(database);

        sortedDatabase.forEach((id, person) -> {
            System.out.println("\n" + person);
            if (!person.getFines().isEmpty()) {
                System.out.println("  Штрафы:");
                person.getFines().forEach(fine -> System.out.println("    - " + fine));
            } else {
                System.out.println("  Штрафы: отсутствуют");
            }
        });

        printDatabaseStatistics();
    }

    // 2. Распечатка данных по конкретному коду
    public void printDataByPersonalId(String personalId) {
        System.out.println("\n=== ДАННЫЕ ПО ПЕРСОНАЛЬНОМУ КОДУ: " + personalId + " ===");

        Person person = database.get(personalId);
        if (person == null) {
            System.out.println("Человек с персональным кодом '" + personalId + "' не найден.");
            return;
        }

        System.out.println(person);
        if (!person.getFines().isEmpty()) {
            System.out.println("Штрафы:");
            person.getFines().forEach(fine -> System.out.println("  - " + fine));
        } else {
            System.out.println("Штрафы: отсутствуют");
        }
    }

    // 3. Распечатка данных по конкретному типу штрафа
    public void printDataByFineType(String fineType) {
        System.out.println("\n=== ДАННЫЕ ПО ТИПУ ШТРАФА: " + fineType + " ===");

        List<Fine> finesOfType = new ArrayList<>();
        List<Person> personsWithFines = new ArrayList<>();

        // Собираем все штрафы указанного типа
        for (Person person : database.values()) {
            List<Fine> personFines = person.getFinesByType(fineType);
            if (!personFines.isEmpty()) {
                finesOfType.addAll(personFines);
                personsWithFines.add(person);
            }
        }

        if (finesOfType.isEmpty()) {
            System.out.println("Штрафы типа '" + fineType + "' не найдены.");
            return;
        }

        System.out.println("Найдено штрафов: " + finesOfType.size());
        double totalAmount = finesOfType.stream().mapToDouble(Fine::getAmount).sum();
        System.out.printf("Общая сумма: %.2f\n", totalAmount);

        // Группируем по людям
        Map<Person, List<Fine>> finesByPerson = new HashMap<>();
        for (Person person : personsWithFines) {
            finesByPerson.put(person, person.getFinesByType(fineType));
        }

        finesByPerson.forEach((person, fines) -> {
            System.out.println("\n" + person.getFirstName() + " " + person.getLastName() + " (ID: " + person.getPersonalId() + ")");
            fines.forEach(fine -> System.out.println("  - " + fine));
        });
    }

    // 4. Распечатка данных по конкретному городу
    public void printDataByCity(String city) {
        System.out.println("\n=== ДАННЫЕ ПО ГОРОДУ: " + city + " ===");

        List<Fine> finesInCity = new ArrayList<>();
        Set<Person> personsInCity = new HashSet<>();

        // Собираем все штрафы из указанного города
        for (Person person : database.values()) {
            List<Fine> personFines = person.getFinesByCity(city);
            if (!personFines.isEmpty()) {
                finesInCity.addAll(personFines);
                personsInCity.add(person);
            }
        }

        if (finesInCity.isEmpty()) {
            System.out.println("Штрафы из города '" + city + "' не найдены.");
            return;
        }

        System.out.println("Найдено штрафов: " + finesInCity.size());
        double totalAmount = finesInCity.stream().mapToDouble(Fine::getAmount).sum();
        System.out.printf("Общая сумма: %.2f\n", totalAmount);

        // Группируем по типам штрафов
        Map<String, List<Fine>> finesByType = finesInCity.stream()
                .collect(Collectors.groupingBy(Fine::getType));

        System.out.println("\nРаспределение по типам штрафов:");
        finesByType.forEach((type, fines) -> {
            double typeAmount = fines.stream().mapToDouble(Fine::getAmount).sum();
            System.out.printf("  %s: %d штрафов, сумма: %.2f\n", type, fines.size(), typeAmount);
        });

        System.out.println("\nЛюди со штрафами в городе " + city + ":");
        personsInCity.forEach(person -> {
            double personAmount = person.getFinesByCity(city).stream()
                    .mapToDouble(Fine::getAmount)
                    .sum();
            System.out.printf("  %s %s (ID: %s) - %.2f\n",
                    person.getFirstName(), person.getLastName(),
                    person.getPersonalId(), personAmount);
        });
    }

    // 5. Добавление нового человека с информацией о нем
    public boolean addPerson(String personalId, String firstName, String lastName, String address, String phone) {
        if (database.containsKey(personalId)) {
            System.out.println("Ошибка: человек с персональным кодом '" + personalId + "' уже существует.");
            return false;
        }

        Person person = new Person(personalId, firstName, lastName, address, phone);
        database.put(personalId, person);
        System.out.println("Человек успешно добавлен: " + firstName + " " + lastName);
        return true;
    }

    // 6. Добавление новых штрафов для существующей записи
    public boolean addFineToPerson(String personalId, Fine fine) {
        Person person = database.get(personalId);
        if (person == null) {
            System.out.println("Ошибка: человек с персональным кодом '" + personalId + "' не найден.");
            return false;
        }

        // Проверяем уникальность ID штрафа
        if (person.getFine(fine.getId()) != null) {
            System.out.println("Ошибка: штраф с ID '" + fine.getId() + "' уже существует.");
            return false;
        }

        person.addFine(fine);
        System.out.println("Штраф успешно добавлен для " + person.getFirstName() + " " + person.getLastName());
        return true;
    }

    // 7. Удаление штрафа
    public boolean removeFine(String personalId, String fineId) {
        Person person = database.get(personalId);
        if (person == null) {
            System.out.println("Ошибка: человек с персональным кодом '" + personalId + "' не найден.");
            return false;
        }

        if (person.removeFine(fineId)) {
            System.out.println("Штраф с ID '" + fineId + "' успешно удален.");
            return true;
        } else {
            System.out.println("Ошибка: штраф с ID '" + fineId + "' не найден.");
            return false;
        }
    }

    // 8. Замена информации о человеке и его штрафах
    public boolean replacePersonInfo(String personalId, String newFirstName, String newLastName,
                                     String newAddress, String newPhone, List<Fine> newFines) {
        Person person = database.get(personalId);
        if (person == null) {
            System.out.println("Ошибка: человек с персональным кодом '" + personalId + "' не найден.");
            return false;
        }

        person.setFirstName(newFirstName);
        person.setLastName(newLastName);
        person.setAddress(newAddress);
        person.setPhone(newPhone);
        person.setFines(newFines);

        System.out.println("Информация о человеке с ID '" + personalId + "' успешно обновлена.");
        return true;
    }

    // Дополнительные методы

    // Поиск человека по имени/фамилии
    public List<Person> findPersonByName(String firstName, String lastName) {
        return database.values().stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                        person.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());
    }

    // Получение всех типов штрафов в базе
    public Set<String> getAllFineTypes() {
        return database.values().stream()
                .flatMap(person -> person.getFines().stream())
                .map(Fine::getType)
                .collect(Collectors.toSet());
    }

    // Получение всех городов в базе
    public Set<String> getAllCities() {
        return database.values().stream()
                .flatMap(person -> person.getFines().stream())
                .map(Fine::getCity)
                .collect(Collectors.toSet());
    }

    // Статистика базы данных
    private void printDatabaseStatistics() {
        System.out.println("\n=== СТАТИСТИКА БАЗЫ ДАННЫХ ===");
        System.out.println("Всего людей в базе: " + database.size());

        long totalFines = database.values().stream()
                .mapToLong(person -> person.getFines().size())
                .sum();
        System.out.println("Всего штрафов: " + totalFines);

        double totalAmount = database.values().stream()
                .mapToDouble(Person::getTotalFinesAmount)
                .sum();
        System.out.printf("Общая сумма всех штрафов: %.2f\n", totalAmount);

        // Топ-5 людей с наибольшей суммой штрафов
        System.out.println("\nТоп-5 людей с наибольшей суммой штрафов:");
        database.values().stream()
                .sorted((p1, p2) -> Double.compare(p2.getTotalFinesAmount(), p1.getTotalFinesAmount()))
                .limit(5)
                .forEach(person -> System.out.printf("  %s %s: %.2f\n",
                        person.getFirstName(), person.getLastName(),
                        person.getTotalFinesAmount()));
    }

    public Map<String, Person> getDatabase() { return new HashMap<>(database); }
}
