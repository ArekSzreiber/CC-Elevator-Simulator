package com.codecool.controller;

import com.codecool.model.*;

import java.util.LinkedList;
import java.util.Random;

public class ElevatorSimulator {

    public static void generatePerson() {//todo podzielić na część robiącą ludzi i kontroler
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

    public static void main(String[] args) {
        Building.createBuilding();
        PeopleSpawner peopleSpawner;
        Thread spawnThread;

        peopleSpawner = new PeopleSpawner(5, 11);
        spawnThread = new Thread(peopleSpawner);
        spawnThread.run();
        System.out.println("Done");

        peopleSpawner = new PeopleSpawner(5000, 1);
        spawnThread = new Thread(peopleSpawner);
        spawnThread.run();
        System.out.println("Done");

        peopleSpawner = new PeopleSpawner(20, 5);
        spawnThread = new Thread(peopleSpawner);
        spawnThread.run();
        System.out.println("Done");
    }
}
