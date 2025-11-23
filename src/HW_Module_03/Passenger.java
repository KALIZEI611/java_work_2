package HW_Module_03;

class Passenger {
    private int id;
    private long arrivalTime;
    private long boardingTime;

    public Passenger(int id, long arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public void setBoardingTime(long boardingTime) {
        this.boardingTime = boardingTime;
    }

    public long getWaitingTime() {
        return boardingTime - arrivalTime;
    }

    public int getId() { return id; }
    public long getArrivalTime() { return arrivalTime; }
}

