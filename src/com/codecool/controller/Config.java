package com.codecool.controller;

public class Config {
    public final static int FLOORS_NUMBER = 7;
    public final static int ELEVATORS_NUMBER = 4;
    public final static int WINDOW_HEIGHT = 740;
    public final static int WINDOW_WIDTH = 1300;
    public final static int ELEVATOR_CAPACITY = 3;
    public final static int PERSON_WIDTH = 30;
    public final static double FLOOR_HEIGHT = (double)WINDOW_HEIGHT / FLOORS_NUMBER;
    public final static double ELEVATOR_WIDTH = PERSON_WIDTH * ELEVATOR_CAPACITY;
}
