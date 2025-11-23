package HW_Module_03;

import java.util.PriorityQueue;
import java.util.Scanner;

public class MainTask1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== СИМУЛЯЦИЯ МОРСКОГО ПРИЧАЛА ===");

        // Настройка параметров симуляции
        System.out.print("Введите максимальную вместимость причала (по умолчанию 30): ");
        int maxCapacity = 30;
        try {
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                maxCapacity = Integer.parseInt(input);
            }
        } catch (NumberFormatException e) {
            System.out.println("Используется значение по умолчанию: 30");
        }

        System.out.print("Введите время симуляции в часах (по умолчанию 8): ");
        int simulationHours = 8;
        try {
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                simulationHours = Integer.parseInt(input);
            }
        } catch (NumberFormatException e) {
            System.out.println("Используется значение по умолчанию: 8 часов");
        }

        // Создание модели
        BoatPierModel model = new BoatPierModel(maxCapacity);

        // Запуск симуляции
        long simulationTime = simulationHours * 60 * 60 * 1000; // в миллисекундах
        long currentTime = 0;
        String[] timesOfDay = {"утро", "день", "вечер"};
        int currentTimeOfDayIndex = 0;

        long morningEnd = 4 * 60 * 60 * 1000;    // утро: 0-4 часа
        long dayEnd = 6 * 60 * 60 * 1000;        // день: 4-6 часов

        long nextPassengerTime = model.getNextPassengerTime(timesOfDay[currentTimeOfDayIndex]);
        long nextBoatTime = model.getNextBoatTime(timesOfDay[currentTimeOfDayIndex]);

        System.out.println("\nЗапуск симуляции...");
        System.out.println("Максимальная вместимость причала: " + maxCapacity);
        System.out.println("Время симуляции: " + simulationHours + " часов");
        System.out.println("=========================================");

        // Основной цикл симуляции
        while (currentTime < simulationTime) {
            // Смена времени суток
            if (currentTime >= morningEnd && currentTimeOfDayIndex == 0) {
                currentTimeOfDayIndex = 1;
                System.out.println("\n=== Наступил день ===");
            } else if (currentTime >= dayEnd && currentTimeOfDayIndex == 1) {
                currentTimeOfDayIndex = 2;
                System.out.println("\n=== Наступил вечер ===");
            }

            String currentTimeOfDay = timesOfDay[currentTimeOfDayIndex];

            // Добавление нового пассажира
            if (currentTime >= nextPassengerTime) {
                model.addPassenger(currentTime);
                nextPassengerTime = currentTime + model.getNextPassengerTime(currentTimeOfDay);
            }

            // Прибытие нового катера
            if (currentTime >= nextBoatTime) {
                Boat boat = model.arriveBoat(currentTimeOfDay, currentTime);
                int boarded = model.boardPassengers(boat);
                System.out.printf("[Время: %d мин] Прибыл катер %d (%s), посадка: %d пассажиров, очередь: %d\n",
                        currentTime / 60000,
                        boat.getId(),
                        boat.isFinalStop() ? "конечная" : "промежуточная",
                        boarded,
                        model.getCurrentPeopleCount());
                nextBoatTime = currentTime + model.getNextBoatTime(currentTimeOfDay);
            }

            currentTime += 1000; // Увеличиваем время на 1 секунду
        }

        // Вывод статистики
        System.out.println("\n" + "=".repeat(50));
        model.printStatistics(timesOfDay[currentTimeOfDayIndex], simulationTime);

        // Дополнительный анализ
        performAdditionalAnalysis(model);

        scanner.close();
    }

    private static void performAdditionalAnalysis(BoatPierModel model) {
        System.out.println("\n=== ДОПОЛНИТЕЛЬНЫЙ АНАЛИЗ ===");

        // Анализ катеров по вместимости
        PriorityQueue<Boat> boatsByCapacity = new PriorityQueue<>(
                (b1, b2) -> Integer.compare(b2.getFreeSeats(), b1.getFreeSeats())
        );
        boatsByCapacity.addAll(model.getBoatHistory());

        System.out.println("\nКатеры отсортированные по количеству свободных мест:");
        int count = 0;
        while (!boatsByCapacity.isEmpty() && count < 10) { // Показываем первые 10
            Boat boat = boatsByCapacity.poll();
            System.out.printf("Катер %d: %d свободных мест (%s)\n",
                    boat.getId(),
                    boat.getFreeSeats(),
                    boat.isFinalStop() ? "конечная" : "промежуточная");
            count++;
        }

        // Анализ эффективности по времени суток
        System.out.println("\nЭффективность работы причала по времени суток:");
        for (String time : model.getPassengerIntervals().keySet()) {
            double passengerRate = 60.0 / model.getPassengerIntervals().get(time);
            double boatRate = 60.0 / model.getBoatIntervals().get(time);
            double efficiency = passengerRate / boatRate;

            System.out.printf("Время суток '%s':\n", time);
            System.out.printf("  - Интенсивность потока пассажиров: %.2f чел/час\n", passengerRate);
            System.out.printf("  - Интенсивность прибытия катеров: %.2f ед/час\n", boatRate);
            System.out.printf("  - Коэффициент эффективности: %.2f\n", efficiency);

            if (efficiency > 1.0) {
                System.out.println("  - Статус: Перегружен (пассажиров больше, чем может перевезти)");
            } else if (efficiency < 0.7) {
                System.out.println("  - Статус: Недогружен (катеров слишком много)");
            } else {
                System.out.println("  - Статус: Оптимальная нагрузка");
            }
        }

        // Общая статистика
        System.out.println("\nОБЩАЯ СТАТИСТИКА:");
        System.out.println("Всего катеров за симуляцию: " + model.getBoatHistory().size());
        System.out.println("Текущая очередь пассажиров: " + model.getCurrentPeopleCount());
        System.out.printf("Среднее время ожидания: %.2f минут\n", model.calculateAverageWaitingTime());
    }
}
