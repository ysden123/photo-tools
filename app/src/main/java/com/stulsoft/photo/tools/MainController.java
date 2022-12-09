/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools;

import com.stulsoft.photo.slib.utils.SystemUtils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
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
        SystemUtils.shutdown();
        Platform.exit();
    }
}
