class ParkingLot {
  private final SemaphoreParking semaphore;
  private final int[] gateCounts;
  private int totalCarsServed;

  public ParkingLot(int totalParkingSpots) {
      this.semaphore = new SemaphoreParking(totalParkingSpots);
      this.gateCounts = new int[3];
      this.totalCarsServed = 0;
  }
  public void enterParking(Car car) throws InterruptedException {
      car.setInfo("Car " + car.getId()+ " from Gate " + car.getGateNumber());
      System.out.println(car.getInfo() + " arrived at time " + car.getArrivalTime());
      semaphore.acquire(car);
      synchronized (this) {
          gateCounts[car.getGateNumber() - 1]++;
          totalCarsServed++;
      }

      Thread.sleep(car.getParkingDuration() * 1000);
      semaphore.release(car);
  }

  public void printService() {
      System.out.println("Total Cars Served: " + totalCarsServed);
      System.out.println("Current Cars in Parking: " + semaphore.getOccupied());
      System.out.println("Details:");
      for (int i = 0; i < gateCounts.length; i++) {
          System.out.println("- Gate " + (i + 1) + " served " + gateCounts[i] + " cars.");
      }
  }
}
