/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Photo Tools. " + findVersion());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String findVersion() {
        String version = "Version: ";
        String implementationVersion = System.getProperty("implementationVersion");
        return version.concat(implementationVersion == null ? "Unavailable" : implementationVersion);
    }
}
