package com.elevator.car;

public class StandardElevatorCar extends ElevatorCar {
    private int totalFloors;

    public StandardElevatorCar(int totalFloors, double weightLimit) {
        super(totalFloors, weightLimit);
        this.totalFloors = totalFloors;
    }

    @Override
    public boolean canServiceFloor(int floor) {
        if (floor >= 1) {
            if (floor <= totalFloors) {
                return true;
            }
        }
        return false;
    }
}
