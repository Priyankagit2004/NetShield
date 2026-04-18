public class BusNetwork {
    private Device head;
    private int size;

    //Boundary tracking fields
    private Device leftBoundary;
    private Device rightBoundary;

    //Constructor
    public BusNetwork (int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Network size must be positive");
        }

        Device h = new Device(1);
        this.head = h;

        for(int i = 2; i <= n; i++) {
            Device temp = new Device(i);
            h.setNext(temp);
            temp.setPrev(h);
            h = temp;
        }

        this.size = n;
    }

    //Getters
    public Device getHead() {
        return this.head;
    }

    public int getSize() {
        return this.size;
    }

    public Device getLeftBoundary() {
        return this.leftBoundary;
    }

    public Device getRightBoundary() {
        return this.rightBoundary;
    }

    //Methods
    public Device getDeviceById(int id) {
        
        Device temp = this.head;

        while(temp != null) {
            if(temp.getDeviceId() == id) return temp;
            temp = temp.getNext();
        }

        return null;
    }

    public void displayNetwork() {
        Device temp = this.head;

        System.out.print("NULL <-> ");
        while(temp != null) {
            System.out.print(temp);
            if(temp.rightLinkBroken()) {
                System.out.print(" X ");
            } else {
                System.out.print(" <-> ");
            }
            temp = temp.getNext();
        }
        System.out.print("NULL\n");

    }

    public void breakLink(Device a, Device b) {
        if (a.getNext() == b) {
            a.setRightLinkBroken(true);
            b.setLeftLinkBroken(true);
        }
        else if (b.getNext() == a) {
            b.setRightLinkBroken(true);
            a.setLeftLinkBroken(true);
        }
    }

    public int countSafeDevices() {
        int count = 0;

        Device temp = this.head;
        while(temp != null) {
            if(!temp.isInfected()) count++;
            temp = temp.getNext();
        }

        return count;
    }

    public boolean canSpread() {
        if(leftBoundary == null) return false;
        return leftBoundary.canSpreadLeft() || rightBoundary.canSpreadRight();
    }

    //Simulation methods
    public void infectInitialDevice(int id) {
        if (leftBoundary != null) {
            System.out.println("Infection already initialized.");
            return;
        }

        Device temp = getDeviceById(id);

        if(temp == null) {
            System.out.println("Warning : Device with this id not found");
            return;
        }

        temp.setInfected(true);
        this.leftBoundary = temp;
        this.rightBoundary = temp;

        System.out.println("Initial infection at Device " + id);
    }

    //Spread virus
    public void spreadNextStep() {
        Device l = this.getLeftBoundary();
        Device r = this.getRightBoundary();

        if(l == null) {
            System.out.println("No infected node");
            return;
        }

        boolean canLeft = l.canSpreadLeft();
        boolean canRight = r.canSpreadRight();

        if(!canLeft && !canRight) {
            System.out.println("Virus contained");
            return;
        }

        if(canRight && !canLeft) {
            //Infect the node next to right boundary
            Device temp = r.getNext();
            temp.setInfected(true);
            this.rightBoundary = temp;
            return;
        }

        if(canLeft && !canRight) {
            //Infect the node prev to left boundary
            Device temp = l.getPrev();
            temp.setInfected(true);
            this.leftBoundary = temp;
            return;
        }

        //Generate node to be infected randomly (0-> left, 1-> right)
        int range = 2; // 0 or 1
        int rand = (int) (Math.random() * range);

        if(rand == 0) {
            //Infect the node prev to left boundary
            Device temp = l.getPrev();
            temp.setInfected(true);
            this.leftBoundary = temp;
            return;
        }

        if(rand == 1) {
            //Infect the node next to right boundary
            Device temp = r.getNext();
            temp.setInfected(true);
            this.rightBoundary = temp;
            return;
        }
    }

    //Contain virus
    public void containOneStep() {
        Device l = this.getLeftBoundary();
        Device r = this.getRightBoundary();

        if(l == null) {
            System.out.println("No infected node");
            return;
        }

        boolean canLeft = l.canSpreadLeft();
        boolean canRight = r.canSpreadRight();

        if(!canLeft && !canRight) {
            System.out.println("Virus contained");
            return;
        }

        if(canRight && !canLeft) {
            //Break the link between right boundary and its next link
            Device temp = r.getNext();
            this.breakLink(r, temp);
            return;
        }

        if(canLeft && !canRight) {
            //Break the link between left boundary and its previous link
            Device temp = l.getPrev();
            this.breakLink(l, temp);
            return;
        }

        //Break the boundary that will isolate more number of device
        int leftDevices = l.getDeviceId() - 1;
        int rightDevices = this.getSize() - r.getDeviceId();

        if(leftDevices >= rightDevices) {
            //Break the link between left boundary and its previous link
            Device temp = l.getPrev();
            this.breakLink(l, temp);
        } else {
            //Break the link between right boundary and its next link
            Device temp = r.getNext();
            this.breakLink(r, temp);
        }

    }

    //Complete simulation step
    public void simulate() {
        while(canSpread()) {
            containOneStep();
            spreadNextStep();
            displayNetwork();
            System.out.println();
        }

        System.out.println("Simulation finished.");
        System.out.println("Safe devices: " + countSafeDevices());
        System.out.println("Infected devices: " + (getSize() - countSafeDevices()));
    }
    
}
