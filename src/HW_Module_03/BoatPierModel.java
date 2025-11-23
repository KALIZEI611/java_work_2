package HW_Module_03;

import java.util.*;

public class BoatPierModel {
    private Queue<Passenger> passengerQueue = new LinkedList<>();
    private List<Boat> boatHistory = new ArrayList<>();
    private Map<String, Double> passengerIntervals = new HashMap<>();
    private Map<String, Double> boatIntervals = new HashMap<>();

    private Random random = new Random();
    private int passengerIdCounter = 1;
    private int boatIdCounter = 1;
    private int maxCapacity;

    public BoatPierModel(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        initializeTimeIntervals();
    }

    private void initializeTimeIntervals() {
        passengerIntervals.put("утро", 5.0);
        passengerIntervals.put("день", 3.0);
        passengerIntervals.put("вечер", 7.0);

        boatIntervals.put("утро", 15.0);
        boatIntervals.put("день", 10.0);
        boatIntervals.put("вечер", 20.0);
    }

    public long getNextPassengerTime(String timeOfDay) {
        double interval = passengerIntervals.get(timeOfDay);
        double lambda = 1.0 / interval;
        return (long) (-Math.log(1 - random.nextDouble()) / lambda * 60000);
    }

    public long getNextBoatTime(String timeOfDay) {
        double interval = boatIntervals.get(timeOfDay);
        double lambda = 1.0 / interval;
        return (long) (-Math.log(1 - random.nextDouble()) / lambda * 60000);
    }

    public void addPassenger(long currentTime) {
        if (passengerQueue.size() < maxCapacity) {
            passengerQueue.offer(new Passenger(passengerIdCounter++, currentTime));
        }
    }

    public Boat arriveBoat(String timeOfDay, long currentTime) {
        boolean isFinalStop = random.nextBoolean();
        int capacity = 20 + random.nextInt(31);
        Boat boat = new Boat(boatIdCounter++, capacity, isFinalStop, currentTime);
        boatHistory.add(boat);
        return boat;
    }

    public int boardPassengers(Boat boat) {
        return boat.boardPassengers(passengerQueue);
    }

    public double calculateAverageWaitingTime() {
        if (boatHistory.isEmpty()) return 0;

        long totalWaitingTime = 0;
        int totalPassengers = 0;

        Set<Passenger> processedPassengers = new HashSet<>();

        for (Boat boat : boatHistory) {
        }

        if (boatHistory.size() > 1) {
            for (int i = 1; i < boatHistory.size(); i++) {
                long interval = boatHistory.get(i).getArrivalTime() - boatHistory.get(i-1).getArrivalTime();
                totalWaitingTime += interval / 2;
            }
            return (double) totalWaitingTime / (boatHistory.size() - 1) / 60000;
        }

        return 0;
    }

    public double calculateOptimalBoatInterval(String timeOfDay, int maxPeople) {
        double passengerArrivalRate = 60.0 / passengerIntervals.get(timeOfDay);
        double requiredBoatRate = passengerArrivalRate / maxPeople;
        return 60.0 / requiredBoatRate;
    }

    public int getCurrentPeopleCount() {
        return passengerQueue.size();
    }

    public void printStatistics(String timeOfDay, long simulationTime) {
        System.out.println("\n=== СТАТИСТИКА МОДЕЛИ ПРИЧАЛА ===");
        System.out.println("Время суток: " + timeOfDay);
        System.out.println("Время моделирования: " + simulationTime / 60000 + " мин");
        System.out.println("Текущее количество людей на остановке: " + getCurrentPeopleCount());
        System.out.printf("Среднее время ожидания: %.2f мин\n", calculateAverageWaitingTime());
        System.out.printf("Оптимальный интервал между катерами (для макс. %d человек): %.2f мин\n",
                maxCapacity, calculateOptimalBoatInterval(timeOfDay, maxCapacity));

        System.out.println("\nИстория катеров:");
        for (Boat boat : boatHistory) {
            System.out.printf("Катер %d: %s, свободных мест: %d\n",
                    boat.getId(),
                    boat.isFinalStop() ? "конечная" : "промежуточная",
                    boat.getFreeSeats());
        }

        System.out.println("\nПараметры генерации:");
        System.out.printf("Интервал между пассажирами: %.1f мин\n", passengerIntervals.get(timeOfDay));
        System.out.printf("Интервал между катерами: %.1f мин\n", boatIntervals.get(timeOfDay));
    }

    public Queue<Passenger> getPassengerQueue() { return passengerQueue; }
    public List<Boat> getBoatHistory() { return boatHistory; }
    public Map<String, Double> getPassengerIntervals() { return passengerIntervals; }
    public Map<String, Double> getBoatIntervals() { return boatIntervals; }
}
