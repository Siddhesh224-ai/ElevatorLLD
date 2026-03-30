package com.elevator.strategy;

import com.elevator.model.Request;
import com.elevator.car.ElevatorCar;
import java.util.List;

public interface ElevatorStrategy {
    ElevatorCar dispatch(Request request, List<ElevatorCar> cars);
    boolean shouldStopAt(int floor, ElevatorCar car);
}
