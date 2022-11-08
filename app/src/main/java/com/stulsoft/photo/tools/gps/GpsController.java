/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools.gps;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GpsController  implements Initializable {
    private File lastDirectory = null;

    @FXML
    private TextField path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void onShow() {
        if (path.getText().isEmpty()){
            return;
        }

        var error = ShowOnMap.showOnMap(path.getText());
        error.ifPresent(msg -> {
            var alert = new Alert(Alert.AlertType.WARNING, msg);
            alert.setTitle("A problem with image file");
            alert.setHeaderText(null);
            alert.show();
        });
    }

    public void onFileSelect(MouseEvent event) {
        var fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.tif", "*.dng")
        );
        if (lastDirectory != null) {
            fileChooser.setInitialDirectory(lastDirectory);
        }
        var node = (Node) event.getSource();
        var parentScene = node.getScene();
        File selectedFile = fileChooser.showOpenDialog(parentScene.getWindow());
        if (selectedFile != null) {
            lastDirectory = selectedFile.getParentFile();
            path.setText(selectedFile.getAbsolutePath());
        }
    }
}
