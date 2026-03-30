package com.elevator;

import com.elevator.car.ElevatorCar;
import com.elevator.car.StandardElevatorCar;
import com.elevator.panel.OutsidePanel;
import com.elevator.strategy.ElevatorStrategy;
import com.elevator.emergency.EmergencyEvent;
import com.elevator.emergency.BasicEmergencyHandler;
import com.elevator.enums.Direction;
import com.elevator.model.Request;
import com.elevator.exception.InvalidFloorException;

import java.util.List;
import java.util.ArrayList;

public class ElevatorSystem {
    private static ElevatorSystem instance;

    private List<ElevatorCar> cars;
    private List<OutsidePanel> outsidePanels;
    private ElevatorStrategy strategy;
    private int totalFloors;

    private ElevatorSystem(int totalFloors, int numberOfCars, ElevatorStrategy strategy) {
        this.totalFloors = totalFloors;
        this.strategy = strategy;
        this.cars = new ArrayList<>();
        this.outsidePanels = new ArrayList<>();

        for (int i = 0; i < numberOfCars; i++) {
            ElevatorCar car = new StandardElevatorCar(totalFloors, 500.0);
            car.registerHandler(new BasicEmergencyHandler());
            this.cars.add(car);
        }

        for (int i = 1; i <= totalFloors; i++) {
            this.outsidePanels.add(new OutsidePanel(i));
        }
    }

    public static ElevatorSystem getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ElevatorSystem has not been initialised yet.");
        }
        return instance;
    }

    public static ElevatorSystem initialise(int totalFloors, int numberOfCars, ElevatorStrategy strategy) {
        if (instance == null) {
            instance = new ElevatorSystem(totalFloors, numberOfCars, strategy);
        }
        return instance;
    }

    public void handleOutsideRequest(int floor, Direction direction) {
        OutsidePanel panel = getOutsidePanelForFloor(floor);
        if (panel == null) {
            throw new InvalidFloorException("Floor " + floor + " does not exist in this building.");
        }
        Request request = null;
        if (direction == Direction.UP) {
            request = panel.pressUp();
        } else if (direction == Direction.DOWN) {
            request = panel.pressDown();
        }
        ElevatorCar assignedCar = strategy.dispatch(request, cars);
        System.out.println("Car assigned to floor " + floor + " request.");
        assignedCar.moveToFloor(floor);
    }

    public void handleInsideRequest(ElevatorCar car, int targetFloor) {
        if (targetFloor < 1) {
            throw new InvalidFloorException("Floor " + targetFloor + " does not exist in this building.");
        }
        if (targetFloor > totalFloors) {
            throw new InvalidFloorException("Floor " + targetFloor + " does not exist in this building.");
        }
        car.getInsidePanel().pressFloor(targetFloor);
        System.out.println("Inside request: car moving to floor " + targetFloor + ".");
        car.moveToFloor(targetFloor);
    }

    public void triggerEmergency(EmergencyEvent event) {
        System.out.println("SYSTEM: Emergency triggered \u2014 " + event.getDescription());
        for (ElevatorCar car : cars) {
            car.onEmergency(event);
        }
    }

    public ElevatorCar getCarAtIndex(int index) {
        return cars.get(index);
    }

    public OutsidePanel getOutsidePanelForFloor(int floor) {
        for (OutsidePanel panel : outsidePanels) {
            if (panel.getFloor() == floor) {
                return panel;
            }
        }
        return null;
    }
}
