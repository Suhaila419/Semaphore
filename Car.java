class Car implements Runnable {
  private final int id;
  private final int gateNumber;
  private final int arrivalTime;
  private final int parkingDuration;
  private final ParkingLot parkingLot;
  private String info;
  private long waitTime;

  public Car(int id, int gateNumber, int arrivalTime, int parkingDuration, ParkingLot parkingLot) {
      this.id = id;
      this.gateNumber = gateNumber;
      this.arrivalTime = arrivalTime;
      this.parkingDuration = parkingDuration;
      this.parkingLot = parkingLot;
      this.waitTime = 0;
  }

  public void setInfo(String info) {
      this.info = info;
  }

  public String getInfo() {
      return info;
  }

  public void setWaitTime(long waitTime) {
      this.waitTime = waitTime;
  }

  public long getWaitTime() {
      return waitTime;
  }

  public int getParkingDuration() {
      return parkingDuration;
  }
  public int getGateNumber() {
    return gateNumber;
  }
  public int getId() {
    return id;
  }
  public int getArrivalTime() {
    return arrivalTime;
  }


  @Override
  public void run() {
      try {
          Thread.sleep(arrivalTime * 1000); // Simulate arrival delay
          parkingLot.enterParking(this); // Attempt to park
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }
  }
}
