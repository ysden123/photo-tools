/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools;

import com.stulsoft.photo.slib.utils.SystemUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.JarURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public class App extends Application {
    private static final String DEFAULT_PROPERTY_VALUE = "Unavailable";

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Photo Tools. " + findVersion());
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> SystemUtils.shutdown());
        IconUtils.setIcon(primaryStage);
        primaryStage.show();
    }

    private String findVersion() {
        String version = "Version: ";
        String className = getClass().getSimpleName() + ".class";
        try {
            String classPath = Objects.requireNonNull(getClass().getResource(className)).toString();
            if (!classPath.startsWith("jar")) {
                version = version.concat(DEFAULT_PROPERTY_VALUE);
            } else {
                URL url = new URL(classPath);
                JarURLConnection jarConnection = (JarURLConnection) url.openConnection();
                Manifest manifest = jarConnection.getManifest();
                Attributes attributes = manifest.getMainAttributes();
                var theVersion = attributes.getValue("Implementation-Version");
                if (theVersion == null) {
                    theVersion = DEFAULT_PROPERTY_VALUE;
                }
                version = version.concat(theVersion);
            }
        } catch (Exception exception) {
            version = version.concat(DEFAULT_PROPERTY_VALUE);
            exception.printStackTrace();
        }
        return version;
    }
}
