package com.elevator.model;

public class Door {
    private boolean isOpen = false;

    public void open() {
        this.isOpen = true;
        System.out.println("Door opened.");
    }

    public void close() {
        this.isOpen = false;
        System.out.println("Door closed.");
    }

    public boolean isOpen() {
        return isOpen;
    }
}
