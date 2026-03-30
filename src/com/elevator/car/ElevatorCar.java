package com.elevator.car;

import com.elevator.panel.InsidePanel;
import com.elevator.model.Door;
import com.elevator.emergency.EmergencyHandler;
import com.elevator.emergency.EmergencyEvent;
import com.elevator.emergency.EmergencyEventListener;
import com.elevator.enums.ElevatorStatus;
import java.util.List;
import java.util.ArrayList;

public abstract class ElevatorCar implements EmergencyEventListener {
    private int currentFloor = 1;
    private ElevatorStatus status = ElevatorStatus.STATIONARY;
    private double weightLimit;
    private InsidePanel insidePanel;
    private Door door;
    private List<EmergencyHandler> emergencyHandlers;

    public ElevatorCar(int totalFloors, double weightLimit) {
        this.weightLimit = weightLimit;
        this.insidePanel = new InsidePanel(totalFloors);
        this.door = new Door();
        this.emergencyHandlers = new ArrayList<>();
    }

    public abstract boolean canServiceFloor(int floor);

    public void moveToFloor(int targetFloor) {
        if (this.status == ElevatorStatus.UNDER_MAINTENANCE) {
            System.out.println("Car is under maintenance, cannot move.");
            return;
        }
        if (this.door.isOpen()) {
            this.door.close();
        }
        System.out.println("Car moving from floor " + this.currentFloor + " to floor " + targetFloor + ".");
        int previousFloor = this.currentFloor;
        this.currentFloor = targetFloor;
        if (targetFloor > previousFloor) {
            this.status = ElevatorStatus.MOVING_UP;
        } else {
            this.status = ElevatorStatus.MOVING_DOWN;
        }
        this.status = ElevatorStatus.STATIONARY;
        System.out.println("Car arrived at floor " + targetFloor + ". Doors opening.");
        this.door.open();
    }

    @Override
    public void onEmergency(EmergencyEvent event) {
        this.status = ElevatorStatus.STATIONARY;
        this.door.open();
        System.out.println("Emergency received in elevator. Stopping and opening doors.");
        for (EmergencyHandler handler : emergencyHandlers) {
            if (handler.canHandle(event)) {
                handler.handle(event);
            }
        }
    }

    @Override
    public void registerHandler(EmergencyHandler handler) {
        this.emergencyHandlers.add(handler);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    public double getWeightLimit() {
        return weightLimit;
    }

    public InsidePanel getInsidePanel() {
        return insidePanel;
    }

    public Door getDoor() {
        return door;
    }
}
