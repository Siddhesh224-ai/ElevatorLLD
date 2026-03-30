package com.elevator.strategy;

import com.elevator.model.Request;
import com.elevator.car.ElevatorCar;
import com.elevator.enums.ElevatorStatus;
import com.elevator.exception.NoCarAvailableException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OptimisedStrategy implements ElevatorStrategy {
    private Map<ElevatorCar, Integer> carDestinations = new HashMap<>();

    @Override
    public ElevatorCar dispatch(Request request, List<ElevatorCar> cars) {
        ElevatorCar bestCar = null;
        int minDistance = Integer.MAX_VALUE;

        for (ElevatorCar car : cars) {
            if (car.getStatus() == ElevatorStatus.STATIONARY) {
                int distance = Math.abs(car.getCurrentFloor() - request.getOriginFloor());
                if (distance < minDistance) {
                    minDistance = distance;
                    bestCar = car;
                }
            }
        }

        if (bestCar != null) {
            carDestinations.put(bestCar, request.getOriginFloor());
            return bestCar;
        }

        for (ElevatorCar car : cars) {
            if (car.getStatus() != ElevatorStatus.UNDER_MAINTENANCE) {
                carDestinations.put(car, request.getOriginFloor());
                return car;
            }
        }

        throw new NoCarAvailableException("No elevator car available to handle the request.");
    }

    @Override
    public boolean shouldStopAt(int floor, ElevatorCar car) {
        Integer destination = carDestinations.get(car);
        if (destination == null) {
            return false;
        }

        if (car.getStatus() == ElevatorStatus.MOVING_UP) {
            if (floor > car.getCurrentFloor()) {
                if (floor < destination) {
                    return true;
                }
            }
        }

        if (car.getStatus() == ElevatorStatus.MOVING_DOWN) {
            if (floor < car.getCurrentFloor()) {
                if (floor > destination) {
                    return true;
                }
            }
        }

        return false;
    }
}
