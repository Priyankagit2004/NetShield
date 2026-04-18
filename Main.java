import java.util.Scanner;

public class Main {

    private static final int MAX_DEVICES = 15;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=======================================");
        System.out.println("       NETWORK SIMULATION SYSTEM       ");
        System.out.println("=======================================");

        // Choose network type
        System.out.println("Choose Network Type:");
        System.out.println("1. Ring Network");
        System.out.println("2. Bus Network");
        System.out.print("Enter choice (1 or 2): ");
        int choice = sc.nextInt();

        // Input network size
        System.out.print("\nEnter number of devices (1 - 15): ");
        int size = sc.nextInt();

        if (size < 1 || size > MAX_DEVICES) {
            System.out.println(" Invalid size! Must be between 1 and 15.");
            return;
        }

        // Input infected device
        System.out.print("Enter initial infected device ID (1 - " + size + "): ");
        int infectedId = sc.nextInt();

        if (infectedId < 1 || infectedId > size) {
            System.out.println(" Invalid device ID!");
            return;
        }

        // Run simulation
        runSimulation(size, infectedId, choice);

        sc.close();
    }

    // Single simulation (not multiple tests now)
    private static void runSimulation(int size, int infectedId, int choice) {

        System.out.println("\n=======================================");
        System.out.println("Starting Simulation...");
        System.out.println("=======================================\n");

        try {
            if (choice == 1) {
                System.out.println("Using Ring Network\n");

                RingNetwork network = new RingNetwork(size);

                network.displayNetwork();
                System.out.println();

                network.infectInitialDevice(infectedId);
                network.displayNetwork();
                System.out.println();

                network.simulate();

            } else if (choice == 2) {
                System.out.println("Using Bus Network\n");

                BusNetwork network = new BusNetwork(size);

                network.displayNetwork();
                System.out.println();

                network.infectInitialDevice(infectedId);
                network.displayNetwork();
                System.out.println();

                network.simulate();

            } else {
                System.out.println("Invalid choice!");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\n=======================================");
        System.out.println("Simulation Ended");
        System.out.println("=======================================");
    }
}