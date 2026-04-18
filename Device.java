//NetShield - Topology Based Virus Detection and Isolation System

import java.util.Objects;

public class Device {
    private int deviceId;
    private boolean isInfected;
    private boolean leftLinkBroken;
    private boolean rightLinkBroken;
    private Device prev;
    private Device next;

    //Constructor
    public Device(int deviceId) {
        this.deviceId = deviceId;
        this.isInfected = false;
        this.leftLinkBroken = false;
        this.rightLinkBroken = false;
        this.next = null;
        this.prev = null;
    }

    //Getters
    public int getDeviceId() {
        return this.deviceId;
    }

    public boolean isInfected() {
        return this.isInfected;
    }

    public boolean leftLinkBroken() {
        return this.leftLinkBroken;
    }

    public boolean rightLinkBroken() {
        return this.rightLinkBroken;
    }

    public Device getNext() {
        return this.next;
    }

    public Device getPrev() {
        return this.prev;
    }

    //Setters
    public void setInfected(boolean infected) {
        this.isInfected = infected;
    }

    public void setLeftLinkBroken(boolean broken) {
        this.leftLinkBroken = broken;
    }

    public void setRightLinkBroken(boolean broken) {
        this.rightLinkBroken = broken;
    }

    public void setNext(Device next) {
        this.next = next;
    }

    public void setPrev(Device prev) {
        this.prev = prev;
    }

    //Utility methods
    public boolean canSpreadLeft() {
        return prev != null && !leftLinkBroken;
    }

    public boolean canSpreadRight() {
        return next != null && !rightLinkBroken;
    }

    //Overriding toString
    @Override
    public String toString() {
        return "Device " + deviceId + (isInfected ? " [INFECTED]" : " [SAFE]");
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)return true;
        if(!(o instanceof Device))return false;
        Device d = (Device)o;
        return deviceId == d.deviceId;
    }
    
}
