package com.codecool.view;

import com.codecool.controller.Config;
import com.codecool.model.Elevator;
import com.codecool.model.Floor;
import com.codecool.model.Person;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.LinkedBlockingQueue;

public class ElevatorView extends Rectangle implements PropertyChangeListener {
    private Floor floor;
    private int passengersNumber;

    public ElevatorView(Floor floor) {
        super(Config.ELEVATOR_WIDTH, Config.FLOOR_HEIGHT, Color.AQUAMARINE);
        this.floor = floor;
        passengersNumber = 0;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("floor")) {//todo to zmienić na switch
            this.setFloor((Floor) evt.getNewValue());
            int floorNumber = floor.getFloorNumber();
            this.setTranslateY(floorNumber * Config.FLOOR_HEIGHT);
        } else if (evt.getPropertyName().equals("passengersNumber")) {
            this.passengersNumber = (int) evt.getNewValue();
            switch (passengersNumber) {
                case 0:
                    this.setFill(Color.AQUAMARINE);
                    break;
                case 1:
                    this.setFill(Color.GREEN);
                    break;
                case 2:
                    this.setFill(Color.YELLOW);
                    break;
                case 3:
                    this.setFill(Color.RED);
                    break;
                default:
                    this.setFill(Color.WHITESMOKE);
            }
        }
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
