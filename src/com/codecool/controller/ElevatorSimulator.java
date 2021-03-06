package com.codecool.controller;

import com.codecool.model.*;

import java.util.LinkedList;
import java.util.Random;

public class ElevatorSimulator {

    public static void generatePerson() {//todo divide into part for creating people and controller
        Random randomNumber = new Random();
        Building kolejowa = Building.getBuilding();
        int currentFloor = randomNumber.nextInt(kolejowa.getFloors().length);
        int destinationFloor = currentFloor;

        while (destinationFloor == currentFloor) {
            destinationFloor = randomNumber.nextInt(kolejowa.getFloors().length);
        }

        Person person = new Person(destinationFloor);
        kolejowa.getFloors()[currentFloor].addPersonToRelevantQueue(person);
        Task newTask = new Task(kolejowa.getFloors()[currentFloor].getFloorNumber(), true);
        assignTask(newTask);
    }

    private static void assignTask(Task task) {
        Building building = Building.getBuilding();
        LinkedList<Elevator> availableElevators = building.getAvailableElevators(task.getDestinationFloorNumber());
        Elevator theChosenElevator;
        if (availableElevators.size() > 0) {
            theChosenElevator = building.getClosestElevator(availableElevators, task);
        } else {
            theChosenElevator = building.getElevatorWithSmallestNumberOfTasks();
        }
        theChosenElevator.addTask(task);
        if (!theChosenElevator.isOperating()) {
            theChosenElevator.activate();
        }
    }
}
