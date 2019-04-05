package com.codecool.model;

import com.codecool.controller.Config;
import com.codecool.view.Terminal;

import java.util.LinkedList;

public class Building {
    private final Elevator[] elevators;
    private final Floor[] floors;
    private static Building thisBuilding;

    public Floor[] getFloors() {
        return floors;
    }

    public Elevator[] getElevators() {
        return elevators;
    }

    public static Building getBuilding() {
        return thisBuilding;
    }

    public static void createBuilding() {
        if (thisBuilding == null) {
            thisBuilding = new Building(Config.FLOORS_NUMBER, Config.ELEVATORS_NUMBER);
        } else {
            System.out.println("Building already exists!");
        }
    }

    private Building(int nrOfFloors, int nrOfElevators) {
        floors = new Floor[nrOfFloors];
        for (int i = 0; i < nrOfFloors; i++) {
            floors[i] = new Floor(i);
        }
        elevators = new Elevator[nrOfElevators];
        for (int i = 0; i < nrOfElevators; i++) {
            elevators[i] = new Elevator(floors[0], i);
        }
        //Terminal.buildingCreationMessage(nrOfFloors, nrOfElevators);
    }

    public Floor getLowerFloor(Floor floor) {
        if (floors[floor.getFloorNumber() - 1] == null) {
            return floors[floor.getFloorNumber()];
        } else {
            return floors[floor.getFloorNumber() - 1];
        }
    }

    public Floor getHigherFloor(Floor floor) {
        return floors[floor.getFloorNumber() + 1];
    }//zaebpieczyc jw.



    public LinkedList<Elevator> getAvailableElevators(int destinationFloorNumber) {
        LinkedList<Elevator> availableElevators = new LinkedList<>();
        for (Elevator elevator : elevators) {
            if (elevator.isAvailable(destinationFloorNumber)) {
                availableElevators.add(elevator);
            }
        }
        return availableElevators;
    }

    public Elevator getElevatorWithSmallestNumberOfTasks() {
        int smallestNumberOfTasks = Integer.MAX_VALUE;
        Elevator leastBusy = elevators[0];
        for (Elevator elevator : elevators) {
            int numberOfTasks = elevator.getNumberOfTasks();
            if (numberOfTasks < smallestNumberOfTasks) {
                smallestNumberOfTasks = numberOfTasks;
                leastBusy = elevator;
            }
        }
        return leastBusy;
    }

    public Elevator getClosestElevator(LinkedList<Elevator> availableElevators, Task task) {
        int distanceToClosestElevator = Integer.MAX_VALUE;
        Elevator theClosestElevator = availableElevators.get(0);
        for (Elevator elevator : availableElevators) {
            int distanceFromPersonToElevator = Math.abs(task.getDestinationFloorNumber() - elevator.getFloor().getFloorNumber());
            if (distanceFromPersonToElevator < distanceToClosestElevator) {
                distanceToClosestElevator = distanceFromPersonToElevator;
                theClosestElevator = elevator;
            }
        }
        return theClosestElevator;
    }

}
