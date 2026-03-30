package com.elevator.strategy;

import com.elevator.model.Request;
import com.elevator.car.ElevatorCar;
import com.elevator.enums.ElevatorStatus;
import com.elevator.exception.NoCarAvailableException;
import java.util.List;

public class FCFSStrategy implements ElevatorStrategy {

    @Override
    public ElevatorCar dispatch(Request request, List<ElevatorCar> cars) {
        for (ElevatorCar car : cars) {
            if (car.getStatus() == ElevatorStatus.STATIONARY) {
                return car;
            }
        }
        throw new NoCarAvailableException("No elevator car available to handle the request.");
    }

    @Override
    public boolean shouldStopAt(int floor, ElevatorCar car) {
        return false;
    }
}
