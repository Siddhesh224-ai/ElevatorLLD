package com.elevator.panel;

import com.elevator.button.FloorButton;
import com.elevator.button.AlarmButton;
import com.elevator.button.OpenButton;
import com.elevator.button.CloseButton;
import com.elevator.exception.InvalidFloorException;

import java.util.ArrayList;
import java.util.List;

public class InsidePanel {
    private List<FloorButton> floorButtons;
    private AlarmButton alarmButton;
    private OpenButton openButton;
    private CloseButton closeButton;

    public InsidePanel(int totalFloors) {
        this.floorButtons = new ArrayList<>();
        for (int i = 1; i <= totalFloors; i++) {
            this.floorButtons.add(new FloorButton(i));
        }
        this.alarmButton = new AlarmButton();
        this.openButton = new OpenButton();
        this.closeButton = new CloseButton();
    }

    public void pressFloor(int floor) {
        boolean found = false;
        for (FloorButton button : floorButtons) {
            if (button.getTargetFloor() == floor) {
                button.press();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new InvalidFloorException("Floor " + floor + " does not exist in this building.");
        }
    }

    public void pressAlarm() {
        this.alarmButton.press();
    }

    public void pressOpen() {
        this.openButton.press();
    }

    public void pressClose() {
        this.closeButton.press();
    }
}
