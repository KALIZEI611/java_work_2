package HW_Module_03;

import java.util.*;

public class MarinePierSimulation {
    public static void main(String[] args) {
        BoatPierModel model = new BoatPierModel(30);

        long simulationTime = 8 * 60 * 60 * 1000;
        long currentTime = 0;
        String[] timesOfDay = {"утро", "день", "вечер"};
        int currentTimeOfDayIndex = 0;

        long morningEnd = 4 * 60 * 60 * 1000;
        long dayEnd = 6 * 60 * 60 * 1000;

        long nextPassengerTime = model.getNextPassengerTime(timesOfDay[currentTimeOfDayIndex]);
        long nextBoatTime = model.getNextBoatTime(timesOfDay[currentTimeOfDayIndex]);

        System.out.println("Запуск симуляции морского причала...");

        while (currentTime < simulationTime) {
            if (currentTime >= morningEnd && currentTimeOfDayIndex == 0) {
                currentTimeOfDayIndex = 1;
                System.out.println("=== Наступил день ===");
            } else if (currentTime >= dayEnd && currentTimeOfDayIndex == 1) {
                currentTimeOfDayIndex = 2;
                System.out.println("=== Наступил вечер ===");
            }

            String currentTimeOfDay = timesOfDay[currentTimeOfDayIndex];

            if (currentTime >= nextPassengerTime) {
                model.addPassenger(currentTime);
                nextPassengerTime = currentTime + model.getNextPassengerTime(currentTimeOfDay);
            }

            if (currentTime >= nextBoatTime) {
                Boat boat = model.arriveBoat(currentTimeOfDay, currentTime);
                int boarded = model.boardPassengers(boat);
                System.out.printf("Время: %d мин - Прибыл катер %d, посадка: %d пассажиров, очередь: %d\n",
                        currentTime / 60000, boat.getId(), boarded, model.getCurrentPeopleCount());
                nextBoatTime = currentTime + model.getNextBoatTime(currentTimeOfDay);
            }

            currentTime += 1000;
        }

        model.printStatistics(timesOfDay[currentTimeOfDayIndex], simulationTime);

        performAdditionalAnalysis(model);
    }

    private static void performAdditionalAnalysis(BoatPierModel model) {
        System.out.println("\n=== ДОПОЛНИТЕЛЬНЫЙ АНАЛИЗ ===");
        Set<Double> sortedWaitingTimes = new TreeSet<>();
        PriorityQueue<Boat> boatsByCapacity = new PriorityQueue<>(
                (b1, b2) -> Integer.compare(b2.getFreeSeats(), b1.getFreeSeats())
        );
        boatsByCapacity.addAll(model.getBoatHistory());
        System.out.println("Катеры отсортированные по количеству свободных мест:");
        while (!boatsByCapacity.isEmpty()) {
            Boat boat = boatsByCapacity.poll();
            System.out.printf("Катер %d: %d свободных мест\n", boat.getId(), boat.getFreeSeats());
        }

        Map<String, List<Double>> efficiencyByTime = new HashMap<>();
        for (String time : model.getPassengerIntervals().keySet()) {
            efficiencyByTime.put(time, new ArrayList<>());
        }

        for (String time : model.getPassengerIntervals().keySet()) {
            double passengerRate = 60.0 / model.getPassengerIntervals().get(time);
            double boatRate = 60.0 / model.getBoatIntervals().get(time);
            double efficiency = passengerRate / boatRate;
            efficiencyByTime.get(time).add(efficiency);

            System.out.printf("Время суток '%s': эффективность = %.2f\n", time, efficiency);
        }
    }
}
