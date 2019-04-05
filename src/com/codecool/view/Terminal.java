package com.codecool.view;

import com.codecool.model.*;

import java.util.List;

public class Terminal {

    public static void buildingCreationMessage(int nrOfFloors, int nrOfElevators) {
        System.out.println("Building: " + nrOfFloors + " floors and " + nrOfElevators + " elevators. Here the game begins...\n");
    }

    public static void confirmTaskAssignmentToElevator(Elevator elevator, Task task) {
        if (task.hasToLoad()) {
            System.out.println("Elevator: " + elevator.getName() + " on floor " + elevator.getFloor().getFloorNumber() +
                    ", task => " + task.getDestinationFloorNumber() +
                    " to take a person.");
        } else {
            System.out.println("Elevator:" + elevator.getName() + " loads person on floor " + elevator.getFloor().getFloorNumber() +
                    "and is to transport a person to " + task.getDestinationFloorNumber() +
                    " .");
        }
    }

    public static void personCreationMessage(int floor, int destinationFloor) {
        System.out.println("Created person " + floor + " => " + destinationFloor);
    }

    public static void personCreationMessage(int floor, Person person) {
        System.out.println("Created person " + person.getName() + " on " + floor + " floor with destination floor " + person.getDestinationFloor() + ".");
    }

    public static void personLoadMessage(Elevator elevator, Person person) {
        System.out.println("Person " + person.getName() + " onboarded on floor " + elevator.getFloor().getFloorNumber() + " by " + elevator.getName() + ".");
    }

    public static void print(Task task) {
        if (task == null) {
            System.out.print("null");
            return;
        }
        System.out.print("[" + task.getDestinationFloorNumber());
        if (task.hasToLoad()) {
            System.out.print(", load], ");
        } else {
            System.out.print(", unload], ");
        }
    }

    private static void print(Person[] people) {
        System.out.print("People:");
        for (Person person : people) {
            System.out.print(" " + person.getDestinationFloor());
        }
        System.out.println();
    }

    public static void showElevator(Elevator elevator) {
        System.out.println(elevator.getName() + " on " + elevator.getFloor().getFloorNumber() + " floor, direction:" + elevator.getCurrentDirection());
        System.out.print("Tasks: ");
        print(elevator.getPeople());
        System.out.println("#############################################################");
    }

    private static String getQueueString(List<Person> queue) {
        StringBuilder out = new StringBuilder();
        for (Person person : queue) {
            out.append(person.getName()).append(", ");
        }
        return out.toString();
    }

    private static void showFloor(Floor floor) {
        System.out.println("\tFloor " + floor.getFloorNumber() + " Up: " + getQueueString(floor.getUpQueue()) +
                " | Down: " + getQueueString(floor.getDownQueue()) + " | Transported: " + getQueueString(floor.getTransportedPeople()));
    }

    public static void showFloors() {
        Building kolejowa = Building.getBuilding();
        Floor[] floors = kolejowa.getFloors();
        for (int i = floors.length - 1; i >= 0; i--) {
            for (Elevator elevator : kolejowa.getElevators()) {
                if (elevator.getFloor().getFloorNumber() == floors[i].getFloorNumber())
                    System.out.print("#");
            }
            showFloor(floors[i]);
        }
    }

    public static void transportedPersonMessage(Person person, Elevator elevator) {
        System.out.println("Person " + person.getName() + " successfully transported to floor " + person.getDestinationFloor() + " by " + elevator.getName() + ".");
    }
}
