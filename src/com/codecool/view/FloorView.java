package com.codecool.view;

import com.codecool.controller.Config;
import com.codecool.model.Floor;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

public class FloorView extends HBox {
    private Floor floor;

    public FloorView(Floor floor) {
        super(10);
        //this.setBorder(new Border(new BorderStroke()));
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");
        this.setHeight(Config.FLOOR_HEIGHT);
        this.setPrefHeight(Config.FLOOR_HEIGHT);
        this.setWidth(400);//TODO poprawić to na responsywną szerokość
        this.setPrefWidth(400);
        this.floor = floor;
    }
}
