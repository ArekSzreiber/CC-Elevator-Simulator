package com.codecool.model;

public class Task {
    private int destinationFloorNumber;
    private boolean loadPerson;

    //TODO it should know how many people it is carrying
    //or which person belongs to which task
    public boolean hasToLoad() {
        return loadPerson;
    }

    public Task(int destinationFloorNumber, boolean loadPerson) {
        this.destinationFloorNumber = destinationFloorNumber;
        this.loadPerson = loadPerson;
    }

    public Task(Person passenger) {
        this.destinationFloorNumber = passenger.getDestinationFloor();
        this.loadPerson = false;
    }

    public int getDestinationFloorNumber() {
        return destinationFloorNumber;
    }

}
