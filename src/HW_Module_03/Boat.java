package HW_Module_03;

import java.util.Iterator;
import java.util.Queue;
import java.util.Random;

public class Boat {
    private int id;
    private int capacity;
    private int freeSeats;
    private boolean isFinalStop;
    private long arrivalTime;

    public Boat(int id, int capacity, boolean isFinalStop, long arrivalTime) {
        this.id = id;
        this.capacity = capacity;
        this.freeSeats = new Random().nextInt(capacity) + 1;
        this.isFinalStop = isFinalStop;
        this.arrivalTime = arrivalTime;
    }

    public int getFreeSeats() { return freeSeats; }
    public boolean isFinalStop() { return isFinalStop; }
    public int getId() { return id; }
    public long getArrivalTime() { return arrivalTime; }

    public int boardPassengers(Queue<Passenger> passengers) {
        int boarded = 0;
        Iterator<Passenger> iterator = passengers.iterator();

        while (iterator.hasNext() && boarded < freeSeats) {
            Passenger passenger = iterator.next();
            passenger.setBoardingTime(arrivalTime);
            iterator.remove();
            boarded++;
        }

        freeSeats -= boarded;
        return boarded;
    }
}
