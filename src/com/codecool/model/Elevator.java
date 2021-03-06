package com.codecool.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.util.concurrent.LinkedBlockingQueue;


public class Elevator implements Runnable {
    private static final int CAPACITY = 3;
    private static final int SPEED = 100;
    private Floor floor;
    private String name;
    private LinkedBlockingQueue<Task> newTasks = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Person> people = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Task> tasks = new LinkedBlockingQueue<>();
    private java.lang.Thread thread;

    private PropertyChangeSupport support;

    //TODO add elevatorState for getting available elevators
    public void activate() {
        if (!thread.isAlive()) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public void addTask(Task task) {
        newTasks.add(task);
    }

    public Elevator(Floor floor, int elevatorNumber) {
        this.floor = floor;
        this.name = "Elevator" + elevatorNumber;
        this.thread = new Thread("thread" + name);
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public Direction getCurrentDirection() {
        int currentFloorNumber = this.floor.getFloorNumber();
        Task currentTask = getCurrentTask();
        if (currentTask != null) {
            int nextFloorNumber = currentTask.getDestinationFloorNumber();
            if (nextFloorNumber < currentFloorNumber) {
                return Direction.GOING_DOWN;
            } else if (nextFloorNumber > currentFloorNumber) {
                return Direction.GOING_UP;
            } else {
                return Direction.WAITING;
            }
        } else {
            return Direction.WAITING;
        }
    }

    private Task getCurrentTask() {
        takeNewTask();
        if (hasFreeSpace()) {
            for (Task task : tasks) {
                if (!task.hasToLoad()) {
                    return task;
                }
            }
        }
        return tasks.peek();
    }

    public Floor getFloor() {
        return floor;
    }

    public String getName() {
        return name;
    }

    private Direction getNewTaskDirection(int destinationFloorNumber) {
        int currentFloorNumber = this.floor.getFloorNumber();
        if (destinationFloorNumber < currentFloorNumber) {
            return Direction.GOING_DOWN;
        } else if (destinationFloorNumber > currentFloorNumber) {
            return Direction.GOING_UP;
        } else {
            return Direction.WAITING;
        }
    }

    public int getNumberOfTasks() {
        return tasks.size();
    }

    public Person[] getPeople() {
        return people.toArray(new Person[0]);
    }

    private void handleTask() {
        Direction currentDirection = getCurrentDirection();
        moveOneStep(currentDirection);
        unloadPeople();
        loadPeople(currentDirection);
    }

    private boolean hasTasksOrPassengers() {
        return !tasks.isEmpty() || !people.isEmpty();
    }

    private boolean hasFreeSpace() {
        return people.size() < CAPACITY;
    }

    private boolean hasLoadingTasks(int floorNumber) {
        for (Task task : tasks) {
            if (task.hasToLoad() && task.getDestinationFloorNumber() == floorNumber) {
                return true;
            }
        }
        return false;
    }

    private int getNumberOfLoadingTasks() {
        int counter = 0;
        for (Task task : tasks) {
            if (task.hasToLoad()) {
                counter++;
            }
        }
        return counter;
    }

    private boolean hasFreeSlots() {
        return (people.size() + getNumberOfLoadingTasks()) < CAPACITY;
    }

    boolean isAvailable(int destinationFloorNumber) {
        Direction newTaskDirection = getNewTaskDirection(destinationFloorNumber);
        return (hasFreeSlots() && newTaskDirection.equals(getCurrentDirection()))
                || tasks.isEmpty();
    }

    public boolean isOperating() {
        if (thread != null) {
            return thread.isAlive();
        } else {
            return false;
        }
    }

    private void loadPeople(Direction elevatorDirection) {
        Person newPassenger;
        Task newTask;
        while (hasFreeSpace() && !floor.isEmpty(elevatorDirection)) {
            newPassenger = loadPerson(elevatorDirection);
            removeLoadingTask(floor.getFloorNumber());
            newTask = newPassenger.getUnloadingTask();
            addTask(newTask);
        }
        while (hasLoadingTasks(floor.getFloorNumber()) && floor.isEmpty() && Direction.WAITING.equals(elevatorDirection)) {
            removeLoadingTask(floor.getFloorNumber());
        }
    }

    private Person loadPerson(Direction direction) {
        Person newPassenger;
        if (Direction.GOING_UP.equals(direction)) {
            newPassenger = floor.popPersonFromUpQueue();
        } else if (Direction.GOING_DOWN.equals(direction)) {
            newPassenger = floor.popPersonFromDownQueue();
        } else {
            newPassenger = floor.popPersonFromAnyQueue();
        }
        if (newPassenger != null) {
            support.firePropertyChange("passengersNumber", people.size(), people.size() + 1);
            people.add(newPassenger);
        }
        return newPassenger;
    }

    private void moveOneStep(Direction current) {//todo when elevator has to change its level by n, it should move to it directly and moving time should be n times longer
        Floor previousFloor = floor;
        try {
            Thread.sleep(SPEED);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (Direction.GOING_UP.equals(current)) {
            floor = Building.getBuilding().getHigherFloor(floor);
        } else if (Direction.GOING_DOWN.equals(current)) {
            floor = Building.getBuilding().getLowerFloor(floor);
        }
        support.firePropertyChange("floor", previousFloor, floor);
    }

    public void unloadPeople() {
        Floor currentFloor = this.getFloor();
        int oldPassengersNumber = people.size();
        for (Person person : people) {
            if (person.getDestinationFloor() == currentFloor.getFloorNumber()) {
                people.remove(person);
                currentFloor.addToTransportedPeople(person);
                support.firePropertyChange("passengersNumber", oldPassengersNumber, people.size());
            }
        }
        removeUnloadingTasks(currentFloor.getFloorNumber());
    }

    private void removeUnloadingTasks(int floorNumber) {
        for (Task task : tasks) {
            if (!task.hasToLoad() && task.getDestinationFloorNumber() == floorNumber) {
                tasks.remove(task);
            }
        }
    }

    private void removeLoadingTask(int floorNumber) {
        for (Task task : tasks) {
            if (task.hasToLoad() && task.getDestinationFloorNumber() == floorNumber) {
                tasks.remove(task);
                return;
            }
        }
    }

    @Override
    public void run() {
        takeNewTask();
        while (hasTasksOrPassengers()) {
            handleTask();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void takeNewTask() {
        Task newTask = newTasks.poll();
        if (newTask != null) {
            tasks.add(newTask);
        }
    }

}
