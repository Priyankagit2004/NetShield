public class RingNetwork {
    private Device head;
    private int size; 

    private Device leftBoundary;
    private Device rightBoundary;

    // Constructor
    public RingNetwork(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Network size must be positive");
        }

        Device first = new Device(1);
        this.head = first;
        Device prev = first; 

        // Create circular linked list
        for (int i = 2; i <= n; i++) {
            Device temp = new Device(i);
            prev.setNext(temp);
            temp.setPrev(prev);
            prev = temp;
        }

        prev.setNext(first);
        first.setPrev(prev);

        this.size = n;
    }

    public Device getDeviceById(int id) {
        Device temp = head;
        int count = 0;

        while (count < size) {
            if (temp.getDeviceId() == id) return temp;
            temp = temp.getNext();
            count++;
        }
        return null;
    }

    public void displayNetwork() {
        Device temp = head;
        int count = 0;

        do {
            System.out.print(temp);
            if (temp.rightLinkBroken()) {
                System.out.print(" X ");
            } else {
                System.out.print(" <-> ");
            }
            temp = temp.getNext();
            count++;
        } while (count < size);

        System.out.println("(back to start)\n");
    }

    public void breakLink(Device a, Device b) {
        if (a.getNext() == b) {
            a.setRightLinkBroken(true);
            b.setLeftLinkBroken(true);
        } else if (b.getNext() == a) {
            b.setRightLinkBroken(true);
            a.setLeftLinkBroken(true);
        }
    }

    public void infectInitialDevice(int id) {
        Device temp = getDeviceById(id);

        if (temp == null) {
            System.out.println("Device not found");
            return;
        }

        temp.setInfected(true);
        leftBoundary = temp;
        rightBoundary = temp;

        System.out.println("Initial infection at Device " + id);
    }

    public boolean canSpread() {
        return leftBoundary.canSpreadLeft() || rightBoundary.canSpreadRight();
    }

    public void spreadNextStep() {
        boolean canLeft = leftBoundary.canSpreadLeft();
        boolean canRight = rightBoundary.canSpreadRight();

        // If no spreading possible
        if (!canLeft && !canRight) {
             System.out.println("Virus contained");
            return;
        }

        // If only right side possible
        if (canRight && !canLeft) {
            Device temp = rightBoundary.getNext();
            temp.setInfected(true);
            rightBoundary = temp;
            return;
        }

        // If only left side possible
        if (canLeft && !canRight) {
            Device temp = leftBoundary.getPrev();
            temp.setInfected(true);
            leftBoundary = temp;
            return;
        }

        // If both sides possible then randomly choose one side
        int rand = (int)(Math.random() * 2);

        if (rand == 0) {
            Device temp = leftBoundary.getPrev();
            temp.setInfected(true);
            leftBoundary = temp;
        } else {
            Device temp = rightBoundary.getNext();
            temp.setInfected(true);
            rightBoundary = temp;
        }
    }

    public void containOneStep() {
        if (leftBoundary.canSpreadLeft()) {
            breakLink(leftBoundary, leftBoundary.getPrev());
        } else if (rightBoundary.canSpreadRight()) {
            breakLink(rightBoundary, rightBoundary.getNext());
        }
    }

    public int countSafeDevices() {
        int count = 0;
        Device temp = head;
        int i = 0;

        while (i < size) {
            if (!temp.isInfected()) count++;
            temp = temp.getNext();
            i++;
        }
        return count;
    }

    public void simulate() {
        while (canSpread()) {
            containOneStep();
            spreadNextStep();
            displayNetwork();
        }

        System.out.println("Simulation finished.");
        System.out.println("Safe devices: " + countSafeDevices());
    }
}