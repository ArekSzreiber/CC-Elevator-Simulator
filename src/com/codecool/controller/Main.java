package com.codecool.controller;

import com.codecool.model.Building;
import com.codecool.model.PeopleSpawner;
import com.codecool.view.BuildingView;
import com.codecool.view.ElevatorView;
import com.codecool.view.FloorView;
import com.codecool.view.PersonView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    private static void prepareWindow(Stage stage) {
        stage.setTitle("Elevator Simulator");
        Pane layout = new Pane();
        Scene scene = new Scene(layout, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        BuildingView buildingView = new BuildingView(layout, Building.getBuilding());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {

        Building.createBuilding();
        prepareWindow(stage);

        PeopleSpawner peopleSpawner;
        Thread spawnThread;

        peopleSpawner = new PeopleSpawner(20000, 1);
        spawnThread = new Thread(peopleSpawner);
        spawnThread.run();

        peopleSpawner = new PeopleSpawner(100, 10);
        spawnThread = new Thread(peopleSpawner);
        spawnThread.run();

    }
}
