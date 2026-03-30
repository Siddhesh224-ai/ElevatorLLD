package com.elevator.button;

public abstract class Button {
    private boolean isPressed = false;

    public void press() {
        this.isPressed = true;
        this.onPress();
    }

    public void reset() {
        this.isPressed = false;
    }

    public abstract void onPress();

    public boolean isPressed() {
        return isPressed;
    }
}
