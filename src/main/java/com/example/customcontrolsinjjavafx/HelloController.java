package com.example.customcontrolsinjjavafx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    public void JKD(ActionEvent actionEvent) {
        JDKButtler coche = new JDKButtler();
        Stage stage = new Stage();
        try {
            coche.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}