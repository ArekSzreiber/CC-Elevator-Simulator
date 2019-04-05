package com.codecool.model;

public class Task {
    private int destinationFloorNumber;
    private boolean loadPerson;
    //TODO żeby task wiedział też ile osób przewozi
    //albo jakie dokładnie osoby ma zbindowane
    public boolean hasToLoad() {
        return loadPerson;
    }

    public Task(int destinationFloorNumber, boolean loadPerson){
        this.destinationFloorNumber = destinationFloorNumber;
        this.loadPerson = loadPerson;
    }

    public Task(Person passenger){
        this.destinationFloorNumber = passenger.getDestinationFloor();
        this.loadPerson = false;
    }

    public int getDestinationFloorNumber() {
        return destinationFloorNumber;
    }

}
