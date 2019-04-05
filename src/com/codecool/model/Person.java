package com.codecool.model;

import java.util.Random;

public class Person {
    private final int destinationFloor;
    private final String name;
    private static String[] names = {"Maciek", "Józek", "Antek", "Janek", "Krzychu", "Marian", "Ferdek", "Walduś", "Halina", "Grażyna", "Hela",
            "Jaćka", "Heniek", "Stasek", "Franek", "Zbynek", "Zenek", "Brygida", "Kuba", "Klemens", "Eustachy", "Edward",
            "Eugeniusz", "Fryderyk", "Gniewomir", "Gustaw", "Gżegoż", "Irek", "Jerzy", "Julian", "Kajetan", "Konstanty",
            "Kryspin", "Krzesimir", "Kondrad", "Kazek", "Leopold", "Leszek", "Leopold", "Maurycy", "Mieszko", "Arek", "Karol"};

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public String getName() {
        return name;
    }

    private String getRandomName() {
        Random random = new Random();
        return names[random.nextInt(names.length)];
    }

    public Task getUnloadingTask() {
        return new Task(this);
    }

    public Person(int destinationFloor) {
        this.destinationFloor = destinationFloor;
        this.name = getRandomName();

    }
}
