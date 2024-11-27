import java.util.Queue;
import java.util.LinkedList;

public class SemaphoreParking {
    private int totalParkingSpots;
    private final Queue<Car> waitingCars;
    private final Object lock;
    public SemaphoreParking(int totalParkingSpots) {
        this.totalParkingSpots = totalParkingSpots;
        this.waitingCars = new LinkedList<>();
        this.lock = new Object();
    }

    public void acquire(Car car) throws InterruptedException {
        synchronized (lock) {
            long startWait = System.currentTimeMillis();
            
            if (totalParkingSpots > 0 && waitingCars.isEmpty()) {
                totalParkingSpots--;
                System.out.println(car.getInfo() + " parked. (Parking Status: " + (4 - totalParkingSpots) + " spots occupied)");
                return;
            }

            System.out.println(car.getInfo() + " waiting for a spot.");
            waitingCars.add(car);
            while (waitingCars.peek() != car || totalParkingSpots == 0) {
                lock.wait();
                Thread.sleep(500);
            }

            waitingCars.poll();
            totalParkingSpots--;

            long endWait = System.currentTimeMillis();
            car.setWaitTime((endWait - startWait + 1000)/1000);

            System.out.println(car.getInfo() + " parked after waiting for " + car.getWaitTime() + " units of time. (Parking Status: " + (4 - totalParkingSpots) + " spots occupied)");
        }
    }

    public void release(Car car) {
        synchronized (lock) {
            totalParkingSpots++;
            System.out.println(car.getInfo() + " left after " + car.getParkingDuration() + " units of time. (Parking Status: " + (4 - totalParkingSpots) + " spots occupied)");
            lock.notify();
        }
    }
    public int getOccupied() {
      return 4 - totalParkingSpots;
    }

}
