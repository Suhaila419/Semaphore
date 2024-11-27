import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int totalParkingSpots = 4;
        ParkingLot parkingLot = new ParkingLot(totalParkingSpots);

        Gate gate1 = new Gate(1, parkingLot);
        Gate gate2 = new Gate(2, parkingLot);
        Gate gate3 = new Gate(3, parkingLot);

        List<Gate> gates = List.of(gate1, gate2, gate3);
        List<Thread> gateThreads = new ArrayList<>();

        String inputFilePath = "input.txt";
        //Reading input from file
        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                int gateNumber = Integer.parseInt(parts[0].split(" ")[1]);
                int carId = Integer.parseInt(parts[1].split(" ")[1]);
                int arrivalTime = Integer.parseInt(parts[2].split(" ")[1]);
                int parkingDuration = Integer.parseInt(parts[3].split(" ")[1]);

                Car car = new Car(carId, gateNumber, arrivalTime, parkingDuration, parkingLot);
                if (gateNumber == 1) gate1.addCar(car);
                else if (gateNumber == 2) gate2.addCar(car);
                else if (gateNumber == 3) gate3.addCar(car);
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Start gates
        for (Gate gate : gates) {
            Thread gateThread = new Thread(gate);
            gateThreads.add(gateThread);
            gateThread.start();
        }

        // Wait for all gates to complete
        for (Thread gateThread : gateThreads) {
            try {
                gateThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Print final summary
        parkingLot.printService();
    }
}
