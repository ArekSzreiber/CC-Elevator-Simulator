package com.codecool.model;

import com.codecool.controller.ElevatorSimulator;

public class PeopleSpawner implements Runnable {
    private final int numberOfPeopleToCreate;
    private final int spawnInterval;//milliseconds

    public PeopleSpawner(int spawnInterval, int numberOfPeople){
        this.spawnInterval = spawnInterval;
        this.numberOfPeopleToCreate = numberOfPeople;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfPeopleToCreate; i++) {
            ElevatorSimulator.generatePerson();
            try {
                Thread.sleep(spawnInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
