/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private File lastDirectory = null;

    @FXML
    private TextField path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onGps() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gps/gps.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Show image on map");
            stage.setScene(scene);
            IconUtils.setIcon(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onEmptyDirs() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("emptydir/empty-dirs.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Find empty directories");
            stage.setScene(scene);
            IconUtils.setIcon(stage);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void onQuit() {
        Platform.exit();
    }
}
