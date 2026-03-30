package com.elevator;

import com.elevator.strategy.FCFSStrategy;
import com.elevator.strategy.OptimisedStrategy;
import com.elevator.strategy.ElevatorStrategy;
import com.elevator.enums.Direction;
import com.elevator.exception.InvalidFloorException;
import com.elevator.car.ElevatorCar;
import com.elevator.model.Request;
import com.elevator.enums.RequestType;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ElevatorSystem system = ElevatorSystem.initialise(10, 3, new FCFSStrategy());

        System.out.println("--- step ---");
        system.handleOutsideRequest(3, Direction.UP);

        System.out.println("--- step ---");
        ElevatorCar activeCar = system.getCarAtIndex(0);
        system.handleInsideRequest(activeCar, 7);

        System.out.println("--- step ---");
        system.handleOutsideRequest(1, Direction.UP);

        System.out.println("--- step ---");
        system.getCarAtIndex(1).getInsidePanel().pressAlarm();

        System.out.println("--- step ---");
        try {
            system.handleInsideRequest(system.getCarAtIndex(0), 99);
        } catch (InvalidFloorException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        System.out.println("--- step ---");
        ElevatorStrategy newStrategy = new OptimisedStrategy();
        List<ElevatorCar> allCars = new ArrayList<>();
        allCars.add(system.getCarAtIndex(0));
        allCars.add(system.getCarAtIndex(1));
        allCars.add(system.getCarAtIndex(2));
        
        Request req = new Request(8, Direction.DOWN, RequestType.OUTSIDE);
        ElevatorCar dispatchedCar = newStrategy.dispatch(req, allCars);
        System.out.println("With OptimisedStrategy, manual dispatch assigned car currently at floor " + dispatchedCar.getCurrentFloor());

        System.out.println("--- step ---");
        for (int i = 0; i < 3; i++) {
            ElevatorCar c = system.getCarAtIndex(i);
            System.out.println("Car " + i + " is at floor " + c.getCurrentFloor() + " with status " + c.getStatus());
        }
    }
}
